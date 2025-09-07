// package hellospire.multiplayer;
//
//
// import basemod.ReflectionHacks;
//
// import com.badlogic.gdx.graphics.Color;
// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
// import hellospire.character.Sonic;
// import skindex.registering.SkindexRegistry;
// import skindex.skins.player.PlayerSkin;
// import spireTogether.SpireTogetherMod;
// import spireTogether.UnlockableItem.UnlockMethod;
// import spireTogether.modcompat.generic.energyorbs.CustomizableEnergyOrbCustom;
// import spireTogether.monsters.CharacterEntity;
// import spireTogether.monsters.playerChars.NetworkCharPreset;
// import spireTogether.ui.elements.presets.Nameplate;
// import spireTogether.util.UIElements;
//
// public class NetworkHedgehog extends NetworkCharPreset {
//     private static final float ORB_SCALE = ReflectionHacks.getPrivateStatic(CustomizableEnergyOrbCustom.class, "ORB_IMG_SCALE");
//     public static Nameplate rewardNameplate = (Nameplate)new Nameplate("reward_bogwarden", Color.valueOf("2B2B2B"), Color.valueOf("2B2B2B"), UnlockMethod.ACHIEVEMENT).SetUnlockDescription("This nameplate is unlocked by beating RECOMMENDED with The Bogwarden");
//
//     public NetworkHedgehog() {
//         super(new TheBogwarden(TheBogwarden.characterStrings.NAMES[1], TheBogwarden.Enums.THE_BOGWARDEN_OCEAN));
//         loadAnimation(makeImagePath("char/mainChar/bogwarden.atlas"), makeImagePath("char/mainChar/bogwarden.json"), 1f);
//         lobbyScale = 1.6f;
//     }
//
//     public String GetThreeLetterID() {
//         return "STH";
//     }
//
//     public PlayerSkin GetGhostSkin() {
//         return SkindexRegistry.getPlayerSkinByClassAndId(playerClass, makeID("ghost"));
//     }
//
//     public CharacterEntity CreateNew() {
//         return new NetworkBogwarden();
//     }
//
//     public Texture GetNameplateIcon(String s) {
//         return TexLoader.getTexture(makeImagePath("char/multiplayer/icons/"+s+".png"));
//     }
//
//     public Texture GetDefaultIcon() {
//         return GetNameplateIcon("basic");
//     }
//
//     public Texture GetWhiteSpecialIcon() {
//         return GetNameplateIcon("whiteSpecial");
//     }
//
//     public Nameplate GetNameplateUnlock() {
//         return rewardNameplate;
//     }
//
//     public Color GetCharColor() {
//         return SonicMod.characterColor.cpy();
//         return BogMod.characterColor.cpy();
//     }
//
//     @SpirePatch(clz=SpireTogetherMod.class, method="RegisterModdedChars", requiredModId="spireTogether")
//     public static class Register {
//         public static void Postfix() {
//             SpireTogetherMod.allCharacterEntities.put(TheBogwarden.Enums.THE_BOGWARDEN_OCEAN, new NetworkBogwarden());
//         }
//     }
//
//     @SpirePatch(clz=UIElements.Nameplates.class, method="Init", requiredModId="spireTogether")
//     public static class AddNameplate {
//         public static void Postfix() {
//             UIElements.Nameplates.nameplates.add(rewardNameplate);
//             for (String s : new String[] {"briarpatch"})
//                 UIElements.Nameplates.nameplates.add(new Nameplate(s, Color.valueOf("2B2B2B"), Color.valueOf("2B2B2B"), UnlockMethod.FREE));
//         }
//     }
// }