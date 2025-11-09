package theHedgehog.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnPlayerDeathPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import theHedgehog.actions.ModXFastAction;

import static theHedgehog.SonicMod.makeID;
import static theHedgehog.relics.CDPastRelic.reviveCost;

public class OneUpPower extends BasePower implements OnPlayerDeathPower {
    public static final String POWER_ID = makeID("OneUpPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public static final int reviveAmount = 50;

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public OneUpPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + ReviveAmount() + DESCRIPTIONS[1];
    }

    @Override
    public boolean onPlayerDeath(AbstractPlayer abstractPlayer, DamageInfo damageInfo) {
        trigger(abstractPlayer);
        return false;
    }

    private int ReviveAmount() {
        return (int) (AbstractDungeon.player.maxHealth * (reviveAmount * 0.01));
    }

    public void trigger(AbstractPlayer abstractPlayer) {
        AbstractDungeon.actionManager.addToTop(new ModXFastAction(() -> {
            AbstractDungeon.player.loseGold(reviveCost);
            this.amount -= 1;
            if (this.amount <= 0) {
                AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            }
        }));
        AbstractDungeon.actionManager.addToTop(new HealAction(abstractPlayer, abstractPlayer, ReviveAmount()));
        AbstractDungeon.actionManager.addToTop(new VFXAction(this.owner, new IntenseZoomEffect(this.owner.hb.cX, this.owner.hb.cY, true), 0.05F, true));
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

}