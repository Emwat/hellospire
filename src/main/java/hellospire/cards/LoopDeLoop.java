package hellospire.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import hellospire.actions.LoopDeLoopAction;
import hellospire.character.Sonic;
import hellospire.relics.AirBoostShoesRelic;
import hellospire.util.CardStats;

public class LoopDeLoop extends BaseCard {
    public static final String ID = makeID("LoopDeLoop");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public LoopDeLoop() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        // setExhaustive2();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoopDeLoopAction(p, magicNumber));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (isPlayerHandNull()) {
            return;
        }

        if (AbstractDungeon.player.hasRelic(AirBoostShoesRelic.ID)) {
            return;
        }

        if (AbstractDungeon.player.discardPile.isEmpty() || AbstractDungeon.player.hasPower(NoDrawPower.POWER_ID)) {
            this.glowColor = Color.RED.cpy();
            return;
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new LoopDeLoop();
    }
}
