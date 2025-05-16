package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

import java.util.ArrayList;

public class DevCreateCard extends BaseCard {
    public static final String ID = makeID("DevCreateCard");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.SPECIAL, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public DevCreateCard() {
        super(ID, info);
        this.setInnate(true);
        this.returnToHand = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> tmp = new ArrayList<>();

        tmp.add(new Acceleration());
        tmp.add(new AMAZING());
        tmp.add(new Athleticism());
        tmp.add(new BackSpinKick());
        tmp.add(new BlastOff());
        tmp.add(new BlueBlur());
        tmp.add(new BlueTornado());
        tmp.add(new BouncePad());
        tmp.add(new ClawsUnleashed());
        tmp.add(new Crouch());
        tmp.add(new DashPanel());
        tmp.add(new Defend());
        tmp.add(new DirectJump());
        tmp.add(new DizzySpin());
        tmp.add(new DoubleAirKick());
        tmp.add(new Drift());
        tmp.add(new DropDash());
        tmp.add(new EndlessBoost());
        tmp.add(new FireSomersault());
        tmp.add(new FireTackle());
        tmp.add(new FlagPole());
        tmp.add(new FootSweep());
        tmp.add(new GrindRail());
        tmp.add(new HeavyBounceSlam());
        tmp.add(new Height());
        tmp.add(new HomingAttack());
        tmp.add(new HomingDash());
//        tmp.add(new HummingTop());
//        tmp.add(new InstaShield());
        tmp.add(new Leap());
//        tmp.add(new LightSpeedAttack());
//        tmp.add(new MagicHands());
        tmp.add(new Momentum());
        tmp.add(new MyRainbow());
//        tmp.add(new Pose());
//        tmp.add(new PowerRing());
        tmp.add(new PunchRush());
        tmp.add(new QuickAir());
        tmp.add(new QuickStep());
        tmp.add(new RampJump());
        tmp.add(new ScissorKick());
        tmp.add(new SecretRoute());
        tmp.add(new SkyRing());
        tmp.add(new Slide());
        tmp.add(new SonicFlare());
        tmp.add(new SonicWind());
        tmp.add(new SonicWave());
//        tmp.add(new SpeedUp());
        tmp.add(new SpinDash());
        tmp.add(new Strike());
        tmp.add(new SuperSonicForm());
        tmp.add(new Taunt());
        tmp.add(new Teaser());
        tmp.add(new Trick());
        tmp.add(new TripleKick());
        tmp.add(new UpDraft());
//        tmp.add(new UpperSpin());
        tmp.add(new VolcanoSlider());
        tmp.add(new WallJump());
        tmp.add(new Windmill());
        tmp.add(new WindUpPunch());

        addToBot(new LoseEnergyAction(99));
        addToBot(new GainEnergyAction(3));
        addToBot(new HealAction(m, p, 999));
        addToBot(new SelectCardsAction(tmp, 1, "Choose a card to add to your hand", cards -> {
            for (AbstractCard c : cards) {
                addToBot(new MakeTempCardInHandAction(c, 1, true));
            }
        }));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new DevCreateCard();
    }
}
