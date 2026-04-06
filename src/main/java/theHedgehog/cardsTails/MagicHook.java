package theHedgehog.cardsTails;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PersistFields;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.cards.BaseCard;
import theHedgehog.cards.TopKick;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

import java.util.Iterator;

public class MagicHook extends BaseCard {
    public static final String ID = makeID("MagicHook");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public MagicHook() {
        super(ID, info);

        setCostUpgrade(0);
    }

    /// Draw an Attack that costs 2+. It costs 1 less this turn. NL Exhaust.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ModXFastAction(()->{
            if (p.drawPile.isEmpty() || p.hasPower(NoDrawPower.POWER_ID)) {
                return;
            }

            for (int i = 0; i < p.drawPile.size(); i++) {
                AbstractCard c = p.drawPile.group.get(i);
                if (c.costForTurn >= 2) {
                    AbstractDungeon.player.hand.addToHand(c);
                    c.unfadeOut();
                    c.unhover();
                    c.setAngle(0.0F, true);
                    c.lighten(false);
                    c.drawScale = 0.12F;
                    c.targetDrawScale = 0.75F;
                    c.fadingOut = false;
                    p.drawPile.removeCard(c);
                    p.onCardDrawOrDiscard();
                    c.applyPowers();
                    break;
                }
            }

        }));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new MagicHook();
    }
}