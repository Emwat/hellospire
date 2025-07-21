package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import hellospire.SonicMod;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.powers.RingPower;
import hellospire.util.CardStats;

import java.util.Objects;

public class Ring extends BaseCard {
    public static final String ID = makeID("Ring");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public Ring() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);

        setSelfRetain(true);
        setExhaust(true);
        tags.add(CardTags.HEALING);

        if (Loader.isModLoaded("PrideMod") || isTheRainbow()) {
            loadCardImage(SonicMod.imagePath("cards/skill/WorldRings.png"));
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.SoundAction(SoundLibrary.Ring));

        if (RingPower.isLightSpeedDashing) {
            addToBot(new HealAction(p, p, magicNumber));
        } else {
            addToBot(new AddTemporaryHPAction(p, p, magicNumber));
        }

        if (p.hasPower(makeID("SuperSonicPower"))) {
            addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1), 1));
        }

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

    @Override
    public void triggerWhenCopied() {
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new ApplyPowerAction(p, p, new RingPower(p, 1)));
        super.triggerWhenCopied();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new Ring();
    }
}
