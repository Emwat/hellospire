package theHedgehog.character;

import theHedgehog.SonicMod;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static theHedgehog.SonicMod.characterPath;

public class ModSkinDictionary {
    private static final Map<String, ModSkin> modAnimations = new HashMap<>();

    static public final String skinBaseID = SonicMod.makeID("skinBase");
    static public final String skinBattleSonicBlueID = SonicMod.makeID("skinBattleSonicBlue");
    static public final String skinBattleSonicRedID = SonicMod.makeID("skinBattleSonicRed");
    static public final String skinBattleSonicGreenID = SonicMod.makeID("skinBattleSonicGreen");
    static public final String skinCaptainSonicID = SonicMod.makeID("skinCaptainSonic");
    static public final String skinBattleAmyID = SonicMod.makeID("skinBattleAmy");
    static public final String skinBattleKnucklesID = SonicMod.makeID("skinBattleKnuckles");
    static public final String skinBattleShadowID = SonicMod.makeID("skinBattleShadow");
    static public final String skinBattleTailsID = SonicMod.makeID("skinBattleTails");

    public static class ModSkin {
        private final String id;
        private final String name;
        private final String cardbackPath;
        private final String description;
        private final String path;
        private final Map<String, Boolean> animationKeys;

        private ModSkin(ModSkinBuilder builder) {
            this.id = builder.id;
            this.name = builder.name;
            this.cardbackPath = builder.cardbackPath;
            this.path = builder.path;
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

        public String getPath() {
            return path;
        }

        public String getCardbackPath() {
            return cardbackPath;
        }

        public boolean hasAnimation(String key) {
            return animationKeys.getOrDefault(key, false);
        }
    }

    public static class ModSkinBuilder {
        private final String id;
        private final String name;
        private final String path;
        private final String cardbackPath;
        private final String description;
        private final Map<String, Boolean> animationKeys = new HashMap<>();

        public ModSkinBuilder(String id, String name, String path, String cardbackPath, String description) {
            this.id = id;
            this.name = name;
            this.path = path;
            this.cardbackPath = cardbackPath;
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

    public static void initializeModSkins() {
        registerCharacter(skinBaseID, "Battle Sonic", characterPath("animation/SonicBattlePose.scml"),
                SonicMod.characterPath("cardback/"),
                "Time to party!",
                "idle", "happy", "hurt", "attack", "super");

        registerCharacter(skinBattleSonicBlueID, "Battle Sonic Blue", characterPath("animation/blue/SonicBattlePose.scml"),
                SonicMod.characterPath("cardback/"),
                "Extra blue!",
                "idle", "happy", "hurt", "attack", "super");

        registerCharacter(skinBattleSonicGreenID, "Battle Sonic Green", characterPath("animation/green/SonicBattlePose.scml"),
                SonicMod.characterPath("cardback/"),
                "Now you're almost like the Silent.",
                "idle", "happy", "hurt", "attack", "super");

        registerCharacter(skinBattleSonicRedID, "Battle Sonic Red", characterPath("animation/red/SonicBattlePose.scml"),
                SonicMod.characterPath("cardback/"),
                "Grr.",
                "idle", "happy", "hurt", "attack", "super");

        registerCharacter(skinCaptainSonicID, "Captain Sonic", characterPath("animation/captain/SonicCaptainPose.scml"),
                SonicMod.characterPath("cardback/"),
                "See, I'm one of those 'leap before you think' kinda guys.",
                "idle", "idle2", "happy", "happy2", "hurt", "hurt2", "attack", "attack2", "super");

        registerCharacter(skinBattleAmyID, "Battle Amy", characterPath("animation/amy/battleAmy.scml"),
                SonicMod.characterPath("cardback/amy/"),
                "L, Let's not use the hammer.",
                "idle", "happy", "hurt", "attack");

        registerCharacter(skinBattleKnucklesID, "Battle Knuckles", characterPath("animation/knuckles/battleKnuckles.scml"),
                SonicMod.characterPath("cardback/knuckles/"),
                "Way to go, Knucklehead!",
                "idle", "idle2", "idle3", "attack");

        registerCharacter(skinBattleShadowID, "Battle Shadow", characterPath("animation/shadow/battleShadow.scml"),
                SonicMod.characterPath("cardback/shadow/"),
                "The military has mistaken me for the likes of you!",
                "idle", "idle2", "idle3");

        registerCharacter(skinBattleTailsID, "Battle Tails", characterPath("animation/tails/battleTails.scml"),
                SonicMod.characterPath("cardback/tails/"),
                "Long time no see!",
                "idle", "idle2", "idle3");
    }

    private static void registerCharacter(String id, String name, String path, String cardbackPath, String description, String... animations) {
        ModSkinBuilder builder = new ModSkinBuilder(id, name, path, cardbackPath, description);
        for (String animation : animations) {
            builder.withAnimation(animation);
        }
        modAnimations.put(id, builder.build());
    }

    public static ModSkin getModSkin(String id) {
        return modAnimations.get(id);
    }

    public static Map<String, ModSkin> getModAnimations() {
        return modAnimations;
    }
}
