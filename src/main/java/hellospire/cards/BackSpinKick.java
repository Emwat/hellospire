package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.MyModConfig;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class BackSpinKick extends BaseCard {
    public static final String ID = makeID("BackSpinKick");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            3
    );

    private static final int DAMAGE = 18;
    private static final int UPG_DAMAGE = 4;
    private static int attacksPlayed;

    public BackSpinKick() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        if (MyModConfig.enableKicksForStrikeDummy) {
            tags.add(CardTags.STRIKE);
        }
    }

    /// Deal !D! damage. This costs 1 less for each attack played.
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    public void atTurnStart() {
        this.resetAttributes();
        attacksPlayed = 0;
        this.applyPowers();
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        super.triggerOnOtherCardPlayed(c);
        int currentCost = this.costForTurn;
        if (c.type == CardType.ATTACK) {
            attacksPlayed++;
            currentCost -= 1;
        }
        if (this.costForTurn > 0) {
            this.setCostForTurn(Math.min(this.baseCost - attacksPlayed, currentCost));
            this.isCostModifiedForTurn = true;
        }
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }
}
