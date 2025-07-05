package kaka.patches.materialSelect;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;

import kaka.MaterialZoneManager;


@SpirePatch(clz = HandCardSelectScreen.class, method = "update")
public class Update {
    @SpirePrefixPatch
    public static void patch(HandCardSelectScreen __instance) {
        MaterialZoneManager.materials.update();
        MaterialZoneManager.materials.updateHoverLogic();
    }
}