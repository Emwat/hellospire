package hellospire.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import hellospire.MyModConfig;
import hellospire.SonicTags;
import hellospire.SoundLibrary;
import hellospire.relics.*;
import hellospire.ui.EvolveChaoOption;
import hellospire.ui.HedgehogPack;

import java.util.ArrayList;
import java.util.Arrays;


public class ChaoEvolveEffect extends AbstractGameEffect {
    private final EvolveChaoOption evolveChaoOption;
    private final AbstractCard.CardTags tag;
    private final float x;
    private final float y;

    private final Color screenColor;

    public ChaoEvolveEffect(EvolveChaoOption evolveChaoOption, AbstractCard.CardTags tag, float x, float y) {
        this.screenColor = AbstractDungeon.fadeColor.cpy();
        this.duration = 1.5F;
        this.screenColor.a = 0.0F;
        this.evolveChaoOption = evolveChaoOption;
        this.tag = tag;
        this.x = x;
        this.y = y;
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    public void update() {
        if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
            this.updateBlackScreenColor();
        }

        if (this.duration < 0.0F) {
            ChaoThing();
            this.isDone = true;
            if (CampfireUI.hidden) {
                AbstractRoom.waitTimer = 0.0F;
                if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
                    // GemsPack.socketBonfireOption.reCheck();
                    evolveChaoOption.EnableOrDisableButton();
                    ((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI.reopen();
                    // there was a bug with the fire sound persisting and I'm not sure why,
                    // so this is basically a randomly thrown out preventative measure.
                    ((RestRoom) AbstractDungeon.getCurrRoom()).cutFireSound();
                }
            }

        }
    }

    private void ChaoThing(){
        AbstractDungeon.player.loseGold(HedgehogPack.goldCostToEvolve);
        AbstractDungeon.player.loseRelic(ChaoRelic.ID);
        if (MyModConfig.enableSound) {
            CardCrawlGame.sound.play(SoundLibrary.Chao);
        }
        AbstractRelic rewardRelic = null;
        if (tag == SonicTags.LIKE_IRONCLAD) { rewardRelic = new ChaoIroncladRelic(); }
        else if (tag == SonicTags.LIKE_SILENT) { rewardRelic = new ChaoSilentRelic(); }
        else if (tag == SonicTags.LIKE_DEFECT) { rewardRelic = new ChaoDefectRelic(); }
        else if (tag == SonicTags.LIKE_WATCHER) { rewardRelic = new ChaoWatcherRelic(); }

        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(x, y, rewardRelic);
    }

    private void updateBlackScreenColor() {
        if (this.duration > 1.0F) {
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.0F) * 2.0F);
        } else {
            this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / 1.5F);
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float) Settings.WIDTH, (float) Settings.HEIGHT);
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
            AbstractDungeon.gridSelectScreen.render(sb);
        }

    }




    public void dispose() {
    }

}