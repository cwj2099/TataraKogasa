package kaka.patches.materialSelect;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;

import kaka.MaterialZoneManager;

@SpirePatch(clz = HandCardSelectScreen.class, method = "hoverCheck")
public class HoverCheck {
    @SpirePostfixPatch
    public static void patch(HandCardSelectScreen __instance) {

        if(!MaterialZoneManager.showInScreen) return;
        if(__instance.hoveredCard != null) return;

        AbstractCard hovered = null;
        for (AbstractCard card : MaterialZoneManager.materials.group) {
            if (card.hb.hovered) {
                hovered = card;
                break;
            }
        }
        __instance.hoveredCard = hovered;
        
        if (__instance.hoveredCard != null) {
            __instance.hoveredCard.drawScale = 1.0F;
            __instance.hoveredCard.targetDrawScale = 1.0F;
            __instance.hoveredCard.setAngle(0.0F, true);
        }
    }
}