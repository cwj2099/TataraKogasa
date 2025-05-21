package kaka.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.TopPanel;

import kaka.frontEnd.MaterialPotionSlotManager;

@SpirePatch(clz = TopPanel.class, method = "render")
public class RenderPatch {
    @SpirePostfixPatch
    public static void renderExtraPotions(TopPanel __instance, SpriteBatch sb) {
        MaterialPotionSlotManager.render(sb);
    }
}


