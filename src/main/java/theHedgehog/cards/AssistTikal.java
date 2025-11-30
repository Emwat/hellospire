package theHedgehog.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class AssistTikal extends BaseCard {
    public static final String ID = makeID("AssistTikal");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );
    private static final int BLOCK = 10;
    private static final int UPG_BLOCK = 3;
    private static final Color FLAVOR_BOX_COLOR = CardHelper.getColor(255, 218, 128);
    private static final Color FLAVOR_TEXT_COLOR = CardHelper.getColor(153, 205, 51);

    public AssistTikal() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
        setInnate(true);
        setExhaust(true);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDeadOrEscaped() && m.getIntentBaseDmg() >= 0) {
                this.glowColor = Color.PINK.cpy();
                break;
            }
        }
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new AssistTikal();
    }
}
