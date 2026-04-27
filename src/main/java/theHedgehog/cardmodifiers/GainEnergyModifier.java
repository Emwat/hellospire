package theHedgehog.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static theHedgehog.SonicMod.makeID;

// https://github.com/daviscook477/BaseMod/wiki/CardModifiers
public class GainEnergyModifier extends AbstractCardModifier {

    public static String ID = makeID("modifierGainEnergy");
    private final static String gainEnergyDescription = CardCrawlGame.languagePack.getUIString(ID).TEXT[0];
    private int energyGain;

    public GainEnergyModifier(int energyGain) {
        this.energyGain = energyGain;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        StringBuilder stringBuilder = new StringBuilder();
        if (energyGain > 0) {
            for (int i = 0; i < energyGain; i++) {
                stringBuilder.append(" [E] ");
            }
        }

        return rawDescription + gainEnergyDescription.replace("{X}", stringBuilder.toString());
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        super.onUse(card, target, action);
        addToBot(new GainEnergyAction(energyGain));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new GainEnergyModifier(this.energyGain);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}