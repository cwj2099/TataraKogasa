package kaka.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


import kaka.BasicMod;
import kaka.frontEnd.CardMaterialSelectScreen;

@SpirePatch(clz = AbstractDungeon.class, method = "update")
public class DungeonUpdatePatch {
    @SpirePostfixPatch
    public static void updateCMSelection(AbstractDungeon __instance) {
        if(AbstractDungeon.screen == CardMaterialSelectScreen.CARD_MATERIAL_SELECT){
            BasicMod.cmSelection.update();
        }
    }
}
