package theHedgehog.cards;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class AssistEspio extends BaseCard implements OnObtainCard {
    public static final String ID = makeID("AssistEspio");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 3;
    private static final int UPG_BLOCK = 3;
    private static final int MAGIC = 2;
    private static final Color FLAVOR_BOX_COLOR = CardHelper.getColor(200, 0, 140);
    private static final Color FLAVOR_TEXT_COLOR = CardHelper.getColor(255, 240, 0);

    public AssistEspio() {
        super(ID, info);
        this.cardsToPreview = new Shiv();

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
        tags.add(SonicTags.LIKE_SILENT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.Espio));
        addToBot(new GainBlockAction(p, block));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview, this.magicNumber + GetExtraShiv()));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (GetExtraShiv() == 1) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }

        if (AbstractDungeon.player.hand.size() + magicNumber + GetExtraShiv() > BaseMod.MAX_HAND_SIZE + 1) {
            this.glowColor = Color.RED.cpy();
        }
    }

    private int GetExtraShiv(){
        return SonicMod.attackCardsPlayedThisTurn == 0 ? 1 : 0;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AssistEspio();
    }

    @Override
    public void onObtainCard() {
        removeAssistCard(this.hasTag(SonicTags.UPG_ASSIST));
    }
}
