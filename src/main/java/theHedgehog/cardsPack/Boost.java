package theHedgehog.cardsPack;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicTags;
import theHedgehog.actions.BoostAction;
import theHedgehog.cards.BaseCard;
import theHedgehog.character.Sonic;
import theHedgehog.powers.RingPower;
import theHedgehog.util.CardStats;
import thePackmaster.ThePackmaster;

public class Boost extends BaseCard {
    public static final String ID = makeID("PackBoost");
    private static final CardStats info = Loader.isModLoaded("anniv5") ?
            new CardStats(
            ThePackmaster.Enums.PACKMASTER_RAINBOW,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            0
    ) :
            new CardStats(
                    Sonic.Meta.CARD_COLOR,
                    CardType.ATTACK,
                    CardRarity.SPECIAL,
                    CardTarget.ALL_ENEMY,
                    0
            );

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 3;

    public Boost() {
        super(ID, info);
        // this.cardsToPreview = new hellospire.cardsPackExclusive.Ring();
        this.isMultiDamage = true;

        setDamage(DAMAGE, UPG_DAMAGE);

        tags.add(SonicTags.ERA_MODERN);

        // if (Loader.isModLoaded("anniv5") && SpireAnniversary5Mod.oneFrameMode) {
        //     ApplyOneFrameModeSetting();
        // } else {
        //     setBackgroundTexture(SonicMod.characterPath("cardback/bg_attack.png"), SonicMod.characterPath("cardback/bg_attack_p"));
        //     setOrbTexture(SonicMod.characterPath("cardback/small_orb.png"), SonicMod.characterPath("cardback/energy_orb.png"));
        //     // setOrbTexture(Sonic.Meta.SMALL_ORB, Sonic.Meta.ENERGY_ORB);
        // }
    }

    // public static final String ID =  SonicMod.makeID("PackBoost");
    // private static final int COST = 0;
    // private static final int DAMAGE = 4;
    // private static final int UPGRADE_DAMAGE = 6;
    //
    // public Boost() {
    //     super(ID, COST, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
    //     this.cardsToPreview = new Ring();
    //     this.isMultiDamage = true;
    //     this.baseDamage = DAMAGE;
    // }
    //
    // @Override
    // public void upp() {
    //     this.upgradeDamage(UPGRADE_DAMAGE);
    // }
    //
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new BoostAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        AbstractPlayer p = AbstractDungeon.player;
        if (!p.hasPower(RingPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(p, p, new RingPower(p, 0)));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Boost();
    }


}
