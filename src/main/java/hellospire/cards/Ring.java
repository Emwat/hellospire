package hellospire.cards;

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
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import hellospire.MyModConfig;
import hellospire.SonicMod;
import hellospire.SonicTags;
import hellospire.SoundLibrary;
import hellospire.actions.ModFastAction;
import hellospire.character.Sonic;
import hellospire.powers.RingPower;
import hellospire.util.CardStats;

import java.util.ArrayList;
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

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

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

        if (IsConfusedEgg()){
            if (Settings.language.name().equalsIgnoreCase("eng")) {
                this.name = "Coin";
            }
            initializeTitle();
            loadCardImage(SonicMod.imagePath("cards/skill/Ring_b.png"));
        }

        if (MyModConfig.enableCrossModIntegrations && Loader.isModLoaded("ModAchievement")){
            if (!UnlockTracker.isAchievementUnlocked(makeID("Ringmaster"))) {
                unlockRingmasterAchievement();
            }
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.SoundAction(SoundLibrary.Ring));

        if (RingPower.isLightSpeedDashing) {
            addToBot(new HealAction(p, p, 1));
        } else {
            addToBot(new AddTemporaryHPAction(p, p, magicNumber));
        }

        addToBot(new ModFastAction(() -> ReturnBoostToHand(p)));
    }

    private void ReturnBoostToHand(AbstractPlayer p){
        if (!p.discardPile.isEmpty()) {
            for (AbstractCard card : p.discardPile.group) {
                if (Objects.equals(card.cardID, Boost.ID)) {
                    addToBot(new DiscardToHandAction(card));
                }
            }
        };
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

    private void unlockRingmasterAchievement(){
        if (AbstractDungeon.player == null) {
            return;
        }
        int total = 0;
        total += countRings(AbstractDungeon.player.drawPile.group);
        total += countRings(AbstractDungeon.player.exhaustPile.group);
        total += countRings(AbstractDungeon.player.discardPile.group);
        total += countRings(AbstractDungeon.player.hand.group);
        if (total > 20) {
            UnlockTracker.unlockAchievement(makeID("Ringmaster"));
        }
    }

    private int countRings(ArrayList<AbstractCard> group){
        int count = 0;
        for (AbstractCard c : group) {
            if (c.cardID.equals(this.cardID)) {
                count++;
            }
        }
        return count;
    }
}
