package hellospire.events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import hellospire.SonicMod;

import java.util.ArrayList;

import static hellospire.SonicMod.makeID;

public class GravitySwitchEvent extends PhasedEvent {
    public static final String ID = makeID("GravitySwitchEvent");

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final String IMG = SonicMod.imagePath("events/gravitySwitch.png");

    public GravitySwitchEvent() {
        super(ID, NAME, IMG);

        registerPhase("start", new TextPhase(DESCRIPTIONS[0])
                .addOption(new TextPhase.OptionInfo(OPTIONS[0]).enabledCondition(() -> !getUncommonCards().isEmpty(), OPTIONS[1])
                        .setOptionResult((i)->{
                            ArrayList<Integer> randomIndexes = new ArrayList<Integer>();
                            for (int j = 0; j < 5; j++) {
                                int randomNumber = AbstractDungeon.eventRng.random(0, AbstractDungeon.player.masterDeck.size() - 1);
                                while (randomIndexes.contains(randomNumber)) {
                                    randomNumber = AbstractDungeon.eventRng.random(0, AbstractDungeon.player.masterDeck.size() - 1);
                                }
                                randomIndexes.add(randomNumber);
                            }


                            transitionKey("01accept");
                        }))
                .addOption(OPTIONS[2], (i)->transitionKey("02reject")));

        registerPhase("01accept", new TextPhase(DESCRIPTIONS[1]).addOption(OPTIONS[3], (i)->openMap()));
        registerPhase("02reject", new TextPhase(DESCRIPTIONS[2]).addOption(OPTIONS[3], (i)->openMap()));

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
