package theHedgehog.cards;

import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ModifyBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.powers.RingPower;
import theHedgehog.util.CardStats;

public class CloseShave extends BaseCard {
    public static final String ID = makeID("CloseShave");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private static final int BLOCK = 4;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 10;
    // private int blockDeductions = 0;

    public CloseShave() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int outputBlock = block;
        addToBot(new GainBlockAction(p, outputBlock));
        // int ringsAmount = getPower(p, RingPower.POWER_ID);
        // int temp_hp = TempHPField.tempHp.get(p);
        addToBot(new ModXFastAction(() -> {
            if (p.hand.size() <= magicNumber - 1) {
                addToBot(new GainBlockAction(p, outputBlock));
            }
        }));
        // if (ringsAmount == 0) {
        // }
        // addToBot(new ModifyBlockAction(this.uuid, -1));
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        AbstractPlayer p = AbstractDungeon.player;

        if (p.hand.size() <= magicNumber) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }

    }


    @Override
    public AbstractCard makeCopy() { // Optional
        return new CloseShave();
    }
}
