package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.powers.LevelUpFlightPower;
import hellospire.powers.LevelUpPowerPower;
import hellospire.powers.LevelUpSpeedPower;
import hellospire.util.CardStats;

import static hellospire.SonicMod.imagePath;

public class LevelUp extends BaseCard {
    public static final String ID = makeID("LevelUp");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    // NOTE: wordLevelUp
    // If you update this, please also update Keywords.json wordLevelUp
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 1;

    private static CardType LastTypeCardPlayed;

    public LevelUp() {
        super(ID, info);
        this.cardsToPreview = new Ring();

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.LevelUp));
        if (magicNumber > 0){
            addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), magicNumber));
        }

        if (LastTypeCardPlayed == CardType.ATTACK) {
            addToBot(new ApplyPowerAction(p, p, new LevelUpPowerPower(p, 1)));
        } else if (LastTypeCardPlayed == CardType.SKILL) {
            addToBot(new ApplyPowerAction(p, p, new LevelUpSpeedPower(p, 1)));
        } else if (LastTypeCardPlayed == CardType.POWER) {
            addToBot(new ApplyPowerAction(p, p, new LevelUpFlightPower(p, 1)));
        } else {
            // addToBot(new MakeTempCardInHandAction(new Ring().makeStatEquivalentCopy(), magicNumber));
            addToBot(new ApplyPowerAction(p, p, new LevelUpSpeedPower(p, 1)));
        }
    }

    @Override
    public void triggerWhenDrawn() {
        UpdateLastCardPlayed();
        UpdateCardImageAndText();
        super.triggerWhenDrawn();
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        UpdateLastCardPlayed();
        UpdateCardImageAndText();
        super.triggerOnOtherCardPlayed(c);
    }

    private void UpdateCardImageAndText() {
        if (LastTypeCardPlayed == CardType.ATTACK) {
            loadCardImage(LevelUpPath("LevelUpPower.png"));
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        } else if (LastTypeCardPlayed == CardType.SKILL) {
            loadCardImage(LevelUpPath("LevelUpSpeed.png"));
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[2];
        } else if (LastTypeCardPlayed == CardType.POWER) {
            loadCardImage(LevelUpPath("LevelUpFlight.png"));
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
        } else {
            loadCardImage(LevelUpPath("LevelUp.png"));
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[2];
        }

        if (upgraded) {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + this.rawDescription;
        }
        initializeDescription();
    }

    // "{@@}Add !M! Ring{!M!|>1=s} to your hand. ",
    //         "For each Ring in your hand, increase damage dealt from cards by !M!.",
    //         "For each Ring in your hand, increase Block gained from cards by !M!.",
    //         "For each Ring in your hand, gain !M! Temporary Focus at the start of your turn."

    private String LevelUpPath(String filename) {
        return imagePath("cards/skill/" + filename);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new LevelUp();
    }

    private void UpdateLastCardPlayed() {
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty()) {
            LastTypeCardPlayed = null;
            return;
        }
        LastTypeCardPlayed = ((AbstractCard) AbstractDungeon.actionManager.cardsPlayedThisCombat.get(
                AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1)).type;
    }

}
