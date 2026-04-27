package theHedgehog.cards;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicMod;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.ModFastAction;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.strings.SonicExtraCardStrings;
import theHedgehog.strings.SonicTalkStrings;
import theHedgehog.util.CardStats;
import theHedgehog.util.TextureLoader;

public class BouncePad extends BaseCard implements BranchingUpgradesCard {
    public static final String ID = makeID("BouncePad");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            1
    );

    private static String[] NAMES;

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private int plays = 0;
    private final int maxPlays = 1;

    public BouncePad() {
        super(ID, info);
        this.cardsToPreview = new Ring();

        setBlock(BLOCK);
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.returnToHand = this.upgraded && this.isBranchUpgrade() && plays < maxPlays && !isBranchMaxedHandSize();
        if (this.upgraded && this.isBranchUpgrade() && isBranchMaxedHandSize()) {
            addToBot(new ModXFastAction(() -> {
                AbstractDungeon.player.createHandIsFullDialog();
            }));
        }

        addToBot(SoundLibrary.SoundAction(SoundLibrary.Spring));
        addToBot(new GainBlockAction(p, block));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), magicNumber));
        addToBot(new ModFastAction(() -> {
            plays++;
            updatePlays();
        }));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
            if (isBranchUpgrade()) {
                branchUpgrade();
            } else {
                baseUpgrade();
            }
        }
    }

    public void baseUpgrade() {
        upgradeMagicNumber(UPG_MAGIC);
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void branchUpgrade() {
        name = NAMES[0];
        loadCardImage(SonicMod.imagePath("cards/skill/BouncePadSerial.png"));
        portraitImg = TextureLoader.getTexture(SonicMod.imagePath("cards/skill/BouncePadSerial_p.png"));
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    private void updatePlays(){
        if (upgraded && this.isBranchUpgrade()){
            if (plays < maxPlays) {
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            } else {
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[2];
            }
            this.initializeDescription();
        }
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        addToBot(new ModFastAction(() -> {
            plays = 0;
            updatePlays();;
        }));
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (AbstractDungeon.player.hand.size() + magicNumber > BaseMod.MAX_HAND_SIZE + 1) {
            this.glowColor = Color.RED.cpy();
        }
    }

    private boolean isBranchMaxedHandSize(){
        return this.upgraded && this.isBranchUpgrade() &&
                AbstractDungeon.player.hand.size() + 2 > BaseMod.MAX_HAND_SIZE + 1;
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new BouncePad();
    }

    static {
        NAMES = SonicMod.modLocalizedStrings.getExtraCardString(ID).NAMES;
    }
}
