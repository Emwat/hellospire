package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicMod;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;

public class Gizoid extends BaseCard {
    public static final String ID = makeID("Gizoid");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.CURSE,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            -2
    );

    public Gizoid() {
        super(ID, info);
        this.setSelfRetain(true);
    }

    /// "Show me your power. Or I shall not obey. I represent all things, and shall become Gizoid, the conquerer of all."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    private class EmerlPackage {
        public AbstractCard card;
        public String message;

        public EmerlPackage(AbstractCard card, String message) {
            this.card = card;
            this.message = message;
        }
    }


    @Override
    public void onRetained() {
        super.onRetained();
        ArrayList<EmerlPackage> Packages = new ArrayList<>();
        Packages.add(new EmerlPackage(new GizoidSonic(), cardStrings.EXTENDED_DESCRIPTION[0]));
        Packages.add(new EmerlPackage(new GizoidTails(), cardStrings.EXTENDED_DESCRIPTION[1]));
        Packages.add(new EmerlPackage(new GizoidKnuckles(), cardStrings.EXTENDED_DESCRIPTION[2]));
        Packages.add(new EmerlPackage(new GizoidAmy(), cardStrings.EXTENDED_DESCRIPTION[3]));
        Packages.add(new EmerlPackage(new GizoidCream(), cardStrings.EXTENDED_DESCRIPTION[4]));
        Packages.add(new EmerlPackage(new GizoidRouge(), cardStrings.EXTENDED_DESCRIPTION[4]));
        Packages.add(new EmerlPackage(new GizoidShadow(), cardStrings.EXTENDED_DESCRIPTION[5]));
        Packages.add(new EmerlPackage(new GizoidChaos(), cardStrings.EXTENDED_DESCRIPTION[7]));
        Packages.add(new EmerlPackage(new GizoidEggman(), cardStrings.EXTENDED_DESCRIPTION[6]));
        Packages.add(new EmerlPackage(new GizoidE102r(), cardStrings.EXTENDED_DESCRIPTION[7]));

        EmerlPackage randomCard = Packages.get(AbstractDungeon.cardRandomRng.random(0, Packages.size() - 1));
        loadCardImage(SonicMod.imagePath("cards/skill/EmeraldShard.png"));
        addToBot(new MakeTempCardInHandAction(randomCard.card.makeStatEquivalentCopy(), 1));
        this.rawDescription = randomCard.message;
        initializeDescription();
    }
//     "EXTENDED_DESCRIPTION": [
//             "Thank you Sonic.",
//             "Has Tails gotten stronger?",
//             "Don't tease him too much, okay?",
//             "Mom.",
//             "I'll miss all of you.",
//             "Should I be glad that...I was born...?",
//             "Initiating data acquisition..."
//             "..."
//             ]

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Gizoid();
    }
}
