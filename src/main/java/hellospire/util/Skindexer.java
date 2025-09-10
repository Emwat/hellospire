// package hellospire.util;
//
// import com.badlogic.gdx.Gdx;
// import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
// import java.util.Arrays;
// import java.util.List;
//
// import hellospire.SonicMod;
// import hellospire.character.Sonic;
// import skindex.itemtypes.CustomizableItem;
// import skindex.registering.SkindexPlayerSkinRegistrant;
// import skindex.registering.SkindexRegistry;
// import skindex.skins.player.*;
// import skindex.unlockmethods.FreeUnlockMethod;
//
// import static hellospire.SonicMod.characterPath;
// import static hellospire.SonicMod.makeID;
//
//
// public class Skindexer implements SkindexPlayerSkinRegistrant {
//     public static void register() {
//         SkindexRegistry.subscribe(new Skindexer());
//     }
//
//     public List<PlayerSkin> getDefaultPlayerSkinsToRegister() {
//         return Arrays.asList(new SonicSkin(makeID("skinBase")));
//     }
//
//     public List<PlayerSkin> getPlayerSkinsToRegister() {
//         return Arrays.asList(
//                 new SonicSkin(makeID("blue")),
//                 new SonicSkin(makeID("red")),
//                 new SonicSkin(makeID("yellow")),
//                 new SonicSkin(makeID("ghost"))
//         );
//     }
//
//     public static class SonicSkin extends PlayerSpriterSkin {
//         public SonicSkin(String id) {
//             super(new SonicSkinData(id));
//         }
//
//         @Override
//         public CustomizableItem makeCopy() {
//             return new SonicSkin(id);
//         }
//
//         private static class SonicSkinData extends PlayerSpriterSkinData {
//             public SonicSkinData(String id) {
//                 scmlUrl = characterPath("animation/SonicBattlePose.scml");
//
//                 this.id = id;
//                 name = id; //CardCrawlGame.languagePack.getUIString(id).TEXT[0];
//                 SonicMod.logger.info("SonicSkinData name is " + name);
//                 this.scale = 2F;
//
//                 unlockMethod = FreeUnlockMethod.methodId;
//                 playerClass = Sonic.Meta.THE_HEDGEHOG.name();
//                 SonicMod.logger.info("SonicSkinData playerClass name() is " + playerClass);
//
//             }
//         }
//     }
// }