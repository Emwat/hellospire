// package theHedgehog.cards;
//
// import com.badlogic.gdx.graphics.Color;
// import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
// import com.megacrit.cardcrawl.actions.common.GainBlockAction;
// import com.megacrit.cardcrawl.cards.AbstractCard;
// import com.megacrit.cardcrawl.characters.AbstractPlayer;
// import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
// import com.megacrit.cardcrawl.monsters.AbstractMonster;
// import theHedgehog.character.Sonic;
// import theHedgehog.util.CardStats;
//
// public class CloseShaveRage extends BaseCard {
//     public static final String ID = makeID("CloseShave");
//     private static final CardStats info = new CardStats(
//             Sonic.Meta.CARD_COLOR,
//             CardType.SKILL,
//             CardRarity.UNCOMMON,
//             CardTarget.SELF,
//             0
//     );
//
//     private static final int BLOCK = 2;
//     private static final int UPG_BLOCK = 2;
//     // private int blockDeductions = 0;
//     private static final Color FLAVOR_BOX_COLOR = Color.BLUE.cpy();
//     private static final Color FLAVOR_TEXT_COLOR = new Color(251F, 202F, 6F, 1.0F);
//
//     public CloseShaveRage() {
//         super(ID, info);
//         setBlock(BLOCK, UPG_BLOCK);
//
//         FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
//         FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
//     }
//
//     @Override
//     public void use(AbstractPlayer p, AbstractMonster m) {
//         if (p == null || p.hand.isEmpty()) {
//             return;
//         }
//
//         for (int i = 0; i < CalculateNumberOfNonSkill(p); i++) {
//             addToBot(new GainBlockAction(p, block));
//         }
//     }
//
//     public void applyPowers() {
//         super.applyPowers();
//         this.baseMagicNumber = 0;
//         this.magicNumber = 0;
//
//         this.baseMagicNumber = CalculateNumberOfNonSkill(AbstractDungeon.player);
//     }
//
//     private int CalculateNumberOfNonSkill(AbstractPlayer p){
//         if (p == null || p.hand.isEmpty()) {
//             return 0;
//         }
//
//         int output = 0;
//         for (int i = 0; i < p.hand.size(); i++) {
//             AbstractCard handCard = p.hand.group.get(i);
//             if (handCard.type != CardType.SKILL){
//                 output++;
//             }
//         }
//         return output;
//     }
//
//     @Override
//     public AbstractCard makeCopy() { // Optional
//         return new CloseShaveRage();
//     }
// }
