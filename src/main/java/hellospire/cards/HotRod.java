package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 4;
    private static final int EARLY_BLOCK = 5;
    private static final int MID_BLOCK = 7;
    private static final int BLOCK = 10;
    private static final int BAD_BLOCK = 3;

    private static final int UPG_BLOCK = 2;

    private static final int END_TICKER = 2;
    // ticker should NOT be static. If static, it will apply across all copies of HotRod in your deck.
    // For instance, you have 1 HotRod in your hand. If you draw another HotRod, the second HotRod will have the same ticker
    // as the one in your hand.
    private int ticker;

    public HotRod() {
        super(ID, info);

        setDamage(0, 0);
        setBlock(EARLY_BLOCK, 1);
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

    private void TransformCardEffects(){
        if (ticker == 2) {
            this.setDamage(0, 0);
            this.setBlock(EARLY_BLOCK, 1);
            this.type = CardType.SKILL;
            this.target = CardTarget.SELF;
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
            loadCardImage(SonicMod.imagePath("cards/skill/HotRod.png"));
        } else if (ticker == 1) {
            this.setDamage(0, 0);
            this.setBlock(MID_BLOCK, 1);
            this.type = CardType.SKILL;
            this.target = CardTarget.SELF;
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            this.initializeDescription();
            loadCardImage(SonicMod.imagePath("cards/skill/HotRod.png"));
        } else if (ticker == 0) {
            this.setDamage(DAMAGE, UPG_DAMAGE);
            this.setBlock(BLOCK, UPG_BLOCK);
            this.type = CardType.ATTACK;
            this.target = CardTarget.ENEMY;
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[2];
            this.initializeDescription();
            loadCardImage(SonicMod.imagePath("cards/attack/HotRod2.png"));
        } else {
            this.setBlock(BAD_BLOCK, BAD_BLOCK + 1);
            this.setDamage(0, 0);
            this.type = CardType.SKILL;
            this.target = CardTarget.SELF;
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
            this.initializeDescription();
            loadCardImage(SonicMod.imagePath("cards/skill/HotRod.png"));
        }
    }

    //    "EXTENDED_DESCRIPTION": [
//            "Standard. NL Gain !B! block.",
//            "HOT! NL Gain !B! block.",
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
