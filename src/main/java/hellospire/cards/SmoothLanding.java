package hellospire.cards;

import basemod.interfaces.OnPlayerTurnStartSubscriber;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import hellospire.SonicMod;
import hellospire.SoundLibrary;
import hellospire.actions.SwapCostsAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;

public class SmoothLanding extends BaseCard {
    public static final String ID = makeID("SmoothLanding");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 2;

    public SmoothLanding() {
        super(ID, info);
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChannelAction(new Frost()));
        addToBot(new ChannelAction(new Frost()));
        if (hasVigor() || this.upgraded) {
            addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, magicNumber), magicNumber));
            addToBot(SoundLibrary.PlayVoice(SoundLibrary.OmochaoPerfectLanding));
        } else {
            addToBot(SoundLibrary.PlayVoice(SoundLibrary.OmochaoIncorrectLanding));
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
        return AbstractDungeon.player.hasPower("Vigor");
    }

    private void transitionToSmoothLanding() {
        if (hasVigor() || this.upgraded) {
            name = "Smooth Landing";
            initializeTitle();
            loadCardImage(SonicMod.imagePath("cards/skill/SmoothLanding1.png"));
        } else {
            name = "Incorrect Landing";
            initializeTitle();
            loadCardImage(SonicMod.imagePath("cards/skill/SmoothLanding.png"));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SmoothLanding();
    }
}
