package theHedgehog.packs;

import theHedgehog.cards.*;
import thePackmaster.packs.AbstractCardPack;

import java.util.ArrayList;

import static theHedgehog.SonicMod.makeID;

public class SonicStylePack extends AbstractHedgehogPack {
    public static final String ID = makeID("SonicStylePack");

    public SonicStylePack() {
        super(ID, RocketAccel.ID, new AbstractCardPack.PackSummary(2, 2, 3, 4, 2,
                PackSummary.Tags.Exhaust, PackSummary.Tags.Tokens));
    }

    public ArrayList<String> getCards() {
        ArrayList<String> cards = new ArrayList<>();

        // 3 Commons, 4 Uncommons, 3 Rares
        // cards.add(.ID);
        cards.add(theHedgehog.cardsPackExclusive.Boost.ID);
        cards.add(theHedgehog.cardsPackExclusive.BouncePad.ID);
        cards.add(theHedgehog.cardsPackExclusive.HomingAttack.ID);
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