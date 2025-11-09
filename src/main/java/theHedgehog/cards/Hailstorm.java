package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicTags;
import theHedgehog.actions.ModFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class Hailstorm extends BaseCard {
    public static final String ID = makeID("Hailstorm");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;
    public int timesDrawn = 0;

    public Hailstorm() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        tags.add(SonicTags.ERA_ADVENTURE);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ModFastAction(() -> {
            if (timesDrawn == 0) {
                incrementTimesDrawn();
            }
            for (int i = 0; i < timesDrawn; i++) {
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }
        }));
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        addToBot(new ModFastAction(() -> {
            incrementTimesDrawn();
        }));
    }

    private void incrementTimesDrawn() {
        timesDrawn++;

        if (timesDrawn == 1) {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + timesDrawn + cardStrings.EXTENDED_DESCRIPTION[1];
        } else {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + timesDrawn + cardStrings.EXTENDED_DESCRIPTION[2];
        }
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new Hailstorm();
    }
}
