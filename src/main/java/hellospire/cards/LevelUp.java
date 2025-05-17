package hellospire.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SoundLibrary;
import hellospire.character.MyCharacter;
import hellospire.powers.LevelUpFlightPower;
import hellospire.powers.LevelUpPowerPower;
import hellospire.powers.LevelUpSpeedPower;
import hellospire.util.CardStats;

import static hellospire.BasicMod.imagePath;

public class LevelUp extends BaseCard {
    public static final String ID = makeID("LevelUp");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            0
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
        addToBot(new SFXAction(SoundLibrary.LevelUp));
//        addToBot(new AbstractGameAction() {
//            @Override
//            public void update() {
//                UpdateLastCardPlayed();
//                BaseMod.logger.info(String.format("LastTypeCardPlayed"), LastTypeCardPlayed);
//                this.isDone = true;
//            }
//        });
        if (LastTypeCardPlayed == CardType.ATTACK) {
            addToBot(new ApplyPowerAction(p, p, new LevelUpPowerPower(p, magicNumber)));
        } else if (LastTypeCardPlayed == CardType.SKILL) {
            addToBot(new ApplyPowerAction(p, p, new LevelUpSpeedPower(p, magicNumber)));
        } else if (LastTypeCardPlayed == CardType.POWER) {
            addToBot(new ApplyPowerAction(p, p, new LevelUpFlightPower(p, magicNumber)));
        } else {
            addToBot(new MakeTempCardInHandAction(new Ring().makeStatEquivalentCopy(), 2 ));
//            addToBot(new ApplyPowerAction(p, p, new LevelUpSpeedPower(p, magicNumber)));
        }
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        UpdateLastCardPlayed();
        if (LastTypeCardPlayed == CardType.ATTACK) {
            loadCardImage(LevelUpPath("LevelUpPower.png"));
        } else if (LastTypeCardPlayed == CardType.SKILL) {
            loadCardImage(LevelUpPath("LevelUpSpeed.png"));
        } else if (LastTypeCardPlayed == CardType.POWER) {
            loadCardImage(LevelUpPath("LevelUpFlight.png"));
        } else {
            loadCardImage(LevelUpPath("LevelUpSpeed.png"));
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
            LastTypeCardPlayed = CardType.SKILL;
            return;
        }
        LastTypeCardPlayed = ((AbstractCard) AbstractDungeon.actionManager.cardsPlayedThisCombat.get(
                AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1)).type;
    }
}
