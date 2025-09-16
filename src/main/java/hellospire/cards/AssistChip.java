package hellospire.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.CalmStance;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class AssistChip extends BaseCard {
    public static final String ID = makeID("AssistChip");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;
    private static final Color FLAVOR_BOX_COLOR = CardHelper.getColor(139, 39, 68);
    private static final Color FLAVOR_TEXT_COLOR = CardHelper.getColor(255, 251, 236);

    public AssistChip() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
        setSelfRetain(true);
        setExhaust(true);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.Chip));
        addToBot(new GainBlockAction(p, block));
        addToBot(new ChangeStanceAction(CalmStance.STANCE_ID));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AssistChip();
    }
}
