package hellospire.powers;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.GoForTheEyes;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.EchoPower;
import hellospire.SonicMod;

import java.util.ArrayList;

import static hellospire.SonicMod.makeID;

public class BaitPower extends BasePower {
    public static final String POWER_ID = makeID("BaitPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    private final AbstractPlayer player;
    private AbstractMonster attackingMonster;

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public BaitPower(AbstractCreature owner, AbstractPlayer player, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.player = player;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }


    @Override
    public void atStartOfTurn() {
        SonicMod.logger.info("at End of Turn");

        ArrayList<AbstractMonster> monsters = AbstractDungeon.getCurrRoom().monsters.monsters;
        ArrayList<AbstractMonster> attackingMonsters = getAttackingMonsters(monsters);
        if (!attackingMonsters.isEmpty()) {
            this.attackingMonster = attackingMonsters.get(AbstractDungeon.miscRng.random(0, attackingMonsters.size() - 1));
            int intentDamage = attackingMonster.getIntentBaseDmg();
            int intentMultiAmt = ReflectionHacks.getPrivate(attackingMonster, AbstractMonster.class, "intentMultiAmt");
            intentMultiAmt = intentMultiAmt == -1 ? 1 : intentMultiAmt;

            attackingMonster.flipHorizontal = !attackingMonster.flipHorizontal;
            SonicMod.logger.info("intentDamage: " + intentDamage);
            SonicMod.logger.info("intentMultiAmt: " + intentMultiAmt);
            for (int i = 0; i < intentMultiAmt; i++) {
                addToTop(new GainBlockAction(player, (int)(intentDamage * 0.75F)));
                addToBot(new DamageAction(owner, new DamageInfo(owner, intentDamage)));
            }
        }
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                attackingMonster.flipHorizontal = !attackingMonster.flipHorizontal;
                this.isDone = true;
            }
        });
        addToBot(new ReducePowerAction(owner, owner, ID, amount));
    }

    private ArrayList<AbstractMonster> getAttackingMonsters(ArrayList<AbstractMonster> monsters) {
        ArrayList<AbstractMonster> attackingMonsters = new ArrayList<>();

        for (AbstractMonster m : monsters) {
            if (!m.isDeadOrEscaped() && m.getIntentBaseDmg() >= 0) {
                attackingMonsters.add(m);
            }
        }
        return attackingMonsters;
    }

    private AbstractMonster modGetRandomMonster() {
        return AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}