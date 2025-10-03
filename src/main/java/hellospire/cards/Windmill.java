package hellospire.cards;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Anger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SoundLibrary;
import hellospire.actions.ModFastAction;
import hellospire.actions.RandomizeCostAction;
import hellospire.cardmodifiers.SpinUpModifier;
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

    // Anger 6 Damage
    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;

    public Windmill() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        CardModifierManager.addModifier(this, new SpinUpModifier());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        addToBot(new SelectCardsInHandAction(
                1,
                CardCrawlGame.languagePack.getUIString(makeID("WindmillMessage")).TEXT[0],
                false, false, pickableCards, cards -> {
            for (AbstractCard c : cards) {
                int oldCost = c.costForTurn;
                addToBot(new RandomizeCostAction(c));
                addToBot(new ModFastAction(() -> {
                    int difference = Math.abs(c.costForTurn - oldCost);
                    if (difference >= 3 && c.costForTurn == 0) {
                        addToBot(SoundLibrary.VoiceAction(SoundLibrary.PerfectBingo));
                    } else if (c.costForTurn == 0) {
                        addToBot(SoundLibrary.VoiceAction(SoundLibrary.Bingo));
                        if (p instanceof Sonic) {
                            addToBot(new ModFastAction(() -> {
                                ((Sonic) p).playAnimation("happy");
                            }));
                        }
                    } else if (c.costForTurn == 3 || (oldCost == 2 && c.costForTurn == 2)) {
                        if (p instanceof Sonic) {
                            addToBot(new ModFastAction(() -> {
                                ((Sonic) p).playAnimation("hurt");
                            }));
                        }
                    }
                }));
            }
        }));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new Windmill();
    }
}
