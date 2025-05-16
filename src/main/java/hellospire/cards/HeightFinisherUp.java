package hellospire.cards;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Wish;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

public class HeightFinisherUp extends BaseCard {
    public static final String ID = makeID("HeightFinisherUp");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    public HeightFinisherUp() {
        super(ID, info); //Pass the required information to the BaseCard constructor.

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Marisa ChargeUpSpray
        if (!p.hasPower("GainHeight")) {
            return;
        }

        int gainedHeight = p.getPower("GainHeight").amount;


        if (p.getPower("GainHeight").amount >= 2) {
            for (int i = 0; i < gainedHeight; i++) {
                if (i % 2 == 0) {
                    AbstractDungeon.actionManager.addToTop(new ChannelAction(new Frost()));
                }
            }

        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new HeightFinisherUp();
    }
}
