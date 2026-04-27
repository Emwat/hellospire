package theHedgehog.cardsPack;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.cards.BaseCard;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;
import thePackmaster.ThePackmaster;

public class DropDash extends BaseCard {
    public static final String ID = makeID("PackDropDash");
    private static final CardType cardtype = CardType.SKILL;
    private static final CardTarget cardTarget = CardTarget.SELF;
    private static final int cost = 2;
    private static final CardStats info = Loader.isModLoaded("anniv5") ?
            new CardStats(ThePackmaster.Enums.PACKMASTER_RAINBOW, cardtype, CardRarity.COMMON, cardTarget, cost) :
            new CardStats(Sonic.Meta.CARD_COLOR, cardtype, CardRarity.SPECIAL, cardTarget, cost);

    private static final int BLOCK = 12;
    private static final int UPG_BLOCK = 4;
//    private static final int MAGIC = 4;

    /// "DESCRIPTION": Gain !B! Block. Gain HALF of !B! next turn.
    /// "Gain !B! Block. If you exhausted a card this turn, gain 2."
    public DropDash() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
//        setMagic(MAGIC);
        tags.add(SonicTags.LIKE_SILENT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.SoundAction(SoundLibrary.DropDash));
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
