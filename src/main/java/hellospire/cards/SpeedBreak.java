package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.ApplyBulletTimeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import hellospire.character.Sonic;
import hellospire.powers.SpeedHurtPower;
import hellospire.util.CardStats;

public class SpeedBreak extends BaseCard {
    public static final String ID = makeID("SpeedBreak");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            3
    );

    public SpeedBreak() {
        super(ID, info);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SpeedHurtPower(p)));
        addToBot(new ApplyBulletTimeAction());
    }

    public void upgrade() {

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SpeedBreak();
    }
}
