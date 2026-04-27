package theHedgehog.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.cardmodifiers.SpinUpModifier;
import theHedgehog.character.Sonic;
import theHedgehog.multiplayer.ModMultiplayerHelper;
import theHedgehog.util.CardStats;

import static theHedgehog.multiplayer.ModMultiplayerHelper.IsCharacterEntity;

public class Bumper extends BaseCard {
    public static final String ID = makeID("Bumper");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            3
    );

    // Impervious is 30(40) block
    private static final int BLOCK = 16;
    private static final int UPG_BLOCK = 4;

    public Bumper() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
        setSelfRetain(true);
        CardModifierManager.addModifier(this, new SpinUpModifier());
        tags.add(SonicTags.LIKE_WATCHER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new Bumper();
    }
}
