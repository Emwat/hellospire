package theHedgehog.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

import java.util.ArrayList;

public class HotRod extends BaseCard {
    public static final String ID = makeID("HotRod");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );
    private static String[] NAMES;

    public static final int BLOCK_WARMUP = 5;
    public static final int BLOCK_STANDARD = 7;
    public static final int BLOCK_CRITICAL = 9;
    public static final int DAMAGE = 6;
    public static final int UPG_DAMAGE = 2;
    public static final int BLOCK_OVERLOADED = 3;

    public static final int UPG_BLOCK = 2;

    private static final int END_TICKER = 2;
    private HotRod0 preview0 = new HotRod0(); //Warming Up
    private HotRod1 preview1 = new HotRod1(); //Standard
    private HotRod2 preview2 = new HotRod2(); //Critical
    private HotRod3 preview3 = new HotRod3(); //Overloaded
    // ticker should NOT be static. If static, it will apply across all copies of HotRod in your deck.
    // For instance, you have 1 HotRod in your hand. If you draw another HotRod, the second HotRod will have the same ticker
    // as the one in your hand.
    private int ticker;

    public HotRod() {
        super(ID, info);

        MultiCardPreview.add(this, preview0, preview1, preview2, preview3);

        setDamage(0, 0);
        setBlock(BLOCK_WARMUP, 1);

        ticker = END_TICKER;
        tags.add(SonicTags.ERA_MODERN);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        if (ticker == 0) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void triggerWhenDrawn() {
        ticker = END_TICKER;
        TransformCardEffects();
        super.triggerWhenDrawn();
    }

    @Override
    public void onMoveToDiscard() {
        ticker = END_TICKER;
        TransformCardEffects();
        super.onMoveToDiscard();
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
//        super.triggerOnOtherCardPlayed(c);
        ticker--;
        TransformCardEffects();
    }

    private void TransformCardEffects() {
        if (ticker == 2) {
            this.name = NAMES[0];
            this.initializeTitle();
            this.setDamage(0, 0);
            this.setBlock(BLOCK_WARMUP, 1);
            this.type = CardType.SKILL;
            this.target = CardTarget.SELF;
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
            loadCardImage(SonicMod.imagePath("cards/skill/HotRod.png"));
        } else if (ticker == 1) {
            this.name = NAMES[1];
            this.initializeTitle();
            this.setDamage(0, 0);
            this.setBlock(BLOCK_STANDARD, 1);
            this.type = CardType.SKILL;
            this.target = CardTarget.SELF;
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            this.initializeDescription();
            loadCardImage(SonicMod.imagePath("cards/skill/HotRod.png"));
        } else if (ticker == 0) {
            this.name = NAMES[2];
            this.initializeTitle();
            this.setDamage(DAMAGE, UPG_DAMAGE);
            this.setBlock(BLOCK_CRITICAL, UPG_BLOCK);
            this.type = CardType.ATTACK;
            this.target = CardTarget.ENEMY;
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[2];
            this.initializeDescription();
            loadCardImage(SonicMod.imagePath("cards/attack/HotRod2.png"));
        } else {
            this.name = NAMES[3];
            this.initializeTitle();
            this.setBlock(BLOCK_OVERLOADED, 1);
            this.setDamage(0, 0);
            this.type = CardType.SKILL;
            this.target = CardTarget.SELF;
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
            this.initializeDescription();
            loadCardImage(SonicMod.imagePath("cards/skill/HotRodOverloaded.png"));
        }
    }

    //    "EXTENDED_DESCRIPTION": [
//            "Warming up. NL Gain !B! Block.",
//            "Standard. NL Gain !B! block.",
//            "Critical! NL Gain !B! block. NL Deal !D! damage.",
//            "Overloaded. NL Gain !B! block."
//            ]

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (ticker == 0) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            ArrayList<AbstractCard> previewCards = MultiCardPreview.multiCardPreview.get(this);
            if (previewCards != null) {
                for (AbstractCard c : MultiCardPreview.multiCardPreview.get(this)) {
                    c.upgrade();
                }
            }
        }
        super.upgrade();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new HotRod();
    }

    static {
        NAMES = SonicMod.modLocalizedStrings.getExtraCardString(ID).NAMES;
    }
}
