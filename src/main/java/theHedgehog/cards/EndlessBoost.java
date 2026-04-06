package theHedgehog.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BerserkPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.character.Sonic;
import theHedgehog.relics.AirBoostShoesRelic;
import theHedgehog.util.CardStats;

public class EndlessBoost extends BaseCard {
    public static final String ID = makeID("EndlessBoost");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = -1;

    public EndlessBoost() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        tags.add(SonicTags.LIKE_IRONCLAD);
        tags.add(SonicTags.RIGHTMOST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int magicOutput = magicNumber;
//        addToBot(SoundLibrary.PlayRandomVoice(new ArrayList<>(Arrays.asList(
//                SoundLibrary.CatchMeIfYouCan,
//                SoundLibrary.NeverUnderestimate
//        ))));
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.NeverUnderestimate));
        if (this.forceConditionEffect || CheckIfRightCard(this, p.hand)) {
            magicOutput -= 1;
        }
        if (magicOutput > 0){
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, -magicOutput), -magicOutput));
        }
        addToBot(new ApplyPowerAction(p, p, new BerserkPower(p, 1)));
    }

    // public void upgrade() {
    //     if (!this.upgraded) {
    //         this.upgradeName();
    //         this.upgradeMagicNumber(UPG_MAGIC);
    //         this.setInnate(true);
    //         this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
    //         this.initializeDescription();
    //     }
    //     super.upgrade();
    // }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (isPlayerHandNull()) {
            return;
        }

        if (AbstractDungeon.player.hasRelic(AirBoostShoesRelic.ID)) {
            this.glowColor = Color.WHITE.cpy();
            return;
        }

        if (CheckIfRightCard(this, AbstractDungeon.player.hand)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new EndlessBoost();
    }
}
