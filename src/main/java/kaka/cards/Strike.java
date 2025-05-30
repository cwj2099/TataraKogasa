package kaka.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import kaka.actions.SelectCardsInHandAction;
import kaka.character.MyCharacter;
import kaka.util.CardStats;

public class Strike extends BaseCard{

    public static final String ID = makeID(Strike.class.getSimpleName()); 
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.BASIC, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly, 
    //but constants at the top of the file are easy to adjust.
    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;

    public Strike() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        tags.add(CardTags.STARTER_STRIKE);
        tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hand.size() <= 2) {
            AbstractDungeon.actionManager.addToBottom(
                new DiscardAction(p, p, p.hand.size(), false)
            );
            return;
        }

        AbstractDungeon.actionManager.addToBottom(
            new SelectCardsInHandAction("Choose 2 cards to keep", 2, selectedCards -> {
                for(AbstractCard c : selectedCards){
                    AbstractDungeon.player.hand.moveToDiscardPile(c);
                }
            })
        );
    }

}
