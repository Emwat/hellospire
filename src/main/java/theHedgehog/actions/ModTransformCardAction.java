// package theHedgehog.actions;
//
// import com.badlogic.gdx.graphics.Color;
// import com.megacrit.cardcrawl.actions.AbstractGameAction;
// import com.megacrit.cardcrawl.cards.AbstractCard;
// import com.megacrit.cardcrawl.core.Settings;
// import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
// import theHedgehog.SonicTags;
// import theHedgehog.cards.BaseCard;
//
// public class ModTransformCardAction extends AbstractGameAction {
//     private AbstractCard target;
//     private AbstractCard replacement;
//
//     /// Multiple Chaos Emerald Attacks tend to glitch. Can't use :/
//     public ModTransformCardAction(AbstractCard target, AbstractCard replacement) {
//         this.actionType = ActionType.CARD_MANIPULATION;
//         this.duration = Settings.ACTION_DUR_MED;
//
//         this.target = target;
//         this.replacement = replacement;
//         if (Settings.FAST_MODE) {
//             this.startDuration = 0.05F;
//         } else {
//             this.startDuration = 0.15F;
//         }
//
//         this.duration = this.startDuration;
//     }
//
//
//     public void update() {
//         if (this.duration == this.startDuration) {
//             int index = -1;
//             for (int i = 0; i < AbstractDungeon.player.hand.size(); i++) {
//                 AbstractCard thisCard = AbstractDungeon.player.hand.group.get(i);
//                 if (thisCard.equals(target)) {
//                     index = i;
//                     break;
//                 }
//             }
//
//             if (index != -1) {
//                 this.replacement.current_x = target.current_x;
//                 this.replacement.current_y = target.current_y;
//                 this.replacement.target_x = target.target_x;
//                 this.replacement.target_y = target.target_y;
//                 this.replacement.drawScale = 1.0F;
//                 this.replacement.targetDrawScale = target.targetDrawScale;
//                 this.replacement.angle = target.angle;
//                 this.replacement.targetAngle = target.targetAngle;
//                 this.replacement.superFlash(Color.WHITE.cpy());
//                 AbstractDungeon.player.hand.group.set(index, this.replacement);
//                 AbstractDungeon.player.hand.glowCheck();
//             } else {
//                 for (int i = 0; i < AbstractDungeon.player.discardPile.size(); i++) {
//                     AbstractCard thisCard = AbstractDungeon.player.discardPile.group.get(i);
//                     if (thisCard.equals(target)) {
//                         index = i;
//                         break;
//                     }
//                 }
//                 if (index != -1) {
//                     AbstractDungeon.player.discardPile.group.set(index, this.replacement);
//                 }
//             }
//         }
//
//         this.tickDuration();
//     }
//
//
// }
