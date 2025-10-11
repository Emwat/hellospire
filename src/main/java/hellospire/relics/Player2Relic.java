package hellospire.relics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hellospire.MyModConfig;
import hellospire.SonicMod;
import hellospire.SoundLibrary;
import hellospire.actions.ModFastAction;
import hellospire.actions.ModTextInCenterAction;
import hellospire.character.Sonic;
import hellospire.util.TextureLoader;

import static hellospire.SonicMod.makeID;

public class Player2Relic extends BaseRelic {
    private static final String NAME = "Player2Relic"; // The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); // This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.RARE; // The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; // The sound played when the relic is clicked.
    private static int cardsPlayed = 0;
    private static int currentTurn = 0;

    public Player2Relic() {
        super(ID, NAME, Sonic.Meta.CARD_COLOR, RARITY, SOUND);

        if (MyModConfig.enableCrossModIntegrations && Loader.isModLoaded("downfall")) {
            img = TextureLoader.getTexture(SonicMod.imagePath("relics/Player2MetalRelic.png"));
            outlineImg = TextureLoader.getTexture(SonicMod.imagePath("relics/Player2MetalRelicOutline.png"));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        currentTurn = 0;
        setCardsPlayed(0);
    }

    @Override
    public void atTurnStart() {
        currentTurn++;
        setCardsPlayed(0);
        if (currentTurn >= 3) {
            this.grayscale = true;
        }
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        setCardsPlayed(cardsPlayed + 1);

        if (cardsPlayed == 1 && !this.grayscale) {
            this.pulse = true;
            this.beginPulse();
            if (currentTurn == 1) {
                addToTop(new ModTextInCenterAction(DESCRIPTIONS[1] + DESCRIPTIONS[2], Color.WHITE.cpy()));
            } else if (currentTurn == 2) {
                addToTop(new ModTextInCenterAction(DESCRIPTIONS[1] + DESCRIPTIONS[3], Color.WHITE.cpy()));
            }
        }

        if (cardsPlayed == 2 && currentTurn < 3) {
            Player2Relic thisRelic = this;
            addToTop(new ModFastAction(() -> {
                thisRelic.flash();
                thisRelic.stopPulse();
                addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
                if (MyModConfig.enableCrossModIntegrations && Loader.isModLoaded("downfall")) {
                    addToBot(new ModTextInCenterAction(c.name.toUpperCase() + DESCRIPTIONS[4], Color.PINK.cpy()));
                    if (!SonicMod.sawMetalRelic) {
                        addToBot(SoundLibrary.VoiceAction(SoundLibrary.MetalData));
                        SonicMod.sawMetalRelic = true;
                    }
                }
                if (currentTurn == 2) {
                    thisRelic.grayscale = true;
                }
            }));

        }
    }

    // This function is here to rename counter so it's more intuitive to code around
    private void setCardsPlayed(int newNumber) {
        cardsPlayed = newNumber;
        counter = newNumber;
    }

    @Override
    public void renderCounter(SpriteBatch sb, boolean inTopPanel) {
        super.renderCounter(sb, inTopPanel);
        FontHelper.renderFontRightTopAligned(
                sb,
                FontHelper.topPanelInfoFont,
                Integer.toString(currentTurn),
                this.currentX + 30.0F * Settings.scale,
                this.currentY + 35.0F * Settings.scale,
                Color.WHITE);
    }

    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }
}
