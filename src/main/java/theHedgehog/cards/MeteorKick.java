package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theHedgehog.SonicTags;
import theHedgehog.character.Sonic;
import theHedgehog.relics.AirBoostShoesRelic;
import theHedgehog.util.CardStats;

public class MeteorKick extends BaseCard {
    public static final String ID = makeID("MeteorKick");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 3;
    private static final int MAGIC = 14;
    private static final int UPG_MAGIC = 3;
    private static final int heavyCost = 3;

    public MeteorKick() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC);
        tags.add(SonicTags.KICK);
        tags.add(SonicTags.ERA_MODERN);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        if (HasHeavyCard()) {
            this.baseDamage += magicNumber;
        }
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new MeteorKick();
    }

    private boolean HasHeavyCard() {
        if (this.cost >= heavyCost || this.costForTurn >= heavyCost) {
            return true;
        }

        if (AbstractDungeon.player == null) {
            return false;
        }

        CardGroup hand = AbstractDungeon.player.hand;
        if (hand.isEmpty()) {
            return false;
        }

        for (int i = 0; i < hand.size(); i++) {
            AbstractCard handCard = hand.group.get(i);
            if (handCard.cost >= heavyCost) {
                return true;
            }
        }
        return false;
    }
}
