package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import theHedgehog.SonicMod;
import theHedgehog.character.Sonic;
import theHedgehog.multiplayer.ModMultiplayerHelper;
import theHedgehog.util.CardStats;

import static theHedgehog.multiplayer.ModMultiplayerHelper.IsCharacterEntity;

public class Defend extends BaseCard {
    public static final String ID = makeID("Defend");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR, // The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, // The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.BASIC, // Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, // The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 // The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    // These will be used in the constructor. Technically you can just use the values directly,
    // but constants at the top of the file are easy to adjust.
    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;

    public Defend() {
        super(ID, info); // Pass the required information to the BaseCard constructor.

        setBlock(BLOCK, UPG_BLOCK);

        tags.add(CardTags.STARTER_DEFEND);
        if (IsConfusedEgg()) {
            loadCardImage(SonicMod.imagePath("cards/skill/Defend_b.png"));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (ModMultiplayerHelper.HasHelpYourBro() && IsCharacterEntity(m)) {
            m.addBlock(block);
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, AbstractGameAction.AttackEffect.SHIELD));
            return;
        } else if (ModMultiplayerHelper.HasSpireTogether() && IsCharacterEntity(m) && this.upgraded) {
            m.addBlock(block);
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, AbstractGameAction.AttackEffect.SHIELD));
            return;
        }

        addToBot(new GainBlockAction(p, block));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new Defend();
    }
}
