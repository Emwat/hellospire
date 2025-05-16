package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Sanctity;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

public class LevelUp extends BaseCard {
    public static final String ID = makeID("Strike");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            1
    );

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;
    private CardType LastTypeCardPlayed;

    public LevelUp() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (LastTypeCardPlayed == CardType.ATTACK) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber)));
        } else if (LastTypeCardPlayed == CardType.SKILL) {
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber)));
        } else if (LastTypeCardPlayed == CardType.POWER) {
            addToBot(new ApplyPowerAction(p, p, new FocusPower(p, magicNumber)));
        } else {
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber)));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new LevelUp();
    }

    private void CheckLastCardPlayed() {
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty()) {
            LastTypeCardPlayed = CardType.SKILL;
            return;
        }
        LastTypeCardPlayed = ((AbstractCard) AbstractDungeon.actionManager.cardsPlayedThisCombat.get(
                AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1)).type;
    }
}
