package theHedgehog.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;
import theHedgehog.util.TextureLoader;

import java.util.Objects;

import static theHedgehog.SonicMod.imagePath;

public class ClawsUnleashed extends BaseCard implements BranchingUpgradesCard {
    public static final String ID = makeID("ClawsUnleashed");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 14;
    private static final int BASE_UPG_DAMAGE = 4;
    private static final int BRANCH_UPG_DAMAGE = 0;
    private final String playerErrorMessage =  CardCrawlGame.languagePack.getUIString(makeID("ClawsUnleashedMessage")).TEXT[0];

    public ClawsUnleashed() {
        super(ID, info);

        setDamage(DAMAGE, BASE_UPG_DAMAGE);
        tags.add(SonicTags.CLAW);
        tags.add(SonicTags.ERA_MODERN);
    }

    /// "Can only be played if you have a Dark orb."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new ClawEffect(m.hb.cX, m.hb.cY, Color.CYAN, Color.WHITE), 0.1F));
        }

        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);

        if (this.upgraded && this.isBranchUpgrade()) {
            return canUse;
        }

        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (Objects.equals(orb.name, "Dark")) {
                return canUse;
            }
        }

        this.cantUseMessage = playerErrorMessage;
        return false;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (isBranchUpgrade()) {
                branchUpgrade();
            } else {
                baseUpgrade();
            }
        }
    }

    public void baseUpgrade() {
        upgradeDamage(BASE_UPG_DAMAGE);
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void branchUpgrade() {
        upgradeDamage(BRANCH_UPG_DAMAGE);
        this.loadCardImage( imagePath("cards/attack/ClawsUnleashed2.png"));
        this.portraitImg = TextureLoader.getTexture(SonicMod.imagePath("cards/attack/ClawsUnleashed2_p.png"));
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new ClawsUnleashed();
    }
}
