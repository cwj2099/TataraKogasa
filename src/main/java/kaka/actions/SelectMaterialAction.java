package kaka.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import kaka.BasicMod;
import kaka.CustomTags;
import kaka.MaterialZoneManager;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SelectMaterialAction extends AbstractGameAction {
    private final Predicate<AbstractCard> predicate;
    private final Consumer<List<AbstractCard>> callback;
    private final String text;
    private final boolean anyNumber;
    private final boolean canPickZero;
    private final Map<AbstractCard, Origin> cardOriginMap = new HashMap<>();

    private enum Origin {
        HAND, MATERIAL
    }

    private final AbstractPlayer player;
    private final List<AbstractCard> tempHand = new ArrayList<>();

    public SelectMaterialAction(
            int amount,
            String textForSelect,
            boolean anyNumber,
            boolean canPickZero,
            Predicate<AbstractCard> cardFilter,
            Consumer<List<AbstractCard>> callback
    ) {
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.text = textForSelect;
        this.anyNumber = anyNumber;
        this.canPickZero = canPickZero;
        this.predicate = cardFilter;
        this.callback = callback;
        this.player = AbstractDungeon.player;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            List<AbstractCard> combined = new ArrayList<>();

            // 把手牌和素材栏符合条件的都加进来
            for (AbstractCard c : player.hand.group) {
                if (predicate.test(c)) {
                    combined.add(c);
                    cardOriginMap.put(c, Origin.HAND);
                } else {
                    tempHand.add(c);
                }
            }
            for (AbstractCard c : MaterialZoneManager.materials.group) {
                if (predicate.test(c)) {
                    combined.add(c);
                    cardOriginMap.put(c, Origin.MATERIAL);
                }
            }

            // 手牌中移除不参与选择的卡
            player.hand.group.removeAll(tempHand);

            if (combined.isEmpty()) {
                finish();
                return;
            }

            if (combined.size() <= amount && !anyNumber && !canPickZero) {
                callback.accept(new ArrayList<>(combined));
                finish();
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(text, amount, anyNumber, canPickZero);
            tickDuration();
        } else if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            List<AbstractCard> selected = AbstractDungeon.handCardSelectScreen.selectedCards.group;

            // 回归卡牌到原来的地方
            for (AbstractCard c : selected) {
                Origin origin = cardOriginMap.getOrDefault(c, Origin.HAND);
                if (origin == Origin.HAND) {
                    player.hand.addToTop(c);
                } else if (origin == Origin.MATERIAL) {
                    MaterialZoneManager.materials.addToTop(c);
                    c.tags.remove(CustomTags.ShallReturnMaterial);
                }
            }

            callback.accept(new ArrayList<>(selected));

            // 也要把未选择的卡放回原处
            player.hand.group.addAll(tempHand);
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            finish();
        } else {
            tickDuration();
        }
    }

    private void finish() {
        player.hand.refreshHandLayout();
        player.hand.applyPowers();
        isDone = true;
    }
}
