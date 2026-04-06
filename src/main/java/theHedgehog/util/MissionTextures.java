package theHedgehog.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MissionTextures {
    static Texture textureRankS = TextureLoader.getPowerTexture("MissionSPower");
    static Texture textureRankA = TextureLoader.getPowerTexture("MissionAPower");
    static Texture textureRankB = TextureLoader.getPowerTexture("MissionBPower");
    static Texture textureRankC = TextureLoader.getPowerTexture("MissionCPower");
    public static Texture textureRankS_large = TextureLoader.getHiDefPowerTexture("MissionSPower");
    public static Texture textureRankA_large = TextureLoader.getHiDefPowerTexture("MissionAPower");
    public static Texture textureRankB_large = TextureLoader.getHiDefPowerTexture("MissionBPower");
    public static Texture textureRankC_large = TextureLoader.getHiDefPowerTexture("MissionCPower");
    static TextureAtlas.AtlasRegion atlasRankS = new TextureAtlas.AtlasRegion(textureRankS, 0, 0, textureRankS.getWidth(), textureRankS.getHeight());
    static TextureAtlas.AtlasRegion atlasRankA = new TextureAtlas.AtlasRegion(textureRankA, 0, 0, textureRankA.getWidth(), textureRankA.getHeight());
    static TextureAtlas.AtlasRegion atlasRankB = new TextureAtlas.AtlasRegion(textureRankB, 0, 0, textureRankB.getWidth(), textureRankB.getHeight());
    static TextureAtlas.AtlasRegion atlasRankC = new TextureAtlas.AtlasRegion(textureRankC, 0, 0, textureRankC.getWidth(), textureRankC.getHeight());

    static public void updateIconA(AbstractPower __instance, int amount, int S, int A, int B) {
        if (false) {}
        else if (amount <= S) { __instance.img = textureRankS; __instance.region48 = atlasRankS; }
        else if (amount <= A) { __instance.img = textureRankA; __instance.region48 = atlasRankA; }
        else if (amount <= B) { __instance.img = textureRankB; __instance.region48 = atlasRankB; }
        else { __instance.img = textureRankC; __instance.region48 =  atlasRankC; }
    }

    static public void updateIconZ(AbstractPower __instance, int amount, int S, int A, int B) {
        if (false) {}
        else if (amount <= B) { __instance.img = textureRankC; __instance.region48 = atlasRankC; }
        else if (amount <= A) { __instance.img = textureRankB; __instance.region48 = atlasRankB; }
        else if (amount <= S) { __instance.img = textureRankA; __instance.region48 = atlasRankA; }
        else { __instance.img = textureRankS; __instance.region48 =  atlasRankS; }
    }
}
