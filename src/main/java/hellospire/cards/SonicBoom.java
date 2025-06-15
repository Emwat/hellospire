package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import hellospire.SonicTags;
import hellospire.character.Sonic;
import hellospire.powers.SonicBoomPower;
import hellospire.util.CardStats;

public class SonicBoom extends BaseCard implements BranchingUpgradesCard {
    public static final String ID = makeID("SonicBoom");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 3;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 3;

    public SonicBoom() {
        super(ID, info);
        setBlock(0);
        setMagic(0);
        tags.add(SonicTags.LIKE_IRONCLAD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded && !this.isBranchUpgrade()){
            addToBot(new GainBlockAction(p, block));
        } else if (this.upgraded && this.isBranchUpgrade()) {
            addToBot(new ApplyPowerAction(p, p, new VigorPower(p, magicNumber), magicNumber));
        }
        addToBot(new ApplyPowerAction(p, p, new SonicBoomPower(p, 1)));
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
        super.upgrade();
    }


    public void baseUpgrade() {
        upgradeBlock(UPG_BLOCK);
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void branchUpgrade() {
        upgradeMagicNumber(UPG_MAGIC);
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SonicBoom();
    }
}
