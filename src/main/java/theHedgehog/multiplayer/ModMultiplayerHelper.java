package theHedgehog.multiplayer;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spireTogether.SpireTogetherMod;
import spireTogether.monsters.CharacterEntity;
import spireTogether.network.P2P.P2PManager;
import spireTogether.network.objects.items.NetworkCard;
import theHedgehog.MyModConfig;

public class ModMultiplayerHelper {

    public static boolean HasHelpYourBro(AbstractMonster m) {
        return MyModConfig.enableCrossModIntegrations &&
                Loader.isModLoaded("helpyourbro") &&
                Loader.isModLoaded("spireTogether") &&
                SpireTogetherMod.isConnected &&
                m instanceof CharacterEntity;
    }

    public static boolean HasSpireTogether(AbstractMonster m) {
        return MyModConfig.enableCrossModIntegrations &&
                Loader.isModLoaded("spireTogether") &&
                SpireTogetherMod.isConnected &&
                m instanceof CharacterEntity;
    }

    public static void GiveCardToTeammate(AbstractMonster m, AbstractCard card) {
        AbstractCard tmp = card.makeStatEquivalentCopy();
        tmp.costForTurn = 0;
        tmp.name = (P2PManager.GetSelf()).username + " " + tmp.type.toString();
        tmp.purgeOnUse = true;
        tmp.rawDescription += " NL Purge.";
        ((CharacterEntity) m).addCard(NetworkCard.Generate(tmp), CardGroup.CardGroupType.HAND);
    }
}
