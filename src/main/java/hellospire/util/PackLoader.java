package hellospire.util;

import basemod.AutoAdd;

import hellospire.SonicMod;
import hellospire.cards.BaseCard;
import hellospire.character.Sonic;
import hellospire.packs.AbstractHedgehogPack;
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