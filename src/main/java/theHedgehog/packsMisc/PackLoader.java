package theHedgehog.packsMisc;

import basemod.AutoAdd;

import com.evacipated.cardcrawl.modthespire.Loader;
import theHedgehog.SonicMod;
import theHedgehog.cards.BaseCard;
import theHedgehog.character.Sonic;
import theHedgehog.packs.AbstractHedgehogPack;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.interfaces.EditPacksSubscriber;

public class PackLoader implements EditPacksSubscriber {
    @Override
    public void receiveEditPacks() {
        SpireAnniversary5Mod.allowCardClass(BaseCard.class);
        SpireAnniversary5Mod.allowCardColor(Sonic.Meta.CARD_COLOR);
        new AutoAdd(SonicMod.modID)
                .packageFilter("theHedgehog.packs")
                .any(AbstractHedgehogPack.class, (info, pack) -> SpireAnniversary5Mod.declarePack(pack));
        // if (Loader.isModLoaded("soniclowhealthmusic")) {
        //     new AutoAdd(SonicMod.modID)
        //             .packageFilter("theHedgehog.packsB")
        //             .any(AbstractHedgehogPack.class, (info, pack) -> SpireAnniversary5Mod.declarePack(pack));
        // }
    }
}