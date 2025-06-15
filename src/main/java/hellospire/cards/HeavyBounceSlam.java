package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicMod;
import hellospire.SonicTags;
import hellospire.actions.IncreaseCostAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class HeavyBounceSlam extends BaseCard {
    public static final String ID = makeID("HeavyBounceSlam");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 2;
    private static int timesPlayed = 0;

    public HeavyBounceSlam() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        tags.add(SonicTags.LIKE_IRONCLAD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL),
                damage < 11 ? AbstractGameAction.AttackEffect.BLUNT_LIGHT : AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new ModifyDamageAction(this.uuid, this.damage));
        addToBot(new IncreaseCostAction(this.uuid, 1));
        timesPlayed++;
        if (timesPlayed == 1){
            loadCardImage(SonicMod.imagePath("cards/attack/HeavyBounceSlam1.png"));
        } else if (timesPlayed == 2){
            loadCardImage(SonicMod.imagePath("cards/attack/HeavyBounceSlam2.png"));
        } else {
            loadCardImage(SonicMod.imagePath("cards/attack/HeavyBounceSlam.png"));
        }

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new HeavyBounceSlam();
    }
}
