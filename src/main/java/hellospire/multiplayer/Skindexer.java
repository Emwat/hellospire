package hellospire.multiplayer;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.Arrays;
import java.util.List;

import hellospire.SonicMod;
import hellospire.character.Sonic;
import skindex.itemtypes.CustomizableItem;
import skindex.registering.SkindexPlayerSkinRegistrant;
import skindex.registering.SkindexRegistry;
import skindex.skins.player.*;
import skindex.unlockmethods.FreeUnlockMethod;

import static hellospire.SonicMod.characterPath;
import static hellospire.SonicMod.makeID;


public class Skindexer implements SkindexPlayerSkinRegistrant {
    public static void register() {
        SkindexRegistry.subscribe(new Skindexer());
    }

    public List<PlayerSkin> getDefaultPlayerSkinsToRegister() {
        return Arrays.asList(new SonicSkin(makeID("skinBase")));
    }

    public List<PlayerSkin> getPlayerSkinsToRegister() {
        return Arrays.asList(
                new SonicSkin(makeID("blue")),
                new SonicSkin(makeID("red")),
                new SonicSkin(makeID("green")),
                new SonicSkin(makeID("ghost"))
        );
    }

    public static class SonicSkin extends PlayerSpriterSkin {
        public SonicSkin(String id) {
            super(new SonicSkinData(id));
            scale = 1.0f;
        }

        @Override
        public CustomizableItem makeCopy() {
            return new SonicSkin(id);
        }

        @Override
        public boolean loadOnPlayer() {
            if (AbstractDungeon.player != null && AbstractDungeon.player instanceof Sonic)
                ((Sonic)AbstractDungeon.player).setupAnimation(id.equals(makeID("skinBase")) ? "character" : "character/"+id.replace(SonicMod.modID + ":", ""));
            return super.loadOnPlayer();
        }

        private static class SonicSkinData extends PlayerSpriterSkinData {
            public SonicSkinData(String id) {
                if (id.equals(makeID("skinBase"))) {
                    scmlUrl = characterPath("animation/SonicBattlePose.scml");
                } else {
                    scmlUrl = characterPath("animation/" + id.replace(SonicMod.modID + ":", "") + "/SonicBattlePose.scml");
                }

                this.id = id;
                name = id; //CardCrawlGame.languagePack.getUIString(id).TEXT[0];
                SonicMod.logger.info("SonicSkinData name is " + name);
                this.scale = 2F;

                unlockMethod = FreeUnlockMethod.methodId;
                playerClass = Sonic.Meta.THE_HEDGEHOG.name();
                SonicMod.logger.info("SonicSkinData playerClass name() is " + playerClass);

            }
        }
    }
}