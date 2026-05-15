package theHedgehog.events;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.TimeEater;
import com.megacrit.cardcrawl.monsters.city.Champ;
import com.megacrit.cardcrawl.monsters.city.TheCollector;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theHedgehog.SonicMod;
import theHedgehog.modachievements.achievements;
import theHedgehog.strings.SonicTalkStrings;

import java.util.HashMap;
import java.util.Map;

import static theHedgehog.SonicMod.makeID;
import static theHedgehog.util.UnlockUtil.unlockModAchievement;

public class EnemyBossSonic extends AbstractMonster {
    private static final Logger logger = LogManager.getLogger(EnemyBossSonic.class.getName());
    public static final String ID = makeID("EnemyBossSonic");
    private static SonicTalkStrings dialogues = SonicMod.modLocalizedStrings.getTalkString(ID);
    private static final String[] playerReplies = dialogues.DIALOG;
    private static final String[] bossSays = dialogues.DIALOG_0;
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String IMAGE_URL = SonicMod.characterPath("/animation/red/Sonic_pose_63.png");
    public static final String[] MOVES;
    public static final String[] DIALOG;

    // TheCollector HP = 282;
    // TheCollector A_2_HP = 300;
    // Champ HP = 420;
    // Champ A_9_HP = 440;
    public static final int HP = 282;
    public static final int A_2_HP = 300;
    public int tauntDamage = 0;
    public int homingAttackDamage = 8;
    public int spinDashDamage = 10;
    private int spinDashBlock;
    public int whirlwindDamage = 4;
    private int tauntDebuffAmt = 4;
    private int turnCount = 0;
    private boolean ultUsed = false;
    private boolean initialSpawn = true;
    private HashMap<Integer, AbstractMonster> enemySlots = new HashMap();
    private static float talkDuration = 5;
    private static final String TAUNT_NAME;
    private static final String HOMINGATTACK_NAME;
    private static final String SPINDASH_NAME;
    private static final String WHIRLWIND_NAME;
    private boolean firstTurn = true;

    public EnemyBossSonic() {
        super(NAME, ID, HP, 15.0F, -40.0F, 300.0F, 390.0F, IMAGE_URL, 60.0F, MathUtils.random(-5.0F, 25.0F));
        this.dialogX = -90.0F * Settings.scale;
        this.dialogY = 10.0F * Settings.scale;
        this.type = EnemyType.BOSS;
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(A_2_HP);

        } else {
            this.setHp(HP);
        }

        this.damage.add(new DamageInfo(this, this.tauntDamage));
        this.damage.add(new DamageInfo(this, this.homingAttackDamage));
        this.damage.add(new DamageInfo(this, this.spinDashDamage));
        this.damage.add(new DamageInfo(this, this.whirlwindDamage));
        // this.animation = new CustomSpriterAnimation(ModSkinDictionary.defaultAnimationPath);
        // this.loadAnimation("images/monsters/theCity/collector/skeleton.atlas", "images/monsters/theCity/collector/skeleton.json", 1.0F);
        // AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        // e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_CITY");
        // UnlockTracker.markBossAsSeen("COLLECTOR");
        addToBot(new ApplyPowerAction(this, this, new MalleablePower(this)));
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                // Taunt
                int randomNumber = MathUtils.random(bossSays.length - 1);
                if (firstTurn) {
                    firstTurn = false;
                    addToBot(new TalkAction(this, bossSays[randomNumber], talkDuration, talkDuration));
                    addToBot(new TalkAction(true, playerReplies[randomNumber], talkDuration, talkDuration));
                }
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, this.tauntDebuffAmt, true), this.tauntDebuffAmt));
                break;
            case 1:
                // Homing Attack
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                break;
            case 2:
                // Spin Dash
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                if (AbstractDungeon.ascensionLevel >= 19) {
                    addToBot(new GainBlockAction(this, this, this.spinDashBlock + 5));
                } else {
                    addToBot(new GainBlockAction(this, this, this.spinDashBlock));
                }
                break;
            case 3:
                // Whirlwind
                addToBot(new SFXAction("ATTACK_WHIRLWIND"));
                addToBot(new VFXAction(new WhirlwindEffect(), 0.0F));
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(3), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(3), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(3), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                break;
            default:
                logger.info("ERROR: Default Take Turn was called on " + this.name);
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {

        if (this.turnCount == 0) {
            this.setMove(TAUNT_NAME, (byte) 0, Intent.DEBUFF);
            this.turnCount++;
        } else if (this.turnCount == 1) {
            this.setMove(HOMINGATTACK_NAME, (byte) 1, Intent.ATTACK, this.damage.get(1).base);
            this.turnCount++;
        } else if (this.turnCount == 2) {
            this.setMove(SPINDASH_NAME, (byte) 2, Intent.ATTACK_DEFEND, this.damage.get(2).base);
            this.turnCount++;
        } else if (this.turnCount == 3) {
            this.setMove(WHIRLWIND_NAME, (byte) 3, Intent.ATTACK, this.damage.get(3).base, 3, true);
            this.turnCount = 0;
        } else {
            this.setMove(TAUNT_NAME, (byte) 0, Intent.DEBUFF);
        }
    }

    private boolean isMinionDead() {
        for (Map.Entry<Integer, AbstractMonster> m : this.enemySlots.entrySet()) {
            if ((m.getValue()).isDying) {
                return true;
            }
        }

        return false;
    }

    public void update() {
        super.update();
    }

    public void die() {
        this.useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        ++this.deathTimer;
        super.die();
        this.onBossVictoryLogic();

        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDead && !m.isDying) {
                AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(m));
                AbstractDungeon.actionManager.addToTop(new SuicideAction(m));
                AbstractDungeon.actionManager.addToTop(new VFXAction(m, new InflameEffect(m), 0.2F));
            }
        }

    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        TAUNT_NAME = MOVES[0];
        HOMINGATTACK_NAME = MOVES[1];
        SPINDASH_NAME = MOVES[2];
        WHIRLWIND_NAME = MOVES[3];
    }
}
