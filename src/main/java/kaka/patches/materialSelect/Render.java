package kaka.patches.materialSelect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;

import kaka.MaterialZoneManager;

@SpirePatch(clz = HandCardSelectScreen.class, method = "render")
public class Render {
    @SpirePostfixPatch
    public static void patch(HandCardSelectScreen __instance, SpriteBatch sb) {
        MaterialZoneManager.render(sb);
    }
}

