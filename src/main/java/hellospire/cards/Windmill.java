package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicMod;
import hellospire.SonicTags;
import hellospire.SoundLibrary;
import hellospire.actions.HeavyIncrementAction;
import hellospire.actions.HeavyKeepCostAction;
import hellospire.actions.RandomizeCostAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class Windmill extends BaseCard {
    public static final String ID = makeID("Windmill");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;

    public Windmill() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        tags.add(SonicTags.HEAVY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HeavyIncrementAction(this));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        addToBot(new SelectCardsInHandAction(
                1,
                "Windmill: Select a card and randomize its cost for the rest of combat.",
                false, false, pickableCards, cards -> {
            for (AbstractCard c : cards) {
                int oldCost = c.costForTurn;
                addToBot(new RandomizeCostAction((BaseCard) c));
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        int difference = Math.abs(c.costForTurn - oldCost);
                        if (difference >= 3) {
                            addToBot(SoundLibrary.PlayVoice(SoundLibrary.PerfectBingo));
                        } else if (c.costForTurn == 0) {
                            addToBot(SoundLibrary.PlayVoice(SoundLibrary.Bingo));
                        }
                        this.isDone = true;
                    }
                });
            }
        }));
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        addToBot(new HeavyKeepCostAction(this));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Windmill();
    }
}
