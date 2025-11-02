package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.actions.YESSSAction;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class FalconPunch extends BaseCard {
    public static final String ID = makeID("FalconPunch");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 3;

    /// "DESCRIPTION": "Deal !D! damage. NL When you are attacked this turn, deal !M! damage to the attacker."
    public FalconPunch() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        tags.add(SonicTags.LIKE_SILENT);
        tags.add(SonicTags.ERA_MODERN);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new YESSSAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new DrawCardAction(1));
        addToBot(new ExhaustAction(1, false));
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();

        if (this.upgraded &&
                getPower(AbstractDungeon.player, StrengthPower.POWER_ID) >= 7 &&
                getPower(AbstractDungeon.player, DexterityPower.POWER_ID) >= 7) {
                    loadCardImage(SonicMod.imagePath("cards/attack/FalconPunchSuper.png"));
        }
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new FalconPunch();
    }
}
