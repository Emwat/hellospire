package hellospire.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Armaments;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hellospire.relics.FireSoulRelic;

public interface CrestOfFireCard {
    Color CREST_OF_FIRE_BURN_GLOW_COLOR = Color.RED;

    static final int CREST_OF_FIRE_MARK = 7;

//    default void Self_Damage(AbstractCard card){
//        card.addToBot()
//    }

    default boolean willBurnPlayer(AbstractCard thisCard) {
        int upgrades = thisCard.timesUpgraded;
        int fireSoulCounter = 0;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(FireSoulRelic.ID)){
            fireSoulCounter = AbstractDungeon.player.getRelic(FireSoulRelic.ID).counter;
        }

        return upgrades > CREST_OF_FIRE_MARK + fireSoulCounter;
    }
}
