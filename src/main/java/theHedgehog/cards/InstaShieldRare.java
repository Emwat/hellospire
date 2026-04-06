package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.actions.ActivatePassiveOrbAction;
import theHedgehog.actions.DashPanelAction;
import theHedgehog.actions.ModFastAction;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

import java.util.Map;

public class InstaShieldRare extends BaseCard {
    public static final String ID = makeID("InstaShieldRare");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 3;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 0;
    private static final int PERSIST_AMT = 1;
    private static final int PERSIST_AMT_UPGRADED = 1;
    private static final String KEYWORD_PERSIST = "CustomVar_PERSIST";
    private int plays = 0;

    public InstaShieldRare() {
        super(ID, info);
        loadCardImage(SonicMod.imagePath("cards/attack/InstaShield.png"));
        SetChaosEmeraldCardback();

        setBlock(BLOCK, UPG_BLOCK);
        setDamage(DAMAGE, UPG_DAMAGE);
        // setMagic(MAGIC, UPG_MAGIC);
        setCustomVar(KEYWORD_PERSIST, PERSIST_AMT, PERSIST_AMT_UPGRADED);
        tags.add(SonicTags.LIKE_WATCHER);
        tags.add(SonicTags.ERA_CLASSIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.returnToHand = plays < customVar(KEYWORD_PERSIST);
        atbInstaShield(p, m);
        addToBot(new ModFastAction(() -> {
            plays++;
            updatePlays();
        }));
    }

    private void atbInstaShield(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new ActivatePassiveOrbAction(p));
        // addToBot(new ApplyPowerAction(p, p, new FlameBarrierPower(p, magicNumber)));
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        addToBot(new ModFastAction(() -> {
            plays = 0;
            updatePlays();
        }));
    }

    private void updatePlays() {
        int usesRemaining = customVar(KEYWORD_PERSIST) - plays;
        if (usesRemaining > 0) {
            int s = usesRemaining == 1 ? 1 : 2;
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] +
                    cardStrings.EXTENDED_DESCRIPTION[s].replace("{X}", String.valueOf(usesRemaining));
        } else {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        }
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new InstaShieldRare();
    }
}
