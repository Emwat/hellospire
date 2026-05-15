package theHedgehog.cards;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.MyModConfig;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.actions.ModFastAction;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;
import theHedgehog.util.TextureLoader;

public class HomingAttack extends BaseCard implements BranchingUpgradesCard {
    public static final String ID = makeID("HomingAttack");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            1
    );
    private static String[] NAMES;

    private static final int DAMAGE = 8;
    private int plays = 0;
    private final int maxPlays = 1;

    public HomingAttack() {
        super(ID, info);
        this.cardsToPreview = new Trick();

        if (MyModConfig.enableCrossModIntegrations && Loader.isModLoaded("PrideMod")) {
            loadCardImage(SonicMod.imagePath("cards/attack/HomingAttackPrideMod.png"));
        }

        setDamage(DAMAGE);
        tags.add(SonicTags.ERA_ADVENTURE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.returnToHand = this.upgraded && this.isBranchUpgrade() && plays < maxPlays && !isBranchMaxedHandSize();
        if (this.upgraded && this.isBranchUpgrade() && isBranchMaxedHandSize()) {
            addToBot(new ModXFastAction(() -> {
                AbstractDungeon.player.createHandIsFullDialog();
            }));
        }

        AbstractCard trick = new Trick();
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));

        if (this.upgraded && !this.isBranchUpgrade()) {
            addToBot(new MakeTempCardInHandAction(trick, 1, true));
        }
        addToBot(new MakeTempCardInHandAction(trick, 1, true));
        addToBot(new ModFastAction(() -> {
            plays++;
            updatePlays();
        }));
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

    // "Deal !D! damage. NL Add 2 [#efc851]Tricks[] to your hand.",
    public void baseUpgrade() {
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    // Deal !D! damage. NL Add a [#efc851]Trick[] to your hand. This stays in your hand for 1 more use.
    public void branchUpgrade() {
        name = NAMES[0];
        loadCardImage(SonicMod.imagePath("cards/attack/HomingAttackSerial.png"));
        portraitImg = TextureLoader.getTexture(SonicMod.imagePath("cards/attack/HomingAttackSerial_p.png"));
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    private void updatePlays() {
        if (upgraded && this.isBranchUpgrade()) {
            if (plays < maxPlays) {
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            } else {
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[2];
            }
            this.initializeDescription();
        }
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        addToBot(new ModFastAction(() -> {
            plays = 0;
            updatePlays();
        }));
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        int tricks = 1;
        if (this.upgraded && !this.isBranchUpgrade()){
            tricks++;
        }

        if (AbstractDungeon.player.hand.size() + tricks > BaseMod.MAX_HAND_SIZE + 1) {
            this.glowColor = Color.RED.cpy();
        }

        if (isBranchMaxedHandSize()) {
            this.glowColor = Color.RED.cpy();
        }
    }

    private boolean isBranchMaxedHandSize() {
        int tricksToAdd = 2;
        if (plays == 1) {
            tricksToAdd--;
        }
        return this.upgraded && this.isBranchUpgrade() &&
                AbstractDungeon.player.hand.size() + tricksToAdd > BaseMod.MAX_HAND_SIZE + 1;
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new HomingAttack();
    }

    static {
        NAMES = SonicMod.modLocalizedStrings.getExtraCardString(ID).NAMES;
    }
}
