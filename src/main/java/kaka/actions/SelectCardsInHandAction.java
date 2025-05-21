package kaka.actions;

import java.util.ArrayList;
import java.util.function.Consumer;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import kaka.BasicMod;

public class SelectCardsInHandAction extends AbstractGameAction {
    private Consumer<ArrayList<AbstractCard>> callback;
    private String message;
    private int amount;

    public SelectCardsInHandAction(String message, int amount, Consumer<ArrayList<AbstractCard>> callback) {
        this.amount = amount;
        this.callback = callback;
        this.message = message;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if (AbstractDungeon.player.hand.isEmpty()) {
                this.isDone = true;
                return;
            }

            BasicMod.cmSelection.open(message, amount, false, false);
            this.tickDuration();
            return;
        }

        if (!BasicMod.cmSelection.wereCardsRetrieved) {
            ArrayList<AbstractCard> selected = BasicMod.cmSelection.selectedCards.group;

            

            // 移除选中的卡，回到原位或弃牌等
            for (AbstractCard c : selected) {
                AbstractDungeon.player.hand.addToHand(c);
            }

            AbstractDungeon.player.hand.refreshHandLayout();
            BasicMod.cmSelection.wereCardsRetrieved = true;
            this.isDone = true;

            // 回调函数处理所选卡
            callback.accept(selected);
        }

        this.tickDuration();
    }
}

