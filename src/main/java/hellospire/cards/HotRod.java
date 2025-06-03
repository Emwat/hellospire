package hellospire.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicMod;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class HotRod extends BaseCard {
    public static final String ID = makeID("HotRod");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public static final int BLOCK_WARMUP = 5;
    public static final int BLOCK_STANDARD = 7;
    public static final int BLOCK_HOT = 9;
    public static final int BLOCK_CRITICAL = 10;
    public static final int DAMAGE = 6;
    public static final int UPG_DAMAGE = 2;
    public static final int BLOCK_OVERLOADED = 3;

    public static final int UPG_BLOCK = 2;

    private static final int END_TICKER = 3;
    // ticker should NOT be static. If static, it will apply across all copies of HotRod in your deck.
    // For instance, you have 1 HotRod in your hand. If you draw another HotRod, the second HotRod will have the same ticker
    // as the one in your hand.
    private int ticker;

    public HotRod() {
        super(ID, info);

        HotRod0 preview0 = new HotRod0(); //Warming Up
        HotRod1 preview1 = new HotRod1(); //Standard
        HotRod2 preview2 = new HotRod2(); //Hot
        HotRod3 preview3 = new HotRod3(); //Critical
        HotRod4 preview4 = new HotRod4(); //Overloaded
        if (this.upgraded) {
            preview0.upgrade();
            preview1.upgrade();
            preview2.upgrade();
            preview3.upgrade();
            preview4.upgrade();
        }
        MultiCardPreview.add(this, preview0, preview1, preview2, preview3, preview4);

        setDamage(0, 0);
        setBlock(BLOCK_WARMUP, 1);

        ticker = END_TICKER;
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
        super.triggerWhenDrawn();
        ticker = END_TICKER;
        TransformCardEffects();
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
//        super.triggerOnOtherCardPlayed(c);
        ticker--;
        TransformCardEffects();
    }

    private void TransformCardEffects() {
        if (ticker == 3) {
            this.setDamage(0, 0);
            this.setBlock(BLOCK_WARMUP, 1);
            this.type = CardType.SKILL;
            this.target = CardTarget.SELF;
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
            loadCardImage(SonicMod.imagePath("cards/skill/HotRod.png"));
        } else if (ticker == 2) {
            this.setDamage(0, 0);
            this.setBlock(BLOCK_STANDARD, 1);
            this.type = CardType.SKILL;
            this.target = CardTarget.SELF;
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            this.initializeDescription();
            loadCardImage(SonicMod.imagePath("cards/skill/HotRod.png"));
        } else if (ticker == 1) {
            this.setDamage(0, 0);
            this.setBlock(BLOCK_HOT, UPG_BLOCK);
            this.type = CardType.SKILL;
            this.target = CardTarget.SELF;
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[2];
            this.initializeDescription();
            loadCardImage(SonicMod.imagePath("cards/skill/HotRod.png"));
        } else if (ticker == 0) {
            this.setDamage(DAMAGE, UPG_DAMAGE);
            this.setBlock(BLOCK_CRITICAL, UPG_BLOCK);
            this.type = CardType.ATTACK;
            this.target = CardTarget.ENEMY;
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
            this.initializeDescription();
            loadCardImage(SonicMod.imagePath("cards/attack/HotRod2.png"));
        } else {
            this.setBlock(BLOCK_OVERLOADED, 1);
            this.setDamage(0, 0);
            this.type = CardType.SKILL;
            this.target = CardTarget.SELF;
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[4];
            this.initializeDescription();
            loadCardImage(SonicMod.imagePath("cards/skill/HotRod.png"));
        }
    }

    //    "EXTENDED_DESCRIPTION": [
//            "Warming up. NL Gain !B! Block.",
//            "Standard. NL Gain !B! block.",
//            "Hot. NL Gain !B! block.",
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
    public AbstractCard makeCopy() { //Optional
        return new HotRod();
    }
}
