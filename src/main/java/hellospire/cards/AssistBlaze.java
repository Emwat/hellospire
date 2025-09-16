package hellospire.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.BowlingBash;
import com.megacrit.cardcrawl.cards.red.FlameBarrier;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class AssistBlaze extends BaseCard {
    public static final String ID = makeID("AssistBlaze");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 4;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 2;
    private static final Color FLAVOR_BOX_COLOR = CardHelper.getColor(180, 164, 193);
    private static final Color FLAVOR_TEXT_COLOR = CardHelper.getColor(61, 15, 79);

    public AssistBlaze() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setEthereal(true);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.Blaze));

        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.1F));
        } else {
            this.addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.5F));
        }

        for (AbstractMonster m2 : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m2.isDeadOrEscaped()) {
                addToBot(new GainBlockAction(p, p, block));
                addToBot(new ApplyPowerAction(p, p, new FlameBarrierPower(p, magicNumber), magicNumber));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new AssistBlaze();
    }
}
