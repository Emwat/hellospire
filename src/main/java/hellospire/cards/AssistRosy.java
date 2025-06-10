package hellospire.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.utility.ConditionalDrawAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.util.CardStats;



public class AssistRosy extends BaseCard {
    public static final String ID = makeID("AssistRosy");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;
    private static final Color FLAVOR_BOX_COLOR = Color.BLUE.cpy();
    private static final Color FLAVOR_TEXT_COLOR = Color.WHITE.cpy();

    public AssistRosy() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);

        FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ConditionalDrawAction(this.magicNumber, CardType.ATTACK));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = this.shouldGlow() ? AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy() : AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }

    private boolean shouldGlow() {
        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == CardType.ATTACK) {
                return false;
            }
        }

        return true;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AssistRosy();
    }
}
