package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicTags;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class Windmill extends BaseCard {
    public static final String ID = makeID("Windmill");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;

    public Windmill() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        tags.add(SonicTags.ANTI_DASH);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
//        addToBot(new SelectCardsAction(
//                p.hand.group,
//                1,
//                "Windmill: Select a card and randomize its cost for this turn.",
//                cards -> {
//                    for (AbstractCard c : cards) {
////                c.modifyCostForCombat(AbstractDungeon.cardRandomRng.random(0, 3));
//                        c.setCostForTurn(AbstractDungeon.cardRandomRng.random(0, 3));
//                    }
//                }));
        addToBot(new SelectCardsInHandAction(
                1,
                "Windmill: Select a card and randomize its cost for the rest of combat.",
                false, false, cardFilter -> {
            return true;
        }, cards -> {
            for (AbstractCard c : cards) {
                int currentCost = c.costForTurn;
//                c.modifyCostForCombat(AbstractDungeon.cardRandomRng.random(0, 3));
                c.setCostForTurn(AbstractDungeon.cardRandomRng.random(0, 3));
                if (currentCost != c.costForTurn) {
                    c.isCostModifiedForTurn = true;
                }
            }
        }));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Windmill();
    }
}
