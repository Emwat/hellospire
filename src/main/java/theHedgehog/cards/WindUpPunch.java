package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;
import theHedgehog.util.GeneralUtils;

public class WindUpPunch extends BaseCard {
    public static final String ID = makeID("WindUpPunch");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 12;
    private static final int UPG_DAMAGE = 2;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;
    private static final int EXTRA_DAMAGE = 2;
    private static final int UPG_EXTRA_DAMAGE = 2;
    private static final String EXTRA_DAMAGE_KEYWORD = "CustomVar_DAMAGE";

    public WindUpPunch() {
        super(ID, info);
        this.cardsToPreview = new Trick();

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
        // setCustomVar(EXTRA_DAMAGE_KEYWORD, EXTRA_DAMAGE, UPG_EXTRA_DAMAGE);
        tags.add(SonicTags.LIKE_IRONCLAD);
        tags.add(SonicTags.ERA_CLASSIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.Attack5Go));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), 1 ));
    }

    // @Override
    // public void calculateCardDamage(AbstractMonster mo) {
    //     int realBaseDamage = this.baseDamage;
    //     if (getPower(AbstractDungeon.player, VigorPower.POWER_ID) > 0) {
    //         this.baseDamage = this.baseDamage + customVar(EXTRA_DAMAGE_KEYWORD);
    //     }
    //     super.calculateCardDamage(mo);
    //     this.baseDamage = realBaseDamage;
    //     this.isDamageModified = this.damage != this.baseDamage;
    // }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new WindUpPunch();
    }
}
