package hellospire.cards;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Vigilance;
import com.megacrit.cardcrawl.cards.red.Anger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.stances.WrathStance;
import hellospire.SonicTags;
import hellospire.actions.HeavyIncrementAction;
import hellospire.actions.HeavyKeepCostAction;
import hellospire.cardmodifiers.MagicHandsModifier;
import hellospire.character.Sonic;
import hellospire.powers.RelaxPower;
import hellospire.util.CardStats;

public class Relax extends BaseCard {
    public static final String ID = makeID("Relax");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Relax() {
        super(ID, info);
        tags.add(SonicTags.LIKE_WATCHER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard copy = this.makeStatEquivalentCopy();
        if (this.upgraded) {
            copy.upgrade();
        }

        // addToBot(new ApplyPowerAction(p, p, new RelaxPower(p, 1), 1));
        addToBot(new ChangeStanceAction(CalmStance.STANCE_ID));
        addToBot(new MakeTempCardInDrawPileAction(copy, 1, true, true));
        if (WrathCondition(p)) {
            addToBot(new ChangeStanceAction(WrathStance.STANCE_ID));
        }
    }

    @Override
    public void upgrade() {
        setEthereal(true);
        super.upgrade();
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (isPlayerHandNull()) {
            return;
        }

        if (WrathCondition(AbstractDungeon.player)) {
            this.glowColor = Color.RED.cpy();
        }
    }

    private boolean WrathCondition(AbstractPlayer p){
        return CheckIfLeftCard(this, p.hand) || CheckIfRightCard(this, p.hand);
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new Relax();
    }
}
