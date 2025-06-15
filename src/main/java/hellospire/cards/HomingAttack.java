package hellospire.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class HomingAttack extends BaseCard implements BranchingUpgradesCard {
    public static final String ID = makeID("HomingAttack");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 1;
    private String upgradeStatus = "base";

    public HomingAttack() {
        super(ID, info);
//        this.cardsToPreview = new Ring();
//        this.cardsToPreview = new Trick();
        Ring ring = new Ring();
        Trick trick = new Trick();
        MultiCardPreview.add(this, ring, trick);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard ring = new Ring();
        AbstractCard trick = new Trick();

        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));

        if (this.upgraded && upgradeStatus == "Rings2") {
            addToBot(new MakeTempCardInHandAction(ring, 1, true));
        }
        addToBot(new MakeTempCardInHandAction(ring, 1, true));

        if (this.upgraded && upgradeStatus == "Tricks2") {
            addToBot(new MakeTempCardInHandAction(trick, 1, true));
        }
        addToBot(new MakeTempCardInHandAction(trick, 1, true));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            if (isBranchUpgrade()) {
                branchUpgrade();
            } else {
                baseUpgrade();
            }
        }
    }


    public void baseUpgrade() {
        upgradeStatus = "Tricks2";
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    public void branchUpgrade() {
        name = "Homing Dash";
        upgradeStatus = "Rings2";
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

//    "EXTENDED_DESCRIPTION": [
//            "Deal !D! damage. NL Add 2 Rings and a Trick to your hand.",
//            "Deal !D! damage. NL Add a Ring and 2 Tricks to your hand."
//            ]


    @Override
    public AbstractCard makeCopy() { //Optional
        return new HomingAttack();
    }
}
