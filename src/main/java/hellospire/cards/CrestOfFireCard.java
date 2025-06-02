package hellospire.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;

public interface CrestOfFireCard {
    Color CREST_OF_FIRE_BURN_GLOW_COLOR = Color.RED;

    static final int CREST_OF_FIRE_MARK = 7;

//    default void Self_Damage(AbstractCard card){
//        card.addToBot()
//    }

    default boolean willBurnPlayer(AbstractCard thisCard) {
        int upgrades = thisCard.timesUpgraded;
        return upgrades > CREST_OF_FIRE_MARK;
    }
}
