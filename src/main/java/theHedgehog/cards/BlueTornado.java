package theHedgehog.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Terror;
import com.megacrit.cardcrawl.cards.red.Shockwave;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.ModTextInCenterAction;
import theHedgehog.character.Sonic;
import theHedgehog.powers.DizzyPower;
import theHedgehog.util.CardStats;
import theHedgehog.util.TextureLoader;

public class BlueTornado extends BaseCard implements BranchingUpgradesCard {
    public static final String ID = makeID("BlueTornado");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int MAGIC = 99;
    private static final int DIZZY = 0;
    private static final int UPG_DIZZY = 2;
    private static final String DIZZY_KEYWORD = "CustomVar_DIZZY";

    public BlueTornado() {
        super(ID, info);

        setMagic(MAGIC);
        // setCustomVar(DIZZY_KEYWORD, DIZZY, UPG_DIZZY);
        setExhaust(true);
        tags.add(SonicTags.LIKE_SILENT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // TogetherInSpire
        if (this.target != CardTarget.ENEMY && m == null) {
            int countMonsters = 0;
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!mo.isDeadOrEscaped()) {
                    countMonsters++;
                }
            }
            if (countMonsters == 1) {
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!mo.isDeadOrEscaped()) {
                        m = mo;
                        break;
                    }
                }
            } else {
                AbstractCard c = new Terror();
                c.upgrade();
                addToBot(new MakeTempCardInHandAction(c.makeStatEquivalentCopy(), 1));
                this.purgeOnUse = true;
                return;
            }
        }

        addToBot(SoundLibrary.SoundAction(SoundLibrary.BlueTornado));

        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));
        if (this.upgraded && !isBranchUpgrade()) {
            addToBot(new ApplyPowerAction(m, p, new DizzyPower(m, customVar(DIZZY_KEYWORD)), customVar(DIZZY_KEYWORD)));
        } else if (this.upgraded && isBranchUpgrade()) {
            addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), 1));
        }
        // if (CheckIfLeftCard(this, p.hand) || CheckIfRightCard(this, p.hand)) {
        //     addToBot(new ExhaustSpecificCardAction(this, p.hand));
        // }
        addToBot(new ExhaustSpecificCardAction(this, p.hand));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (isBranchUpgrade()) {
                branchUpgrade();
            } else {
                baseUpgrade();
            }
        }
    }

    public void baseUpgrade() {
        setCustomVar(DIZZY_KEYWORD, UPG_DIZZY);
        loadCardImage(SonicMod.imagePath("cards/skill/BlueTornado2.png"));
        portraitImg = TextureLoader.getTexture(SonicMod.imagePath("cards/skill/BlueTornado2_p.png"));
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void branchUpgrade() {
        this.cardsToPreview = new Trick();
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new BlueTornado();
    }
}
