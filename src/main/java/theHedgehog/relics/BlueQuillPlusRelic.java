package theHedgehog.relics;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHedgehog.cards.Shortcut;
import theHedgehog.character.Sonic;

import static theHedgehog.SonicMod.makeID;

public class BlueQuillPlusRelic extends BaseRelic {
    private static final String NAME = "BlueQuillPlusRelic";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private static final int numberOfShortcuts = 3;

    public BlueQuillPlusRelic() {
        super(ID, NAME, Sonic.Meta.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + numberOfShortcuts + this.DESCRIPTIONS[1];
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(BlueQuillRelic.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(BlueQuillRelic.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public void atBattleStartPreDraw() {
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new MakeTempCardInHandAction(new Shortcut(), numberOfShortcuts, false));
    }
}
