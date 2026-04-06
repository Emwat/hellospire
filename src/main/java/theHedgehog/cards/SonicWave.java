package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import theHedgehog.SonicTags;
import theHedgehog.actions.EvokeAllOrbsWithoutRemovingAction;
import theHedgehog.actions.ModFastAction;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;
import theHedgehog.util.GeneralUtils;

public class SonicWave extends BaseCard {
    public static final String ID = makeID("SonicWave");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 2;

    public SonicWave() {
        super(ID, info);

        setMagic(MAGIC);
        tags.add(SonicTags.LIKE_DEFECT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        // addToBot(new YESSSAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new ModFastAction(() -> {
            if (HasEmptyOrbSlots()) {
                for (int i = 0; i < magicNumber; i++) {
                    addToBot(new ChannelAction(new Lightning()));
                }
            } else {
                if (!this.upgraded) {
                    addToBot(new ModXFastAction(() -> {
                        this.setExhaust(true);
                    }));
                }
                addToBot(new EvokeAllOrbsWithoutRemovingAction());
            }
        }));
    }

    @Override
    public void applyPowers() {
        transitionToFullBar();
        super.applyPowers();
    }

    // "EXTENDED_DESCRIPTION": [
    //         "If you have any empty Orb slots, Channel !M! Lightning.",
    //         " NL ",
    //         " Otherwise Evoke ALL Orbs twice and Exhaust.",
    //         " Otherwise Evoke ALL Orbs twice."
    //         ]

    private void transitionToFullBar() {
        if (!GeneralUtils.isIndeedWithoutADoubtInCombat()) {
            return;
        }
        int y = !this.upgraded ? 2 : 3;
        if (HasEmptyOrbSlots()) {
            this.rawDescription = String.format("%s%s%s",
                    cardStrings.EXTENDED_DESCRIPTION[0],
                    cardStrings.EXTENDED_DESCRIPTION[1],
                    GeneralUtils.ColorWord("[#808080]", cardStrings.EXTENDED_DESCRIPTION[y])
            );
        } else {
            this.rawDescription = String.format("%s%s%s",
                    GeneralUtils.ColorWord("[#808080]", cardStrings.EXTENDED_DESCRIPTION[0]),
                    cardStrings.EXTENDED_DESCRIPTION[1],
                    cardStrings.EXTENDED_DESCRIPTION[y]
            );
        }
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new SonicWave();
    }
}
