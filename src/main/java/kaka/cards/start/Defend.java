package kaka.cards.start;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import kaka.cards.BaseCard;
import kaka.character.MyCharacter;
import kaka.util.CardStats;

public class Defend extends BaseCard {

    public static final String ID = makeID(Defend.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.NONE,
            1
    );

    private static final int DEFEND = 5;
    private static final int UPG_DEFEND = 3;

    public Defend() {
        super(ID, info);
        tags.add(CardTags.STARTER_DEFEND);

        setBlock(DEFEND, UPG_DEFEND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
         this.addToBot(new GainBlockAction(p, p, block));
    }
}
