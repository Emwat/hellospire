package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

public class Athleticism extends BaseCard {
    public static final String ID = makeID("Athleticism");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public Athleticism() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
    }

    ///     "DESCRIPTION": "Exhaust up to two cards. NL Your debuffs decrease by one."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ExhaustAction(magicNumber, false, true, true));
        addToBot(new RemoveDebuffsAction(p));
//        addToBot(new SelectCardsAction(p.hand.group, 2, "Athleticism: Select cards to exhaust", cards -> {
//            for (AbstractCard c : cards) {
//                addToBot(new ExhaustAction());
//            }
//        }));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Athleticism();
    }
}
