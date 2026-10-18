package theHedgehog.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.evacipated.cardcrawl.mod.stslib.patches.cardInterfaces.BranchingUpgradesPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.controller.CInputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import theHedgehog.SonicMod;

/**
 * Controller support for StSLib's branching upgrades.
 *
 * StSLib only reads the mouse for these screens (InputHelper.justClickedLeft and hitbox hover),
 * and vanilla re-centres the controller cursor on the normal upgrade preview every frame, so a
 * controller player could never reach the branch upgrade (and could confirm the normal one
 * before ever being asked).
 *
 * Smith / upgrade-a-card screens (GridCardSelectScreen):
 *   Up / Down (left stick or d-pad) moves the cursor between the normal and branch previews,
 *   Select (A) chooses the highlighted one, Confirm (Y) then confirms as usual.
 *
 * Card view popup (compendium / deck view):
 *   The same button that toggles the upgrade preview (Proceed) now cycles
 *   base -> upgrade -> branch upgrade -> base for branching cards.
 */
public class BranchUpgradeControllerPatch {
    private static final String UI_ID = SonicMod.makeID("BranchUpgradeController");
    private static UIStrings uiStrings;

    // 0 = normal upgrade preview (top), 1 = branch upgrade preview (bottom)
    private static int choice = 0;
    private static AbstractCard trackedBranchCard = null;

    private static UIStrings strings() {
        if (uiStrings == null) {
            uiStrings = CardCrawlGame.languagePack.getUIString(UI_ID);
        }
        return uiStrings;
    }

    /** True while a controller player is looking at the normal/branch preview pair. */
    private static boolean isChoosingUpgradePath(GridCardSelectScreen screen) {
        return Settings.isControllerMode
                && screen.forUpgrade
                && screen.confirmScreenUp
                && screen.upgradePreviewCard != null
                && BranchingUpgradesPatch.getHoveredCard() instanceof BranchingUpgradesCard
                && BranchingUpgradesPatch.BranchSelectFields.branchUpgradePreviewCard.get(screen) != null;
    }

    private static boolean isWaitingForChoice(GridCardSelectScreen screen) {
        return BranchingUpgradesPatch.BranchSelectFields.waitingForBranchUpgradeSelection.get(screen);
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "update")
    public static class Update {
        @SpirePrefixPatch
        public static void Prefix(GridCardSelectScreen __instance) {
            // StSLib only hides the confirm button while waiting; Proceed/TopPanel still confirm,
            // which silently locked controller players into the normal upgrade.
            if (isChoosingUpgradePath(__instance) && isWaitingForChoice(__instance)) {
                CInputActionSet.proceed.unpress();
                CInputActionSet.topPanel.unpress();
            }
        }

        // Postfix so this runs after vanilla's final CInputHelper.setCursor(upgradePreviewCard.hb).
        @SpirePostfixPatch
        public static void Postfix(GridCardSelectScreen __instance) {
            if (!isChoosingUpgradePath(__instance)) {
                choice = 0;
                trackedBranchCard = null;
                return;
            }

            AbstractCard normalCard = __instance.upgradePreviewCard;
            AbstractCard branchCard = BranchingUpgradesPatch.BranchSelectFields.branchUpgradePreviewCard.get(__instance);

            if (branchCard != trackedBranchCard) {
                // The previews were just created this frame, by the same Select press that
                // picked the card. Start on the normal preview and ignore that press.
                trackedBranchCard = branchCard;
                choice = 0;
                CInputHelper.setCursor(normalCard.hb);
                return;
            }

            if (CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) {
                choice = 0;
            } else if (CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) {
                choice = 1;
            }

            AbstractCard focused = choice == 0 ? normalCard : branchCard;
            AbstractCard other = choice == 0 ? branchCard : normalCard;
            CInputHelper.setCursor(focused.hb);

            if (CInputActionSet.select.isJustPressed()) {
                CInputActionSet.select.unpress();
                // Mirrors what StSLib's SelectBranchedUpgrade does for a mouse click.
                BranchingUpgradesPatch.BranchSelectFields.isBranchUpgrading.set(__instance, choice == 1);
                BranchingUpgradesPatch.BranchSelectFields.waitingForBranchUpgradeSelection.set(__instance, false);
                focused.beginGlowing();
                other.stopGlowing();
                CardCrawlGame.sound.play("CARD_SELECT");
            }
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "render")
    public static class Render {
        @SpirePostfixPatch
        public static void Postfix(GridCardSelectScreen __instance, SpriteBatch sb) {
            if (!isChoosingUpgradePath(__instance)) {
                return;
            }

            UIStrings text = strings();
            float x = Settings.WIDTH * 0.63f + 230.0f * Settings.scale;
            float y = Settings.HEIGHT / 2.0f;

            if (isWaitingForChoice(__instance)) {
                FontHelper.renderFontLeft(sb, FontHelper.buttonLabelFont, text.TEXT[2],
                        x - 32.0f * Settings.scale, y + 150.0f * Settings.scale, Settings.GOLD_COLOR);
            }

            drawKey(sb, CInputActionSet.up.getKeyImg(), x, y + 60.0f * Settings.scale);
            drawKey(sb, CInputActionSet.down.getKeyImg(), x, y);
            FontHelper.renderFontLeft(sb, FontHelper.tipBodyFont, text.TEXT[0],
                    x + 40.0f * Settings.scale, y + 30.0f * Settings.scale, Settings.CREAM_COLOR);

            drawKey(sb, CInputActionSet.select.getKeyImg(), x, y - 70.0f * Settings.scale);
            FontHelper.renderFontLeft(sb, FontHelper.tipBodyFont, text.TEXT[1],
                    x + 40.0f * Settings.scale, y - 70.0f * Settings.scale, Settings.CREAM_COLOR);
        }

        private static void drawKey(SpriteBatch sb, Texture img, float x, float y) {
            if (img == null) {
                return;
            }
            sb.setColor(Color.WHITE);
            sb.draw(img, x - 32.0f, y - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f,
                    Settings.scale, Settings.scale, 0.0f, 0, 0, 64, 64, false, false);
        }
    }

    // ---- Card view popup (compendium / master deck view) ----------------------------------

    @SpirePatch(clz = SingleCardViewPopup.class, method = "updateUpgradePreview")
    public static class PopupCycle {
        @SpirePrefixPatch
        public static void Prefix(SingleCardViewPopup __instance, AbstractCard ___card) {
            if (!(___card instanceof BranchingUpgradesCard) || !CInputActionSet.proceed.isJustPressed()) {
                return;
            }

            boolean viewingBranch = BranchingUpgradesPatch.BranchUpgradeButton.isViewingBranchUpgrade.get(__instance);
            if (SingleCardViewPopup.isViewingUpgrade) {
                // upgrade -> branch upgrade (base -> upgrade is left to vanilla)
                CInputActionSet.proceed.unpress();
                SingleCardViewPopup.isViewingUpgrade = false;
                BranchingUpgradesPatch.BranchUpgradeButton.isViewingBranchUpgrade.set(__instance, true);
            } else if (viewingBranch) {
                // branch upgrade -> base
                CInputActionSet.proceed.unpress();
                BranchingUpgradesPatch.BranchUpgradeButton.isViewingBranchUpgrade.set(__instance, false);
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderUpgradeViewToggle")
    public static class PopupHint {
        @SpirePostfixPatch
        public static void Postfix(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard ___card) {
            if (!Settings.isControllerMode || !(___card instanceof BranchingUpgradesCard)) {
                return;
            }
            Hitbox hb = BranchingUpgradesPatch.BranchUpgradeButton.branchUpgradeHb.get(__instance);
            Texture img = CInputActionSet.proceed.getKeyImg();
            if (hb == null || img == null) {
                return;
            }
            sb.setColor(Color.WHITE);
            sb.draw(img, hb.cX - 132.0f * Settings.scale - 32.0f, hb.cY - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f,
                    Settings.scale, Settings.scale, 0.0f, 0, 0, 64, 64, false, false);
        }
    }
}
