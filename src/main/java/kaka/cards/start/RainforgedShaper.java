package kaka.cards.start;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;
import kaka.CustomTags;
import kaka.actions.SelectMaterialAction;
import kaka.cards.BaseCard;
import kaka.character.MyCharacter;
import kaka.modifiers.MoltenModifier;
import kaka.util.CardStats;

public class RainforgedShaper extends BaseCard {

    public static final String ID = makeID(RainforgedShaper.class.getSimpleName());
    AbstractPlayer currentPlayer;

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.NONE,
            0
    );

    public RainforgedShaper() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        currentPlayer = p;
        AbstractDungeon.actionManager.addToBottom(new SelectMaterialAction(
            3,
            "Melt into a card",
            false,
            false,
            this::isValidMeltTarget,
            this::handleMoltenToggle
        ));
    }

    private boolean isValidMeltTarget(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, MoltenModifier.ID);
    }

    private void handleMoltenToggle(java.util.List<AbstractCard> selectedCards) {

        //根据第一张牌的标签决定花色
        AbstractCard firstCard = selectedCards.get(0);
        CardColor cardColor = CardColor.COLORLESS;
        if(firstCard.tags.contains(CustomTags.Red)) cardColor = CardColor.RED;
        if(firstCard.tags.contains(CustomTags.Green)) cardColor = CardColor.GREEN;
        if(firstCard.tags.contains(CustomTags.Blue)) cardColor = CardColor.BLUE;
        if(firstCard.tags.contains(CustomTags.Purple)) cardColor = CardColor.PURPLE;

        //如果明确有花色
        if(cardColor != CardColor.COLORLESS){
            CardGroup pool = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for(AbstractCard c: CardLibrary.getAllCards()){
                if(c.color == cardColor
                && c.rarity == firstCard.rarity) pool.addToBottom(c);
            }
            currentPlayer.hand.addToHand(pool.getRandomCard(true));
        }

        //如果没有明确花色
        else{
            currentPlayer.hand.addToHand(CardLibrary.getAnyColorCard(firstCard.rarity));
        }

        
        //让耗材被上标记
        for (AbstractCard card : selectedCards) {
            CardModifierManager.addModifier(card, new MoltenModifier()); 
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
        }
    }

}

