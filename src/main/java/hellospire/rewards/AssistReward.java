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
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.BackToBasics;
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
    public boolean isAssistUpgraded;

    private static final Color TIP_COL = Color.WHITE.cpy();
    private static final float XOFFSET = 25f * Settings.scale;
    protected static final float REWARD_X_POS = Settings.WIDTH * 0.434F;
    public AbstractCard card;
    protected AbstractCard renderCard;

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

        AbstractCard reward = new Madness();
        if (AssistAmy.ID.equals(id)) {
            reward = new AssistAmy();
        } else if (AssistBarry.ID.equals(id)) {
            reward = new AssistBarry();
        } else if (AssistBig.ID.equals(id)) {
            reward = new AssistBig();
        } else if (AssistBlaze.ID.equals(id)) {
            reward = new AssistBlaze();
        } else if (AssistCharmy.ID.equals(id)) {
            reward = new AssistCharmy();
        } else if (AssistChip.ID.equals(id)) {
            reward = new AssistChip();
        } else if (AssistCream.ID.equals(id)) {
            reward = new AssistCream();
        } else if (AssistEspio.ID.equals(id)){
            reward = new AssistEspio();
        } else if (AssistJet.ID.equals(id)){
            reward = new AssistJet();
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
        } else if (AssistVector.ID.equals(id)){
            reward = new AssistVector();
        } else {
            SonicMod.logger.error("SpireWolf25 AssistReward Loading Error: AssistCard {} is not found.", id);
        }

        if (isAssistUpgraded) {
            reward.upgrade();
        }

        return new AssistReward(assist, assist.uuid, reward, isAssistUpgraded);
    }

    public AssistReward(AbstractCard assist, UUID uuid, AbstractCard transformedAssist, boolean isAssistUpgraded) {
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
        if (assist.upgraded || hasToxicEgg || isAssistUpgraded) {
            transformedAssist.upgrade();
            if (hasToxicEgg) {
                AbstractDungeon.player.getRelic("Toxic Egg").flash();
            }
        }

        card = transformedAssist;
        renderCard = transformedAssist.makeStatEquivalentCopy();
    }

    @Override
    public boolean claimReward() {
        ArrayList<AbstractCard> masterDeck = AbstractDungeon.player.masterDeck.group;

        for (int i = masterDeck.size() - 1; i >= 0; --i) {
            AbstractCard card = masterDeck.get(i);
            if (card.uuid == this.uuid) {
                AbstractDungeon.player.masterDeck.removeCard(card);
                break;
            }
        }

        AbstractDungeon.effectList.add(
                new ShowCardAndObtainEffect(transformedAssist.makeStatEquivalentCopy(),
                        (float) Settings.WIDTH / 2.0F,
                        (float) Settings.HEIGHT / 2.0F));
        return true;
    }

    @Override
    public void update() {
        if (hb.hovered && InputHelper.justClickedRight && !isDone) {
            CardCrawlGame.sound.playA("UI_CLICK_1", 0.25f);
            CardCrawlGame.cardPopup.open(transformedAssist);
        }
        super.update();
    }

    // @Override
    // public void render(SpriteBatch sb) {
    //     Color col;
    //     if (hb.hovered) {
    //         sb.setColor(new Color(0.4f, 0.6f, 0.6f, 1.0f));
    //         col = Settings.GOLD_COLOR;
    //     } else {
    //         sb.setColor(new Color(0.5f, 0.6f, 0.6f, 0.8f));
    //         col = Settings.CREAM_COLOR;
    //     }
    //
    //     if (hb.clickStarted) {
    //         sb.draw(ImageMaster.REWARD_SCREEN_ITEM, Settings.WIDTH / 2.0f - 232.0f, y - 49.0f, 232.0f, 49.0f, 464.0f, 98.0f, Settings.xScale * 0.98f, Settings.scale * 0.98f, 0.0f, 0, 0, 464, 98, false, false);
    //     } else {
    //         sb.draw(ImageMaster.REWARD_SCREEN_ITEM, Settings.WIDTH / 2.0f - 232.0f, y - 49.0f, 232.0f, 49.0f, 464.0f, 98.0f, Settings.xScale, Settings.scale, 0.0f, 0, 0, 464, 98, false, false);
    //     }
    //
    //     if (this.flashTimer != 0.0f) {
    //         sb.setColor(0.6f, 1.0f, 1.0f, this.flashTimer * 1.5f);
    //         sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
    //         sb.draw(ImageMaster.REWARD_SCREEN_ITEM, Settings.WIDTH / 2.0f - 232.0f, this.y - 49.0f, 232.0f, 49.0f, 464.0f, 98.0f, Settings.xScale * 1.03f, Settings.scale * 1.15f, 0.0f, 0, 0, 464, 98, false, false);
    //         sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    //     }
    //
    //     float scale = renderCard.drawScale;
    //
    //     renderCard.drawScale = 0.175f;
    //     renderCard.current_x = card.target_x = hb.x + ((AbstractCard.RAW_W * renderCard.drawScale) * Settings.scale) / 2f + XOFFSET;
    //     renderCard.current_y = card.target_y = hb.cY;
    //     renderCard.render(sb);
    //
    //     renderCard.drawScale = scale;
    //
    //     FontHelper.renderSmartText(sb, FontHelper.cardDescFont_N, text, Settings.WIDTH * 0.434F, y + 5.0f * Settings.scale, 1000.0f * Settings.scale, 0.0f, col);
    //     FontHelper.renderSmartText(sb, FontHelper.cardDescFont_N, TEXT[1], REWARD_X_POS, this.y - FontHelper.getHeight(FontHelper.cardDescFont_N, text, Settings.scale) - 6f * Settings.scale, 1000.0f * Settings.scale, 0.0f, TIP_COL);
    //
    //     if (hb.hovered || hb.justHovered) {
    //         SpireAnniversary6Mod.hoverRewardWorkaround = this;
    //     }
    //
    //     hb.render(sb);
    // }
    //
    // //Due to reward scrolling's orthographic camera and render order of rewards, the card needs to be rendered outside of the render method
    // public void renderCardOnHover(SpriteBatch sb) {
    //     renderCard.current_x = card.target_x = InputHelper.mX + (AbstractCard.RAW_W * renderCard.drawScale) * Settings.scale;
    //     renderCard.current_y = card.target_y = InputHelper.mY;
    //     renderCard.render(sb);
    // }
}