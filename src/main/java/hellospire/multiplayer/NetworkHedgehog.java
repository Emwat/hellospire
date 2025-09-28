package hellospire.multiplayer;


import basemod.ReflectionHacks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import hellospire.SonicMod;
import hellospire.character.Sonic;
import hellospire.util.TextureLoader;
import skindex.registering.SkindexRegistry;
import skindex.skins.player.PlayerSkin;
import spireTogether.SpireTogetherMod;
import spireTogether.UnlockableItem.UnlockMethod;
import spireTogether.monsters.CharacterEntity;
import spireTogether.monsters.playerChars.NetworkCharPreset;
import spireTogether.ui.elements.presets.Nameplate;
import spireTogether.util.UIElements;

public class NetworkHedgehog extends NetworkCharPreset {
    public static Nameplate rewardNameplate = (Nameplate)new Nameplate(
            "reward_hedgehog",
            Color.valueOf("0D2E71"),
            Color.valueOf("0D2E71"), UnlockMethod.ACHIEVEMENT)
            .SetUnlockDescription("This nameplate is unlocked by beating RECOMMENDED with The Hedgehog");

    public NetworkHedgehog() {
        super(new Sonic());
        // lobbyScale = 1f;
    }

    public String GetThreeLetterID() {
        return "STH";
    }

    public PlayerSkin GetGhostSkin() {
        return SkindexRegistry.getPlayerSkinByClassAndId(playerClass, SonicMod.makeID("ghost"));
    }

    public CharacterEntity CreateNew() {
        return new NetworkHedgehog();
    }

    public Texture GetNameplateIcon(String s) {
        return TextureLoader.getTexture("spireTogetherResources/images/ui/charIcons/" + s + ".png");
    }

    public Texture GetDefaultIcon() {
        return GetNameplateIcon("HedgehogIcon");
    }

    public Texture GetWhiteSpecialIcon() {
        return GetNameplateIcon("whiteSpecial/HedgehogIcon");
    }

    public Nameplate GetNameplateUnlock() {
        return rewardNameplate;
    }

    public Color GetCharColor() {
        return Sonic.Meta.cardColor.cpy();
    }

    @SpirePatch(clz=SpireTogetherMod.class, method="RegisterModdedChars", requiredModId="spireTogether")
    public static class Register {
        public static void Postfix() {
            SpireTogetherMod.allCharacterEntities.put(Sonic.Meta.THE_HEDGEHOG, new NetworkHedgehog());
        }
    }

    @SpirePatch(clz=UIElements.Nameplates.class, method="Init", requiredModId="spireTogether")
    public static class AddNameplate {
        public static void Postfix() {
            UIElements.Nameplates.nameplates.add(rewardNameplate);
        }
    }
}