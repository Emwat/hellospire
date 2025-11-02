package theHedgehog.cards;

import com.evacipated.cardcrawl.mod.stslib.variables.RefundVariable;
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

    private static final int MAGIC = 2;

    public Drift() {
        super(ID, info);

        setMagic(MAGIC);

        tags.add(SonicTags.LIKE_DEFECT);
        RefundVariable.setBaseValue(this, 1);
    }

    /// "DESCRIPTION": "Activate the passive effects of your orbs X times."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            addToBot(new DriftAction(p, magicNumber, this.freeToPlayOnce, this.energyOnUse + 1));
        } else {
            addToBot(new DriftAction(p, magicNumber, this.freeToPlayOnce, this.energyOnUse));
        }
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
            energy = energy * magicNumber;

            thisCard.rawDescription = String.format("%s%s%s", cardStrings.EXTENDED_DESCRIPTION[0], energy, cardStrings.EXTENDED_DESCRIPTION[1]);
            initializeDescription();
        }));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new Drift();
    }
}
