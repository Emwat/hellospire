package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.NotStanceCheckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.EmptyFist;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.EmptyStanceEffect;
import hellospire.SonicMod;
import hellospire.SonicTags;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.util.CardStats;
import hellospire.util.TextureLoader;

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
    private final String[] texts = CardCrawlGame.languagePack.getCharacterString(makeID("TheHedgehog")).TEXT;

    ///    "DESCRIPTION": "Apply 2 Vulnerable. NL Gain 2 Temporary Dexterity."
    public Taunt() {
        super(ID, info);

        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        float duration = 3f;
        float bubbleDuration = 3f;

        if (!(AbstractDungeon.player instanceof Sonic)) {
            addToBot(new TalkAction(true, texts[8], 2f, 2f));
        } else {
            String voiceLine = SoundLibrary.GetRandomVoice(new ArrayList<>(Arrays.asList(
                    SoundLibrary.CatchMeIfYouCan,
                    SoundLibrary.StepItUp,
                    SoundLibrary.TooSlow
            )));

            // "TEXT": [
            //     "A free spirited hedgehog that hates evil. NL (You can change the voice frequency in Main Menu > Mods > The Hedgehog > Config)",
            //     "You charge your spin dash.",
            //             "Navigating an unlit street, ",
            //             "Catch me if you can!",
            //             "Come on! Step it up!",
            //             "You're too slow!",
            //             "I'm a HEDGEHOG!",
            //             "Do you know who I am?",
            // ]

            if (Sonic.currentModSkin.getName().contains("Sonic")){
                addToBot(SoundLibrary.VoiceAction(voiceLine));
                if (Objects.equals(voiceLine, SoundLibrary.CatchMeIfYouCan)) {
                    addToBot(new TalkAction(true, texts[3], duration, bubbleDuration));
                } else if (Objects.equals(voiceLine, SoundLibrary.StepItUp)) {
                    addToBot(new TalkAction(true, texts[4], duration, bubbleDuration));
                } else if (Objects.equals(voiceLine, SoundLibrary.TooSlow)) {
                    addToBot(new TalkAction(true, texts[5], duration, bubbleDuration));
                }
            } else if (Sonic.currentModSkin.getName().contains("Knuckles")) {
                if (Objects.equals(voiceLine, SoundLibrary.CatchMeIfYouCan)) {
                    addToBot(new TalkAction(true, "You and me. 1 v 1.", duration, bubbleDuration));
                } else if (Objects.equals(voiceLine, SoundLibrary.StepItUp)) {
                    addToBot(new TalkAction(true, "Your funeral.", duration, bubbleDuration));
                } else if (Objects.equals(voiceLine, SoundLibrary.TooSlow)) {
                    addToBot(new TalkAction(true, "I'll take you on!", duration, bubbleDuration));
                }
            } else if (Sonic.currentModSkin.getName().contains("Shadow")) {
                if (Objects.equals(voiceLine, SoundLibrary.CatchMeIfYouCan)) {
                    addToBot(new TalkAction(true, "You don't even stand a chance.", duration, bubbleDuration));
                } else if (Objects.equals(voiceLine, SoundLibrary.StepItUp)) {
                    addToBot(new TalkAction(true, "Pathetic.", duration, bubbleDuration));
                } else if (Objects.equals(voiceLine, SoundLibrary.TooSlow)) {
                    addToBot(new TalkAction(true, "There's no time for games.", duration, bubbleDuration));
                }
            } else if (Sonic.currentModSkin.getName().contains("Tails")) {
                if (Objects.equals(voiceLine, SoundLibrary.CatchMeIfYouCan)) {
                    addToBot(new TalkAction(true, "Ready for me?", duration, bubbleDuration));
                } else if (Objects.equals(voiceLine, SoundLibrary.StepItUp)) {
                    addToBot(new TalkAction(true, "I'll show you how powerful my Cyclone is!", duration, bubbleDuration));
                } else if (Objects.equals(voiceLine, SoundLibrary.TooSlow)) {
                    addToBot(new TalkAction(true, "All's well that ends well.", duration, bubbleDuration));
                }
            }
        }

        if (this.upgraded && this.isBranchUpgrade()) {
            addToBot(new NotStanceCheckAction("Neutral", new VFXAction(new EmptyStanceEffect(p.hb.cX, p.hb.cY), 0.1F)));
            addToBot(new ChangeStanceAction("Neutral"));
        }
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));
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
    public AbstractCard makeCopy() { //Optional
        return new Taunt();
    }
}
