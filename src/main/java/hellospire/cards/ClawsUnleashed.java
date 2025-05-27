package hellospire.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class ClawsUnleashed extends BaseCard {
    public static final String ID = makeID("ClawsUnleashed");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 19;
    private static final int UPG_DAMAGE = -3;

    public ClawsUnleashed() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    /// "Can only be played if you have a Dark orb."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new ClawEffect(m.hb.cX, m.hb.cY, Color.CYAN, Color.WHITE), 0.1F));
        }

        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            return true;
        }

        for (AbstractOrb orb :  AbstractDungeon.player.orbs){
            if(orb.name == new Dark().name){
                return true;
            }
        }
        // TODO: Fix the NullPointerException
//        this.cantUseMessage = CardCrawlGame.languagePack.getUIString("ClawsUnleashedMessage").TEXT[0];
        return false;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new ClawsUnleashed();
    }
}
