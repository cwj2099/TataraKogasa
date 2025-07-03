package kaka.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import kaka.BasicMod;

@SpirePatch(clz = AbstractDungeon.class, method = "render")
public class DungeonRenderPatch {
    @SpirePostfixPatch
    public static void renderCMSelection(AbstractDungeon __instance, SpriteBatch sb) {
    }
}