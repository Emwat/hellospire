package theHedgehog.events;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import theHedgehog.powers.DizzyPlayerPower;

import static theHedgehog.SonicMod.*;

public class EnemyBossSonicProp extends AbstractMonster {
    public static final String ID = makeID("EnemyBossSonicProp");
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final int HP_MIN = 10;
    public static final int HP_MAX = 12;
    public static final int A_2_HP_MIN = 14;
    public static final int A_2_HP_MAX = 16;
    public final int BLOCK_AMT = 7;
    public final int STR_AMT = 2;
    private boolean firstMove = true;
    private static final String BOUNCEPAD_NAME;
    private static final String BOUNCEPAD_IMG = imagePath("monsters/BouncePad.png");
    private final Texture BOUNCEPAD_TEXTURE = ImageMaster.loadImage(BOUNCEPAD_IMG);
    private static final String RAMPJUMP_NAME;
    private static final String RAMPJUMP_IMG = imagePath("monsters/RampJump.png");
    private final Texture RAMPJUMP_TEXTURE = ImageMaster.loadImage(RAMPJUMP_IMG);
    private static final String DASHPANEL_NAME;
    private static final String DASHPANEL_IMG = imagePath("monsters/DashPanel.png");;
    private final Texture DASHPANEL_TEXTURE = ImageMaster.loadImage(DASHPANEL_IMG);

    public EnemyBossSonicProp(float x, float y) {
        super(NAME, ID, AbstractDungeon.monsterHpRng.random(HP_MIN, HP_MAX), -5.0F, -20.0F, 145.0F, 240.0F, BOUNCEPAD_IMG, x, y);
        this.setMove((byte)1, Intent.DEFEND, BLOCK_AMT);
        this.damage.add(new DamageInfo(this, BLOCK_AMT));
        // this.loadAnimation("images/monsters/theCity/torchHead/skeleton.atlas", "images/monsters/theCity/torchHead/skeleton.json", 1.0F);
        // AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        // e.setTime(e.getEndTime() * MathUtils.random());
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(A_2_HP_MIN, A_2_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }
    }

    public void usePreBattleAction() {
        addToBot(new ApplyPowerAction(this, this, new MinionPower(this)));
        addToBot(new ApplyPowerAction(this, this, new ReactivePower(this)));
        addToBot(new ApplyPowerAction(this, this, new IntangiblePower(this, 99)));
    }

    public void takeTurn() {
        // BronzeOrb
        // BronzeAutomaton
        AbstractMonster boss = AbstractDungeon.getMonsters().getMonster(EnemyBossSonic.ID);

        switch (this.nextMove) {
            case 0:
                // Bounce Pad
                addToBot(new GainBlockAction(boss, this, BLOCK_AMT));
                break;
            case 1:
                // Ramp Jump
                addToBot(new ApplyPowerAction(boss, this, new StrengthPower(boss, this.STR_AMT), this.STR_AMT));
                break;
            case 2:
                // Dash Panel
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new DizzyPlayerPower(AbstractDungeon.player, 1)));
                // addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new SlowPower(AbstractDungeon.player, 1)));
                break;
            default:
        }
    }

    public void changeState(String stateName) {
        switch (stateName) {
            case "BOUNCE_PAD":
                break;
            case "RAMP_JUMP":
                break;
            case "DASH_PANEL":
                break;
        }

    }

    public void update() {
        super.update();
    }

    protected void getMove(int num) {
        if (this.firstMove) {
            this.firstMove = false;
            this.setMove((byte)0, Intent.DEFEND);
            this.img = BOUNCEPAD_TEXTURE;
        } else {
            if (num < 33) {
                if (!this.lastMove((byte)0)) {
                    this.setMove((byte)0, Intent.DEFEND);
                    this.img = BOUNCEPAD_TEXTURE;
                } else {
                    this.getMove(AbstractDungeon.aiRng.random(33, 99));
                }
            } else if (num < 66) {
                if (!this.lastMove((byte)1)) {
                    this.setMove((byte)1, Intent.BUFF);
                    this.img = RAMPJUMP_TEXTURE;
                } else if (AbstractDungeon.aiRng.randomBoolean(0.4F)) {
                    this.setMove((byte)0, Intent.DEFEND);
                    this.img = BOUNCEPAD_TEXTURE;
                } else {
                    this.setMove((byte)2, Intent.DEBUFF);
                    this.img = DASHPANEL_TEXTURE;
                }
            } else {
                if (!this.lastMove((byte)2)) {
                    this.setMove((byte)2, Intent.DEBUFF);
                    this.img = DASHPANEL_TEXTURE;
                } else {
                    this.getMove(AbstractDungeon.aiRng.random(0, 66));
                }
            }

            this.createIntent();
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        BOUNCEPAD_NAME = MOVES[0];
        RAMPJUMP_NAME = MOVES[1];
        DASHPANEL_NAME = MOVES[2];
    }
}
