package theHedgehog.skins;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Prefs;
import org.apache.commons.lang3.math.NumberUtils;
import theHedgehog.MyModConfig;
import theHedgehog.SonicMod;
import theHedgehog.character.Sonic;
import theHedgehog.character.SonicTipTracker;

import java.util.*;

import static theHedgehog.SonicMod.characterPath;
import static theHedgehog.SonicMod.sonicmodConfig;

public class ModSkinDictionary {
    private static ModSkinDictionary instance;
    private static final Map<String, ModSkin> modAnimations = new LinkedHashMap<>();

    static public final String skinBaseID = SonicMod.makeID("skinBase");
    static public final String skinBattleSonicBlueID = SonicMod.makeID("skinBattleSonicBlue");
    static public final String skinBattleSonicRedID = SonicMod.makeID("skinBattleSonicRed");
    static public final String skinBattleSonicGreenID = SonicMod.makeID("skinBattleSonicGreen");
    static public final String skinCaptainSonicID = SonicMod.makeID("skinCaptainSonic");
    static public final String skinBattleAmyID = SonicMod.makeID("skinBattleAmy");
    static public final String skinBattleKnucklesID = SonicMod.makeID("skinBattleKnuckles");
    static public final String skinBattleShadowID = SonicMod.makeID("skinBattleShadow");
    static public final String skinBattleTailsID = SonicMod.makeID("skinBattleTails");
    public static final String CONFIG_CURRENT_SKIN = "SonicCurrentSkinID";
    public static final String defaultAnimationPath = characterPath("animation/SonicBattlePose.scml");

    public enum Contact {
        Amy,
        Knuckles,
        Shadow,
        Sonic,
        Tails
    }

    public static ModSkinDictionary getInstance() {
        if (instance == null) {
            instance = new ModSkinDictionary();
        }
        return instance;
    }

    public static class ModSkin {
        private final String id;
        private final String name;
        private final Contact contact;
        private final String characterPath;
        private final String description;
        private final String animationPath;
        private final Map<String, Boolean> animationKeys;

        private ModSkin(ModSkinBuilder builder) {
            this.id = builder.id;
            this.name = builder.name;
            this.contact = builder.contact;
            this.characterPath = builder.characterPath;
            this.animationPath = builder.animationPath;
            this.description = builder.description;
            this.animationKeys = Collections.unmodifiableMap(builder.animationKeys);
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getAnimationPath() {
            return animationPath;
        }

        public String getCharacterPath() {
            return characterPath;
        }

        public boolean hasAnimation(String key) {
            return animationKeys.getOrDefault(key, false);
        }

        public String getContact() {
            return contact.name();
        }
    }

    public static class ModSkinBuilder {
        private final String id;
        private final String name;
        private final Contact contact;
        private final String animationPath;
        private final String characterPath;
        private final String description;
        private final Map<String, Boolean> animationKeys = new HashMap<>();

        public ModSkinBuilder(String id, String name, Contact contact, String animationPath, String characterPath, String description) {
            this.id = id;
            this.name = name;
            this.contact = contact;
            this.animationPath = animationPath;
            this.characterPath = characterPath;
            this.description = description;
        }

        public ModSkinBuilder withAnimation(String key) {
            animationKeys.put(key, true);
            return this;
        }

        public ModSkin build() {
            return new ModSkin(this);
        }
    }

    static public void initializeModSkins() {
        registerCharacter(skinBaseID, "Battle Sonic", Contact.Sonic, defaultAnimationPath,
                newCharacterPath("character"),
                "Time to party!",
                "idle", "happy", "hurt", "attack", "super");

        registerCharacter(skinBattleSonicBlueID, "Battle Sonic Blue", Contact.Sonic, characterPath("animation/blue/SonicBattlePose.scml"),
                newCharacterPath("character"),
                "Blue Raspberry",
                "idle", "happy", "hurt", "attack", "super");

        registerCharacter(skinBattleSonicGreenID, "Battle Sonic Green", Contact.Sonic, characterPath("animation/green/SonicBattlePose.scml"),
                newCharacterPath("character"),
                "Gumball Eyes",
                "idle", "happy", "hurt", "attack", "super");

        registerCharacter(skinBattleSonicRedID, "Battle Sonic Red", Contact.Sonic, characterPath("animation/red/SonicBattlePose.scml"),
                newCharacterPath("character"),
                "Cherry",
                "idle", "happy", "hurt", "attack", "super");

        // if (!SonicTipTracker.tips.get(SonicTipTracker.HasUnlockedCaptainSonic)) {
        //     registerCharacter(skinCaptainSonicID, "Captain Sonic (locked)", Contact.Sonic, defaultAnimationPath,
        //             newCharacterPath("character"),
        //             "You must beat Ascension 10 to unlock Captain Sonic.",
        //             "idle", "happy", "hurt", "attack", "super");
        // } else {
        registerCharacter(skinCaptainSonicID, "Captain Sonic", Contact.Sonic, newCharacterPath("characterSonicCaptain/animation/SonicCaptainPose.scml"),
                newCharacterPath("characterSonicCaptain"),
                "See, I'm one of those 'leap before you think' kinda guys.",
                "idle", "idle2", "happy", "happy2", "hurt", "hurt2", "attack", "attack2", "super");
        // }

        registerCharacter(skinBattleTailsID, "Battle Tails", Contact.Tails, newCharacterPath("characterTails/animation/battleTails.scml"),
                newCharacterPath("characterTails"),
                "Long time no see!",
                "idle", "idle2", "idle3");

        registerCharacter(skinBattleKnucklesID, "Battle Knuckles", Contact.Knuckles, newCharacterPath("characterKnuckles/animation/battleKnuckles.scml"),
                newCharacterPath("characterKnuckles"),
                "Do I look like I need your power?",
                "idle", "idle2", "idle3", "attack");

        registerCharacter(skinBattleAmyID, "Battle Amy", Contact.Amy, newCharacterPath("characterAmy/animation/battleAmy.scml"),
                newCharacterPath("characterAmy"),
                "L, Let's not use the hammer.",
                "idle", "happy", "hurt", "attack");

        registerCharacter(skinBattleShadowID, "Battle Shadow", Contact.Shadow, newCharacterPath("characterShadow/animation/battleShadow.scml"),
                newCharacterPath("characterShadow"),
                "The military has mistaken me for the likes of you!",
                "idle", "idle2", "idle3");

    }

    static public void initializeModSkinsUnlockables() {

    }

    private static String newCharacterPath(String folder) {
        SonicMod.logger.info(SonicMod.imagePath(folder));
        return SonicMod.imagePath(folder);
    }

    private static void registerCharacter(String id, String name, Contact contact, String animationPath, String characterPath, String description, String... animations) {
        ModSkinBuilder builder = new ModSkinBuilder(id, name, contact, animationPath, characterPath, description);
        for (String animation : animations) {
            builder.withAnimation(animation);
        }
        modAnimations.put(id, builder.build());
    }

    public static ModSkin getBaseSkin() {
        return modAnimations.get(skinBaseID);
    }

    public static ModSkin getModSkin(String id) {
        if (modAnimations.containsKey(id))
            return modAnimations.get(id);
        return modAnimations.get(skinBaseID);
    }

    public static Map<String, ModSkin> getModAnimations() {
        return modAnimations;
    }

    public float getMaxNameLength() {
        float max = -1;
        for (ModSkin skin : modAnimations.values()) {
            max = NumberUtils.max(FontHelper.getWidth(SkinSelectionUI.skinNameFont, skin.name, 1f), max);
        }

        return max;
    }

    public void iterateSkin(AbstractPlayer player, boolean forward) {
        ArrayList<ModSkin> skins = new ArrayList<>(modAnimations.values());

        int curIndex = skins.indexOf(modAnimations.get(sonicmodConfig.getString(CONFIG_CURRENT_SKIN)));
        if (forward) {
            if (++curIndex >= skins.size()) curIndex = 0;
        } else {
            if (--curIndex < 0) curIndex = skins.size() - 1;
        }
        saveCurrentModSkin(skins.get(curIndex).id);

        if (player instanceof Sonic)
            ((Sonic)player).setSkin(skins.get(curIndex).id);
    }

    public void saveCurrentModSkin(String id) {
        try {
            sonicmodConfig.setString(CONFIG_CURRENT_SKIN, id);
            sonicmodConfig.save();
        } catch (Exception e) {
            SonicMod.logger.error(e);
        }
    }
}
