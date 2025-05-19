package kaka.potions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionColor;
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity;
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize;

public class SoulCrystal extends AbstractPotion {
    public static final String ID = "modid:SoulCrystal";

    public SoulCrystal() {
        super("Soul Crystal", ID, PotionRarity.UNCOMMON, PotionSize.S, PotionColor.SMOKE);
        this.isThrown = true;
        this.targetRequired = true;
    }

    @Override
    public void use(AbstractCreature target) {
        if (target != null) {
            AbstractDungeon.actionManager.addToBottom(
                new DamageAction(target, new DamageInfo(AbstractDungeon.player, 5, DamageInfo.DamageType.THORNS),
                                AbstractGameAction.AttackEffect.FIRE)
            );
        }
        AbstractDungeon.actionManager.addToBottom(
            new GainBlockAction(AbstractDungeon.player, 5)
        );
    }

    @Override
    public void initializeData() {
        this.name = "Soul Crystal";
        this.description = "A mysterious energy. Can be consumed by special skills.";
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        //this.initializeTips();
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'makeCopy'");
    }
}

