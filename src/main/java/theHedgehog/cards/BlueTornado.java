package theHedgehog.cards;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Trip;
import com.megacrit.cardcrawl.cards.green.Terror;
import com.megacrit.cardcrawl.cards.red.Shockwave;
import com.megacrit.cardcrawl.cards.red.Uppercut;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.ModFastAction;
import theHedgehog.actions.ModTextInCenterAction;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.actions.RandomizeCostAction;
import theHedgehog.cardmodifiers.ModRetainModifier;
import theHedgehog.character.Sonic;
import theHedgehog.powers.DizzyPower;
import theHedgehog.util.CardStats;
import theHedgehog.util.TextureLoader;

public class BlueTornado extends BaseCard {
    public static final String ID = makeID("BlueTornado");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private static final int DIZZY = 0;
    private static final int UPG_DIZZY = 2;
    private static final String DIZZY_KEYWORD = "CustomVar_DIZZY";

    public BlueTornado() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        // setCustomVar(DIZZY_KEYWORD, DIZZY, UPG_DIZZY);
        tags.add(SonicTags.LIKE_SILENT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // TogetherInSpire
        if (this.target != CardTarget.ENEMY && m == null) {
            m = getLastMonsterOrNull();
            if (m == null) {
                AbstractCard c = GenerateFailSafe();
                addToBot(new MakeTempCardInHandAction(c, 1));
                this.purgeOnUse = true;
                return;
            }
        }

        addToBot(SoundLibrary.SoundAction(SoundLibrary.BlueTornado));

        addToBot(new ApplyPowerAction(m, p, new DizzyPower(m, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));
        addToBot(new SelectCardsInHandAction(
                1,
                CardCrawlGame.languagePack.getUIString(makeID("BlueTornadoMessage")).TEXT[0],
                false, true, pickableCards, cards -> {
            for (AbstractCard c : cards) {
                addToBot(new ModXFastAction(() -> {
                    if (c.isCostModifiedForTurn) {
                        int newCost = 0;
                        if (c.costForTurn >= 3) {
                            newCost = 0;
                        } else if (c.costForTurn == 2) {
                            newCost = 1;
                        } else if (c.costForTurn == 1) {
                            newCost = 2;
                        } else if (c.costForTurn == 0) {
                            newCost = 3;
                        }
                        BaseCard.setCostForCombat(c, newCost);
                    } else {
                        int newCost = 0;
                        if (c.cost >= 3) {
                            newCost = 0;
                        } else if (c.cost == 2) {
                            newCost = 1;
                        } else if (c.cost == 1) {
                            newCost = 2;
                        } else if (c.cost == 0) {
                            newCost = 3;
                        }
                        BaseCard.setCostForCombat(c, newCost);
                    }
                    c.isCostModifiedForTurn = true;
                }));
            }
        }));

    }

    private AbstractCard GenerateFailSafe(){
        AbstractCard c = new Uppercut().makeStatEquivalentCopy();
        BaseCard.setCostForCombat(c, 0);
        c.baseDamage = 0;

        if (this.upgraded) {
            c.upgrade();
        }
        CardModifierManager.addModifier(c, new ModRetainModifier());
        c.name = cardStrings.EXTENDED_DESCRIPTION[2];
        c.initializeDescription();
        return c;
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new BlueTornado();
    }
}
