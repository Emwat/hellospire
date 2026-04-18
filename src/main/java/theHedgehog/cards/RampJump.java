package theHedgehog.cards;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spireTogether.network.P2P.P2PManager;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.network.objects.items.NetworkCard;
import spireTogether.util.SpireHelp;
import theHedgehog.SonicTags;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

import static theHedgehog.multiplayer.ModMultiplayerHelper.CountTeammates;
import static theHedgehog.util.GeneralUtils.CapitalizeFirstLetter;

public class RampJump extends BaseCard {
    public static final String ID = makeID("RampJump");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public RampJump() {
        super(ID, info);
        this.cardsToPreview = new Trick();

        // setMagic(MAGIC);
        setMagic(MAGIC, UPG_MAGIC);
        // setExhaust(true);
        tags.add(SonicTags.LIKE_SILENT);

        if (Loader.isModLoaded("spireTogether")) {
            this.rawDescription = this.cardStrings.DESCRIPTION + this.cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), magicNumber));

        if (Loader.isModLoaded("spireTogether") && CheckIfCenterCard(this, p.hand)) {
            AbstractCard trick = this.cardsToPreview.makeCopy();
            if (Settings.language == Settings.GameLanguage.ENG) {
                if (P2PManager.GetPlayer(0) != null) {
                    trick.name = "Trick w/ " + (P2PManager.GetSelf()).username;
                }
            }

            for (P2PPlayer e : SpireHelp.Multiplayer.Players.GetPlayers(true, true)) {
                addToBot(new ModXFastAction(() -> {
                    e.addCard(NetworkCard.Generate(trick), CardGroup.CardGroupType.HAND);
                }));
            }
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        atbChangeImage();
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        super.triggerOnOtherCardPlayed(c);
        atbChangeImage();
    }

    private void atbChangeImage() {
        if (Loader.isModLoaded("spireTogether")) {
            addToBot(new ModXFastAction(() -> {
                if (CountTeammates() > 0 && CheckIfCenterCard(this, AbstractDungeon.player.hand)) {
                    loadCardImage(imageSkillPath("RainbowRing.png"));
                } else {
                    loadCardImage(imageSkillPath("RampJump.png"));
                }
            }));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (AbstractDungeon.player.hand.size() + magicNumber > BaseMod.MAX_HAND_SIZE + 1) {
            this.glowColor = Color.RED.cpy();
            return;
        }

        if (Loader.isModLoaded("spireTogether")) {
            if (CountTeammates() > 0 && CheckIfCenterCard(this, AbstractDungeon.player.hand)) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            }
        }
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new RampJump();
    }
}
