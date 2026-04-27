package theHedgehog.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicTags;
import theHedgehog.character.Sonic;
import theHedgehog.relics.AirBoostShoesRelic;
import theHedgehog.util.CardStats;
import theHedgehog.util.GeneralUtils;

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
    private static final int UPG_DAMAGE = 3;
    private static final int HORSE_KICK_MULTIPLIER = 2;

    public HorseKick() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        tags.add(SonicTags.KICK);
        tags.add(SonicTags.ERA_CLASSIC);
        tags.add(SonicTags.RIGHTMOST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
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

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        // int vigorAndMore = GeneralUtils.getVigorAndMoreAmount2(this.baseDamage);
        int vigorAndMore = (int)GeneralUtils.getVigorAndMoreAmount(this.baseDamage);
        if (this.forceConditionEffect || CheckIfRightCard(this, AbstractDungeon.player.hand)) {
            this.baseDamage = ((this.baseDamage + vigorAndMore) * HORSE_KICK_MULTIPLIER) - vigorAndMore;
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

        if (AbstractDungeon.player.hasRelic(AirBoostShoesRelic.ID)) {
            this.glowColor = Color.WHITE.cpy();
            return;
        }

        if (CheckIfRightCard(this, AbstractDungeon.player.hand)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new HorseKick();
    }
}
