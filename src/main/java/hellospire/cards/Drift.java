package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ConjureBladeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.ConjureBlade;
import com.megacrit.cardcrawl.cards.tempCards.Expunger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import hellospire.SonicMod;
import hellospire.actions.DriftAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class Drift extends BaseCard {
    public static final String ID = makeID("Drift");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            -1
    );

    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = 2;

    public Drift() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
    }

    ///"DESCRIPTION": "Activate the passive effects of your orbs X times."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            addToBot(new DriftAction(p, this.freeToPlayOnce, this.energyOnUse + 1));
        } else {
            addToBot(new DriftAction(p, this.freeToPlayOnce, this.energyOnUse));
        }
    }

    @Override
    public void triggerWhenDrawn() {
        updateDescription();
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        updateDescription();
        //SonicMod.logger.info("cre" + Settings.CREAM_COLOR.toString());
    }

    private void updateDescription(){
        AbstractCard thisCard = this;
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int energy = EnergyPanel.totalCount;
                if (AbstractDungeon.player.hasRelic("Chemical X")) {
                    energy += 2;
                }
                if (thisCard.upgraded){
                    energy += 1;
                }
                energy = energy * 3;

                thisCard.rawDescription = String.format("%s%s%s", cardStrings.EXTENDED_DESCRIPTION[0], energy, cardStrings.EXTENDED_DESCRIPTION[1]);
                initializeDescription();
                this.isDone = true;
            }
        });
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Drift();
    }
}
