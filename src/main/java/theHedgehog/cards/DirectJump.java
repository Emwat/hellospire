package theHedgehog.cards;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicTags;
import theHedgehog.character.Sonic;
import theHedgehog.powers.DirectJumpPower;
import theHedgehog.util.CardStats;

public class DirectJump extends BaseCard implements BranchingUpgradesCard {
    public static final String ID = makeID("DirectJump");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public DirectJump() {
        super(ID, info);
        this.cardsToPreview = new Ring();
        setExhaust(true);
        tags.add(SonicTags.LIKE_WATCHER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DirectJumpPower(p, 1)));
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
        this.upgradeBaseCost(0);
    }

    public void branchUpgrade() {
        // name = "Serial Homing Attack";
        // loadCardImage(SonicMod.imagePath("cards/attack/HomingAttackSerial.png"));
        // portraitImg = TextureLoader.getTexture(SonicMod.imagePath("cards/attack/HomingAttackSerial_p.png"));
        setExhaust(false);
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new DirectJump();
    }
}
