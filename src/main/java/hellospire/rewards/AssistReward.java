package hellospire.rewards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.beyond.WrithingMass;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import static hellospire.SonicMod.makeID;

public class AssistReward extends CustomReward {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(makeID("AssistMessage")).TEXT;
//    private static final Texture ICON = new Texture(Gdx.files.internal("[pathtotexturefile]"));

    public AbstractCard assist;
    public UUID uuid;
    public AbstractCard transformedAssist;

    public AssistReward(AbstractCard assist, UUID uuid, AbstractCard transformedAssist) {
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

//        AbstractDungeon.player.masterDeck.removeCard(this.assist);
        AbstractDungeon.effectList.add(
                new ShowCardAndObtainEffect(transformedAssist.makeCopy(),
                        (float) Settings.WIDTH / 2.0F,
                        (float) Settings.HEIGHT / 2.0F));
        return true;
    }
}