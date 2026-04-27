package theHedgehog.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Equilibrium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.YESSSAction;
import theHedgehog.character.Sonic;
import theHedgehog.powers.FalconPunchRarePower;
import theHedgehog.util.CardStats;

public class FalconPunchRare extends BaseCard {
    public static final String ID = makeID("FalconPunchRare");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    // private static final int DAMAGE = 20;
    // private static final int UPG_DAMAGE = 2;
    private static final int MAGIC = 1;

    /// "DESCRIPTION": "Deal !D! damage. NL When you are attacked this turn, deal !M! damage to the attacker."
    public FalconPunchRare() {
        super(ID, info);
        loadCardImage(SonicMod.imagePath("cards/skill/FalconPunch.png"));
        SetChaosEmeraldCardback();

        this.cardsToPreview = new FalconPunchRare2();

        // setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC);

        tags.add(SonicTags.LIKE_SILENT);
        tags.add(SonicTags.ERA_MODERN);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // addToBot(new ApplyPowerAction(p, p, new EquilibriumPower(p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new FalconPunchRarePower(p)));
        addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, magicNumber)));
        // addToBot(new VFXAction(new WhirlwindEffect(new Color(1.0F, 0.9F, 0.4F, 1.0F), true)));
        // addToBot(new PressEndTurnButtonAction());

        // // addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        // addToBot(new YESSSAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        // addToBot(new DrawCardAction(magicNumber));
        addToBot(new ExhaustAction(magicNumber, false));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new FalconPunchRare();
    }
}
