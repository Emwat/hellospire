package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.character.Sonic;
import theHedgehog.powers.LevelUpSpeedPower;
import theHedgehog.util.CardStats;

public class LevelUpSpeedPick extends BaseCard {
    public static final String ID = makeID("LevelUpSpeedPick");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            -2
    );


    public LevelUpSpeedPick() {
        super(ID, info);
        this.cardsToPreview = new Ring();
        loadCardImage(imageSkillPath("LevelUpSpeed.png"));
        this.name =  CardCrawlGame.languagePack.getCardStrings(LevelUp.ID).EXTENDED_DESCRIPTION[4];
        initializeTitle();
        this.rawDescription = CardCrawlGame.languagePack.getCardStrings(LevelUp.ID).EXTENDED_DESCRIPTION[2];
        initializeDescription();

    }

          // "{@@}Add !M! Ring{!M!|>1=s} to your hand. ",
          //         "For each Ring in your hand, increase damage dealt from cards by 1.",
          //         "For each Ring in your hand, increase Block gained from cards by 1.",
          //         "For each Ring in your hand, increase Focus by 1.",
          //         "Level Up Speed",
          //         "Level Up Flight",
          //         "Level Up Power"

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void onChoseThisOption() {
        AbstractCreature p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new LevelUpSpeedPower(p, 1), 1));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new LevelUpSpeedPick();
    }
}
