package hellospire.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.Chrysalis;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import hellospire.SonicTags;

import java.util.ArrayList;

public class CrestOfFireAction extends AbstractGameAction {
    private DamageInfo info;
    private AbstractCard theCard = null;
    private AbstractPlayer thePlayer = null;

    public CrestOfFireAction(AbstractCreature target, DamageInfo info, AbstractPlayer p, AbstractCard card) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_MED;
        this.theCard = card;
        this.thePlayer = p;
    }

    public void update() {
        boolean didUpgrade = false;
        ArrayList<AbstractCard> upgradedCards = new ArrayList<>();
        if (this.duration == Settings.ACTION_DUR_MED && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.FIRE));
            this.target.damage(this.info);
            if ((((AbstractMonster) this.target).isDying || this.target.currentHealth <= 0) &&
                    !this.target.halfDead &&
                    !this.target.hasPower("Minion")) {
                for (AbstractCard theCard : thePlayer.masterDeck.group) {
                    if (theCard.tags.contains(SonicTags.CREST_OF_FIRE)) {
                        didUpgrade = true;
                        theCard.upgrade();
                        thePlayer.bottledCardUpgradeCheck(theCard);
                        upgradedCards.add(theCard);
                    }
                }

            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();

        if (this.isDone && theCard != null && didUpgrade) {
            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
            AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(theCard.makeStatEquivalentCopy()));
            this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        }
//        This commented code is not working.
//        if (this.isDone && !upgradedCards.isEmpty()) {
//            for (AbstractCard theCard : upgradedCards) {
//                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
//                AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(theCard.makeStatEquivalentCopy()));
//                this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
//            }
//        }

    }
}
