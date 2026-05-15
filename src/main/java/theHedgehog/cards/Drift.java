package theHedgehog.cards;

import com.evacipated.cardcrawl.mod.stslib.variables.RefundVariable;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theHedgehog.SonicTags;
import theHedgehog.actions.DriftAction;
import theHedgehog.actions.ModFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class Drift extends BaseCard {
    public static final String ID = makeID("Drift");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            -1
    );

    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 1;
    private static final int MULTIPLIER = 2;
    private static final String MULTIPLIER_KEYWORD = "CustomVar_MULTIPLIER"; // Yultiplier for ZHS

    public Drift() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        setCustomVar(MULTIPLIER_KEYWORD, MULTIPLIER);
        tags.add(SonicTags.LIKE_DEFECT);
        RefundVariable.setBaseValue(this, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DriftAction(p, customVar(MULTIPLIER_KEYWORD), this.freeToPlayOnce, this.energyOnUse + magicNumber));
        // addToBot(new GainEnergyAction(ENERGY_GAIN));
    }

    @Override
    public void triggerWhenDrawn() {
        updateDescription();
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        updateDescription();
    }

    private void updateDescription() {
        AbstractCard thisCard = this;
        addToBot(new ModFastAction(() -> {
            int energy = EnergyPanel.totalCount;
            if (AbstractDungeon.player.hasRelic("Chemical X")) {
                energy += 2;
            }
            if (thisCard.upgraded) {
                energy += 1;
            }
            energy = energy * customVar(MULTIPLIER_KEYWORD);

            thisCard.rawDescription = String.format("%s%s%s", cardStrings.EXTENDED_DESCRIPTION[0], energy, cardStrings.EXTENDED_DESCRIPTION[1]);
            initializeDescription();
        }));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new Drift();
    }
}
