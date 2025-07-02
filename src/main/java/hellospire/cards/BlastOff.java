package hellospire.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicTags;
import hellospire.actions.HeavyIncrementAction;
import hellospire.actions.HeavyKeepCostAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class BlastOff extends BaseCard {
    public static final String ID = makeID("BlastOff");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );


    // This card's description is hardcoded to show [E] [E]
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public BlastOff() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        tags.add(SonicTags.HEAVY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int extraMagic = CheckIfLeftCard(this, p.hand) ? 1 : 0;

        addToBot(new GainEnergyAction(magicNumber + extraMagic));
        addToBot(new HeavyIncrementAction(this));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (isPlayerHandNull()) {
            return;
        }

        if (CheckIfLeftCard(this, AbstractDungeon.player.hand)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        addToBot(new HeavyKeepCostAction(this));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BlastOff();
    }
}
