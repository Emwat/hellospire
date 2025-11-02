package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theHedgehog.SonicTags;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

import static theHedgehog.SonicMod.attackCardsPlayedThisTurn;

public class TopKick extends BaseCard {
    public static final String ID = makeID("TopKick");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public TopKick() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
        tags.add(SonicTags.KICK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
//        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new VigorPower(p, magicNumber + attackCardsPlayedThisTurn - 1)));
    }

    // @Override
    // public void calculateCardDamage(AbstractMonster m) {
    //     int realMagicNumber = this.magicNumber;
    //     this.magicNumber = magicNumber + attackCardsPlayedThisTurn;
    //     super.calculateCardDamage(m);
    //     this.magicNumber = realMagicNumber;
    //     this.isMagicNumberModified = magicNumber != baseMagicNumber;
    //
    //     int realMagicNumber = this.baseMagicNumber;
    //     this.baseMagicNumber = baseMagicNumber + attackCardsPlayedThisTurn;
    //     super.calculateCardDamage(m);
    //     this.baseMagicNumber = realMagicNumber;
    //     this.isMagicNumberModified = magicNumber != baseMagicNumber;
    // }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new TopKick();
    }
}
