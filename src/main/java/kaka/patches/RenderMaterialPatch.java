package kaka.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import kaka.MaterialZoneManager;

@SpirePatch(clz = AbstractPlayer.class, method = "renderHand")
public class RenderMaterialPatch {
    @SpirePostfixPatch
    public static void patch(AbstractPlayer __instance, SpriteBatch sb) {
        MaterialZoneManager.render(sb);
    }
}
