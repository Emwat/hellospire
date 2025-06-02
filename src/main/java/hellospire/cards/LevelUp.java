package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.powers.LevelUpFlightPower;
import hellospire.powers.LevelUpPowerPower;
import hellospire.powers.LevelUpSpeedPower;
import hellospire.util.CardStats;

import static hellospire.SonicMod.imagePath;

public class LevelUp extends BaseCard {
    public static final String ID = makeID("LevelUp");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;
    private CardType LastTypeCardPlayed;

    /// I seriously want to nerf this to 1, but honestly, it's just more fun w/ 2
    public LevelUp() {
        super(ID, info);

        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.PlayVoice(SoundLibrary.LevelUp));
        if (LastTypeCardPlayed == CardType.ATTACK) {
            addToBot(new ApplyPowerAction(p, p, new LevelUpPowerPower(p, magicNumber)));
        } else if (LastTypeCardPlayed == CardType.SKILL) {
            addToBot(new ApplyPowerAction(p, p, new LevelUpSpeedPower(p, magicNumber)));
        } else if (LastTypeCardPlayed == CardType.POWER) {
            addToBot(new ApplyPowerAction(p, p, new LevelUpFlightPower(p, magicNumber)));
        } else {
            addToBot(new MakeTempCardInHandAction(new Ring().makeStatEquivalentCopy(), magicNumber ));
//            addToBot(new ApplyPowerAction(p, p, new LevelUpSpeedPower(p, magicNumber)));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        UpdateLastCardPlayed();
        UpdateCardImage();

    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        UpdateLastCardPlayed();
        UpdateCardImage();

    }
    private void UpdateCardImage(){
        if (LastTypeCardPlayed == CardType.ATTACK) {
            loadCardImage(LevelUpPath("LevelUpPower.png"));
        } else if (LastTypeCardPlayed == CardType.SKILL) {
            loadCardImage(LevelUpPath("LevelUpSpeed.png"));
        } else if (LastTypeCardPlayed == CardType.POWER) {
            loadCardImage(LevelUpPath("LevelUpFlight.png"));
        } else {
            loadCardImage(LevelUpPath("LevelUp.png"));
        }
    }

    private String LevelUpPath(String filename){
        return imagePath("cards/skill/" + filename);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new LevelUp();
    }

    private void UpdateLastCardPlayed() {
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty()) {
            LastTypeCardPlayed = null;
            return;
        }
        LastTypeCardPlayed = ((AbstractCard) AbstractDungeon.actionManager.cardsPlayedThisCombat.get(
                AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1)).type;
    }

}
