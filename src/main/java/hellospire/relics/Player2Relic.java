package hellospire.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.defect.SeekAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Hologram;
import com.megacrit.cardcrawl.cards.red.DualWield;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.CentennialPuzzle;
import com.megacrit.cardcrawl.relics.HappyFlower;
import hellospire.SonicMod;
import hellospire.character.Sonic;

import static hellospire.SonicMod.makeID;

public class Player2Relic extends BaseRelic {
    private static final String NAME = "Player2Relic"; // The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); // This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.RARE; // The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; // The sound played when the relic is clicked.
    private static int cardsPlayed = 0;

    public Player2Relic() {
        super(ID, NAME, Sonic.Meta.CARD_COLOR, RARITY, SOUND);

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public void atTurnStart() {
        this.counter++;

        if (this.counter <= 2) {
            cardsPlayed = 0;
        }
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        cardsPlayed++;

        if (cardsPlayed == 1) {
            this.pulse = true;
            this.beginPulse();
        }

        if (cardsPlayed == 2) {
            Player2Relic thisRelic = this;
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    thisRelic.flash();
                    thisRelic.stopPulse();
                    addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
                    this.isDone = true;
                }
            });

        }

    }
}
