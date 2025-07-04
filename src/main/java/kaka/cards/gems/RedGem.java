package kaka.cards.gems;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import kaka.CustomTags;
import kaka.cards.BaseCard;
import kaka.character.MyCharacter;
import kaka.util.CardStats;

public class RedGem extends BaseCard{
    public static final String ID = makeID(RedGem.class.getSimpleName());
    public int minDamage = 4;
    public int maxDamage = 6;

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.CURSE,
            CardRarity.COMMON,
            CardTarget.NONE,
            0
    );

    public RedGem() {
        super(ID, info);
        
        tags.add(CardTags.STRIKE);
        tags.add(CustomTags.Red);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster(true);
        if (target != null) {
            // 创建并加入造成伤害的动作
            
            int actualDamage = AbstractDungeon.cardRandomRng.random(minDamage, maxDamage);

            AbstractDungeon.actionManager.addToBottom(
                new DamageAction(target, new DamageInfo(p, actualDamage, damageTypeForTurn),
                                 AbstractGameAction.AttackEffect.SLASH_HORIZONTAL)
            );
        }
    }
    
}
