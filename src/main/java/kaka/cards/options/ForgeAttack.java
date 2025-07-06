package kaka.cards.options;

import java.util.function.Consumer;


import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import kaka.cards.BaseCard;
import kaka.character.MyCharacter;
import kaka.util.CardStats;

public class ForgeAttack extends BaseCard {
    public static final String ID = makeID(ForgeAttack.class.getSimpleName());
  
    public Consumer<CardTags> callback;

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.NONE,
            0
    );
  
    public ForgeAttack() {
        super(ID, info);
    }

    public void assignCallback(Consumer<CardTags> callback){
        this.callback = callback;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m) {
        onChoseThisOption();
    }
    
    public void onChoseThisOption() {
        callback.accept(CardTags.STARTER_STRIKE);
    }
  
}
