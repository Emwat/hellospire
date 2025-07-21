package hellospire.events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Bite;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import hellospire.SonicMod;

import java.util.ArrayList;

import static hellospire.SonicMod.makeID;

public class RougeEvent extends PhasedEvent {
    public static final String ID = makeID("RougeEvent");

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final String IMG = SonicMod.imagePath("events/rougeTypingAway.png");

    public RougeEvent() {
        super(ID, NAME, IMG);

        registerPhase("start", new TextPhase(DESCRIPTIONS[0])
                .addOption(new TextPhase.OptionInfo(OPTIONS[1]).enabledCondition(() -> !getUncommonCards().isEmpty(), OPTIONS[1])
                        .setOptionResult((i)->{

                            // AbstractRelic relic = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
                            // AbstractDungeon.player.loseGold(70);
                            // AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, relic);
                            // AbstractEvent.logMetricObtainRelicAtCost(ID, "Obtained Relic", relic, 70); //Optional, adds information to run history
                            transitionKey("01accept");
                        }))
                .addOption(OPTIONS[2], (i)->transitionKey("02reject")));

        registerPhase("01accept", new TextPhase(DESCRIPTIONS[1]).addOption(OPTIONS[0], (i)->openMap()));
        registerPhase("02reject", new TextPhase(DESCRIPTIONS[2]).addOption(OPTIONS[0], (i)->openMap()));

        transitionKey("start");
    }

    private ArrayList<AbstractCard> getUncommonCards() {
        ArrayList<AbstractCard> masterDeck = AbstractDungeon.player.masterDeck.group;
        ArrayList<AbstractCard> uncommons = new ArrayList<>();
        ArrayList<AbstractCard> rares = new ArrayList<>();

        for (int i = 0; i < masterDeck.size() - 1; i++) {
            AbstractCard card = (AbstractCard)masterDeck.get(i);
            if (card.rarity == AbstractCard.CardRarity.UNCOMMON) {
                uncommons.add(card);
            } else if (card.rarity == AbstractCard.CardRarity.RARE) {
                rares.add(card);
            }
        }
        if (uncommons.isEmpty())
        {
            return rares;
        }
        return uncommons;

    }
}
