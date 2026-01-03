package theHedgehog.multiplayer;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import theHedgehog.skins.ModSkinDictionary;
import theHedgehog.character.Sonic;
import skindex.itemtypes.CustomizableItem;
import skindex.registering.SkindexPlayerSkinRegistrant;
import skindex.registering.SkindexRegistry;
import skindex.skins.player.*;
import skindex.unlockmethods.FreeUnlockMethod;

import static theHedgehog.SonicMod.makeID;

public class Skindexer implements SkindexPlayerSkinRegistrant {
    public static void register() {
        SkindexRegistry.subscribe(new Skindexer());
    }

    public List<PlayerSkin> getDefaultPlayerSkinsToRegister() {
        return Arrays.asList(new SonicSkin(ModSkinDictionary.skinBaseID));
    }

    public List<PlayerSkin> getPlayerSkinsToRegister() {
        return ModSkinDictionary.getModAnimations().values()
                .stream()
                .map(modSkin -> new SonicSkin(modSkin.getId()))
                .collect(Collectors.toList());
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
            if (AbstractDungeon.player instanceof Sonic) {
                ((Sonic)AbstractDungeon.player).setupAnimation(id);
            }
            return super.loadOnPlayer();
        }

        private static class SonicSkinData extends PlayerSpriterSkinData {
            public SonicSkinData(String id) {
                ModSkinDictionary.ModSkin modSkin = ModSkinDictionary.getModSkin(id);

                this.scmlUrl = modSkin.getAnimationPath();
                this.id = id;
                this.name = modSkin.getName(); //CardCrawlGame.languagePack.getUIString(id).TEXT[0];
                this.scale = 2F;

                unlockMethod = FreeUnlockMethod.methodId;
                playerClass = Sonic.Meta.THE_HEDGEHOG.name();

            }
        }
    }
}