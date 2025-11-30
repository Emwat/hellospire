// package theHedgehog.cardsPackExclusive;
//
// import com.evacipated.cardcrawl.modthespire.Loader;
// import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
// import com.megacrit.cardcrawl.cards.AbstractCard;
// import com.megacrit.cardcrawl.characters.AbstractPlayer;
// import com.megacrit.cardcrawl.monsters.AbstractMonster;
// import theHedgehog.cards.BaseCard;
// import theHedgehog.character.Sonic;
// import theHedgehog.powers.BlastProcessingPower;
// import theHedgehog.util.CardStats;
// import thePackmaster.ThePackmaster;
//
// public class BlastProcessing extends BaseCard {
//     public static final String ID = makeID("PackBlastProcessing");
//     private static final CardStats info = Loader.isModLoaded("anniv5") ?
//             new CardStats(
//             ThePackmaster.Enums.PACKMASTER_RAINBOW,
//             CardType.POWER,
//             CardRarity.RARE,
//             CardTarget.SELF,
//             1
//     ) : new CardStats(
//             Sonic.Meta.CARD_COLOR,
//             CardType.POWER,
//             CardRarity.SPECIAL,
//             CardTarget.SELF,
//             1
//     );
//
//     private static final int MAGIC = 2;
//
//     public BlastProcessing() {
//         super(ID, info);
//
//         setMagic(MAGIC);
//     }
//
//     @Override
//     public void use(AbstractPlayer p, AbstractMonster m) {
//         addToBot(new ApplyPowerAction(p, p, new BlastProcessingPower(p, magicNumber), magicNumber));
//     }
//
//     public void upgrade() {
//         if (!this.upgraded) {
//             this.setInnate(true);
//         }
//
//         super.upgrade();
//     }
//
//     @Override
//     public AbstractCard makeCopy() { //Optional
//         return new BlastProcessing();
//     }
// }
