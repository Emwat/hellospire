package hellospire.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;
import hellospire.SoundLibrary;
import hellospire.actions.ModFastAction;
import hellospire.actions.RandomizeCostAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class AssistJet extends BaseCard {
    public static final String ID = makeID("AssistJet");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;
    private static final Color FLAVOR_BOX_COLOR = CardHelper.getColor(121, 181, 161);
    private static final Color FLAVOR_TEXT_COLOR = CardHelper.getColor(0, 0, 0);

    public AssistJet() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        setEthereal(true);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.Jet));

        for (int i = 0; i < magicNumber; i++) {
            addToBot(new ChannelAction(new Frost()));
        }
        addToBot(new SelectCardsInHandAction(
                1,
                CardCrawlGame.languagePack.getUIString(makeID("AssistJetMessage")).TEXT[0],
                false, false, pickableCards, cards -> {
            for (AbstractCard c : cards) {
                addToBot(new RandomizeCostAction(c));
                addToBot(new ModFastAction(() -> {
                    if (c.costForTurn == 3) {
                        addToBot(SoundLibrary.VoiceAction(SoundLibrary.JetSneeze));
                    } else if (c.costForTurn == 0) {
                        addToBot(SoundLibrary.VoiceAction(SoundLibrary.JetWhat));
                    }
                }));
            }
        }));

    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new AssistJet();
    }
}
