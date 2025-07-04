package kaka.cards.gems;

import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import kaka.CustomTags;
import kaka.cards.BaseCard;
import kaka.character.MyCharacter;
import kaka.util.CardStats;

public class PurpleGem extends BaseCard{
    public static final String ID = makeID(PurpleGem.class.getSimpleName());
    public int minScry = 1;
    public int maxScry =3;

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.CURSE,
            CardRarity.COMMON,
            CardTarget.NONE,
            0
    );

    public PurpleGem() {
        super(ID, info);
        
        tags.add(CustomTags.Purple);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int scryAmount = AbstractDungeon.cardRandomRng.random(minScry, maxScry);
        AbstractDungeon.actionManager.addToBottom(new ScryAction(scryAmount));
    }
}
