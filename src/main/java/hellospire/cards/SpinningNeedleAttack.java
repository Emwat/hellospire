package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import hellospire.SonicMod;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;
import java.util.Arrays;

public class SpinningNeedleAttack extends BaseCard {
    public static final String ID = makeID("SpinningNeedleAttack");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 4;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;
    private int adjacentCosts;

    public SpinningNeedleAttack() {
        super(ID, info);

        setDamage(DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        CalculateAdjacentCosts();
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        super.triggerOnOtherCardPlayed(c);
        CalculateAdjacentCosts();
    }

    private void CalculateAdjacentCosts(){
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ArrayList<AbstractCard> neighbors = getNeighbors(AbstractDungeon.player.hand, true);
                int totalCost = 0;
                for (AbstractCard neighbor : neighbors) {
                    SonicMod.logger.info(String.format("name %s | cost %s | EnergyPanel.totalCount %s",
                            neighbor.name, neighbor.cost, EnergyPanel.totalCount));
                    if (neighbor.cost == -1) {
                        SonicMod.logger.info(String.format("%s += EnergyPanel.totalCount %s", totalCost, EnergyPanel.totalCount));
                        totalCost += EnergyPanel.totalCount;
                    } else if (neighbor.cost < -1) {
                        totalCost += 0;
                    } else {
                        SonicMod.logger.info(String.format("%s += neighor.costForTurn %s", totalCost, neighbor.costForTurn));
                        totalCost += neighbor.costForTurn;
                    }
                }
                SonicMod.logger.info(String.format("totalCost is %s", totalCost));

                adjacentCosts = totalCost;
                this.isDone = true;
            }
        });

    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += adjacentCosts * magicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SpinningNeedleAttack();
    }
}
