package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicTags;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;
import java.util.Objects;

public class LightSpeedAttack extends BaseCard {
    public static final String ID = makeID("LightSpeedAttack");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            3
    );

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;

    public LightSpeedAttack() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        tags.add(SonicTags.LIKE_DEFECT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int hits = CalculateRingHits(p);

        // Ragnarok deals 5(6) damage to an enemy 5(6) times
        if (hits > 6) {
            addToBot(SoundLibrary.VoiceAction(SoundLibrary.BlastAway));
        }
        if (hits > 0) {
            for (int i = 0; i < hits; i++) {
                addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.LIGHTNING));
            }
        }
    }

    private int CalculateRingHits(AbstractPlayer p) {
        int hits = 0;
        hits += CountInDeck(p.hand.group);
        hits += CountInDeck(p.drawPile.group);
        hits += CountInDeck(p.discardPile.group);
        hits += CountInDeck(p.exhaustPile.group);
        return hits;
    }

    private int CountInDeck(ArrayList<AbstractCard> deck) {
        int count = 0;
        if (deck.isEmpty()) {
            return 0;
        }
        for (AbstractCard card : deck) {
            if (card.hasTag(SonicTags.RING)) {
                count++;
            }
        }
        return count;
    }

//    @Override
//    public void triggerOnOtherCardPlayed(AbstractCard c) {
//        super.triggerOnOtherCardPlayed(c);
//
//    }

    public void applyPowers() {
        super.applyPowers();
        this.baseMagicNumber = 0;
        this.magicNumber = 0;

        this.baseMagicNumber = CalculateRingHits(AbstractDungeon.player);

        if (this.baseMagicNumber > 0) {
            this.rawDescription = cardStrings.DESCRIPTION +
                    cardStrings.EXTENDED_DESCRIPTION[0] +
                    this.baseMagicNumber +
                    cardStrings.EXTENDED_DESCRIPTION[1];
            this.initializeDescription();
        }

    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new LightSpeedAttack();
    }


}
