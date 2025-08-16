package hellospire.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicMod;
import hellospire.SonicTags;
import hellospire.SoundLibrary;
import hellospire.actions.FasterAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;
import hellospire.util.TextureLoader;

import java.util.ArrayList;
import java.util.Arrays;

public class BouncePad extends BaseCard implements BranchingUpgradesCard {
    public static final String ID = makeID("BouncePad");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 7;
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
        this.returnToHand = this.upgraded && this.isBranchUpgrade() && plays < maxPlays;

        addToBot(SoundLibrary.SoundAction(SoundLibrary.Spring));
        addToBot(new GainBlockAction(p, block));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), magicNumber));
        addToBot(new FasterAction(() -> {
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
        name = "Serial Bounce Pad";
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
        addToBot(new FasterAction(() -> {
            plays = 0;
            updatePlays();;
        }));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new BouncePad();
    }
}
