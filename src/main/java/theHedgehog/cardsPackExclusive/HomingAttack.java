package theHedgehog.cardsPackExclusive;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicMod;
import theHedgehog.cards.BaseCard;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;
import thePackmaster.ThePackmaster;

public class HomingAttack extends BaseCard {
    public static final String ID = makeID("PackHomingAttack");
    private static final CardStats info = Loader.isModLoaded("anniv5") ?
            new CardStats(
            ThePackmaster.Enums.PACKMASTER_RAINBOW,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    ) : new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 1;
    private static final int PERSIST = 2;
    private static final int PERSIST_UPG = 1;

    public HomingAttack() {
        super(ID, info);
        this.cardsToPreview = new theHedgehog.cardsPackExclusive.Trick();

        setDamage(DAMAGE, UPG_DAMAGE);
        setBackgroundTexture(SonicMod.characterPath("cardback/bg_attack.png"), SonicMod.characterPath("cardback/bg_attack_p"));
        // PersistFields.setBaseValue(this, PERSIST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), 1, true));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.cardsToPreview.upgrade();
            // PersistFields.upgrade(this, PERSIST_UPG);
        }
        super.upgrade();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new HomingAttack();
    }
}
