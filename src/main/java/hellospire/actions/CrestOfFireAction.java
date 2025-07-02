package hellospire.actions;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.MindBloom;
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
        if (this.duration == Settings.ACTION_DUR_MED && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.FIRE));
            this.target.damage(this.info);
            if ((((AbstractMonster) this.target).isDying || this.target.currentHealth <= 0) &&
                    !this.target.halfDead &&
                    !this.target.hasPower("Minion")) {
                didUpgrade = true;

                upgradeCards(thePlayer.masterDeck);
                upgradeCards(thePlayer.hand);
                upgradeCards(thePlayer.drawPile);
                upgradeCards(thePlayer.discardPile);
                upgradeCards(thePlayer.exhaustPile);

                theCard.upgrade();
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();
    }

    protected void upgradeCards(CardGroup cardGroup) {
        int effectCount = 0;
        for (AbstractCard card : cardGroup.group) {
            if (card.tags.contains(SonicTags.CREST_OF_FIRE) && card.canUpgrade()) {
                if (cardGroup.type == CardGroup.CardGroupType.MASTER_DECK) {
                    ++effectCount;
                    if (effectCount <= 20) {
                        float x = MathUtils.random(0.1F, 0.9F) * (float)Settings.WIDTH;
                        float y = MathUtils.random(0.2F, 0.8F) * (float)Settings.HEIGHT;
                        AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy(), x, y));
                        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(x, y));
                    }
                }

                card.upgrade();
                if (cardGroup.type == CardGroup.CardGroupType.HAND) {
                    card.superFlash();
                }
                card.applyPowers();
                thePlayer.bottledCardUpgradeCheck(card);
            }
        }
    }
}
