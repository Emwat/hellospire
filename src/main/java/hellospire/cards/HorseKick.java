package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.GoForTheEyes;
import com.megacrit.cardcrawl.cards.purple.Sanctity;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import hellospire.SonicTags;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class HorseKick extends BaseCard {
    public static final String ID = makeID("HorseKick");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 14;
    private static final int UPG_DAMAGE = 2;

    public HorseKick() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    // Does not work for hologram
    // @Override
    // public void triggerWhenDrawn() {
    //     super.triggerWhenDrawn();
    //     addToBot(setIsRightmostCard(this));
    // }
    //
    // @Override
    // public void triggerOnOtherCardPlayed(AbstractCard c) {
    //     super.triggerOnOtherCardPlayed(c);
    //     addToBot(setIsRightmostCard(this));
    // }
    //
    // private AbstractGameAction setIsRightmostCard(AbstractCard thisCard){
    //     return new AbstractGameAction() {
    //         @Override
    //         public void update() {
    //             isRightmostCard = CheckIfRightCard(thisCard, AbstractDungeon.player.hand);
    //             this.isDone = true;
    //         }
    //     };
    // }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        if (CheckIfRightCard(this, AbstractDungeon.player.hand)) {
            this.baseDamage += this.baseDamage;
        }
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (isPlayerHandNull()) {
            return;
        }

        if (CheckIfRightCard(this, AbstractDungeon.player.hand)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new HorseKick();
    }
}
