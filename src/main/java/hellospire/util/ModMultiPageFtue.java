package hellospire.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.ui.FtueTip;
import com.megacrit.cardcrawl.ui.MultiPageFtue;
import com.megacrit.cardcrawl.vfx.combat.BattleStartEffect;
import hellospire.SonicMod;

import java.util.ArrayList;

public class ModMultiPageFtue extends FtueTip {
    private static TutorialStrings tutorialStrings;
    private static TutorialStrings utilityTutorialStrings;
    public static String[] TEXT;
    public static String[] LABEL;
    private static final int W = 760;
    private static final int H = 580;
    private Color screen = Color.valueOf("1c262a00");
    private float x;
    private float targetX;
    private float startX;
    private float scrollTimer = 0.0F;
    private static final float SCROLL_TIME = 0.3F;
    private int currentSlot = 0;
    private ArrayList<Texture> imgs;
    private ArrayList<Float> xs;
    private int currentTutorialImagesSize;
    private final String lastPageButtonText;
    private final String footerText1;

    public ModMultiPageFtue(String tutorialID, ArrayList<Texture> tutorialImages) {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString(tutorialID);
        utilityTutorialStrings = CardCrawlGame.languagePack.getTutorialString(SonicMod.makeID("UtilityTutorial"));
        TEXT = tutorialStrings.TEXT;
        LABEL = tutorialStrings.LABEL;
        this.imgs = tutorialImages;
        String nextButtonText = utilityTutorialStrings.LABEL[0];
        lastPageButtonText = utilityTutorialStrings.LABEL[1];
        footerText1 = tutorialStrings.LABEL[0];
        currentTutorialImagesSize = tutorialImages.size();
        if (TEXT.length > currentTutorialImagesSize) {
            for (int i = 0; i < TEXT.length - currentTutorialImagesSize; i++) {
                this.imgs.add(this.imgs.get(this.imgs.size() - 1));
            }
        }
        currentTutorialImagesSize = tutorialImages.size();

        AbstractDungeon.player.releaseCard();
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.FTUE;
        AbstractDungeon.overlayMenu.showBlackScreen();
        xs = new ArrayList<Float>();
        this.x = 0.0F;
        xs.add(567.0F * Settings.scale);
        for (int i = 0; i < currentTutorialImagesSize; i++) {
            xs.add(xs.get(i) + (float) Settings.WIDTH);
        }
        AbstractDungeon.overlayMenu.proceedButton.show();
        AbstractDungeon.overlayMenu.proceedButton.setLabel(nextButtonText);
    }

    public void update() {
        if (this.screen.a != 0.8F) {
            Color var10000 = this.screen;
            var10000.a += Gdx.graphics.getDeltaTime();
            if (this.screen.a > 0.8F) {
                this.screen.a = 0.8F;
            }
        }

        if (AbstractDungeon.overlayMenu.proceedButton.isHovered && InputHelper.justClickedLeft || CInputActionSet.proceed.isJustPressed()) {
            CInputActionSet.proceed.unpress();
            if (this.currentSlot == -2) {
                CardCrawlGame.sound.play("DECK_CLOSE");
                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.overlayMenu.proceedButton.hide();
                AbstractDungeon.effectList.clear();
                AbstractDungeon.topLevelEffects.add(new BattleStartEffect(false));
                return;
            }

            AbstractDungeon.overlayMenu.proceedButton.hideInstantly();
            AbstractDungeon.overlayMenu.proceedButton.show();
            CardCrawlGame.sound.play("DECK_CLOSE");
            --this.currentSlot;
            this.startX = this.x;
            this.targetX = (float) (this.currentSlot * Settings.WIDTH);
            this.scrollTimer = 0.3F;
            if (this.currentSlot == -2) {
                AbstractDungeon.overlayMenu.proceedButton.setLabel(lastPageButtonText);
            }
        }

        if (this.scrollTimer != 0.0F) {
            this.scrollTimer -= Gdx.graphics.getDeltaTime();
            if (this.scrollTimer < 0.0F) {
                this.scrollTimer = 0.0F;
            }
        }

        this.x = Interpolation.fade.apply(this.targetX, this.startX, this.scrollTimer / 0.3F);
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.screen);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float) Settings.WIDTH, (float) Settings.HEIGHT);
        sb.setColor(Color.WHITE);

        for (int i = 0; i < currentTutorialImagesSize; i++) {
            sb.draw(imgs.get(i), this.x + xs.get(i) - 380.0F, (float) Settings.HEIGHT / 2.0F - 290.0F, 380.0F, 290.0F, 760.0F, 580.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 760, 580, false, false);
        }
        float offsetY = 0.0F;
        if (Settings.BIG_TEXT_MODE) {
            offsetY = 110.0F * Settings.scale;
        }


        for (int i = 0; i < TEXT.length; i++) {
            FontHelper.renderSmartText(sb, FontHelper.panelNameFont, TEXT[i],
                    this.x + this.xs.get(i) + 400.0F * Settings.scale,
                    (float)Settings.HEIGHT / 2.0F - FontHelper.getSmartHeight(FontHelper.panelNameFont, TEXT[i], 700.0F * Settings.scale, 40.0F * Settings.scale) / 2.0F + offsetY,
                    700.0F * Settings.scale, 40.0F * Settings.scale, Settings.CREAM_COLOR);
        }

        // Yellow Text at the bottom of the screen
        FontHelper.renderFontCenteredWidth(sb, FontHelper.panelNameFont, footerText1, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F - 360.0F * Settings.scale, Settings.GOLD_COLOR);
        String pageIndicator = utilityTutorialStrings.LABEL[3]
                .replace("{0}", Integer.toString(Math.abs(this.currentSlot - 1)))
                .replace("{1}", Integer.toString(TEXT.length));
        FontHelper.renderFontCenteredWidth(sb, FontHelper.tipBodyFont, pageIndicator, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F - 400.0F * Settings.scale, Settings.CREAM_COLOR);
        AbstractDungeon.overlayMenu.proceedButton.render(sb);
    }
}
