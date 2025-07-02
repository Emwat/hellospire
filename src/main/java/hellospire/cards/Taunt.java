package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Bash;
import com.megacrit.cardcrawl.cards.red.PommelStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import hellospire.MyModConfig;
import hellospire.SonicMod;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Taunt extends BaseCard {
    public static final String ID = makeID("Taunt");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 3;

    ///    "DESCRIPTION": "Apply 2 Vulnerable. NL Gain 2 Temporary Dexterity."
    public Taunt() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        String voiceLine = SoundLibrary.GetRandomVoice(new ArrayList<>(Arrays.asList(
                SoundLibrary.CatchMeIfYouCan,
                SoundLibrary.StepItUp,
                SoundLibrary.TooSlow
        )));


        String[] texts = CardCrawlGame.languagePack.getCharacterString(makeID("TheHedgehog")).TEXT;

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

        addToBot(SoundLibrary.PlayVoice(voiceLine));
        if (Objects.equals(voiceLine, SoundLibrary.CatchMeIfYouCan)) {
            addToBot(new TalkAction(true, texts[3], 2f, 2f));
        } else if (Objects.equals(voiceLine, SoundLibrary.StepItUp)) {
            addToBot(new TalkAction(true, texts[4], 2f, 2f));
        } else if (Objects.equals(voiceLine, SoundLibrary.TooSlow)) {
            addToBot(new TalkAction(true, texts[5], 2f, 2f));
        }

        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));
        addToBot(new DrawCardAction(1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPG_MAGIC);
//            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            loadCardImage(SonicMod.imagePath("cards/skill/Taunt2.png"));
//            this.initializeDescription();
        }

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Taunt();
    }
}
