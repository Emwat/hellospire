package theHedgehog.cards;

import basemod.abstracts.CustomSavable;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.TransformCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.GeneticAlgorithm;
import com.megacrit.cardcrawl.cards.colorless.FlashOfSteel;
import com.megacrit.cardcrawl.cards.colorless.Panache;
import com.megacrit.cardcrawl.cards.colorless.RitualDagger;
import com.megacrit.cardcrawl.cards.purple.Ragnarok;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import theHedgehog.MyModConfig;
import theHedgehog.SonicMod;
import theHedgehog.actions.ModTransformWorkaroundAction;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;
import theHedgehog.util.GeneralUtils;

import java.util.ArrayList;

public class ChaosEmeraldAttack extends BaseCard implements CustomSavable<Integer> {
    public static final String ID = makeID("ChaosEmeraldAttack");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            0
    );
    public AbstractCard chosenSaved = null;
    public AbstractCard chosenSavedUpgraded = null;
    public ChaosEmeraldAttack() {
        this(null);
    }
    public ChaosEmeraldAttack(AbstractCard transformation) {
        super(ID, info);

        // A transformation is copied so copies of this card never share one card instance.
        chosenSaved = transformation == null ? RollRareAttack() : transformation.makeCopy();
        chosenSavedUpgraded = chosenSaved.makeCopy();
        chosenSavedUpgraded.upgrade();
        handleMultiCardPreview();
        this.tags.add(CardTags.HEALING);
        if (!allCardsMarkedAsSeen && GeneralUtils.isIndeedWithoutADoubtInCombat()) {
            MarkAllCardsAsSeen();
        }
    }

    private void handleMultiCardPreview(){
        MultiCardPreview.clear(this);
        MultiCardPreview.add(this, chosenSaved.makeCopy(), chosenSavedUpgraded);
        if (chosenSaved instanceof FalconPunchRare) {
            AbstractCard card = new FalconPunchRare2();
            AbstractCard upgCard = new FalconPunchRare2();
            upgCard.upgrade();
            MultiCardPreview.add(this, card, upgCard);
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        // addToBot(new ModTransformCardAction(this, !this.upgraded ? chosenSaved : chosenSavedUpgraded));
        addToBot(new ModTransformWorkaroundAction(this, GetChosenCard()));
    }

    private AbstractCard GetChosenCard(){
        return !this.upgraded ? chosenSaved : chosenSavedUpgraded;
    }

    /**
     * The single source of truth for which attacks Chaos Emerald can become.
     * The order here is NOT the save order; see {@link #cardToInt(AbstractCard)}.
     */
    private static ArrayList<AbstractCard> GetPoweredUpCards() {
        ArrayList<AbstractCard> poweredUpCards = new ArrayList<>();
        poweredUpCards.add(new BoostRare());
        poweredUpCards.add(new ChaosControl());
        poweredUpCards.add(new FalconPunchRare());
        poweredUpCards.add(new InstaShieldRare());
        poweredUpCards.add(new ScissorKickRare());
        poweredUpCards.add(new SonicEagleRare());
        poweredUpCards.add(new TeaserRare());
        return poweredUpCards;
    }

    private static boolean allCardsMarkedAsSeen = false;

    private void MarkAllCardsAsSeen() {
        allCardsMarkedAsSeen = true; // UnlockTracker persists this, so once per session is enough.
        for (AbstractCard card : GetPoweredUpCards()) {
            UnlockTracker.markCardAsSeen(card.cardID);
        }
    }

    /** Picks uniformly from every candidate (including the first one). */
    private static AbstractCard RollRareAttack() {
        ArrayList<AbstractCard> poweredUpCards = GetPoweredUpCards();
        return poweredUpCards.get(generateRandomNumber(poweredUpCards.size()));
    }

    private static int generateRandomNumber(int cap) {
        try {
            return AbstractDungeon.miscRng.random(0, cap - 1);
        } catch (NullPointerException nullPointerException) {
            // No run in progress (card library, main menu): cosmetic roll only.
            return (int) (Math.random() * cap);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (chosenSaved == null) {
            SonicMod.logger.error("chosenSaved is null.");
            new BoostRare().use(p, m);
            return;
        }
        // chosenSaved.use(p, m);
        addToBot(new ModTransformWorkaroundAction(this, GetChosenCard()));
    }

    private static AbstractCard intToCard(int n) {
        // Must mirror cardToInt. Previously 0 mapped to BackSpinKickRare, so a saved
        // ChaosControl reloaded as the wrong attack.
        if (n == 0) return new ChaosControl();
        else if (n == 1) return new BoostRare();
        else if (n == 2) return new FalconPunchRare();
        else if (n == 3) return new InstaShieldRare();
        else if (n == 4) return new ScissorKickRare();
        else if (n == 5) return new SonicEagleRare();
        else if (n == 6) return new TeaserRare();
        AbstractCard fallback = new BoostRare();
        fallback.name = "null attack";
        return fallback;
    }

    private static Integer cardToInt(AbstractCard card) {
        if (card instanceof ChaosControl) return 0;
        else if (card instanceof BoostRare) return 1;
        else if (card instanceof FalconPunchRare) return 2;
        else if (card instanceof InstaShieldRare) return 3;
        else if (card instanceof ScissorKickRare) return 4;
        else if (card instanceof SonicEagleRare) return 5;
        else if (card instanceof TeaserRare) return 6;
        return 7;
    }

    @Override
    public Integer onSave() {
        return cardToInt(this.chosenSaved);
    }

    @Override
    public void onLoad(Integer n) {
        if (n == null) {
            this.chosenSaved = new BoostRare();
            this.chosenSaved.name = "null attack";
            return;
        }
        this.chosenSaved = intToCard(n);
        chosenSavedUpgraded = chosenSaved.makeCopy();
        chosenSavedUpgraded.upgrade();
        handleMultiCardPreview();
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new ChaosEmeraldAttack(this.chosenSaved);
    }
}
