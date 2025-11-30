package theHedgehog.packs;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import theHedgehog.SonicMod;
import thePackmaster.packs.AbstractCardPack;
import thePackmaster.packs.PackPreviewCard;

import static thePackmaster.SpireAnniversary5Mod.makeCardPath;
import static thePackmaster.SpireAnniversary5Mod.makeImagePath;

public abstract class AbstractHedgehogPack extends AbstractCardPack {
    private String previewArtCardID;

    public AbstractHedgehogPack(String id, String previewArt, AbstractCardPack.PackSummary summary) {
        super(
                id,
                CardCrawlGame.languagePack.getUIString(id).TEXT[0],
                CardCrawlGame.languagePack.getUIString(id).TEXT[1],
                CardCrawlGame.languagePack.getUIString(id).TEXT[2],
                summary);
        // super(id, "Card Spam Pack", "Play attacks to deal damage", "SpireWolf25", summary);
        previewArtCardID = previewArt;
        previewPackCard = this.makePreviewCard();
    }

    public PackPreviewCard makePreviewCard() {
        if (previewArtCardID == null) return super.makePreviewCard();
        // return new PackPreviewCard(packID, SonicMod.imagePath("cards/attack" + previewArtCardID.replace(SonicMod.modID + ":", "")+".png"), this);
        return new PackPreviewCard(packID, makeCardPath(previewArtCardID.replace(SonicMod.modID + ":", "") + ".png"), this);
    }

    public String getHatPath() {
        return makeImagePath("hats/" + packID.replace(SonicMod.modID + ":", "") + "Hat.png");
    }
}