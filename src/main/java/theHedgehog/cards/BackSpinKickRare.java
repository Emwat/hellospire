package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Bludgeon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class BackSpinKickRare extends BaseCard {
    public static final String ID = makeID("BackSpinKickRare");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            5
    );

    // Eviscerate 7(9) * 3
    // Blood For Blood 18
    // MasterfulStab 12(16)
    // Bludgeon 32(42)
    private static final int DAMAGE = 32;
    private static final int UPG_DAMAGE = 10;

    public BackSpinKickRare() {
        super(ID, info);
        SetChaosEmeraldCardback();
        loadCardImage(SonicMod.imagePath("cards/attack/BackSpinKick.png"));

        setDamage(DAMAGE, UPG_DAMAGE);
        tags.add(SonicTags.KICK);
        tags.add(SonicTags.LIKE_SILENT);
        tags.add(SonicTags.ERA_MODERN);
    }

    /// Deal !D! damage. This costs 1 less for each attack played.
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        ReduceCostForTurnByAttacksPlayed(null);
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        super.triggerOnOtherCardPlayed(c);
        ReduceCostForTurnByAttacksPlayed(c);
    }

    public void atTurnStart() {
        this.resetAttributes();
        this.applyPowers();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }
}
