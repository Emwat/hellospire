package theHedgehog.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;
import theHedgehog.SonicMod;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.ModFastAction;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.actions.RandomizeCostAction;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;
import theHedgehog.util.ExtraIcons;
import theHedgehog.util.TextureLoader;

public class AssistJet extends BaseCard implements OnObtainCard {
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
    private static final Color FLAVOR_BOX_COLOR = CardHelper.getColor(7, 226, 26);
    private static final Color FLAVOR_TEXT_COLOR = CardHelper.getColor(3, 104, 18);
    private int randomizedCost = -1;
    private final Texture twisterIcon = TextureLoader.getTexture(SonicMod.imagePath("ui/twister.png"));

    public AssistJet() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        setEthereal(true);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        addToBot(new ModXFastAction(()-> {
            if (randomizedCost == -1) {
                randomizedCost = AbstractDungeon.cardRandomRng.random(3);
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] +
                        randomizedCost +
                        cardStrings.EXTENDED_DESCRIPTION[1];
                this.initializeDescription();
            }
        }));
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        addToBot(new ModXFastAction(()-> {
            randomizedCost = -1;
            this.rawDescription = cardStrings.DESCRIPTION;
            this.initializeDescription();
        }));
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
                addToBot(new RandomizeCostAction(c, randomizedCost));
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
    public void update() {
        super.update();
        if (randomizedCost != -1) {
            ExtraIcons.icon(twisterIcon)
                    .text(String.valueOf(randomizedCost))
                    .textColor(new Color(1, 1, 1, this.transparency))
                    .drawColor(new Color(1, 1, 1, this.transparency))
                    .render(this);
        }
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new AssistJet();
    }

    @Override
    public void onObtainCard() {
        removeAssistCard(this.upgraded);
    }
}
