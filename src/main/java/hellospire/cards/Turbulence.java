package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import hellospire.SonicTags;
import hellospire.SoundLibrary;
import hellospire.actions.ActivateLeftMostPassiveOrbAction;
import hellospire.character.Sonic;
import hellospire.powers.TurbulencePower;
import hellospire.util.CardStats;

public class Turbulence extends BaseCard {
    public static final String ID = makeID("Turbulence");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    /// TODO: UPG_MAGIC isn't working.
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = -1;

    public Turbulence() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        tags.add(SonicTags.LIKE_IRONCLAD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.OmochaoTurbulence));
        if (!CheckIfLeftCard(this, p.hand)) {
            addToBot(new ApplyPowerAction(p, p, new FocusPower(p, -magicNumber), -magicNumber));
        }
        addToBot(new ApplyPowerAction(p, p, new TurbulencePower(p, 1), 1));
        if (this.upgraded) {
            addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), 1));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.cardsToPreview = new Trick();
        }
        super.upgrade();
    }


    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (isPlayerHandNull()) {
            return;
        }

        if (CheckIfLeftCard(this, AbstractDungeon.player.hand)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Turbulence();
    }
}
