package theHedgehog.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Vault;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import theHedgehog.SoundLibrary;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class AssistShadow extends BaseCard {
    public static final String ID = makeID("AssistShadow");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            3
    );

    private static final Color FLAVOR_BOX_COLOR = CardHelper.getColor(190, 190, 190);
    private static final Color FLAVOR_TEXT_COLOR = CardHelper.getColor(0, 0, 0);

    public AssistShadow() {
        super(ID, info);

        setExhaust(true);
        setCostUpgrade(2);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.Shadow));
        addToBot(new VFXAction(new WhirlwindEffect(new Color(1.0F, 0.9F, 0.4F, 1.0F), true)));
        addToBot(new SkipEnemiesTurnAction());
        addToBot(new PressEndTurnButtonAction());
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AssistShadow();
    }
}
