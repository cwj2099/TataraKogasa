package kaka.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.ui.panels.TopPanel;

import kaka.MaterialPotionSlotManager;

@SpirePatch(clz = TopPanel.class, method = "update")
public class UpdatePatch {
    @SpirePostfixPatch
    public static void updateExtraPotions(TopPanel __instance) {
        MaterialPotionSlotManager.update();
    }
}
