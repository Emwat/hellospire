package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.MyCharacter;
import hellospire.powers.GainedHeightPower;
import hellospire.powers.GainedTrickPower;
import hellospire.util.CardStats;

public class Trick extends BaseCard {
    public static final String ID = makeID("Trick");
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            -2
    );

    private static final int MAGIC = 1;

    public Trick() {
        super(ID, info);
        setMagic(MAGIC);
        this.exhaust = true;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!this.upgraded) {
            return false;
        }
        return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        addToBot(new TalkAction(p, "Yeah!", 1, 1));
//        addToBot(new ShoutAction(p, "Yeah!", 1, 1));

        addToBot(new SFXAction("YEAH"));
        addToBot(new SFXAction("COOL"));
        addToBot(new SFXAction("OK"));
        addToBot(new SFXAction("YES"));
        addToBot(new ApplyPowerAction(p, p, new GainedTrickPower(p, magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Trick();
    }
}
