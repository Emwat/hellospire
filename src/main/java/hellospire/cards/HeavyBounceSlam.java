package hellospire.cards;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicMod;
import hellospire.SonicTags;
import hellospire.SoundLibrary;
import hellospire.cardmodifiers.SpinUpModifier;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class HeavyBounceSlam extends BaseCard {
    public static final String ID = makeID("HeavyBounceSlam");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 2;
    private static int timesPlayed = 0;

    public HeavyBounceSlam() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        this.returnToHand = true;
        CardModifierManager.addModifier(this, new SpinUpModifier());

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        SonicMod.logger.info(this.name + " damage is " + this.damage);
        // addToBot(new HeavyIncrementAction(this);
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL),
                damage < 11 ? AbstractGameAction.AttackEffect.BLUNT_LIGHT : AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new ModifyDamageAction(this.uuid, this.baseDamage));
        if (!Loader.isModLoaded("PrideMod")){
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    timesPlayed++;
                    if (timesPlayed == 1){
                        loadCardImage(SonicMod.imagePath("cards/attack/HeavyBounceSlam1.png"));
                    } else if (timesPlayed == 2){
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

    // @Override
    // public void triggerOnOtherCardPlayed(AbstractCard c) {
    //     addToBot(new HeavyKeepCostAction(this));
    // }

//    @Override
//    public void updateCost(int amt) {
//        addToBot(new HeavyKeepCostAction(this));
//    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new HeavyBounceSlam();
    }
}
