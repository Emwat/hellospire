package theHedgehog.console;

import basemod.devcommands.ConsoleCommand;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theHedgehog.cards.*;
import theHedgehog.relics.CDFutureRelic;
import theHedgehog.relics.PowerBrakeRelic;

import java.util.ArrayList;

// valid commands:
// sss

// https://github.com/daviscook477/BaseMod/wiki/Console#adding-your-own-commands
public class SonicConsoleCasino extends ConsoleCommand {
    public SonicConsoleCasino() {
        maxExtraTokens = 0; // How many additional words can come after this one. If unspecified, maxExtraTokens = 1.
        minExtraTokens = 0; // How many additional words have to come after this one. If unspecified, minExtraTokens = 0.
        requiresPlayer = false; // if true, means the command can only be executed if during a run. If unspecified, requiresplayer = false.
        simpleCheck = true;
    }

    @Override
    protected void execute(String[] tokens, int depth) {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.add(new HeavyBounceSlam());
        cards.add(new SpinningNeedleAttack());
        cards.add(new Windmill());

        cards.add(new AssistSticks());
        cards.add(new AssistJet());

        cards.add(new GrindRail());

        cards.add(new SlotMachineGame());
        cards.add(new MagicHands());
        cards.add(new Relax());

        cards.add(new DizzySpin());
        cards.add(new Enerbeam());
        cards.add(new CloseShave());

        for (AbstractCard card : cards) {
            float x = MathUtils.random(0.1F, 0.9F) * (float)Settings.WIDTH;
            float y = MathUtils.random(0.2F, 0.8F) * (float)Settings.HEIGHT;
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card.makeStatEquivalentCopy(), x, y));
        }

    }

}
