package theHedgehog.cardsPackExclusive;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicMod;
import theHedgehog.cards.BaseCard;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.ThePackmaster;

public class BouncePad extends BaseCard {
    public static final String ID = makeID("PackBouncePad");
    private static final CardStats info = Loader.isModLoaded("anniv5") ?
            new CardStats(
            ThePackmaster.Enums.PACKMASTER_RAINBOW,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    ) :
            new CardStats(
                    Sonic.Meta.CARD_COLOR,
                    CardType.SKILL,
                    CardRarity.SPECIAL,
                    CardTarget.SELF,
                    1
            );

    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public BouncePad() {
        super(ID, info);
        this.cardsToPreview = new theHedgehog.cardsPackExclusive.Ring();

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);

        // if (Loader.isModLoaded("anniv5") && SpireAnniversary5Mod.oneFrameMode) {
        //     ApplyOneFrameModeSetting();
        // } else {
        //     setBackgroundTexture(SonicMod.characterPath("cardback/bg_skill.png"), SonicMod.characterPath("cardback/bg_skill_p"));
        //     setOrbTexture(SonicMod.characterPath("cardback/small_orb.png"), SonicMod.characterPath("cardback/energy_orb.png"));
        //     // setOrbTexture(Sonic.Meta.SMALL_ORB, Sonic.Meta.ENERGY_ORB);
        // }

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new BouncePad();
    }
}
