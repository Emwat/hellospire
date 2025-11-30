package theHedgehog.skins;

import basemod.patches.com.megacrit.cardcrawl.screens.options.DropdownMenu.DropdownColoring;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import theHedgehog.SonicMod;
import theHedgehog.character.Sonic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class HatMenu {
    public boolean isOpen = false;

    private static DropdownMenu dropdown;

    private static final TextureRegion MENU_BG = new TextureRegion(ImageMaster.loadImage("img/ModPanelBg.png"));

    //positions
    private static final float BG_X_SCALE = Settings.xScale * 0.275f;
    private static final float BG_Y_SCALE = Settings.yScale * 0.8f;
    private static final float BG_X = 525f * Settings.xScale;
    private static final float BG_Y = Settings.HEIGHT - 40f * Settings.yScale - MENU_BG.getRegionHeight() * BG_Y_SCALE;
    private static final float FLAVOR_X = BG_X + MENU_BG.getRegionWidth() * BG_X_SCALE * 0.5f;
    private static final float DROPDOWN_X = 586f * Settings.xScale;
    private static final float DROPDOWN_Y = Settings.HEIGHT - 160f * Settings.yScale;
    private static final float PREVIEW_X = BG_X + (170 * Settings.xScale);
    private static final float PREVIEW_Y = BG_Y + (215 * Settings.yScale);

    public static AbstractPlayer dummy;

    public HatMenu() {
        refreshHatDropdown();
    }

    public static AbstractPlayer getDummy() {
        if (dummy == null) {
            dummy = new Sonic();
            dummy.drawX = PREVIEW_X;
            dummy.drawY = PREVIEW_Y;

            dummy.animX = dummy.animY = 0;
        }
        return dummy;
    }

    public static void refreshHatDropdown() {
        boolean init = false;
        if (dropdown == null) {
            init = true;
        } else {
            dropdown.rows.clear();
        }

        ArrayList<String> optionNames = new ArrayList<>();

        dropdown = new DropdownMenu(null,
                optionNames, FontHelper.tipBodyFont, Settings.CREAM_COLOR);
    }

    public void toggle() {
        if (isOpen) {
            close();
        } else {
            open();
        }
    }

    private void open() {
        isOpen = true;
    }

    private void close() {
        isOpen = false;
    }

    public void update() {
        dropdown.update();
        FontHelper.cardTitleFont.getData().setScale(1f);
    }

    public void render(SpriteBatch sb) {
        sb.draw(MENU_BG, BG_X, BG_Y, 0f, 0f, MENU_BG.getRegionWidth(), MENU_BG.getRegionHeight(), BG_X_SCALE, BG_Y_SCALE, 0f);

        FontHelper.renderWrappedText(sb, FontHelper.panelNameFont, Sonic.currentModSkin.getDescription(), FLAVOR_X, DROPDOWN_Y - (343 * Settings.yScale), 330 * Settings.xScale, Color.YELLOW.cpy(), 0.8F);

        getDummy().renderPlayerImage(sb);

        dropdown.render(sb, DROPDOWN_X, DROPDOWN_Y);
    }

}