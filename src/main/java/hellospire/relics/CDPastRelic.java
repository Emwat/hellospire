package hellospire.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.LizardTail;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hellospire.SonicTags;
import hellospire.SoundLibrary;
import hellospire.actions.ModFastAction;
import hellospire.character.Sonic;
import hellospire.powers.OneUpPower;
import hellospire.powers.RingPower;

import static hellospire.SonicMod.makeID;

public class CDPastRelic extends BaseRelic {
    private static final String NAME = "CDPastRelic"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final AbstractRelic.RelicTier RARITY = AbstractRelic.RelicTier.SPECIAL; //The relic's rarity.
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK; //The sound played when the relic is clicked.
    private static boolean hasSoundPlayed = false;
    public static final int toleranceToPain = 3;
    public static final int reviveCost = 100;

    public CDPastRelic() {
        super(ID, NAME, Sonic.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0].replace("{0}", Integer.toString(toleranceToPain));
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new ApplyPowerAction(p, p, new OneUpPower(p, (int)(p.gold / 100))));
    }

    @Override
    public void wasHPLost(int damageAmount) {
        BaseRelic thisRelic = this;
        if (damageAmount >= toleranceToPain && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToTop(new ModFastAction(() -> {
                // CardCrawlGame.sound.play(SoundLibrary.LoseRings);
                RingPower ringPower = (RingPower) AbstractDungeon.player.getPower(RingPower.POWER_ID);
                if (ringPower != null && ringPower.amount > 0 && !hasSoundPlayed) {
                    thisRelic.flash();
                    addToBot(SoundLibrary.SoundAction(SoundLibrary.LoseRings));
                    hasSoundPlayed = true;
                }

                for (AbstractCard card : AbstractDungeon.player.hand.group) {
                    if (card.hasTag(SonicTags.RING)) {
                        addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand, true));
                    }
                }
                addToBot(new ModFastAction(() -> {
                    if (ringPower != null) {
                        ringPower.CalculateNumberOfRings();
                    }
                }));
            }));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

}