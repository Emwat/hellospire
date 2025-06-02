package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicTags;
import hellospire.actions.CrestOfFireAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;

public class VolcanoSlider extends BaseCard implements CrestOfFireCard{
    public static final String ID = makeID("VolcanoSlider");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 1;
    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 1;


    public VolcanoSlider() {
        this(0);
    }

    public VolcanoSlider(int upgrades) {
        super(ID, info);

        setDamage(DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
        this.timesUpgraded = upgrades;

        tags.add(SonicTags.CREST_OF_FIRE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.timesUpgraded > CREST_OF_FIRE_MARK){
            int self_damage = timesUpgraded - CREST_OF_FIRE_MARK;
            addToBot(new DamageAction(p, new DamageInfo(p, self_damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }

        for (AbstractCard c : getCardsToTheLeft()) {
            addToBot(new ExhaustSpecificCardAction(c, p.hand, true));
        }

//        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new CrestOfFireAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), p, this));
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int exhaustedCards = getCardsToTheLeft().size();
        int realBaseDamage = this.baseDamage;

        this.baseDamage += exhaustedCards * magicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void upgrade() {
        if (canUpgrade()) {
            //        this.upgradeDamage(UPG_DAMAGE);
            this.upgradeMagicNumber(UPG_MAGIC);
            ++this.timesUpgraded;
            this.upgraded = true;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            this.initializeTitle();
        }

    }

    public boolean canUpgrade() {
        return true;
//        if (this.timesUpgraded < 7) {
//            return true;
//        }
//        return false;
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (this.willBurnPlayer(this)) {
            this.glowColor = CrestOfFireCard.CREST_OF_FIRE_BURN_GLOW_COLOR.cpy();
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new VolcanoSlider(this.timesUpgraded);
    }

    private ArrayList<AbstractCard> getCardsToTheLeft() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> cardsToTheLeft = new ArrayList<>();
        boolean keepGoing = true;

        for (AbstractCard q : p.hand.group) {
            if (q == this) {
                keepGoing = false;
                break;
            }

            if (keepGoing) {
                cardsToTheLeft.add(q);
            }
        }

        return cardsToTheLeft;
    }
}
