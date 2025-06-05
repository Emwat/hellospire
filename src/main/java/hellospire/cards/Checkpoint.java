package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicMod;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class Checkpoint extends BaseCard {
    public static final String ID = makeID("Checkpoint");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );
    private final static String playerError = CardCrawlGame.languagePack.getUIString(makeID("CheckpointMessage")).TEXT[0];

    public Checkpoint() {
        super(ID, info);
        this.cardsToPreview = new SuperSonicForm();
//        this.cardsToPreview.rarity = CardRarity.RARE;
        setExhaust(true);
    }

    /// "Apply !M! Vulnerable. NL Add a Ring to your hand."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.PlaySound(SoundLibrary.StarPost));
        this.addToBot(new MakeTempCardInDrawPileAction(
                this.cardsToPreview.makeStatEquivalentCopy(),
                1, true, true, false));
    }

    // OKAY
    // So the player error message will only come up if this.CardTarget is ENEMY or SELF_ENEMY
    // ALL does not work.
    // SELF does not work. (which is what I wanted)
    // SELF_AND_ENEMY does not work.
    // changing super.canUse(p, m) to super.canUse(p, modGetRandomMonster) does not work.
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }

        if (p.hand.group.isEmpty()) {
            this.target = CardTarget.ENEMY;
            this.cantUseMessage = playerError;
            return false;
        }
        for (AbstractCard c : p.hand.group) {
            if (c.cardID.equals(Ring.ID)) {
                this.target = CardTarget.SELF;
                return canUse;
            }
        }

        this.target = CardTarget.ENEMY;
        this.cantUseMessage = playerError;
        return false;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            this.cardsToPreview.upgrade();
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Checkpoint();
    }
}
