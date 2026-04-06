package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Coolheaded;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import theHedgehog.SonicMod;
import theHedgehog.SoundLibrary;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;
import theHedgehog.util.GeneralUtils;

public class SmoothLanding extends BaseCard {
    public static final String ID = makeID("SmoothLanding");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );
    private static final String[] NAMES; // "NAMES": ["Incorrect Landing", "Smooth Landing" ]

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public SmoothLanding() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        transitionToSmoothLanding();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChannelAction(new Frost()));
        if (hasVigor()) {
            addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, magicNumber), magicNumber));
            addToBot(SoundLibrary.VoiceAction(SoundLibrary.OmochaoPerfectLanding));
        } else {
            addToBot(new DrawCardAction(p, magicNumber));
            addToBot(SoundLibrary.VoiceAction(SoundLibrary.OmochaoIncorrectLanding));
        }
    }

    @Override
    public void triggerWhenDrawn() {
        transitionToSmoothLanding();
        super.triggerWhenDrawn();
    }

    @Override
    public void applyPowers() {
        transitionToSmoothLanding();
        super.applyPowers();
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (hasVigor()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    private boolean hasVigor() {
        return AbstractDungeon.player != null && AbstractDungeon.player.hasPower("Vigor");
    }

    private void transitionToSmoothLanding() {
        if (!GeneralUtils.isIndeedWithoutADoubtInCombat()) {
            return;
        }
        if (hasVigor()) {
            // Smooth Landing, draw 2 additional cards
            this.name = NAMES[1];
            initializeTitle();
                this.rawDescription = String.format("%s%s%s%s",
                        cardStrings.EXTENDED_DESCRIPTION[0],
                        cardStrings.EXTENDED_DESCRIPTION[1],
                        cardStrings.EXTENDED_DESCRIPTION[2],
                        GeneralUtils.ColorWord( "[#808080]", cardStrings.EXTENDED_DESCRIPTION[3]));
                initializeDescription();

            loadCardImage(SonicMod.imagePath("cards/skill/SmoothLanding1.png"));
        } else {
            // Incorrect Landing, draw 2 cards now
            this.name = NAMES[0];
            initializeTitle();
            this.rawDescription = String.format("%s%s%s%s",
                    cardStrings.EXTENDED_DESCRIPTION[0],
                    GeneralUtils.ColorWord("[#808080]", cardStrings.EXTENDED_DESCRIPTION[1]),
                    cardStrings.EXTENDED_DESCRIPTION[2],
                    cardStrings.EXTENDED_DESCRIPTION[3]);
            initializeDescription();
            loadCardImage(SonicMod.imagePath("cards/skill/SmoothLanding.png"));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.name = NAMES[1];
            initializeTitle();
            loadCardImage(SonicMod.imagePath("cards/skill/SmoothLanding1.png"));
        }
        super.upgrade();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SmoothLanding();
    }

    static {
        NAMES = SonicMod.modLocalizedStrings.getExtraCardString(ID).NAMES;
    }
}
