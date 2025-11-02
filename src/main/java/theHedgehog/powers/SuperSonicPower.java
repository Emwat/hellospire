package theHedgehog.powers;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theHedgehog.character.Sonic;

import static theHedgehog.SonicMod.makeID;

public class SuperSonicPower extends BasePower {
    public static final String POWER_ID = makeID("SuperSonicPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public SuperSonicPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    // @Override
    // public void atStartOfTurn() {
    //     this.flash();
    //     this.addToBot(new VFXAction(new LightningEffect(this.owner.hb.cX, this.owner.hb.cY)));
    //     this.addToBot(new LoseHPAction(this.owner, this.owner, 99999));
    // }

    public void onVictory() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p instanceof Sonic && p.currentHealth > 0) {
            ((Sonic) p).playAnimation("idle", true);
        }
    }

    public float[] _lightsOutGetXYRI() {
        // 08/28/2025 07:43 PM
        // he's green and he doesn't get brighter w/ more stacks of dex
        int shine = 1;
        AbstractPower str = owner.getPower(DexterityPower.POWER_ID);
        if (str != null) {
            shine = str.amount / 7;
        }
        return new float[]{owner.hb.cX, owner.hb.cY, (100f + 10 * shine) * Settings.scale, (0.5f + 0.05f * shine)};
    }

    public Color[] _lightsOutGetColor() {
        // return new Color[]{new Color(1.0f, 245f / 255f, 0f, 1f)};
        return new Color[] {Color.RED.cpy()};
        // return new Color[] {Color.GOLD.cpy()};
        // return new Color[] {Color.YELLOW};
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}