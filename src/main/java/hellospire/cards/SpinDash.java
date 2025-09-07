package hellospire.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class SpinDash extends BaseCard {
    public static final String ID = makeID("SpinDash");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 15;
    private static final int UPG_DAMAGE = 4;

    public SpinDash() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if (p.drawPile.isEmpty()) {
            return;
        }
        if (m == null) {
            m = modGetRandomMonster();
        }

        // TODO: If you kill an enemy and there are still enemies around, your next card should still play. (Taunt)
        addToBot(new PlayTopCardAction(m, false));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (isPlayerHandNull()) {
            return;
        }

        if (AbstractDungeon.player.drawPile.isEmpty()) {
            this.glowColor = Color.RED.cpy();
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SpinDash();
    }
}
