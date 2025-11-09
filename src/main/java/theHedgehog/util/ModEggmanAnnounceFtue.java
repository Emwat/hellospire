package theHedgehog.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
import com.megacrit.cardcrawl.vfx.combat.BattleStartEffect;
import theHedgehog.SonicMod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ModEggmanAnnounceFtue extends FtueTip {
    private static final TutorialStrings utilityTutorialStrings = CardCrawlGame.languagePack.getTutorialString(SonicMod.makeID("UtilityTutorial"));
    public static final String lblNext = utilityTutorialStrings.LABEL[0];
    public static final String lblImReady = utilityTutorialStrings.LABEL[1];
    public static final String lblPageOfPage = utilityTutorialStrings.LABEL[2];
    public static ArrayList<AnnounceGrouping> Contents;
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
    private ArrayList<Float> xs;
    private String footerText1;
    private int lastSlot;

    public class AnnounceGrouping {
        public int pageNumber;
        public String tutorialID;
        public String tutorialText;
        public String tutorialLabel;
        public Texture image;

        public AnnounceGrouping(int pageNumber, String tutorialID, String tutorialText, String tutorialLabel, Texture image) {
            this.pageNumber = pageNumber;
            this.tutorialID = tutorialID;
            this.tutorialText = tutorialText;
            this.tutorialLabel = tutorialLabel;
            this.image = image;
        }

    }

    public ModEggmanAnnounceFtue(ArrayList<String> tutorialIDs) {
        int page = 0;
        Texture defaultImage = TextureLoader.getTexture(SonicMod.imagePath("events/EggmanAnnounce.png"));
        Contents = new ArrayList<>();
        for (String tutorialID : tutorialIDs) {
            TutorialStrings tutorialStrings = CardCrawlGame.languagePack.getTutorialString(tutorialID);
            int imageCounter = 0;
            for (String tutorialText : tutorialStrings.TEXT) {
                Texture image = null;
                String path = SonicMod.imagePath("events/" + tutorialID.replace(SonicMod.modID + ":", "") + "_" + imageCounter + ".png");
                FileHandle h = Gdx.files.internal(path);
                if (h.exists()) {
                    image = TextureLoader.getTexture(path);
                }
                Contents.add(new AnnounceGrouping(page, tutorialID, tutorialText, tutorialStrings.LABEL[0], image == null ? defaultImage : image));
                imageCounter++;
            }
            page++;
        }

        footerText1 = Contents.get(0).tutorialLabel;
        lastSlot = (Contents.size() - 1) * -1;

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
        for (int i = 0; i < Contents.size(); i++) {
            xs.add(xs.get(i) + (float) Settings.WIDTH);
        }
        AbstractDungeon.overlayMenu.proceedButton.show();
        AbstractDungeon.overlayMenu.proceedButton.setLabel(Contents.size() > 1 ? lblNext : lblImReady);
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
            if (this.currentSlot == lastSlot) {
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
            footerText1 = Contents.get(Math.abs(this.currentSlot)).tutorialLabel;

            this.startX = this.x;
            this.targetX = (float) (this.currentSlot * Settings.WIDTH);
            this.scrollTimer = 0.3F;
            if (this.currentSlot == lastSlot) {
                AbstractDungeon.overlayMenu.proceedButton.setLabel(lblImReady);
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

        for (int i = 0; i < Contents.size(); i++) {
            sb.draw(Contents.get(i).image, this.x + xs.get(i) - 380.0F, (float) Settings.HEIGHT / 2.0F - 290.0F,
                    380.0F, 290.0F,
                    W, H, Settings.scale, Settings.scale,
                    0.0F, 0, 0, W, H, false, false);
        }
        float offsetY = 0.0F;
        if (Settings.BIG_TEXT_MODE) {
            offsetY = 110.0F * Settings.scale;
        }

        for (int i = 0; i < Contents.size(); i++) {
            FontHelper.renderSmartText(sb, FontHelper.panelNameFont, Contents.get(i).tutorialText,
                    this.x + this.xs.get(i) + 400.0F * Settings.scale,
                    (float) Settings.HEIGHT / 2.0F - FontHelper.getSmartHeight(FontHelper.panelNameFont, Contents.get(i).tutorialText,
                            700.0F * Settings.scale,
                            40.0F * Settings.scale) / 2.0F + offsetY,
                    700.0F * Settings.scale,
                    40.0F * Settings.scale, Settings.CREAM_COLOR);
        }

        // Yellow Text at the bottom of the screen
        FontHelper.renderFontCenteredWidth(sb, FontHelper.panelNameFont, footerText1, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F - 360.0F * Settings.scale, Settings.GOLD_COLOR);
        String pageIndicator = lblPageOfPage
                .replace("{0}", Integer.toString(Math.abs(this.currentSlot - 1)))
                .replace("{1}", Integer.toString(Contents.size()));
        FontHelper.renderFontCenteredWidth(sb, FontHelper.tipBodyFont, pageIndicator, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F - 400.0F * Settings.scale, Settings.CREAM_COLOR);
        AbstractDungeon.overlayMenu.proceedButton.render(sb);
    }
}