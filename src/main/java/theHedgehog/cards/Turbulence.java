package theHedgehog.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.character.Sonic;
import theHedgehog.powers.TurbulencePower;
import theHedgehog.relics.AirBoostShoesRelic;
import theHedgehog.util.CardStats;

public class Turbulence extends BaseCard {
    public static final String ID = makeID("Turbulence");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = -1;

    public Turbulence() {
        super(ID, info);
        this.cardsToPreview = new Trick();

        setMagic(MAGIC, UPG_MAGIC);
        tags.add(SonicTags.LIKE_IRONCLAD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int magicOutput = magicNumber;
        if (CheckIfLeftCard(this, p.hand)) {
            addToBot(SoundLibrary.VoiceAction(SoundLibrary.OmochaoTurbulence));
            addToBot(new MakeTempCardInHandAction(this.cardsToPreview, 1, true));
            // magicOutput -= 1;
        }
        if (magicOutput > 0) {
            addToBot(new ApplyPowerAction(p, p, new FocusPower(p, -magicOutput), -magicOutput));
        }

        addToBot(new ApplyPowerAction(p, p, new TurbulencePower(p, 1), 1));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (isPlayerHandNull()) {
            return;
        }

        if (AbstractDungeon.player.hasRelic(AirBoostShoesRelic.ID)) {
            this.glowColor = Color.WHITE.cpy();
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
