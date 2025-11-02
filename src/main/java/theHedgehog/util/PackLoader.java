package theHedgehog.util;

import basemod.AutoAdd;

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
                .packageFilter("hellospire.packs")
                .any(AbstractHedgehogPack.class, (info, pack) -> SpireAnniversary5Mod.declarePack(pack));
    }
}