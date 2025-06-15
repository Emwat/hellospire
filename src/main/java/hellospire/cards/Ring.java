package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Apparition;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import hellospire.SonicTags;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.powers.RingPower;
import hellospire.powers.SuperSonicPower;
import hellospire.util.CardStats;

import java.util.Objects;

public class Ring extends BaseCard {
    public static final String ID = makeID("Ring");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public Ring() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);

        setSelfRetain(true);
        setExhaust(true);
        tags.add(CardTags.HEALING);
        tags.add(SonicTags.LIKE_WATCHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.PlaySound(SoundLibrary.Ring));
        RingPower ringPower = (RingPower) p.getPower(makeID("RingPower"));

        if (RingPower.getAmountHealed() < RingPower.getMaxAmountHealed()) {
            int healAmount = RingPower.calculateAmountToHeal(magicNumber);
            addToBot(new HealAction(p, p, healAmount));
            RingPower.incrementAmountHealed(healAmount);
            ringPower.updateDescription();
        } else {
            addToBot(new TalkAction(true, "These Rings won't heal anymore this combat. Let's get going!", 2f, 2f));
        }

        if (p.hasPower(makeID("SuperSonicPower"))) {
            addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1), 1));
        }

//        addToBot(new AddTemporaryHPAction(p, p, magicNumber));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (!p.discardPile.isEmpty()) {
                    for (AbstractCard card : p.discardPile.group) {
                        if (Objects.equals(card.cardID, Boost.ID)) {
                            addToBot(new DiscardToHandAction(card));
                        }
                    }
                }
                this.isDone = true;
            }
        });
    }

    /// "DESCRIPTION": "Retain. NL Ring. NL While you have this in your hand, you have two temporary dexterity. Exhaust."
    @Override
    public void triggerWhenCopied() {
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new ApplyPowerAction(p, p, new RingPower(p, 1)));
//        this.addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
        super.triggerWhenCopied();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Ring();
    }
}
