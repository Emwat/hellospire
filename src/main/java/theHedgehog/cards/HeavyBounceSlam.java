package theHedgehog.cards;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.cardmodifiers.SpinUpModifier;
import theHedgehog.character.Sonic;
import theHedgehog.relics.AirBoostShoesRelic;
import theHedgehog.util.CardStats;

public class HeavyBounceSlam extends BaseCard {
    public static final String ID = makeID("HeavyBounceSlam");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 2;
    private static int timesPlayed = 0;

    public HeavyBounceSlam() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        CardModifierManager.addModifier(this, new SpinUpModifier());
        tags.add(SonicTags.ERA_CLASSIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.returnToHand = CheckIfLeftCard(this, p.hand);

        if (m != null && damage > 30) {
            addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY, Color.BLUE.cpy())));
            addToBot(new WaitAction(0.8F));
        }

        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL),
                damage < 11 ? AbstractGameAction.AttackEffect.BLUNT_LIGHT : AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new ModifyDamageAction(this.uuid, this.baseDamage));
        if (!Loader.isModLoaded("PrideMod")) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    timesPlayed++;
                    if (timesPlayed == 1) {
                        loadCardImage(SonicMod.imagePath("cards/attack/HeavyBounceSlam1.png"));
                    } else if (timesPlayed == 2) {
                        loadCardImage(SonicMod.imagePath("cards/attack/HeavyBounceSlam2.png"));
                    } else {
                        loadCardImage(SonicMod.imagePath("cards/attack/HeavyBounceSlam.png"));
                    }

                    if (damage > 30) {
                        addToBot(SoundLibrary.VoiceAction(SoundLibrary.SmallAllRight));
                    }

                    this.isDone = true;
                }
            });
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (isPlayerHandNull()) {
            return;
        }

        if (AbstractDungeon.player.hasRelic(AirBoostShoesRelic.ID)) {
            this.glowColor = Color.WHITE.cpy();
            return;
        }

        if (CheckIfLeftCard(this, AbstractDungeon.player.hand)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    // @Override
    // public void triggerOnOtherCardPlayed(AbstractCard c) {
    //     addToBot(new HeavyKeepCostAction(this));
    // }

//    @Override
//    public void updateCost(int amt) {
//        addToBot(new HeavyKeepCostAction(this));
//    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new HeavyBounceSlam();
    }
}
