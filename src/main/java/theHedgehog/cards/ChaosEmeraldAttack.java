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
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import theHedgehog.MyModConfig;
import theHedgehog.SonicMod;
import theHedgehog.actions.ModTransformWorkaroundAction;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;
import theHedgehog.util.GeneralUtils;

import java.util.ArrayList;
import java.util.Arrays;

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

        if (chosenSaved == null) {
            chosenSaved = transformation == null ? GetRareAttackViaMisc() : transformation;
        }
        chosenSavedUpgraded = chosenSaved.makeCopy();
        chosenSavedUpgraded.upgrade();
        handleMultiCardPreview();
        this.tags.add(CardTags.HEALING);
        if (GeneralUtils.isIndeedWithoutADoubtInCombat()) {
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

    /// it didn't work with gambling chip
    private void OldTransform(){
        addToBot(new ModXFastAction(() -> {
            int index = 0;
            for (int i = 0; i < AbstractDungeon.player.hand.size(); i++) {
                AbstractCard thisCard = AbstractDungeon.player.hand.group.get(i);
                if (thisCard.equals(this)) {
                    index = i;
                    break;
                }
                index++;
            }

            addToTop(new TransformCardInHandAction(index, !this.upgraded ? chosenSaved : chosenSavedUpgraded));
        }));
    }

    private void OverwriteEverything() {

        // this = chosenSaved.makeCopy();

        // super(chosen.cardID, chosen.cost, chosen.type, chosen.target, CardRarity.RARE, Sonic.Meta.CARD_COLOR, "");
        cardStrings = CardCrawlGame.languagePack.getCardStrings(chosenSaved.cardID);
        name = cardStrings.NAME;
        originalName = cardStrings.NAME;
        rawDescription = cardStrings.DESCRIPTION;

        String[] multiDamageAttacks = {
                "BoostRare",
                "CyanLaserRare",
                "FootSweepRare"
        };
        if (Arrays.asList(multiDamageAttacks).contains(name)) {
            this.isMultiDamage = true;
            this.target = CardTarget.ALL_ENEMY;
        }

        if (chosenSaved.type == CardType.ATTACK) {
            this.type = CardType.ATTACK;
            loadCardImage(SonicMod.imagePath("cards/attack/" + CleanName() + ".png"));
            setDamage(chosenSaved.baseDamage, 5);
        } else if (chosenSaved.type == CardType.SKILL) {
            this.type = CardType.SKILL;
            loadCardImage(SonicMod.imagePath("cards/skill/" + CleanName() + ".png"));
        }
        setMagic(chosenSaved.baseMagicNumber);

        initializeTitle();
        initializeDescription();
    }

    private String CleanName() {
        return chosenSaved.cardID.replace("Rare", "").replace(makeID(""), "");
    }

    static private AbstractCard GetRareAttack(String input) {
        // return new StrikeRare().makeCopy();
        if (input.isEmpty()) {
            ArrayList<AbstractCard> poweredUpCards = new ArrayList<>();
            poweredUpCards.add(new BoostRare());
            poweredUpCards.add(new ChaosControl());
            poweredUpCards.add(new FalconPunchRare());
            poweredUpCards.add(new InstaShieldRare());
            poweredUpCards.add(new ScissorKickRare());
            poweredUpCards.add(new SonicEagleRare());
            poweredUpCards.add(new TeaserRare());
            int generatedRandomNumber = 0;
            try {
                generatedRandomNumber = AbstractDungeon.miscRng.random(0, poweredUpCards.size() - 1);
            } catch (NullPointerException nullPointerException) {
                generatedRandomNumber = (int) (Math.random() * poweredUpCards.size());
            }
            return poweredUpCards.get(generatedRandomNumber);
        }

        return CardLibrary.getCopy(input, 0, 0);
    }

    private ArrayList<AbstractCard> GetPoweredUpCards() {
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

    private void MarkAllCardsAsSeen() {
        ArrayList<AbstractCard> poweredUpCards = GetPoweredUpCards();
        for (AbstractCard card : poweredUpCards) {
            UnlockTracker.markCardAsSeen(card.cardID);
        }
    }

    private AbstractCard GetRareAttackViaMisc() {
        // return new StrikeRare().makeCopy();
        ArrayList<AbstractCard> poweredUpCards = GetPoweredUpCards();
        while (this.misc == 0) {
            this.misc = generateRandomNumber(poweredUpCards.size());
        }

        return poweredUpCards.get(this.misc);
    }

    private int generateRandomNumber(int cap) {
        int generatedRandomNumber = 0;
        try {
            generatedRandomNumber = AbstractDungeon.miscRng.random(0, cap - 1);
        } catch (NullPointerException nullPointerException) {
            generatedRandomNumber = (int) (Math.random() * cap);
        }

        return generatedRandomNumber;
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
        if (n == 0) return new BackSpinKickRare();
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
