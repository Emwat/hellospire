package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.unique.SkewerAction;
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
import hellospire.actions.DriftAction;
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
        addToBot(new DriftAction(p));
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        DriftAction a = new DriftAction(AbstractDungeon.player);
        int realBaseDamage = this.baseDamage;
        this.baseDamage = a.CalculateEffect();
        super.calculateCardDamage(m);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Drift();
    }
}
