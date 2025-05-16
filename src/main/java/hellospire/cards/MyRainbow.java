package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

public class MyRainbow extends BaseCard {
    public static final String ID = makeID("MyRainbow");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            1
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public MyRainbow() {
        super(ID, info);

//        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
        setExhaust(true);
    }

    /// Play the top 3 cards of your draw pile.
    /// NEED TO CAP THIS. if MyRainbow is the only card, it will infinitely loop.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            this.addToBot(new AbstractGameAction() {
                public void update() {
                    this.addToBot(new PlayTopCardAction(
                            AbstractDungeon.getCurrRoom().monsters.getRandomMonster(
                                    null,
                                    true,
                                    AbstractDungeon.cardRandomRng), false)
                    );
                    this.isDone = true;
                }
            });
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new MyRainbow();
    }
}
