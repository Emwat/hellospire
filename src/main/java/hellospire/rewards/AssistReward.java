package hellospire.rewards;

import basemod.abstracts.CustomReward;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.beyond.WrithingMass;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import hellospire.SonicMod;
import hellospire.cards.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import static hellospire.SonicMod.makeID;

public class AssistReward extends CustomReward {
    public static final String ID = makeID("AssistReward");
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(makeID("AssistMessage")).TEXT;
//    private static final Texture ICON = new Texture(Gdx.files.internal("[pathtotexturefile]"));

    // TEXT is [
    // "Lose",
    // "Get"
    // ]

    public AbstractCard assist;
    public UUID uuid;
    public AbstractCard transformedAssist;
    public Boolean isAssistUpgraded;

    public static AssistReward Constructor2(String type, String id, int amount, int bonusGold) {
        AbstractCard assist = new Assist();
        boolean isAssistUpgraded = bonusGold == 1;
        if (!AbstractDungeon.player.masterDeck.isEmpty()) {
            for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
                if (card.cardID.equals(Assist.ID) && card.upgraded == isAssistUpgraded) {
                    assist = card;
                    break;
                }
            }
        }

        AbstractCard reward = new AssistBig();
        if (AssistAmy.ID.equals(id)) {
            reward = new AssistAmy();
        } else if (AssistBarry.ID.equals(id)) {
            reward = new AssistBarry();
        } else if (AssistBig.ID.equals(id)) {
            reward = new AssistBig();
        } else if (AssistBlaze.ID.equals(id)) {
            reward = new AssistBlaze();
        } else if (AssistChip.ID.equals(id)) {
            reward = new AssistChip();
        } else if (AssistCream.ID.equals(id)) {
            reward = new AssistCream();
        } else if (AssistKnuckles.ID.equals(id)) {
            reward = new AssistKnuckles();
        } else if (AssistRosy.ID.equals(id)) {
            reward = new AssistRosy();
        } else if (AssistRouge.ID.equals(id)) {
            reward = new AssistRouge();
        } else if (AssistShadow.ID.equals(id)) {
            reward = new AssistShadow();
        } else if (AssistSilver.ID.equals(id)) {
            reward = new AssistSilver();
        } else if (AssistSticks.ID.equals(id)) {
            reward = new AssistSticks();
        } else if (AssistTails.ID.equals(id)) {
            reward = new AssistTails();
        } else if (AssistTikal.ID.equals(id)){
            reward = new AssistTikal();
        } else {
            SonicMod.logger.error("SpireWolf25 Error: AssistCard {} is not found.", id);
        }

        return new AssistReward(assist, assist.uuid, reward, isAssistUpgraded);
    }

    public AssistReward(AbstractCard assist, UUID uuid, AbstractCard transformedAssist, Boolean isAssistUpgraded) {
        super(ImageMaster.REWARD_CARD_NORMAL,
                String.format("%s %s. %s %s.",
                        TEXT[0],
                        assist == null ? "ERROR" : assist.name,
                        TEXT[1],
                        transformedAssist == null ? "ERROR" : transformedAssist.name)
                , RewardTypePatch.ASSIST_LOCKIN);
        this.assist = assist;
        this.uuid = uuid;
        this.transformedAssist = transformedAssist;
        this.isAssistUpgraded = isAssistUpgraded;

        boolean hasToxicEgg = AbstractDungeon.player.hasRelic("Toxic Egg");
        if (assist.upgraded || hasToxicEgg) {
            transformedAssist.upgrade();
            if (hasToxicEgg) {
                AbstractDungeon.player.getRelic("Toxic Egg").flash();
            }
        }
    }


    @Override
    public boolean claimReward() {
        ArrayList<AbstractCard> masterDeck = AbstractDungeon.player.masterDeck.group;

        for (int i = masterDeck.size() - 1; i >= 0; --i) {
            AbstractCard card = (AbstractCard) masterDeck.get(i);
            if (card.uuid == this.uuid) {
                AbstractDungeon.player.masterDeck.removeCard(card);
                break;
            }
        }

        AbstractDungeon.effectList.add(
                new ShowCardAndObtainEffect(transformedAssist.makeCopy(),
                        (float) Settings.WIDTH / 2.0F,
                        (float) Settings.HEIGHT / 2.0F));
        return true;
    }
}