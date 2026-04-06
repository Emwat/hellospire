package theHedgehog.ui;

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
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.character.Sonic;
import theHedgehog.effects.ChaoEvolveEffect;
import theHedgehog.relics.*;
import theHedgehog.util.TextureLoader;

import static theHedgehog.SonicMod.makeID;

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
    public EvolveChaoOption() {
        this.label = DESCRIPTIONS[0];   // "Evolve Chao"

        EnableOrDisableButton();
    }

    @Override
    public void useOption() {
        if (this.usable) {
            AbstractCard.CardTags mostTagged = GetMostTagged();
            // AbstractDungeon.player.loseGold(HedgehogPack.goldCostToEvolve);
            // AbstractDungeon.player.loseRelic(ChaoRelic.ID);
            // GrantRelic(mostTagged);
            ChaoEvolveEffect chaoEvolveEffect = new ChaoEvolveEffect(this, mostTagged, this.hb.x, this.hb.y);
            AbstractDungeon.effectList.add(chaoEvolveEffect);
        }
    }

    private void GrantRelic(AbstractCard.CardTags tag) {
        AbstractRelic r = null;
        if (tag == SonicTags.LIKE_IRONCLAD) { r = new ChaoIroncladRelic(); }
        else if (tag == SonicTags.LIKE_SILENT) { r = new ChaoSilentRelic(); }
        else if (tag == SonicTags.LIKE_DEFECT) { r = new ChaoDefectRelic(); }
        else if (tag == SonicTags.LIKE_WATCHER) { r = new ChaoWatcherRelic(); }
        // AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.hb.x, this.hb.y, r);
        AbstractDungeon.getCurrRoom().addRelicToRewards(r);
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        AbstractDungeon.combatRewardScreen.open();
        // ((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI.reopen();
        // there was a bug with the fire sound persisting and I'm not sure why,
        // so this is basically a randomly thrown out preventative measure.
        // ((RestRoom) AbstractDungeon.getCurrRoom()).cutFireSound();
    }

    private AbstractCard.CardTags GetMostTagged() {
        if (AbstractDungeon.player instanceof Sonic) {
            return GetMostTaggedCards();
        } else {
            return GetMostCardTypes();
        }
    }

    private AbstractCard.CardTags GetMostTaggedCards(){
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

        if (maxCount == countIronclad) { return SonicTags.LIKE_IRONCLAD; }
        else if (maxCount == countSilent) { return SonicTags.LIKE_SILENT; }
        else if (maxCount == countDefect) { return SonicTags.LIKE_DEFECT; }
        else if (maxCount == countWatcher) { return SonicTags.LIKE_WATCHER; }
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

    private AbstractCard.CardTags GetMostCardTypes() {
        int countATK = 0;
        int countSKL = 0;
        int countPWR = 0;
        int countMISC = 0;
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card.type == AbstractCard.CardType.ATTACK) { countATK++; }
            else if (card.type == AbstractCard.CardType.SKILL) { countSKL++; }
            else if (card.type == AbstractCard.CardType.POWER) { countPWR++; }
            else { countMISC++; }
        }

        countATK = (int)(countATK * 1.3);
        countPWR = countPWR * 4;
        countMISC = countMISC * 13;
        int[] counts = {countATK, countSKL, countPWR, countMISC};
        int maxCount = counts[0];

        for (int i = 1; i < counts.length; i++) {
            if (counts[i] > maxCount) {
                maxCount = counts[i];
            }
        }

        if (maxCount == countATK) { return SonicTags.LIKE_IRONCLAD; }
        else if (maxCount == countSKL) { return SonicTags.LIKE_SILENT; }
        else if (maxCount == countPWR) { return SonicTags.LIKE_DEFECT; }
        else if (maxCount == countMISC) { return SonicTags.LIKE_WATCHER; }
        return SonicTags.LIKE_DEFECT;
    }

    public void EnableOrDisableButton() {
        // "Evolve Chao",
        //         "Requires a chao.",
        //         "(Free Action) Your chao is careless. Evolve your chao.",
        //         "(Free Action) Your chao is quiet. Evolve your chao.",
        //         "(Free Action) Your chao is energetic. Evolve your chao.",
        //         "(Free Action) Your chao is curious. Evolve your chao."
        boolean hasChao = AbstractDungeon.player.hasRelic(ChaoRelic.ID);

        if (!hasChao) {
            this.usable = false;
        }
        if (this.usable) {
            AbstractCard.CardTags tag = GetMostTagged();
            if (tag == SonicTags.LIKE_IRONCLAD) { this.description = DESCRIPTIONS[2]; }
            else if (tag == SonicTags.LIKE_SILENT) { this.description = DESCRIPTIONS[3]; }
            else if (tag == SonicTags.LIKE_DEFECT) { this.description = DESCRIPTIONS[4]; }
            else if (tag == SonicTags.LIKE_WATCHER) { this.description = DESCRIPTIONS[5]; }
            this.img = TextureLoader.getTexture(SonicMod.imagePath("ui/chaoCampfire.png"));
        } else {
            this.description = DESCRIPTIONS[1];
            this.img = TextureLoader.getTexture(SonicMod.imagePath("ui/chaoCampfireDisabled.png"));
        }
    }

    public void EnableOrDisableButtonWithGold() {
        boolean hasEnoughGold = AbstractDungeon.player.gold >= HedgehogPack.goldCostToEvolve;
        boolean hasChao = AbstractDungeon.player.hasRelic(ChaoRelic.ID);
        if (!hasChao && hasEnoughGold) {
            this.usable = false;
        }
        if (this.usable) {
            this.description = DESCRIPTIONS[1] + HedgehogPack.goldCostToEvolve + DESCRIPTIONS[2];
            this.img = TextureLoader.getTexture(SonicMod.imagePath("ui/chaoCampfire.png"));
        } else {
            this.description = DESCRIPTIONS[3] +  HedgehogPack.goldCostToEvolve + DESCRIPTIONS[4];
            this.img = TextureLoader.getTexture(SonicMod.imagePath("ui/chaoCampfireDisabled.png"));
        }
    }

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