package theHedgehog.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import theHedgehog.MyModConfig;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.ModFastAction;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.modachievements.achievements;
import theHedgehog.powers.SuperSonicPower;
import theHedgehog.util.CardStats;

import static theHedgehog.SonicMod.makeID;
import static theHedgehog.util.UnlockUtil.unlockModAchievement;

public class SuperSonicForm extends BaseCard {
    public static final String ID = makeID("SuperSonicForm");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            3
    );

    private static final int MAGIC = 7;

    public SuperSonicForm() {
        super(ID, info);
        // This code adds Super Sonic to card rewards.
        // this.rarity = CardRarity.RARE;
        setDisplayRarity(CardRarity.RARE);
        // setBackgroundTexture();

        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.GOLD));
        // AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.SonicStyle));
        // addToBot(new GainEnergyAction(magicNumber));
        addToBot(new ApplyPowerAction(p, p, new SuperSonicPower(p)));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new WraithFormPower(p, -1), -1));

        if (this.upgraded) {
            addToBot(new ApplyPowerAction(p, p, new FocusPower(p, magicNumber)));
            addToBot(new ApplyPowerAction(p, p, new BiasPower(p, 1), 1));
        }
        if (MyModConfig.enableBossHeartMusic && "The Heart".equals(AbstractDungeon.lastCombatMetricKey)) {
            addToBot(new ModXFastAction(() -> {
                CardCrawlGame.music.justFadeOutTempBGM();
                CardCrawlGame.music.playTempBgmInstantly("LIVE_AND_LEARN", true);
                unlockModAchievement(achievements.Achievement.SuperSonic.name());
            }));
        }

    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new SuperSonicForm();
    }
}
