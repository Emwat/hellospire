package hellospire.relics;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hellospire.cards.Shortcut;
import hellospire.character.Sonic;

import static hellospire.SonicMod.makeID;

public class BlueQuillRelic extends BaseRelic {
    private static final String NAME = "BlueQuillRelic";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.STARTER;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public BlueQuillRelic() {
        super(ID, NAME, Sonic.Meta.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStartPreDraw() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new MakeTempCardInHandAction(new Shortcut(), 1, false));
    }
}
