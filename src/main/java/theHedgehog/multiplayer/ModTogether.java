package theHedgehog.multiplayer;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spireTogether.cards.CustomMultiplayerCard;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.network.objects.items.NetworkCard;
import theHedgehog.cards.Defend;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

import java.util.ArrayList;

import static theHedgehog.SonicMod.makeID;

public class ModTogether extends CustomMultiplayerCard {
    public static final String ID = makeID("DevTogether");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    public ModTogether(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<P2PPlayer> players = getPlayers(true, true);
        AbstractCard c = new Defend();
        for (P2PPlayer player : players) {
            player.addCard(NetworkCard.Generate(c), CardGroup.CardGroupType.HAND);
        }
    }
}
