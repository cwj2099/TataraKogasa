package kaka.modifiers;

import static kaka.BasicMod.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

import basemod.abstracts.AbstractCardModifier;

public class MoltenModifier extends AbstractCardModifier {
    public final static String ID = makeID(MoltenModifier.class.getSimpleName());
    boolean oldRetain;

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.retain = true; //保留
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        return false; // 禁止打出
    }

    @Override
    public void atEndOfTurn(AbstractCard card, CardGroup group){
        card.retain = true;
    }

    @Override
    public void onRemove(AbstractCard card){
        card.retain = false;
    }



    //爆改描述，以后再说
    // @Override
    // public String modifyDescription(String rawDescription, AbstractCard card) {
    //     if (!rawDescription.contains("!molten!")) {
    //         return rawDescription + " NL !molten!";
    //     }
    //     return rawDescription;
    // }

    @Override
    public AbstractCardModifier makeCopy() {
        return new MoltenModifier();
    }

    //用于检测是否有熔断字段
    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}

