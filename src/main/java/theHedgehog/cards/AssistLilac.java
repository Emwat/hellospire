package theHedgehog.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.ShrugItOff;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

import java.util.ArrayList;
import java.util.Arrays;

public class AssistLilac extends BaseCard implements OnObtainCard {
    public static final String ID = makeID("AssistLilac");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 10;
    private static final int UPG_BLOCK = 3;
    private static final int HITS = 1;
    private static final int MAGIC = 1;
    private static final Color FLAVOR_BOX_COLOR = CardHelper.getColor(183, 126, 243);
    private static final Color FLAVOR_TEXT_COLOR = CardHelper.getColor(0, 0, 0);

    public AssistLilac() {
        super(ID, info);
        setBackgroundTexture(SonicMod.characterPath("cardback/sashlilac/bg_skill.png"),
                SonicMod.characterPath("cardback/sashlilac/bg_skill_p.png"));
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < HITS; i++) {
            addToBot(new GainBlockAction(p, block));
        }
        addToBot(new DrawCardAction(p, magicNumber));
        addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new AssistLilac();
    }

    @Override
    public void onObtainCard() {
        removeAssistCard(this.hasTag(SonicTags.UPG_ASSIST));
    }
}
