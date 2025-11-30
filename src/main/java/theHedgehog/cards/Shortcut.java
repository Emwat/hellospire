package theHedgehog.cards;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import theHedgehog.MyModConfig;
import theHedgehog.SonicMod;
import theHedgehog.actions.LowerCostAction;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;
import theHedgehog.util.TextureLoader;

public class Shortcut extends BaseCard {
    public static final String ID = makeID("Shortcut");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR, // The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, // The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.SPECIAL, // Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, // The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 // The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static String[] NAMES;

    // These will be used in the constructor. Technically you can just use the values directly,
    // but constants at the top of the file are easy to adjust.
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public Shortcut() {
        super(ID, info); // Pass the required information to the BaseCard constructor.

        setSelfRetain(true);
        setMagic(MAGIC, UPG_MAGIC);
        setExhaust(true);

        boolean isBetaStrike = UnlockTracker.betaCardPref.getBoolean(SonicMod.makeID("Strike"), false);
        boolean isBetaDefend = UnlockTracker.betaCardPref.getBoolean(SonicMod.makeID("Defend"), false);
        if ((isBetaStrike && isBetaDefend) || IsConfusedEgg()) {
            loadCardImage(SonicMod.imagePath("cards/skill/Shortcut_b.png"));
            ModSetPortrait(SonicMod.imagePath("cards/skill/Shortcut_b_p.png"));
        } else if (MyModConfig.enableCrossModIntegrations && Loader.isModLoaded("test447_keycuts")) {
            loadCardImage(SonicMod.imagePath("cards/skill/Shortcut_One.png"));
            ModSetPortrait(SonicMod.imagePath("cards/skill/Shortcut_One_p.png"));
            this.name = NAMES[0];
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(magicNumber,
                CardCrawlGame.languagePack.getUIString(makeID("ShortcutMessage")).TEXT[0],
                false, false, pickableCards, cards -> {
            if (cards.isEmpty()) {
                return;
            }

            for (AbstractCard card : cards) {
                addToBot(new LowerCostAction(card, 1));
            }
        }));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new Shortcut();
    }

    static {
        NAMES = SonicMod.modLocalizedStrings.getExtraCardString(ID).NAMES;
    }
}
