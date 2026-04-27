package theHedgehog.cards;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import com.megacrit.cardcrawl.relics.SneckoEye;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theHedgehog.actions.LoopDeLoopAction;
import theHedgehog.actions.ModFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.relics.AirBoostShoesRelic;
import theHedgehog.util.CardStats;

import java.util.ArrayList;
import java.util.List;

import static theHedgehog.util.GeneralUtils.ColorWord;

public class LoopDeLoop extends BaseCard {
    public static final String ID = makeID("LoopDeLoop");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private static final Color FLAVOR_BOX_COLOR = CardHelper.getColor(50, 50, 255);
    private static final Color FLAVOR_TEXT_COLOR = CardHelper.getColor(0, 0, 0);

    public LoopDeLoop() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        setExhaustive(2);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> output = new ArrayList<TooltipInfo>();
        output.add(new TooltipInfo(cardStrings.EXTENDED_DESCRIPTION[5], cardStrings.EXTENDED_DESCRIPTION[6]));
        return output;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoopDeLoopAction(p, magicNumber));
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        updateDescription(true);
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (isPlayerHandNull()) {
            return;
        }

        if (AbstractDungeon.player.hand.size() + magicNumber > BaseMod.MAX_HAND_SIZE + 1){
            this.glowColor = Color.RED.cpy();
        }

        if (AbstractDungeon.player.discardPile.isEmpty() || AbstractDungeon.player.hasPower(NoDrawPower.POWER_ID)) {
            this.glowColor = Color.RED.cpy();
            return;
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
        updateDescription(false);
    }

    private void updateDescription(boolean forceBasicDescription) {
        AbstractCard thisCard = this;
        addToBot(new ModFastAction(() -> {
            int discardPileSize = AbstractDungeon.player.discardPile.size();
            boolean hasConfused = AbstractDungeon.player.hasPower(ConfusionPower.POWER_ID);

            if (forceBasicDescription || discardPileSize == 0) {
                thisCard.rawDescription = !this.upgraded ? cardStrings.DESCRIPTION : cardStrings.UPGRADE_DESCRIPTION;
            } else if (!this.upgraded || discardPileSize == 1) {
                AbstractCard discardCard = AbstractDungeon.player.discardPile.getNCardFromTop(0);
                if (!hasConfused) {
                    thisCard.rawDescription = String.format("%s%s%s",
                            cardStrings.EXTENDED_DESCRIPTION[0],
                            ColorWord("[#efc851ff]", discardCard.name),
                            cardStrings.EXTENDED_DESCRIPTION[2]);
                } else {
                    thisCard.rawDescription = String.format("%s%s%s",
                            cardStrings.EXTENDED_DESCRIPTION[0],
                            ColorWord("[#efc851ff]", discardCard.name),
                            cardStrings.EXTENDED_DESCRIPTION[4]);
                }
            } else if (this.upgraded && discardPileSize >= 2){
                AbstractCard discardCard1 = AbstractDungeon.player.discardPile.getNCardFromTop(0);
                AbstractCard discardCard2 = AbstractDungeon.player.discardPile.getNCardFromTop(1);
                if (!hasConfused) {
                    thisCard.rawDescription = String.format("%s%s%s%s%s",
                            cardStrings.EXTENDED_DESCRIPTION[0],
                            ColorWord("[#efc851ff]", discardCard1.name),
                            cardStrings.EXTENDED_DESCRIPTION[1],
                            ColorWord("[#efc851ff]", discardCard2.name),
                            cardStrings.EXTENDED_DESCRIPTION[3]);
                } else {
                    thisCard.rawDescription = String.format("%s%s%s%s%s",
                            cardStrings.EXTENDED_DESCRIPTION[0],
                            ColorWord("[#efc851ff]", discardCard1.name),
                            cardStrings.EXTENDED_DESCRIPTION[1],
                            ColorWord("[#efc851ff]", discardCard2.name),
                            cardStrings.EXTENDED_DESCRIPTION[4]);
                }
            }

            initializeDescription();
        }));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new LoopDeLoop();
    }
}
