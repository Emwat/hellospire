package hellospire.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.actions.LoopDeLoopAction;
import hellospire.character.Sonic;
import hellospire.relics.AirBoostShoesRelic;
import hellospire.util.CardStats;

public class SecretRoute extends BaseCard {
    public static final String ID = makeID("SecretRoute");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public SecretRoute() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        // setExhaustive2();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int magicOutput = magicNumber;
        if (!this.upgraded && CheckIfRightCard(this, p.hand)) {
            magicOutput += 1;
        }
        addToBot(new LoopDeLoopAction(p, magicOutput));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (isPlayerHandNull()) {
            return;
        }

        if (AbstractDungeon.player.hasRelic(AirBoostShoesRelic.ID)) {
            return;
        }

        if (AbstractDungeon.player.discardPile.isEmpty()) {
            this.glowColor = Color.RED.cpy();
            return;
        }

        if (CheckIfRightCard(this, AbstractDungeon.player.hand)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    private void setExhaustive2() {
        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, 2);
        ExhaustiveField.ExhaustiveFields.exhaustive.set(this, 2);
        exhaust = false;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SecretRoute();
    }
}
