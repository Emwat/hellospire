package hellospire.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.stances.WrathStance;
import hellospire.SonicTags;
import hellospire.actions.ModFastAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;
import java.util.Arrays;

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

        if (!this.upgraded) {
            if (WrathCondition(p)) {
                addToBot(new ChangeStanceAction(WrathStance.STANCE_ID));
            }
        } else {
            ArrayList<AbstractCard> picks = new ArrayList<>(Arrays.asList(
                    new RelaxPick1(),
                    new RelaxPick2()
            ));
            if (this.upgraded) {
                for (AbstractCard pick : picks) {
                    pick.upgrade();
                }
            }

            addToBot(new ChooseOneAction(picks));
        }

    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (this.upgraded) {
            return;
        }

        if (isPlayerHandNull()) {
            return;
        }

        if (WrathCondition(AbstractDungeon.player)) {
            this.glowColor = Color.RED.cpy();
        }
    }

    @Override
    public void triggerWhenDrawn() {
        if (this.upgraded) {
            return;
        }
        addToBot(new ModFastAction( () -> {
                if (WrathCondition(AbstractDungeon.player)) {
                    loadCardImage(imageSkillPath("RelaxPick2.png"));
                } else {
                    loadCardImage(imageSkillPath("Relax.png"));
                }
            })
        );
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (!this.upgraded) {
            addToBot(new ModFastAction(() -> {
                if (WrathCondition(AbstractDungeon.player)) {
                    loadCardImage(imageSkillPath("RelaxPick2.png"));
                } else {
                    loadCardImage(imageSkillPath("Relax.png"));
                }
            }));
        }

        super.triggerOnOtherCardPlayed(c);
    }

    private boolean WrathCondition(AbstractPlayer p) {
        return CheckIfLeftCard(this, p.hand) || CheckIfRightCard(this, p.hand);
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new Relax();
    }
}
