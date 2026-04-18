// package theHedgehog.packsB;
//
// import theHedgehog.cards.*;
// import theHedgehog.packs.AbstractHedgehogPack;
//
// import java.util.ArrayList;
//
// import static theHedgehog.SonicMod.makeID;
//
// public class SonicElementalPack extends AbstractHedgehogPack {
//     public static final String ID = makeID("SonicElementalPack");
//
//     public SonicElementalPack() {
//         super(ID, ThunderShield.ID, new PackSummary(1, 1, 1, 1, 2,
//                 PackSummary.Tags.Orbs));
//     }
//
//     public ArrayList<String> getCards() {
//         ArrayList<String> cards = new ArrayList<>();
//
//         // 3 Commons, 4 Uncommons, 3 Rares
//         // cards.add(.ID);
//         cards.add(InstaShield.ID);
//         cards.add(SmoothLanding.ID);
//
//         cards.add(QuickAir.ID);
//         cards.add(Drift.ID);
//         cards.add(SonicWave.ID);
//         cards.add(Teaser.ID);
//
//         cards.add(ThunderShield.ID);
//         cards.add(Turbulence.ID);
//         cards.add(theHedgehog.cardsPackExclusive.LevelUp.ID);
//
//         return cards;
//     }
//
// }