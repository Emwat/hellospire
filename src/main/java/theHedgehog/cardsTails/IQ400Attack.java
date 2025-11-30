package theHedgehog.cardsTails;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.TransformCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.AllOutAttack;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.ModFastAction;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.actions.RandomizeCostAction;
import theHedgehog.cards.BaseCard;
import theHedgehog.cards.TopKick;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class IQ400Attack extends BaseCard {
    public static final String ID = makeID("IQ400Attack");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 4;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public IQ400Attack() {
        super(ID, info);
        this.isMultiDamage = true;
        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new SelectCardsInHandAction(
                1,
                CardCrawlGame.languagePack.getUIString(makeID("IQ400Message")).TEXT[0],
                false, false, pickableCards, cards -> {
            for (AbstractCard c : cards) {
                int randomNumber = AbstractDungeon.cardRandomRng.random(p.hand.size() - 1);
                addToBot(new TransformCardInHandAction(randomNumber, c.makeStatEquivalentCopy()));
            }
        }));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new IQ400Attack();
    }
}