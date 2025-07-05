package kaka;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

public class MaterialZoneManager {
    public static CardGroup materials = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private static final float MATERIAL_X = 300.0f * Settings.scale;
    private static final float MATERIAL_Y_START = 240.0f * Settings.scale;
    private static final float MATERIAL_Y_GAP = 160.0f * Settings.scale;
    private static final int MATERIAL_LIMIT = 5;

    private static AbstractCard draggingCard = null;
    private static boolean isDragging = false;

    public static void initialize() {
        materials.clear();
    }

    public static void update() {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT)
            return;

        ArrayList<AbstractCard> materialList = materials.group;

        for (int i = 0; i < materialList.size(); i++) {
            AbstractCard card = materialList.get(i);
            float targetY = MATERIAL_Y_START + i * MATERIAL_Y_GAP;

            if (!isDragging || draggingCard != card) {
                card.target_x = MATERIAL_X;
                card.target_y = targetY;
                card.targetDrawScale = 0.8f;
            }

            card.update();
            card.updateHoverLogic();
        }

        // detect hover
        AbstractCard hovered = null;
        for (AbstractCard card : materialList) {
            if (card.hb.hovered) {
                hovered = card;
                break;
            }
        }

        // start drag
        if (!isDragging && hovered != null && InputHelper.justClickedLeft) {
            draggingCard = hovered;
            isDragging = true;
        }

        // update dragging
        if (isDragging && draggingCard != null) {
            draggingCard.current_x = InputHelper.mX;
            draggingCard.current_y = InputHelper.mY;
            draggingCard.target_x = InputHelper.mX;
            draggingCard.target_y = InputHelper.mY;
            draggingCard.drawScale = 1.0f;

            if (!InputHelper.isMouseDown) {
                // released!
                if (isInDropZone()) {
                    draggingCard.use(AbstractDungeon.player, null);
                    materials.removeCard(draggingCard);
                }

                draggingCard = null;
                isDragging = false;
            }
        }
    }

    public static void render(SpriteBatch sb) {
        for (AbstractCard card : materials.group) {
            card.render(sb);
        }

        if (isDragging && draggingCard != null) {
            draggingCard.render(sb);
        }
    }

    public static void addMaterial(AbstractCard card) {
        if (materials.size() >= MATERIAL_LIMIT) return;

        materials.addToTop(card);

        int index = Math.min(materials.size() - 1, MATERIAL_LIMIT - 1);
        float targetY = MATERIAL_Y_START + index * MATERIAL_Y_GAP;

        card.current_x = MATERIAL_X;
        card.current_y = targetY;
        card.target_x = MATERIAL_X;
        card.target_y = targetY;

        card.drawScale = 0.8f;
        card.targetDrawScale = 0.8f;
        card.lighten(false);
        card.unhover();
    }

    public static void clear() {
        materials.clear();
    }

    private static boolean isInDropZone() {
        return InputHelper.mY > 400.0f && InputHelper.mX < 1400.0f;
    }
}
