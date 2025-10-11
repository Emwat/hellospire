package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.MyModConfig;
import hellospire.SonicMod;
import hellospire.actions.ModFastAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;
import hellospire.util.TextureLoader;

public class HomingAttack extends BaseCard implements BranchingUpgradesCard {
    public static final String ID = makeID("HomingAttack");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 2;
    private int plays = 0;
    private final int maxPlays = 1;

    public HomingAttack() {
        super(ID, info);
        this.cardsToPreview = new Trick();

        if (MyModConfig.enableCrossModIntegrations && Loader.isModLoaded("PrideMod")) {
            loadCardImage(SonicMod.imagePath("cards/attack/HomingAttackPrideMod.png"));
        }

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.returnToHand = this.upgraded && this.isBranchUpgrade() && plays < maxPlays;

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
            upgradeDamage(UPG_DAMAGE);
            if (isBranchUpgrade()) {
                branchUpgrade();
            } else {
                baseUpgrade();
            }
        }
    }

    public void baseUpgrade() {
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void branchUpgrade() {
        if (Settings.language.name().equalsIgnoreCase("eng")) {
            name = "Serial Homing Attack";
        }
        loadCardImage(SonicMod.imagePath("cards/attack/HomingAttackSerial.png"));
        portraitImg = TextureLoader.getTexture(SonicMod.imagePath("cards/attack/HomingAttackSerial_p.png"));
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    private void updatePlays(){
        if (upgraded && this.isBranchUpgrade()){
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
            updatePlays();;
        }));
    }

    //    "EXTENDED_DESCRIPTION": [
//            "Deal !D! damage. NL Add a Ring and 2 Tricks to your hand."
//            "Deal !D! damage. NL Add 2 Rings and a Trick to your hand.",
//            ]


    @Override
    public AbstractCard makeCopy() { //Optional
        return new HomingAttack();
    }
}
