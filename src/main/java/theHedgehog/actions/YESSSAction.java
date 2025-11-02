package theHedgehog.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import theHedgehog.MyModConfig;
import theHedgehog.SoundLibrary;

public class YESSSAction extends AbstractGameAction {
    private DamageInfo info;
    private float pitchVar = 0.0F;
    private boolean skipWait;


    public YESSSAction(AbstractCreature target, DamageInfo info, AttackEffect attackEffect) {
        this.info = info;
        this.setValues(target, info);
        this.attackEffect = attackEffect;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_MED;
        this.skipWait = false;

    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
            this.target.damage(this.info);
            if ((((AbstractMonster) this.target).isDying || this.target.currentHealth <= 0) &&
                    !this.target.halfDead &&
                    !this.target.hasPower("Minion")) {
                if (MyModConfig.enableVoice && SoundLibrary.isRandomlyTrue()) {
                    CardCrawlGame.sound.play(SoundLibrary.YESSS, this.pitchVar);
                }
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

            if (!this.skipWait && !Settings.FAST_MODE) {
                this.addToTop(new WaitAction(0.1F));
            }
        }
        this.isDone = true;
    }

}
