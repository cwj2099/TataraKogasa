package kaka.patches.materialSelect;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;

import kaka.CustomTags;
import kaka.MaterialZoneManager;


@SpirePatch(clz = HandCardSelectScreen.class, method = "selectHoveredCard")
public class SelectHoveredCard {
    @SpirePrefixPatch
    public static void patch(HandCardSelectScreen __instance) {

        AbstractCard card = __instance.hoveredCard;
        //处理材料的同时打上标记
        if(MaterialZoneManager.materials.group.contains(card))
        {
            MaterialZoneManager.materials.group.remove(card);
            if(!card.tags.contains(CustomTags.ShallReturnMaterial))
            {
                card.tags.add(CustomTags.ShallReturnMaterial);
            }
        }

    }
}