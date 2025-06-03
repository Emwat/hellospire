package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicMod;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class DropDash extends BaseCard {
    public static final String ID = makeID("DropDash");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    private static final int BLOCK = 12;
    private static final int UPG_BLOCK = 4;
//    private static final int MAGIC = 4;

    /// "DESCRIPTION": Gain !B! Block. Gain HALF of !B! next turn.
    /// "Gain !B! Block. If you exhausted a card this turn, gain 2."
    public DropDash() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
//        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.PlaySound(SoundLibrary.DropDash));
        addToBot(new GainBlockAction(p, block));
        if (SonicMod.cardsExhaustedThisTurn > 0) {
            addToBot(new GainEnergyAction(2));
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (SonicMod.cardsExhaustedThisTurn > 0) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new DropDash();
    }
}
