package hellospire.packs;

import hellospire.SonicMod;
import hellospire.cards.*;
import thePackmaster.packs.AbstractCardPack;

import java.util.ArrayList;

import static hellospire.SonicMod.makeID;
import static thePackmaster.SpireAnniversary5Mod.makeImagePath;

public class SonicStylePack extends AbstractHedgehogPack {
    public static final String ID = makeID("SonicStylePack");

    public SonicStylePack() {
        super(ID, RocketAccel.ID, new AbstractCardPack.PackSummary(4, 2, 3, 3, 2));
    }

    public ArrayList<String> getCards() {
        ArrayList<String> cards = new ArrayList<>();

        // 3 Commons, 4 Uncommons, 3 Rares
        // cards.add(.ID);
        cards.add(hellospire.cardsPackExclusive.Boost.ID);
        cards.add(hellospire.cardsPackExclusive.BouncePad.ID);
        cards.add(hellospire.cardsPackExclusive.HomingAttack.ID);
        cards.add(DropDash.ID);

        cards.add(BackSpinKick.ID);
        cards.add(DashPanel.ID);
        cards.add(SpinDash.ID);

        cards.add(LoopDeLoop.ID);
        cards.add(BlastProcessing.ID);
        cards.add(RocketAccel.ID);

        return cards;
    }

}