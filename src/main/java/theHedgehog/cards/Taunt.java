package theHedgehog.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.NotStanceCheckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Trip;
import com.megacrit.cardcrawl.cards.green.Terror;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.EmptyStanceEffect;
import theHedgehog.SonicMod;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.ModTextInCenterAction;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.strings.ModLocalizedStrings;
import theHedgehog.util.CardStats;
import theHedgehog.util.TextureLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Taunt extends BaseCard implements BranchingUpgradesCard {
    public static final String ID = makeID("Taunt");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF_AND_ENEMY,
            1
    );

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC_BASE = 2;
    private static final int UPG_MAGIC_BRANCH = 1;
    // private static final String[] texts = CardCrawlGame.languagePack.getCharacterString(makeID("TheHedgehog")).TEXT;

    ///    "DESCRIPTION": "Apply 2 Vulnerable. NL Gain 2 Temporary Dexterity."
    public Taunt() {
        super(ID, info);

        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        float duration = 3f;
        float bubbleDuration = 3f;

        // TogetherInSpire
        if (this.target != CardTarget.ENEMY && m == null) {
            m = getLastMonsterOrNull();
            if (m == null) {
                addToBot(new MakeTempCardInHandAction(new Trip().makeStatEquivalentCopy(), 1));
                this.purgeOnUse = true;
            }
        }

        if (!(AbstractDungeon.player instanceof Sonic)) {
            addToBot(new TalkAction(true,
                    SonicMod.modLocalizedStrings.getTalkString(makeID("TauntDefault")).DIALOG[0],
                    duration, bubbleDuration));
        } else {
            addTalkAction(p, duration, bubbleDuration);
        }

        if (this.upgraded && this.isBranchUpgrade()) {
            addToBot(new NotStanceCheckAction("Neutral", new VFXAction(new EmptyStanceEffect(p.hb.cX, p.hb.cY), 0.1F)));
            addToBot(new ChangeStanceAction("Neutral"));
        }
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));
    }

    private void addTalkAction(AbstractPlayer p, float duration, float bubbleDuration) {
        Sonic s = (Sonic) p;

        String voiceLine = SoundLibrary.GetRandomVoice(new ArrayList<>(Arrays.asList(
                SoundLibrary.CatchMeIfYouCan,
                SoundLibrary.StepItUp,
                SoundLibrary.TooSlow
        )));
        String[] texts = SonicMod.modLocalizedStrings.getTalkString(makeID("Taunt" + Sonic.currentModSkin.getContact())).DIALOG;
        addToBot(SoundLibrary.VoiceAction(voiceLine));
        if (Objects.equals(voiceLine, SoundLibrary.CatchMeIfYouCan)) {
            if (Sonic.isAmy()) {
                addToBot(new ModXFastAction(() -> {
                    s.playAnimation(s.dribbleAnimation("attack2", "attack"));
                }));
            } else if (Sonic.isKnuckles()) {
                addToBot(new ModXFastAction(() -> {
                    s.playAnimation(s.dribbleAnimation("attack2", "attack"));
                }));
            } else if (Sonic.isShadow()) {
                addToBot(new ModXFastAction(() -> {
                    s.playAnimation(s.dribbleAnimation("idle3", "idle2"));
                }));
            } else if (Sonic.isTails()) {
                addToBot(new ModXFastAction(() -> {
                    s.playAnimation(s.dribbleAnimation("idle3", "idle3"));
                }));
            }
            addToBot(new TalkAction(true, texts[0], duration, bubbleDuration));
        } else if (Objects.equals(voiceLine, SoundLibrary.StepItUp)) {
            if (Sonic.currentModSkin.getName().contains("Amy")) {
                addToBot(new ModXFastAction(() -> {
                    s.playAnimation(s.dribbleAnimation("attack2", "attack"));
                }));
            }
            addToBot(new TalkAction(true, texts[1], duration, bubbleDuration));
        } else if (Objects.equals(voiceLine, SoundLibrary.TooSlow)) {
            if (Sonic.currentModSkin.getName().contains("Amy")) {
                addToBot(new ModXFastAction(() -> {
                    s.playAnimation(s.dribbleAnimation("happy2", "happy"));
                }));
            }
            addToBot(new TalkAction(true, texts[2], duration, bubbleDuration));
        }
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
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        upgradeMagicNumber(UPG_MAGIC_BASE);

        this.initializeDescription();
    }

    public void branchUpgrade() {
        loadCardImage(SonicMod.imagePath("cards/skill/Taunt2.png"));
        portraitImg = TextureLoader.getTexture(SonicMod.imagePath("cards/skill/Taunt2_p.png"));
        upgradeMagicNumber(UPG_MAGIC_BRANCH);
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        ApplyNewCost(null);
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        super.triggerOnOtherCardPlayed(c);
        ApplyNewCost(c);
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new Taunt();
    }
}
