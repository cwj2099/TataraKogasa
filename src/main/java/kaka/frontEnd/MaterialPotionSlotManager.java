package kaka.frontEnd;

import java.util.ArrayList;

import kaka.frontEnd.MaterialPopUp;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public class MaterialPotionSlotManager {
    public static final ArrayList<AbstractPotion> materialPotions = new ArrayList<>();
    private static final float START_X = 100.0f * Settings.scale;
    private static final float Y = 160.0f * Settings.scale;
    private static final float SPACING = 64.0f * Settings.scale;
    public static MaterialPopUp popUpUi = new MaterialPopUp();

    public static void add(AbstractPotion potion) {
        materialPotions.add(potion);
        positionPotions();
    }

    public static void remove(AbstractPotion potion) {
        materialPotions.remove(potion);
        positionPotions();
    }

    public static void render(SpriteBatch sb) {
        for (AbstractPotion p : materialPotions) {
            p.render(sb);
            if (p.hb != null) {
                p.hb.render(sb);
            }
        }

        popUpUi.render(sb);
    }

    public static void update() {
        for (int i = 0; i < materialPotions.size(); i++) {
            AbstractPotion p = materialPotions.get(i);

            p.hb.update();
            p.update(); // 包括 scale 等动画状态更新

            // 鼠标点击打开 PotionPopUp
            if (p.hb.hovered && InputHelper.justClickedLeft) {
                CardCrawlGame.sound.play("POTION_1", 0.1f);
                popUpUi.open(i,p);
                InputHelper.justClickedLeft = false;
                return; // 防止连续点击多个
            }
        }

        popUpUi.update();
    }

    private static void positionPotions() {
        for (int i = 0; i < materialPotions.size(); i++) {
            AbstractPotion p = materialPotions.get(i);
            p.posX = START_X + i * SPACING;
            p.posY = Y;
            p.hb.move(p.posX, p.posY);
        }
    }
}
