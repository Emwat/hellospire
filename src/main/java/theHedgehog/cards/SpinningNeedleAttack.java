package theHedgehog.cards;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.HeavyBlade;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.actions.ModFastAction;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.actions.RandomizeCostAction;
import theHedgehog.cardmodifiers.SpinUpModifier;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;
import theHedgehog.util.ExtraIcons;
import theHedgehog.util.TextureLoader;

public class SpinningNeedleAttack extends BaseCard {
    public static final String ID = makeID("SpinningNeedleAttack");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 2;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private int randomizedCost = -1;
    private final Texture twisterIcon = TextureLoader.getTexture(SonicMod.imagePath("ui/twisterPurple.png"));

    public SpinningNeedleAttack() {
        super(ID, info);

        setDamage(DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
        CardModifierManager.addModifier(this, new SpinUpModifier());
        tags.add(SonicTags.ERA_MODERN);
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
        // addToBot(new SpinningNeedleAttackAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
        addToBot(new DrawCardAction(1));
        addToBot(new ModFastAction(() -> {
            AbstractCard lastCard = p.hand.getTopCard();
            addToBot(new RandomizeCostAction(lastCard, randomizedCost));
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        if (randomizedCost != -1) {
            this.baseDamage = this.baseDamage + (randomizedCost * magicNumber);
        }
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
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
        return new SpinningNeedleAttack();
    }
}
