package theHedgehog.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import theHedgehog.MyModConfig;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.ModFastAction;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.powers.LSDPower;
import theHedgehog.powers.RingPower;
import theHedgehog.util.CardStats;

import java.util.ArrayList;
import java.util.Objects;

import static theHedgehog.util.GeneralUtils.isIndeedWithoutADoubtInCombat;

public class Ring extends BaseCard {
    public static final String ID = makeID("Ring");
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 3;

    public Ring() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);

        setSelfRetain(true);
        setExhaust(true);
        tags.add(CardTags.HEALING);
        tags.add(SonicTags.RING);

        if (MyModConfig.enableCrossModIntegrations && (Loader.isModLoaded("PrideMod") || isTheRainbow())) {
            loadCardImage(SonicMod.imagePath("cards/skill/WorldRings.png"));
        }

        if (IsConfusedEgg()) {
            if (Settings.language.name().equalsIgnoreCase("eng")) {
                this.name = "Coin";
                initializeTitle();
            }
            loadCardImage(SonicMod.imagePath("cards/skill/Ring_b.png"));
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new ApplyPowerAction(p, p, new RingPower(p, 1)));
    }

    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        atbApplyFocus();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.SoundAction(SoundLibrary.Ring));

        addToBot(new AddTemporaryHPAction(p, p, magicNumber));

        addToBot(new ModFastAction(() -> ReturnBoostToHand(p)));
    }

    private void ReturnBoostToHand(AbstractPlayer p) {
        if (!p.discardPile.isEmpty()) {
            for (AbstractCard card : p.discardPile.group) {
                if (card instanceof Boost || card instanceof BoostRare) {
                    addToBot(new DiscardToHandAction(card));
                }
            }
        }
    }

    @Override
    public void triggerWhenCopied() {
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new ApplyPowerAction(p, p, new RingPower(p, 1)));
        super.triggerWhenCopied();
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        atbApplyFocus();
        return new Ring();
    }

    private void atbApplyFocus() {
        if (isIndeedWithoutADoubtInCombat()) {
            addToBot(new ModXFastAction(() -> {
                for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                    if (!(orb instanceof EmptyOrbSlot))
                        orb.applyFocus();
                }
            }));
        }
    }

    private int countRings(ArrayList<AbstractCard> group) {
        int count = 0;
        for (AbstractCard c : group) {
            if (c.cardID.equals(this.cardID)) {
                count++;
            }
        }
        return count;
    }
}
