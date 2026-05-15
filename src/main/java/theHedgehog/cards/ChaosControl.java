package theHedgehog.cards;

import com.evacipated.cardcrawl.mod.stslib.variables.RefundVariable;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.DriftAction;
import theHedgehog.actions.ModFastAction;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.powers.ChaosControlPower;
import theHedgehog.util.CardStats;
import thePackmaster.ThePackmaster;

public class ChaosControl extends BaseCard {
    public static final String ID = makeID("ChaosControl");
    private static final CardType cardtype = CardType.SKILL;
    private static final CardTarget cardTarget = CardTarget.SELF;
    private static final int cost = -1;
    private static final CardStats info = Loader.isModLoaded("anniv5") ?
            new CardStats(ThePackmaster.Enums.PACKMASTER_RAINBOW, cardtype, CardRarity.RARE, cardTarget, cost) :
            new CardStats(Sonic.Meta.CARD_COLOR, cardtype, CardRarity.SPECIAL, cardTarget, cost);

    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 1;

    public ChaosControl() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        if (!Loader.isModLoaded("anniv5")) {
            SetChaosEmeraldCardback();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (EnergyPanel.totalCount + magicNumber <= 0) {
            return;
        }
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.ChaosControl));
        addToBot(new ApplyPowerAction(p, p, new ChaosControlPower(p, this.energyOnUse + magicNumber)));

        if (!this.freeToPlayOnce) {
            addToBot(new ModXFastAction(() -> {
                p.energy.use(EnergyPanel.totalCount);
            }));
        }
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new ChaosControl();
    }
}
