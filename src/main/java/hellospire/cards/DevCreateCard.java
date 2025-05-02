package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

import java.util.ArrayList;

public class DevCreateCard extends BaseCard {
    public static final String ID = makeID("DevCreateCard");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.SPECIAL, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public DevCreateCard() {
        super(ID, info);
        this.setInnate(true);
        this.returnToHand = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> tmp = new ArrayList<>();

        tmp.add(new PunchRush());
        tmp.add(new WindUpPunch());
        tmp.add(new SonicFlare());

        addToBot(new LoseEnergyAction(99));
        addToBot(new GainEnergyAction(3));
        addToBot(new HealAction(m, p, 999));
        addToBot(new SelectCardsAction(tmp, 1, "Choose a card to add to your hand", cards -> {
            for (AbstractCard c : cards) {
                addToBot(new MakeTempCardInHandAction(c, 1, true));
            }
        }));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new DevCreateCard();
    }
}
