package theHedgehog.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.actions.CrestOfFireAction;
import theHedgehog.character.Sonic;
import theHedgehog.effects.ModFireSomersaultEffect;
import theHedgehog.relics.FireSoulRelic;
import theHedgehog.util.CardStats;
import theHedgehog.util.ExtraIcons;
import theHedgehog.util.TextureLoader;

public class FireSomersault extends BaseCard implements CrestOfFireCard {
    public static final String ID = makeID("FireSomersault");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 1;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private static int FireSoulRelicAmount = 0;
    private final Texture fireIcon = TextureLoader.getTexture(SonicMod.imagePath("ui/fireIcon.png"));

    public FireSomersault() {
        this(0);
    }

    /// "DESCRIPTION": "Deal !D! damage. NL If exhausted, add another Fire Somersault to your hand."
    public FireSomersault(int upgrades) {
        super(ID, info);

        setDamage(DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
        this.timesUpgraded = upgrades;
        tags.add(SonicTags.CREST_OF_FIRE);
        tags.add(SonicTags.ERA_ADVENTURE);
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(FireSoulRelic.ID)){
            FireSoulRelicAmount = AbstractDungeon.player.getRelic(FireSoulRelic.ID).counter;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {
            addToBot(new VFXAction(p, new ModFireSomersaultEffect(p.hb.cX, p.hb.cY, (int)(damage / DAMAGE)), 0.1F));
        } else {
            addToBot(new VFXAction(p, new ModFireSomersaultEffect(p.hb.cX, p.hb.cY, (int)(damage / DAMAGE)), 0.5F));
        }

        if (this.timesUpgraded > CREST_OF_FIRE_MARK) {
            int self_damage = timesUpgraded - CREST_OF_FIRE_MARK;
            addToBot(new DamageAction(p, new DamageInfo(p, self_damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }

        addToBot(new CrestOfFireAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), p, this));
    }

    public void upgrade() {
        if (canUpgrade()) {
            this.upgradeDamage(UPG_DAMAGE);
            this.upgradeMagicNumber(UPG_MAGIC);
            ++this.timesUpgraded;
            this.upgraded = true;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            this.initializeTitle();
        }
    }

    public boolean canUpgrade() {
        return true;
//        if (this.timesUpgraded < 7) {
//            return true;
//        }
//        return false;
    }

    @Override
    public void triggerOnExhaust() {
        AbstractCard tempCard = this.makeStatEquivalentCopy();
        tempCard.baseDamage += tempCard.magicNumber;
        addToBot(new MakeTempCardInHandAction(tempCard, 1, true));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (this.willBurnPlayer(this)) {
            this.glowColor = CrestOfFireCard.CREST_OF_FIRE_BURN_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void update() {
        super.update();
        if (this.timesUpgraded > 7 + FireSoulRelicAmount) {
            ExtraIcons.icon(fireIcon)
                    .text(String.valueOf(timesUpgraded - 7 - FireSoulRelicAmount))
                    .textColor(Color.ORANGE.cpy())
                    .textOffsetY(-30f)
                    .drawColor(new Color(1, 1, 1, this.transparency))
                    .render(this);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new FireSomersault(this.timesUpgraded);
    }
}
