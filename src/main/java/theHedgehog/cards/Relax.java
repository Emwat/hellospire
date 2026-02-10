package theHedgehog.cards;

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
import theHedgehog.MyModConfig;
import theHedgehog.SonicTags;
import theHedgehog.actions.ModFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

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
        setCostUpgrade(0);
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

        ArrayList<AbstractCard> picks = new ArrayList<>(Arrays.asList(
                new RelaxPick1(),
                new RelaxPick2()
        ));

        addToBot(new ChooseOneAction(picks));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new Relax();
    }
}
