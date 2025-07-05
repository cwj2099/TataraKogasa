package kaka.cards.start;


import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import kaka.MaterialZoneManager;
import kaka.cards.BaseCard;
import kaka.cards.gems.PurpleGem;
import kaka.cards.gems.RedGem;
import kaka.character.MyCharacter;
import kaka.util.CardStats;

public class MaterialPick extends BaseCard {

    public static final String ID = makeID(MaterialPick.class.getSimpleName());
    AbstractPlayer currentPlayer;

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.NONE,
            0
    );

    public MaterialPick() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        MaterialZoneManager.addMaterial(new PurpleGem()); 
        MaterialZoneManager.addMaterial(new RedGem());
    }
}
