package hellospire.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hellospire.MyModConfig;
import hellospire.SonicTags;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;

public class TripleKick extends BaseCard {
    public static final String ID = makeID("TripleKick");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 1;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;
    AbstractCard kick2 = new TripleKick2();
    AbstractCard kick3 = new TripleKick3();

    public TripleKick() {
        super(ID, info);

        MultiCardPreview.add(this, kick2, kick3);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
        tags.add(SonicTags.KICK);
        tags.add(SonicTags.LIKE_WATCHER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, magicNumber)));
        addToBot(new MakeTempCardInHandAction(kick2.makeStatEquivalentCopy(), 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            ArrayList<AbstractCard> previewCards = MultiCardPreview.multiCardPreview.get(this);
            if (previewCards != null) {
                for (AbstractCard c : MultiCardPreview.multiCardPreview.get(this)) {
                    c.upgrade();
                }
            }
        }
        super.upgrade();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new TripleKick();
    }
}
