package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicTags;
import hellospire.character.Sonic;
import hellospire.powers.NextTurnEchoPower;
import hellospire.util.CardStats;

public class BlueBlur extends BaseCard {
    public static final String ID = makeID("BlueBlur");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );


    private static final int BLOCK = 3;

    public BlueBlur() {
        super(ID, info);

        setBlock(BLOCK);
        setExhaust(true);
        tags.add(SonicTags.LIKE_SILENT);
    }

    ///            "DESCRIPTION": "Gain !B! Block. Next turn, your first move will play twice."

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new ApplyPowerAction(p, p, new NextTurnEchoPower(p, 1)));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.setExhaust(false);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new BlueBlur();
    }
}
