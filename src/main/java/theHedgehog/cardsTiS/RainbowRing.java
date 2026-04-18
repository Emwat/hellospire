package theHedgehog.cardsTiS;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import spireTogether.cards.CustomMultiplayerCard;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.network.objects.items.NetworkCard;
import spireTogether.util.SpireHelp;
import theHedgehog.SonicMod;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.cards.BaseCard;
import theHedgehog.cards.Trick;
import theHedgehog.character.Sonic;
import theHedgehog.powers.RocketAccelPower;
import theHedgehog.util.CardStats;

import static theHedgehog.SonicMod.makeID;
import static theHedgehog.multiplayer.ModMultiplayerHelper.GiveCardToTeammate;

public class RainbowRing extends BaseCard {
    public static final String ID = makeID("TiSRainbowRing");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final String FLAVOR_TEXT = SonicMod.modLocalizedStrings.getTalkString(makeID("TiSRainbowRingFlavor")).DIALOG[0];
    private static final Color FLAVOR_BOX_COLOR = CardHelper.getColor(0, 0, 0);
    private static final Color FLAVOR_TEXT_COLOR = Color.WHITE.cpy();

    public RainbowRing() {
        super(ID, info);
        this.cardsToPreview = new Trick().makeCopy();
        setCostUpgrade(0);

        FlavorText.AbstractCardFlavorFields.flavor.set(this.cardsToPreview, FLAVOR_TEXT);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this.cardsToPreview, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this.cardsToPreview, FLAVOR_TEXT_COLOR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(1));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeCopy()));

        for (P2PPlayer e : SpireHelp.Multiplayer.Players.GetPlayers(true, true)){
            addToBot(new ModXFastAction(() -> {
                e.addCard(NetworkCard.Generate(this.cardsToPreview.makeCopy()), CardGroup.CardGroupType.HAND);
            }));
        }
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new RainbowRing();
    }

}
