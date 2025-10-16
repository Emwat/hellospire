package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import hellospire.SonicTags;
import hellospire.actions.EvokeAllOrbsWithoutRemovingAction;
import hellospire.actions.ModFastAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

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
                addToBot(new EvokeAllOrbsWithoutRemovingAction());
                if (!this.upgraded) {
                    addToBot(new ExhaustSpecificCardAction(this, p.hand, true));
                }
            }
        }));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SonicWave();
    }
}
