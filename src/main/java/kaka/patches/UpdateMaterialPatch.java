package kaka.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import kaka.MaterialZoneManager;

@SpirePatch(clz = AbstractPlayer.class, method = "updateInput")
public class UpdateMaterialPatch {
    @SpirePostfixPatch
    public static void patch(AbstractPlayer __instance) {
        MaterialZoneManager.update();
    }
}

