// package theHedgehog.packsB;
//
// import theHedgehog.cards.*;
// import theHedgehog.packsA.AbstractHedgehogPack;
//
// import java.util.ArrayList;
//
// import static theHedgehog.SonicMod.makeID;
//
// public class SonicDizzySpinPack extends AbstractHedgehogPack {
//     public static final String ID = makeID("SonicDizzySpinPack");
//
//     public SonicDizzySpinPack() {
//         super(ID, DizzySpin.ID, new PackSummary(3, 2, 4, 3, 1,
//                 PackSummary.Tags.None));
//     }
//
//     public ArrayList<String> getCards() {
//         ArrayList<String> cards = new ArrayList<>();
//
//         // 3 Commons, 4 Uncommons, 3 Rares
//         // cards.add(.ID);
//         cards.add(DizzySpin.ID);
//         cards.add(Bumper.ID);
//         cards.add(Windmill.ID);
//
//         cards.add(MeteorKick.ID);
//         cards.add(BlueTornado.ID);
//         cards.add(DashPanel.ID);
//         cards.add(Momentum.ID);
//
//         cards.add(EndlessBoost.ID);
//         cards.add(SpeedBreak.ID);
//         cards.add(BackSpinKickRare.ID);
//
//         return cards;
//     }
//
// }