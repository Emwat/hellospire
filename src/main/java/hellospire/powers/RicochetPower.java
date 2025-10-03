package hellospire.powers;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.FlurryOfBlows;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import hellospire.SoundLibrary;
import hellospire.actions.ModFastAction;
import hellospire.cards.Ricochet;

import java.util.ArrayList;
import java.util.Objects;

import static hellospire.SonicMod.makeID;

public class RicochetPower extends BasePower {
    public static final String POWER_ID = makeID("RicochetPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private final AbstractPlayer player;

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public RicochetPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.player = (AbstractPlayer) owner;
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        // super.onPlayCard(card, m);

        boolean isVigorAttack = card.type == AbstractCard.CardType.ATTACK && owner.hasPower(VigorPower.POWER_ID);
        int countRicochets = 0;
        ArrayList<AbstractCard> ricochets = new ArrayList<>();
        int currentHandSize = player.hand.size();

        if (Ricochet.ID.equals(card.cardID)) {
            countRicochets++;
        }
        if (!this.player.discardPile.group.isEmpty()) {
            for (AbstractCard discardedCard : this.player.discardPile.group) {
                if (Objects.equals(discardedCard.cardID, Ricochet.ID)) {
                    countRicochets++;
                    if (isVigorAttack && ricochets.size() < BaseMod.MAX_HAND_SIZE - currentHandSize) {
                        ricochets.add(discardedCard);
                    }
                }
            }
        }

        this.amount = isVigorAttack ? countRicochets - ricochets.size() : countRicochets;
        updateDescription();

        if (!ricochets.isEmpty()) {
            addToBot(new SFXAction(SoundLibrary.Spring));
            for (AbstractCard ricochet : ricochets) {
                addToBot(new DiscardToHandAction(ricochet));
            }
        }
    }


    @Override
    public void atStartOfTurnPostDraw() {
        AbstractPower thisPower = this;
        addToBot(new ModFastAction(() -> {
            int countRicochets = 0;

            for (AbstractCard discardedCard : player.discardPile.group) {
                if (Objects.equals(discardedCard.cardID, Ricochet.ID)) {
                    countRicochets++;
                }
            }
            thisPower.amount = countRicochets;
        }));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}