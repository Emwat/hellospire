package theHedgehog.skins;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import theHedgehog.MyModConfig;

import static theHedgehog.SonicMod.makeID;
import static theHedgehog.SonicMod.sonicmodConfig;
import static theHedgehog.skins.FontCreationPatches.biggerTitleFont;
import static theHedgehog.skins.ModSkinDictionary.CONFIG_CURRENT_SKIN;

public class SkinSelectionUI {
    public static BitmapFont skinNameFont = biggerTitleFont;

    private static final float SKIN_TEXT_X = Settings.WIDTH * 0.5f;
    private static final float SKIN_TEXT_Y = Settings.HEIGHT * 0.92f;
    private static float maxSkinNameLength = -1;

    private static UIStrings UIStrings = CardCrawlGame.languagePack.getUIString(makeID("SkinSystemUI"));

    private static Hitbox skinLeftHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
    private static Hitbox skinRightHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);

    public static void render(SpriteBatch sb) {
        Color color = Settings.GOLD_COLOR.cpy();
        color.mul(Settings.CREAM_COLOR);
        FontHelper.renderFontLeft(sb, FontHelper.buttonLabelFont, UIStrings.TEXT[0],
                SKIN_TEXT_X, SKIN_TEXT_Y, Settings.GOLD_COLOR);

        ModSkinDictionary.ModSkin currentSkin = ModSkinDictionary.getModSkin(sonicmodConfig.getString(CONFIG_CURRENT_SKIN));
        FontHelper.renderFontCentered(sb,
                skinNameFont,
                currentSkin.getName(),
                SKIN_TEXT_X + skinLeftHb.width / 2f + maxSkinNameLength / 2f,
                SKIN_TEXT_Y - skinLeftHb.height,
                color);
        // FontHelper.renderFontLeft(sb, smallerTitleFont, currentSkin.getName(),
        //         SKIN_TEXT_X, SKIN_TEXT_Y - skinLeftHb.height * 1.55f, Settings.GOLD_COLOR);

        if (skinLeftHb.hovered) {
            sb.setColor(Color.WHITE);
        } else {
            sb.setColor(Color.LIGHT_GRAY);
        }
        sb.draw(ImageMaster.CF_LEFT_ARROW,
                skinLeftHb.cX - 24.0F, skinLeftHb.cY - 24.0F,
                24.0F, 24.0F,
                48.0F, 48.0F,
                Settings.scale, Settings.scale,
                0.0F, 0, 0, 48, 48, false, false);

        if (skinRightHb.hovered) {
            sb.setColor(Color.WHITE);
        } else {
            sb.setColor(Color.LIGHT_GRAY);
        }
        sb.draw(ImageMaster.CF_RIGHT_ARROW,
                skinRightHb.cX - 24.0F, skinRightHb.cY - 24.0F,
                24.0F, 24.0F,
                48.0F, 48.0F,
                Settings.scale, Settings.scale,
                0.0F, 0, 0, 48, 48, false, false);
        skinLeftHb.render(sb);
        skinRightHb.render(sb);
    }

    public static void update() {
        if (maxSkinNameLength == -1) {
            maxSkinNameLength = ModSkinDictionary.getInstance().getMaxNameLength();

            skinLeftHb.move(SKIN_TEXT_X, SKIN_TEXT_Y - skinLeftHb.height);
            skinRightHb.move(SKIN_TEXT_X + skinLeftHb.width + maxSkinNameLength, SKIN_TEXT_Y - skinLeftHb.height);
        }

        skinLeftHb.update();
        skinRightHb.update();

        if (InputHelper.justClickedLeft) {
            if (skinRightHb.hovered) {
                ModSkinDictionary.getInstance().iterateSkin(HatMenu.getDummy(), true);
                if(!MainMenuUIPatch.hatMenu.isOpen) MainMenuUIPatch.hatMenu.toggle();
            } else if (skinLeftHb.hovered) {
                ModSkinDictionary.getInstance().iterateSkin(HatMenu.getDummy(), false);
                if(!MainMenuUIPatch.hatMenu.isOpen) MainMenuUIPatch.hatMenu.toggle();
            }
        }
    }
}