package kaka.patches.materialSelect;

import java.util.Iterator;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;

import kaka.CustomTags;
import kaka.MaterialZoneManager;

@SpirePatch(clz = HandCardSelectScreen.class, method = "updateSelectedCards")
public class UpdateSelectedCards {
    @SpirePostfixPatch
    public static void patch(HandCardSelectScreen __instance) {

        if(!MaterialZoneManager.showInScreen) return;

        // 使用 iterator 安全遍历并移除符合条件的卡
        Iterator<AbstractCard> iterator = AbstractDungeon.player.hand.group.iterator();
        while (iterator.hasNext()) {
            AbstractCard card = iterator.next();
            if (card.tags.contains(CustomTags.ShallReturnMaterial)) {
                iterator.remove(); // 安全移除
                MaterialZoneManager.addMaterial(card);
            }
        }

    }
}
