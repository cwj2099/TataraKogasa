package kaka.cards.start;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;
import kaka.actions.SelectMaterialAction;
import kaka.cards.BaseCard;
import kaka.character.MyCharacter;
import kaka.modifiers.MoltenModifier;
import kaka.util.CardStats;

public class Melt extends BaseCard {

    public static final String ID = makeID(Melt.class.getSimpleName());
    private static final int BASE_MAGIC = 2;

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.NONE,
            0
    );

    public Melt() {
        super(ID, info);
        this.baseMagicNumber = BASE_MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SelectMaterialAction(
            magicNumber,
            "Melt",
            true,
            true,
            card-> true,
            this::handleMoltenToggle
        ));
    }

    private void handleMoltenToggle(java.util.List<AbstractCard> selectedCards) {
    for (AbstractCard card : selectedCards) {
        if (CardModifierManager.hasModifier(card, MoltenModifier.ID)) {
            CardModifierManager.removeModifiersById(card, MoltenModifier.ID, false);
        } else {
            CardModifierManager.addModifier(card, new MoltenModifier());
        }
    }
}

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1); // 2 -> 3
        }
    }
}

