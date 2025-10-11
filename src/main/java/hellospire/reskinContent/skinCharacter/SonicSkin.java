// package hellospire.reskinContent.skinCharacter;
//
// import com.megacrit.cardcrawl.characters.AbstractPlayer;
// import com.megacrit.cardcrawl.core.CardCrawlGame;
// import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
// import hellospire.SonicMod;
// import hellospire.character.Sonic;
// import hellospire.reskinContent.skinCharacter.skins.Sonic.SonicBase;
// import hellospire.reskinContent.vfx.ReskinUnlockedTextEffect;
//
// public class SonicSkin extends AbstractSkinCharacter {
//     public static final String ID = CardCrawlGame.languagePack.getCharacterString(Sonic.ID).NAMES[0];
//     public static final AbstractSkin[] SKINS = new AbstractSkin[]{
//             new SonicBase()
//     };
//
//     public SonicSkin() {
//         super(ID, SKINS);
//     }
//
//     @Override
//     public void checkUnlock() {
//         if (AbstractDungeon.player.chosenClass == Sonic.Meta.THE_HEDGEHOG && !this.reskinUnlock) {
//             AbstractDungeon.topLevelEffects.add(new ReskinUnlockedTextEffect(4));
//             this.reskinUnlock = true;
//         }
//     }
// }