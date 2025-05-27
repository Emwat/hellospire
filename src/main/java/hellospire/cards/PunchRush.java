package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicMod;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class PunchRush extends BaseCard implements BranchingUpgradesCard {
    public static final String ID = makeID("PunchRush");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 1;
    private static final int UPGRADED_DAMAGE = 1;
    private static final int MAGIC = 2;

    private static final int HITS = 4;
    private static String upgradeStatus = "base";

    public PunchRush() {
        super(ID, info);

        setDamage(DAMAGE);
        setMagic(MAGIC);
        if (this.upgraded) {
            setMagic(UPGRADED_DAMAGE);
        }
    }

    ///    "EXTENDED_DESCRIPTION": [
    ///"Deal !D! damage 4 times. NL Deal !D! + !M! damage.",
    ///        "Deal !D! damage 4 times. NL Play the top card of your draw pile."
    ///        ]
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < HITS; i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        if (this.upgraded && upgradeStatus == "Super Rush") {
            addToBot(new DamageAction(m, new DamageInfo(p, damage + magicNumber, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        } else if (this.upgraded && upgradeStatus == "Rush Spin") {
            addToBot(new PlayTopCardAction(m, false));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (isBranchUpgrade()) {
                branchUpgrade();
            } else {
                baseUpgrade();
            }
        }
    }

    public void baseUpgrade() {
        name = "Super Rush";
        upgradeStatus = this.name;
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
//        this.upgradeBaseCost(1);
        loadCardImage(SonicMod.imagePath("cards/attack/SuperRush.png"));
        this.initializeDescription();
    }

    public void branchUpgrade() {
        name = "Rush Spin";
        upgradeStatus = this.name;
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
//        this.upgradeBaseCost(1);
        loadCardImage(SonicMod.imagePath("cards/attack/RushSpin.png"));
        this.initializeDescription();
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new PunchRush();
    }
}
