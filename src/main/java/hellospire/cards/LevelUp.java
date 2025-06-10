package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.FTL;
import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.cards.purple.Sanctity;
import com.megacrit.cardcrawl.cards.purple.SashWhip;
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
    private static final int MAGIC = 1;
    private static CardType LastTypeCardPlayed;

    public LevelUp() {
        super(ID, info);

        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.PlayVoice(SoundLibrary.LevelUp));
        if (LastTypeCardPlayed == CardType.ATTACK) {
            addToBot(new ApplyPowerAction(p, p, new LevelUpPowerPower(p, magicNumber)));
        } else if (LastTypeCardPlayed == CardType.SKILL) {
            addToBot(new ApplyPowerAction(p, p, new LevelUpSpeedPower(p, magicNumber)));
        } else if (LastTypeCardPlayed == CardType.POWER) {
            addToBot(new ApplyPowerAction(p, p, new LevelUpFlightPower(p, magicNumber)));
        } else {
            addToBot(new MakeTempCardInHandAction(new Ring().makeStatEquivalentCopy(), magicNumber ));
//            addToBot(new ApplyPowerAction(p, p, new LevelUpSpeedPower(p, magicNumber)));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
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

    private void UpdateCardImageAndText(){
        if (LastTypeCardPlayed == CardType.ATTACK) {
            loadCardImage(LevelUpPath("LevelUpPower.png"));
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            initializeDescription();
        } else if (LastTypeCardPlayed == CardType.SKILL) {
            loadCardImage(LevelUpPath("LevelUpSpeed.png"));
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[2];
            initializeDescription();
        } else if (LastTypeCardPlayed == CardType.POWER) {
            loadCardImage(LevelUpPath("LevelUpFlight.png"));
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
            initializeDescription();
        } else {
            loadCardImage(LevelUpPath("LevelUp.png"));
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
        }
    }
//       "Add !M! Rings to your hand.",
//               "Rings give you !M! Strength.",
//               "Rings give you !M! Dexterity.",
//               "Rings give you !M! Temporary Focus at the start of your turn."

    private String LevelUpPath(String filename){
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
