package theHedgehog.cards;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import theHedgehog.MyModConfig;
import theHedgehog.SonicTags;
import theHedgehog.character.Sonic;
import theHedgehog.relics.CDFutureRelic;
import theHedgehog.relics.CDPastRelic;
import theHedgehog.rewards.AssistReward;
import theHedgehog.util.CardStats;

import java.util.ArrayList;

public class Assist extends BaseCard {
    public static final String ID = makeID("Assist");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    ArrayList<AbstractCard> characterCards = new ArrayList<>();

    public Assist() {
        super(ID, info);

        characterCards.add(new AssistAmy());
        characterCards.add(new AssistBarry());
        characterCards.add(new AssistBig());
        characterCards.add(new AssistBlaze());
        characterCards.add(new AssistCharmy());
        characterCards.add(new AssistChip());
        characterCards.add(new AssistCream());
        characterCards.add(new AssistEspio());
        characterCards.add(new AssistJet());
        characterCards.add(new AssistKnuckles());
        characterCards.add(new AssistLilac());
        characterCards.add(new AssistRouge());
        characterCards.add(new AssistShadow());
        characterCards.add(new AssistSilver());
        characterCards.add(new AssistSticks());
        characterCards.add(new AssistTails());
        characterCards.add(new AssistTikal());
        characterCards.add(new AssistVector());

        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int randomNumber = AbstractDungeon.cardRandomRng.random(0, characterCards.size() - 1);
        AbstractCard randomCard = characterCards.get(randomNumber).makeCopy();
        AbstractCard randomCard2 = characterCards.get(randomNumber).makeCopy();
        if (MyModConfig.enableCrossModIntegrations && Loader.isModLoaded("ModAchievement") && Loader.isModLoaded("GooglyMod")){
            if (!UnlockTracker.isAchievementUnlocked(makeID("GooglyEyes"))) {
                unlockGooglyEyesAchievement();
            }
        }

        if (this.upgraded) {
            randomCard.setCostForTurn(-99);
            randomCard.isCostModifiedForTurn = true;
        }

        addToBot(new MakeTempCardInHandAction(randomCard, true));
        if (!this.inBottleLightning) {
            AbstractDungeon.getCurrRoom().rewards.add(
                    new AssistReward(this, this.uuid, randomCard2, this.upgraded));
        }
        tags.add(SonicTags.LIKE_WATCHER);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Assist();
    }

    private void unlockGooglyEyesAchievement(){
        if (AbstractDungeon.player == null) {
            return;
        }

        UnlockTracker.unlockAchievement(makeID("GooglyEyes"));
    }
}
