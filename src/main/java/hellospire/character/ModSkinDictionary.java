package hellospire.character;

import hellospire.SonicMod;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static hellospire.SonicMod.characterPath;

public class ModSkinDictionary {
    private static final Map<String, ModSkin> modAnimations = new HashMap<>();

    public static class ModSkin {
        private final String id;
        private final String name;
        private final String path;
        private final Map<String, Boolean> animationKeys;

        private ModSkin(ModSkinBuilder builder) {
            this.id = builder.id;
            this.name = builder.name;
            this.path = builder.path;
            this.animationKeys = Collections.unmodifiableMap(builder.animationKeys);
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public String getPath() { return path; }
        public boolean hasAnimation(String key) {
            return animationKeys.getOrDefault(key, false);
        }
    }

    public static class ModSkinBuilder {
        private final String id;
        private final String name;
        private final String path;
        private final Map<String, Boolean> animationKeys = new HashMap<>();

        public ModSkinBuilder(String id, String name, String path) {
            this.id = id;
            this.name = name;
            this.path = path;
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
        registerCharacter(SonicMod.makeID("skinBase"), "Battle Sonic", characterPath("animation/SonicBattlePose.scml"),
                "idle", "happy", "hurt", "attack", "super");

        registerCharacter(SonicMod.makeID("skinBattleSonicBlue"), "Battle Sonic Blue", characterPath("animation/blue/SonicBattlePose.scml"),
                "idle", "happy", "hurt", "attack", "super");

        registerCharacter(SonicMod.makeID("skinBattleSonicGreen"), "Battle Sonic Green", characterPath("animation/green/SonicBattlePose.scml"),
                "idle", "happy", "hurt", "attack", "super");

        registerCharacter(SonicMod.makeID("skinBattleSonicRed"), "Battle Sonic Red", characterPath("animation/red/SonicBattlePose.scml"),
                "idle", "happy", "hurt", "attack", "super");

        registerCharacter(SonicMod.makeID("skinCaptainSonic"), "Captain Sonic", characterPath("animation/captain/SonicCaptainPose.scml"),
                "idle", "idle2", "happy", "happy2", "hurt", "hurt2", "attack", "attack2", "super");

        registerCharacter(SonicMod.makeID("skinBattleKnuckles"), "Battle Knuckles", characterPath("animation/knuckles/battleKnuckles.scml"),
                "idle", "idle2", "idle3", "attack");

        registerCharacter(SonicMod.makeID("skinBattleShadow"), "Battle Shadow", characterPath("animation/shadow/battleShadow.scml"),
                "idle", "idle2", "idle3");

        registerCharacter(SonicMod.makeID("skinBattleTails"), "Battle Tails", characterPath("animation/tails/battleTails.scml"),
                "idle", "idle2", "idle3");
    }

    private static void registerCharacter(String id, String name, String path, String... animations) {
        ModSkinBuilder builder = new ModSkinBuilder(id, name, path);
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
