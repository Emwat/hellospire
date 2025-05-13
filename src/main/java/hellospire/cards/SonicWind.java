package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

public class SonicWind extends BaseCard {
    public static final String ID = makeID("SonicWind");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    ///      "DESCRIPTION": "Channel !M! Lightning."
    public SonicWind() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new ChannelAction(new Lightning()));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SonicWind();
    }
}
