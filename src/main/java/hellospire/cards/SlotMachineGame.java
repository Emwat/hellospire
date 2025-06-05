package hellospire.cards;

import basemod.BaseMod;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class SlotMachineGame extends BaseCard {
    public static final String ID = makeID("SlotMachineGame");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );
    private AbstractCard Pull = new SlotMachinePull();
    private AbstractCard Curse = new SlotMachinePullEggman();

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = -1;

    public SlotMachineGame() {
        super(ID, info);
        MultiCardPreview.add(this, Curse, Pull);

        setMagic(MAGIC, UPG_MAGIC);
        this.setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInHandAction(Pull.makeStatEquivalentCopy(), 1));
        addToBot(new MakeTempCardInDrawPileAction(
                Curse.makeStatEquivalentCopy(),
                magicNumber, true, true, false));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SlotMachineGame();
    }
}
