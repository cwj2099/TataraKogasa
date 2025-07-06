package kaka.cards.start;


import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;
import kaka.CustomTags;
import kaka.actions.ChooseCardFromListAction;
import kaka.actions.SelectMaterialAction;
import kaka.cards.BaseCard;
import kaka.cards.options.ForgeAttack;
import kaka.cards.options.ForgeBlock;
import kaka.character.MyCharacter;
import kaka.modifiers.MoltenModifier;
import kaka.util.CardStats;

public class RainforgedShaper extends BaseCard {

    public static final String ID = makeID(RainforgedShaper.class.getSimpleName());
    AbstractPlayer currentPlayer;
    CardTags callbackTag;

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.NONE,
            1
    );

    public RainforgedShaper() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        currentPlayer = p;
        ArrayList<AbstractCard> choices = new ArrayList<>();

        ForgeAttack option1 = new ForgeAttack();
        option1.assignCallback(this::forgeEvent);
        ForgeBlock option2 = new ForgeBlock();
        option2.assignCallback(this::forgeEvent);

        choices.add(option1);
        choices.add(option2);

        AbstractDungeon.actionManager.addToBottom(new ChooseOneAction(choices));
    }

    private void forgeEvent(CardTags callbackTag){
        this.callbackTag = callbackTag;

        AbstractGameAction forgeAction = new SelectMaterialAction(
            2,
            "Melt into a card",
            false,
            false,
            this::isValidMeltTarget,
            this::handleMoltenToggle
        );
        AbstractDungeon.actionManager.addToBottom(forgeAction);
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


        CardGroup pool = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        //如果明确有花色
        if(cardColor != CardColor.COLORLESS){
            for(AbstractCard c: CardLibrary.getAllCards()){
                if(c.color == cardColor
                && c.rarity == firstCard.rarity) pool.addToBottom(c);
            }
        }
        //如果没有明确花色
        else{
            for(AbstractCard c: CardLibrary.getAllCards()){
                if((c.color == CardColor.RED || c.color == CardColor.GREEN || c.color == CardColor.BLUE || c.color == CardColor.PURPLE)
                && c.rarity == firstCard.rarity) pool.addToBottom(c);
            }
        }

        CardGroup finalPool = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        //根据攻击防御进行筛选
        if(callbackTag == CardTags.STARTER_STRIKE){
            for (AbstractCard c : pool.group) {
                if(c.type == CardType.ATTACK) finalPool.addToBottom(c);
            }
        }
        else if(callbackTag == CardTags.STARTER_DEFEND){
            for (AbstractCard c : pool.group) {
                if(c.type == CardType.SKILL
                && c.baseBlock > 0) finalPool.addToBottom(c);
            }
        }


        //让耗材被上标记
        for (AbstractCard card : selectedCards) {
            CardModifierManager.addModifier(card, new MoltenModifier()); 
        }

        ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
        cards.add(finalPool.getRandomCard(true));
        cards.add(finalPool.getRandomCard(true));
        cards.add(finalPool.getRandomCard(true));

        AbstractDungeon.actionManager.addToBottom(new ChooseCardFromListAction(cards, card ->{
            card.costForTurn --;
        }));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
        }
    }

}

