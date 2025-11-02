package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.ApplyBulletTimeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class SpeedBreak extends BaseCard {
    public static final String ID = makeID("SpeedBreak");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    private static final int MAGIC = 3;
    // private static final int UPG_MAGIC = 2;

    public SpeedBreak() {
        super(ID, info);
//        setExhaust(true);
        setMagic(MAGIC);
        setCostUpgrade(2);
        tags.add(SonicTags.LIKE_SILENT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.SpeedBreak));
        addToBot(new DamageAction(p, new DamageInfo(p, magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        // addToBot(new ApplyPowerAction(p, p, new SpeedHurtPower(p)));
        // addToBot(new ApplyPowerAction(p, p, new NoDrawPower(p), 1));
        addToBot(new ApplyBulletTimeAction());
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SpeedBreak();
    }
}
