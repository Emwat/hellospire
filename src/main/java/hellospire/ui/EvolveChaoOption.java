package hellospire.ui;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import hellospire.SonicMod;
import hellospire.SonicTags;
import hellospire.relics.*;
import hellospire.util.TextureLoader;

import java.util.ArrayList;

import static hellospire.SonicMod.makeID;

// also see AddEvolveChaoButtonPatch
// https://github.com/erasels/PackmasterCharacter/blob/main/src/main/java/thePackmaster/ui/EnhanceBonfireOption.java
public class EvolveChaoOption extends AbstractCampfireOption {
    public static final String[] DESCRIPTIONS;
    private static final UIStrings UI_STRINGS;

    static {
        UI_STRINGS = CardCrawlGame.languagePack.getUIString(makeID("EvolveChaoOptions"));
        DESCRIPTIONS = UI_STRINGS.TEXT;
    }

    // private ArrayList<String> idleMessages;
    public EvolveChaoOption(boolean active) {
        this.label = DESCRIPTIONS[0];

        this.usable = active;
        if (active) {
            this.description = DESCRIPTIONS[1] + HedgehogPack.goldCostToEvolve + DESCRIPTIONS[2];
            this.img = TextureLoader.getTexture(SonicMod.imagePath("ui/chaoCampfire.png"));
        } else {
            this.img = TextureLoader.getTexture(SonicMod.imagePath("ui/chaoCampfireDisabled.png"));
            if (AbstractDungeon.player.gold < HedgehogPack.goldCostToEvolve) {
                this.description = DESCRIPTIONS[3] + HedgehogPack.goldCostToEvolve + DESCRIPTIONS[4];
            } else {
                this.description = DESCRIPTIONS[3] + HedgehogPack.goldCostToEvolve + DESCRIPTIONS[4];
            }
        }
    }

    @Override
    public void useOption() {
        if (this.usable) {
            AbstractCard.CardTags mostTagged = GetMostTagged();
            AbstractDungeon.player.loseGold(HedgehogPack.goldCostToEvolve);
            AbstractDungeon.player.loseRelic(ChaoRelic.ID);
            GrantRelic(mostTagged);
        }
    }

    private void GrantRelic(AbstractCard.CardTags tag) {
        AbstractRelic r = null;
        if (tag == SonicTags.LIKE_IRONCLAD) { r = new ChaoIroncladRelic(); }
        else if (tag == SonicTags.LIKE_SILENT) { r = new ChaoSilentRelic(); }
        else if (tag == SonicTags.LIKE_DEFECT) { r = new ChaoDefectRelic(); }
        else if (tag == SonicTags.LIKE_WATCHER) { r = new ChaoWatcherRelic(); }
        AbstractDungeon.getCurrRoom().addRelicToRewards(r);
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        AbstractDungeon.combatRewardScreen.open();
    }

    private AbstractCard.CardTags GetMostTagged() {
        int countIronclad = countTagsInMasterDeck(SonicTags.LIKE_IRONCLAD);
        int countSilent = countTagsInMasterDeck(SonicTags.LIKE_SILENT);
        int countDefect = countTagsInMasterDeck(SonicTags.LIKE_DEFECT);
        int countWatcher = countTagsInMasterDeck(SonicTags.LIKE_WATCHER);
        int[] counts = {countIronclad, countSilent, countDefect, countWatcher};
        int maxCount = counts[0];

        for (int i = 1; i < counts.length; i++) {
            if (counts[i] > maxCount) {
                maxCount = counts[i];
            }
        }

        if (maxCount == countIronclad) {
            return SonicTags.LIKE_IRONCLAD;
        } else if (maxCount == countSilent) {
            return SonicTags.LIKE_SILENT;
        } else if (maxCount == countDefect) {
            return SonicTags.LIKE_DEFECT;
        } else if (maxCount == countWatcher) {
            return SonicTags.LIKE_WATCHER;
        }
        return SonicTags.LIKE_DEFECT;
    }

    private int countTagsInMasterDeck(AbstractCard.CardTags tag) {
        int count = 0;
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card.hasTag(tag)) {
                count++;
            }
        }
        return count;
    }


    // public void reCheck() {
    //     if (SocketGemEffect.getModifiableCards().isEmpty() || SocketGemEffect.getGems().isEmpty() || AbstractDungeon.player.gold < GemsPack.goldCostToSocket) {
    //         this.usable = false;
    //     }
    //     if (this.usable) {
    //         this.description = DESCRIPTIONS[1] + GemsPack.goldCostToSocket + DESCRIPTIONS[2];
    //         this.img = TexLoader.getTexture(SpireAnniversary5Mod.makeImagePath("ui/chaoCampfire.png"));
    //     } else {
    //         this.description = DESCRIPTIONS[3] + GemsPack.goldCostToSocket + DESCRIPTIONS[4];
    //         this.img = TexLoader.getTexture(SpireAnniversary5Mod.makeImagePath("ui/chaoCampfireDisabled.png"));
    //     }
    // }

    @Override
    public void update() {
        float hackScale = ReflectionHacks.getPrivate(this, AbstractCampfireOption.class, "scale");
        // This allows the button to be mouse over even when you don't meet the requirements, so you can see the tooltip.
        // The scale change mimics what normally happens when the button is active.
        // Normally greyed out options lose their hitbox, and we wanted to have this one be able to tell the player why its greyed out.
        if (this.hb.hovered) {

            if (!this.hb.clickStarted) {
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, Settings.scale));
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, Settings.scale));

            } else {
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, 0.9F * Settings.scale));

            }
        } else {
            ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, 0.9F * Settings.scale));
        }
        super.update();
    }
}