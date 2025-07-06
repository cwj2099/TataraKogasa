package kaka.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static kaka.BasicMod.makeID;

import java.util.List;
import java.util.function.Consumer;

public class ChooseCardFromListAction extends AbstractGameAction {
    private static final String ID = makeID(ChooseCardFromListAction.class.getSimpleName());

    private final List<AbstractCard> choices;
    private boolean openedScreen = false;
    private Consumer<AbstractCard> callback;

    public ChooseCardFromListAction(List<AbstractCard> choices, Consumer<AbstractCard> callback) {
        this.callback = callback;
        this.choices = choices;
        this.duration = this.startDuration = 0.5f;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;

        if (!openedScreen) {
            openedScreen = true;
            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : choices) {
                group.addToTop(c.makeStatEquivalentCopy());
            }
            AbstractDungeon.gridSelectScreen.open(group, 1, "Choose one", false, false, false, false);
        } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard selected = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.player.hand.addToHand(selected);
            if(callback != null) callback.accept(selected);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            isDone = true;
        } else {
            tickDuration();
        }
    }
}
