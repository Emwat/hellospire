package theHedgehog.skins;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import theHedgehog.character.Sonic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static theHedgehog.SonicMod.makeID;

public class MainMenuUIPatch {
    public static boolean customDraft;

    private static final Hitbox packDraftToggle = new Hitbox(40.0f * Settings.scale, 40.0f * Settings.scale);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("PackMainMenuUI"));
    private static final String[] TEXT = uiStrings.TEXT;

    private static final ArrayList<PowerTip> toggleTips = new ArrayList<>();

    private static final ArrayList<DropdownMenu> dropdowns = new ArrayList<>();

    private static final ArrayList<String> options = new ArrayList<>();


    //filter button fields
    // We pass these values to the button class, which does its own multiplication by xScale/yScale
    // Since we want this to be offset by a constant amount from the top of the screen, we divide by yScale to cancel it out
    private static final float FILTERBUTTON_X = 55f;
    private static final float FILTERBUTTON_Y = (Settings.HEIGHT - 120f * Settings.yScale) / Settings.yScale;

    private static final HashMap<String, Integer> idToIndex = new HashMap<>();

    //hat button fields
    // We pass these values to the button class, which does its own multiplication by xScale/yScale
    // Since we want this to be offset by a constant amount from the top of the screen, we divide by yScale to cancel it out
    private static final float HATBUTTON_X = 610f;
    private static final float HATBUTTON_Y = (Settings.HEIGHT - 120f * Settings.yScale) / Settings.yScale;

    public static final HatMenu hatMenu = new HatMenu();
    private static final FixedModLabeledButton openHatMenuButton;

    static {
        options.add(TEXT[2]);
        options.add(TEXT[7]);
        openHatMenuButton = new FixedModLabeledButton(uiStrings.TEXT[5], HATBUTTON_X, HATBUTTON_Y, null, (button) -> hatMenu.toggle());
    }

    public static void updateChoiceCount() {
        for (DropdownMenu dropdown : dropdowns) {
            Object o = dropdown.rows.get(1);
            ReflectionHacks.setPrivate(o, o.getClass(), "text", options.get(1));
        }
    }


    @SpirePatch(clz = CharacterOption.class, method = "renderRelics")
    public static class RenderOptions {
        public static void Postfix(CharacterOption obj, SpriteBatch sb) {

            CharSelectInfo c = ReflectionHacks.getPrivate(obj, CharacterOption.class, "charInfo");

            if (c != null && c.player.chosenClass.equals(Sonic.Meta.THE_HEDGEHOG) && obj.selected) {
                // Render toggle button
                // packDraftToggle.move(CHECKBOX_X, CHECKBOX_Y);
                // packDraftToggle.render(sb);
                //
                // sb.setColor(Color.WHITE);
                // float checkScale = Settings.scale * 0.8f;
                // sb.draw(ImageMaster.CHECKBOX, packDraftToggle.cX - 32f, packDraftToggle.cY - 32f, 32.0f, 32.0f, 64.0f, 64.0f, checkScale, checkScale, 0.0f, 0, 0, 64, 64, false, false);
                // if (customDraft) {
                //     sb.draw(ImageMaster.TICK, packDraftToggle.cX - 32f, packDraftToggle.cY - 32f, 32.0f, 32.0f, 64.0f, 64.0f, checkScale, checkScale, 0.0f, 0, 0, 64, 64, false, false);
                // }
                // FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, uiStrings.TEXT[0], packDraftToggle.cX + 25f * Settings.scale, packDraftToggle.cY + FontHelper.getHeight(FontHelper.tipHeaderFont) * 0.5f, Settings.BLUE_TEXT_COLOR);

                // If toggle button is checked, render the dropdowns, too
                // if (customDraft) {
                //     for (int i = dropdowns.size() - 1; i >= 0; i--) {
                //         dropdowns.get(i).render(sb, DROPDOWN_X, DROPDOWNS_START_Y - (DROPDOWNS_SPACING * i));
                //     }
                // }

                // if (filterMenu.isOpen) {
                //     filterMenu.render(sb);
                // }
                // openFilterMenuButton.render(sb);

                if (hatMenu.isOpen) {
                    hatMenu.render(sb);
                }
                openHatMenuButton.render(sb);
            }
        }
    }

    @SpirePatch(clz = CharacterOption.class, method = "updateHitbox")
    public static class UpdateOptions {
        public static void Postfix(CharacterOption obj) {
            CharSelectInfo c = ReflectionHacks.getPrivate(obj, CharacterOption.class, "charInfo");

            if (c != null && c.player.chosenClass.equals(Sonic.Meta.THE_HEDGEHOG) && obj.selected) {
                // If custom draft is enabled, update the dropdowns

                boolean stopInput = false;
                if (customDraft) {
                    for (DropdownMenu d : dropdowns) {
                        if (d.isOpen)
                            stopInput = true;
                        d.update();
                        if (d.isOpen || stopInput) {
                            stopInput = true;
                            InputHelper.justClickedLeft = false;
                            InputHelper.justReleasedClickLeft = false;
                            CInputActionSet.select.unpress();
                            CInputActionSet.proceed.unpress();
                        }
                    }
                }

                // Update the toggle button.
                if (!stopInput) {
                    packDraftToggle.update();
                    if (packDraftToggle.hovered) {
                        if (toggleTips.isEmpty()) {
                            toggleTips.add(new PowerTip(uiStrings.TEXT[0], uiStrings.TEXT[1]));
                        }
                        if (InputHelper.mX < 1400.0f * Settings.scale) {
                            TipHelper.queuePowerTips(InputHelper.mX + 60.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, toggleTips);
                        } else {
                            TipHelper.queuePowerTips(InputHelper.mX - 350.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, toggleTips);
                        }

                        if (InputHelper.justClickedLeft) {
                            CardCrawlGame.sound.playA("UI_CLICK_1", -0.4f);
                            packDraftToggle.clickStarted = true;
                        }
                        if (packDraftToggle.clicked) {
                            customDraft = !customDraft;
                            // SpireAnniversary5Mod.saveCustomDraftEnabled(customDraft);
                            packDraftToggle.clicked = false;
                        }
                    }
                } else {
                }

                // openFilterMenuButton.update();
                // if (filterMenu.isOpen) {
                //     filterMenu.update();
                // }

                openHatMenuButton.update();
                if (hatMenu.isOpen) {
                    hatMenu.update();
                }
            }
        }
    }
}