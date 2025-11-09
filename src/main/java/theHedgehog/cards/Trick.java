package theHedgehog.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import spireTogether.monsters.CharacterEntity;
import spireTogether.network.objects.items.NetworkCard;
import theHedgehog.MyModConfig;
import theHedgehog.SonicMod;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.ModFastAction;
import theHedgehog.actions.ModTextInCenterAction;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.multiplayer.ModMultiplayerHelper;
import theHedgehog.util.CardStats;

import java.util.ArrayList;
import java.util.Arrays;

import static theHedgehog.multiplayer.ModMultiplayerHelper.GiveCardToTeammate;

public class Trick extends BaseCard {
    public static final String ID = makeID("Trick");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 2;
    private static final int REFUND = 1;
    private static final String[] TrickNames1 = new String[]{"indy", "melon"};
    private static final String[] TrickNames2 = new String[]{"blue scorpion", "blue sky", "double mouse", "method", "mute", "nose grab", "tweak", "twister", "japan", "jessy"};
    public static int TricksPlayed = 0;
    public static int firstTrickNumber = 0;

    public Trick() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);

        setEthereal(true);
        setExhaust(true);

        boolean isBetaStrike = UnlockTracker.betaCardPref.getBoolean(SonicMod.makeID("Strike"), false);
        boolean isBetaDefend = UnlockTracker.betaCardPref.getBoolean(SonicMod.makeID("Defend"), false);
        if (MyModConfig.enableCrossModIntegrations && (Loader.isModLoaded("PrideMod") || isTheRainbow())) {
            loadCardImage(SonicMod.imagePath("cards/skill/TrickAlexDivato.png"));
        }

        if ((isBetaStrike && isBetaDefend) || IsConfusedEgg()) {
            loadCardImage(SonicMod.imagePath("cards/skill/Trick_b.png"));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (ModMultiplayerHelper.HasHelpYourBro(m)) {
            addToBot(new ModXFastAction(() -> {
                GiveCardToTeammate(m, this);
            }));
            return;
        }
        addToBot(new AnimateHopAction(p));
        addToBot(new ModFastAction(() -> TricksPlayed++));
        addToBot(SoundLibrary.RandomVoiceAction(new ArrayList<>(Arrays.asList(
                SoundLibrary.ALLRIGHT,
                SoundLibrary.COOL,
                SoundLibrary.OK,
                SoundLibrary.YES
        ))));
        addToBot(new ApplyPowerAction(p, p, new VigorPower(p, magicNumber)));
        addToBot(new GainEnergyAction(REFUND));
    }


    @Override
    public AbstractCard makeCopy() { // Optional
        return new Trick();
    }
}
