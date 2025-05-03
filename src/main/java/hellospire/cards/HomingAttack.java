package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

public class HomingAttack extends BaseCard {
    public static final String ID = makeID("HomingAttack");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 2;

    public HomingAttack() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard height = new Height();
        AbstractCard trick = new Trick();

        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));

        addToBot(new MakeTempCardInHandAction(height, 1,true));
        addToBot(new MakeTempCardInHandAction(trick, 1,true));
    }

    // unused X
    private void Xuse(AbstractPlayer p, AbstractMonster m) {
        AbstractCard height = new Height();
        AbstractCard trick = new Trick();
        int cnt = EnergyPanel.totalCount;
        if (p.hasRelic("Chemical X")) {
            cnt += 2;
        }

        if (cnt > 0) {
            for (int i = 0; i < cnt; i++) {
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));


                addToBot(new MakeTempCardInHandAction(height, 1,true));
                addToBot(new MakeTempCardInHandAction(trick, 1,true));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new HomingAttack();
    }
}
