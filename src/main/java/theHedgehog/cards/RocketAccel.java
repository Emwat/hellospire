package theHedgehog.cards;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.util.SpireHelp;
import theHedgehog.SonicMod;
import theHedgehog.character.Sonic;
import theHedgehog.powers.RocketAccelPower;
import theHedgehog.util.CardStats;

import static theHedgehog.SonicMod.makeID;

public class RocketAccel extends BaseCard {
    public static final String ID = makeID("RocketAccel");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            3
    );

    // Bludgeon does 32/42
    private static final int DAMAGE = 20;
    private static final int UPG_DAMAGE = 4;
    private static final int MAGIC = 8;
    private static final int UPG_MAGIC = 4;
    private static final int TIS_MAGIC = 6;
    private static final int TIS_UPG_MAGIC = 4;

    public RocketAccel() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
        if (Loader.isModLoaded("spireTogether")) {
            setMagic(TIS_MAGIC, TIS_UPG_MAGIC);
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        final float duration = 3f;
        final float bubbleDuration = 3f;

        for (int i = 0; i < 5; ++i) {
            addToBot(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
        }
        for (int i = 0; i < 5; ++i) {
            addToBot(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
        }

        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        if (!Loader.isModLoaded("spireTogether")) {
            addToBot(new ApplyPowerAction(p, p, new RocketAccelPower(p, 2, magicNumber)));
        } else {
            addToBot(new TalkAction(true,
                    SonicMod.modLocalizedStrings.getTalkString(makeID("TiSRocketAccel")).DIALOG[0],
                    duration, bubbleDuration));

            addToBot(new ApplyPowerAction(p, p, new RocketAccelPower(p, 1, magicNumber)));

            for (P2PPlayer e : SpireHelp.Multiplayer.Players.GetPlayers(true, true)){
                e.addPower(new RocketAccelPower(p, 1, this.magicNumber));
            }
        }

    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new RocketAccel();
    }

}
