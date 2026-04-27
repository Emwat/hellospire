package theHedgehog.cardsPack;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SoundLibrary;
import theHedgehog.cards.*;
import theHedgehog.cards.Ring;
import theHedgehog.character.Sonic;
import theHedgehog.powers.LevelUpFlightPower;
import theHedgehog.powers.LevelUpSpeedPower;
import theHedgehog.util.CardStats;
import thePackmaster.ThePackmaster;

import static theHedgehog.multiplayer.ModMultiplayerHelper.GiveCardToTeammate;

public class LevelUp extends BaseCard {
    public static final String ID = makeID("PackLevelUp");
    private static final CardType cardtype = CardType.POWER;
    private static final CardTarget cardTarget = CardTarget.SELF;
    private static final int cost = 1;
    private static final CardStats info = Loader.isModLoaded("anniv5") ?
            new CardStats(ThePackmaster.Enums.PACKMASTER_RAINBOW, cardtype, CardRarity.RARE, cardTarget, cost) :
            new CardStats(Sonic.Meta.CARD_COLOR, cardtype, CardRarity.SPECIAL, cardTarget, cost);

    // NOTE: wordLevelUp
    // If you update this, please also update Keywords.json wordLevelUp and LevelUp
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    private final theHedgehog.cards.Ring preview0 = new Ring(); // Warming Up
    private final LevelUp1 preview1 = new LevelUp1(); // Speed
    private final LevelUp2 preview2 = new LevelUp2(); // Flight
    private final LevelUp3 preview3 = new LevelUp3(); // Power

    public LevelUp() {
        super(ID, info);
        MultiCardPreview.add(this, preview0, preview1, preview2, preview3);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.LevelUp));
        if (magicNumber > 0) {
            addToBot(new MakeTempCardInHandAction(preview0.makeStatEquivalentCopy(), magicNumber));
        }

        addToBot(new ApplyPowerAction(p, p, new LevelUpSpeedPower(p, 1)));
        addToBot(new ApplyPowerAction(p, p, new LevelUpFlightPower(p, 1)));
        addToBot(new ApplyPowerAction(p, p, new LevelUpSpeedPower(p, 1)));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new LevelUp();
    }
}
