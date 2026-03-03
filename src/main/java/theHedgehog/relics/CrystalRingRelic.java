package theHedgehog.relics;

import basemod.abstracts.CustomSavable;
import basemod.helpers.CardPowerTip;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.MarkOfPain;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.ModFastAction;
import theHedgehog.cards.CrystalRing;
import theHedgehog.cards.Ring;
import theHedgehog.character.Sonic;
import theHedgehog.powers.RingPower;

import javax.smartcardio.Card;

import static theHedgehog.SonicMod.makeID;

public class CrystalRingRelic extends BaseRelic  {
    private static final String NAME = "CrystalRingRelic"; // The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); // This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; // The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; // The sound played when the relic is clicked.

    public CrystalRingRelic() {
        super(ID, NAME, Sonic.Meta.CARD_COLOR, RARITY, SOUND);
        AbstractCard tipCard = new CrystalRing();
        this.tips.add(new CardPowerTip(tipCard));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        AbstractCard card = new CrystalRing().makeStatEquivalentCopy();

        addToBot(new MakeTempCardInDrawPileAction(card, 1, true, true));
    }


}
