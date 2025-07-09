package hellospire.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hellospire.character.Sonic;
import hellospire.powers.LevelUpFlightPower;
import hellospire.powers.LevelUpPowerPower;
import hellospire.powers.LevelUpSpeedPower;

import static hellospire.SonicMod.makeID;

public class EmblemPlusRelic extends BaseRelic {
    private static final String NAME = "EmblemPlusRelic"; // The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); // This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; // The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; // The sound played when the relic is clicked.

    public EmblemPlusRelic() {
        super(ID, NAME, Sonic.Meta.CARD_COLOR, RARITY, SOUND);

    }


    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(EmblemRelic.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(EmblemRelic.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(EmblemRelic.ID);
    }

    // @Override
    // public String getUpdatedDescription() {
    //     // Colorize the starter relic's name. Thanks Bard!!! Thanks Nelly!!!!! HAPPY BIRTHDAY, STS MODDING!!!
    //     String name = new EmblemRelic().name;
    //     StringBuilder sb = new StringBuilder();
    //     final String charColor = Sonic.Meta.LIBRARY_COLOR.toString();
    //     if(Settings.language==Settings.GameLanguage.ZHS ||Settings.language==Settings.GameLanguage.ZHT){
    //         sb.append("[#").append(charColor).append("]").append(name).append("[]");
    //     }else {
    //         for (String word : name.split(" ")) {
    //             sb.append("[#").append(charColor).append("]").append(word).append("[] ");
    //             sb.setLength(sb.length() - 1);
    //             sb.append("[#").append(charColor).append("]");
    //         }
    //     }
    //
    //     return DESCRIPTIONS[0] + sb + DESCRIPTIONS[1];
    // }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LevelUpPowerPower(AbstractDungeon.player, 1), 1));
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LevelUpFlightPower(AbstractDungeon.player, 1), 1));
        // this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LevelUpSpeedPower(AbstractDungeon.player, 1), 1));
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
}
