package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Whirlwind;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

public class Drift extends BaseCard {
    public static final String ID = makeID("Drift");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            -1
    );

    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = 2;

    public Drift() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
    }

    ///"DESCRIPTION": "Activate the passive effects of your orbs X times."
    /// TODO: I'm too tired.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//
//        int effect = EnergyPanel.totalCount;
//        if (this.energyOnUse != -1) {
//            effect = this.energyOnUse;
//        }
//
//        if (this.p.hasRelic("Chemical X")) {
//            effect += 2;
//            this.p.getRelic("Chemical X").flash();
//        }
//
//        if (effect > 0) {
//            for(int i = 0; i < effect; ++i) {
//                if (i == 0) {
//                    this.addToBot(new SFXAction("ATTACK_WHIRLWIND"));
//                    this.addToBot(new VFXAction(new WhirlwindEffect(), 0.0F));
//                }
//
//                this.addToBot(new SFXAction("ATTACK_HEAVY"));
//                this.addToBot(new VFXAction(this.p, new CleaveEffect(), 0.0F));
//                this.addToBot(new DamageAllEnemiesAction(this.p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE, true));
//            }
//
//            if (!this.freeToPlayOnce) {
//                this.p.energy.use(EnergyPanel.totalCount);
//            }
//        }
//
//        this.isDone = true;

        int numberOfTimes = 3;
        for (int i = 0; i < numberOfTimes; i++) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                        orb.onStartOfTurn();
                        orb.onEndOfTurn();
                    }
                    this.isDone = true;
                }
            });
        }

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Drift();
    }
}
