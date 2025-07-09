package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.powers.LevelUpPowerPower;
import hellospire.powers.LevelUpSpeedPower;
import hellospire.util.CardStats;

import static hellospire.SonicMod.imagePath;

public class LevelUpPowerPick extends BaseCard {
    public static final String ID = makeID("LevelUpPowerPick");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            -2
    );


    public LevelUpPowerPick() {
        super(ID, info);
        this.cardsToPreview = new Ring();
        loadCardImage(imageSkillPath("LevelUpPower.png"));
        this.name = CardCrawlGame.languagePack.getCardStrings(LevelUp.ID).EXTENDED_DESCRIPTION[6];
        initializeTitle();
        this.rawDescription = CardCrawlGame.languagePack.getCardStrings(LevelUp.ID).EXTENDED_DESCRIPTION[1];
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
        addToBot(new ApplyPowerAction(p, p, new LevelUpPowerPower(p, 1), 1));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new LevelUpPowerPick();
    }
}
