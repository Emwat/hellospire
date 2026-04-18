package theHedgehog.cards;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.ModFastAction;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.actions.RandomizeCostAction;
import theHedgehog.cardmodifiers.SpinUpModifier;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;
import theHedgehog.util.ExtraIcons;
import theHedgehog.util.TextureLoader;

public class Windmill extends BaseCard {
    public static final String ID = makeID("Windmill");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    // Anger 6 Damage
    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 3;
    private int randomizedCost = -1;
    private final Texture twisterIcon = TextureLoader.getTexture(SonicMod.imagePath("ui/twister.png"));

    public Windmill() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        CardModifierManager.addModifier(this, new SpinUpModifier());
        tags.add(SonicTags.ERA_ADVENTURE);
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        atbInitRandomizedCost();
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        atbSetDescriptionToDefault();
    }

    private void atbInitRandomizedCost() {
        addToBot(new ModXFastAction(() -> {
            if (randomizedCost == -1) {
                randomizedCost = AbstractDungeon.cardRandomRng.random(3);
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] +
                        randomizedCost +
                        cardStrings.EXTENDED_DESCRIPTION[1];
                this.initializeDescription();
            }
        }));
    }

    private void atbSetDescriptionToDefault() {
        addToBot(new ModXFastAction(() -> {
            randomizedCost = -1;
            this.rawDescription = cardStrings.DESCRIPTION;
            this.initializeDescription();
        }));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        addToBot(new SelectCardsInHandAction(
                1,
                CardCrawlGame.languagePack.getUIString(makeID("WindmillMessage")).TEXT[0],
                false, false, pickableCards, cards -> {
            for (AbstractCard c : cards) {
                int oldCost = c.costForTurn;
                addToBot(new RandomizeCostAction(c, randomizedCost));
                addToBot(new ModFastAction(() -> {
                    int difference = Math.abs(c.costForTurn - oldCost);
                    if (difference >= 3 && c.costForTurn == 0) {
                        addToBot(SoundLibrary.VoiceAction(SoundLibrary.PerfectBingo));
                    } else if (c.costForTurn == 0) {
                        addToBot(SoundLibrary.VoiceAction(SoundLibrary.Bingo));
                        if (p instanceof Sonic) {
                            addToBot(new ModFastAction(() -> {
                                ((Sonic) p).playAnimation("happy");
                            }));
                        }
                    } else if (c.costForTurn == 3 || (oldCost == 2 && c.costForTurn == 2)) {
                        if (p instanceof Sonic) {
                            addToBot(new ModFastAction(() -> {
                                ((Sonic) p).playAnimation("hurt");
                            }));
                        }
                    }
                }));
            }
        }));
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
        return new Windmill();
    }
}
