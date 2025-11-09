package theHedgehog.util;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.orbs.Lightning;
import theHedgehog.SonicMod;
import theHedgehog.powers.LevelUpFlightPower;

import java.util.ArrayList;

public class OrbSimulation {

    ArrayList<AbstractOrb> Orbs;
    public int evokeDamage = 0;
    public int incomingRings = 0;
    public int incomingFocus = 0;

    public void Initialize(int incomingRings) {
        Orbs = new ArrayList<>();
        Orbs.addAll(AbstractDungeon.player.orbs);
        if (incomingRings > 0 && AbstractDungeon.player.hasPower(LevelUpFlightPower.POWER_ID)) {
            incomingFocus = incomingRings * AbstractDungeon.player.getPower(LevelUpFlightPower.POWER_ID).amount;
        }
    }

    public void Channel(AbstractOrb orb, boolean isIncoming) {
        LogHelper();
        boolean hasEmptyOrb = Orbs.get(Orbs.size() - 1) instanceof EmptyOrbSlot;

        if (hasEmptyOrb) {
            for (int i = Orbs.size() - 1; i >= 0; i--) {
                AbstractOrb iOrb = Orbs.get(i);
                if (iOrb instanceof EmptyOrbSlot) {
                    Orbs.set(i, orb);
                    break;
                }
            }
        } else {
            for (int i = 0; i < Orbs.size(); i++) {
                AbstractOrb iOrb = Orbs.get(i);
                if (i == 0) {
                    if (iOrb instanceof Dark) {
                        evokeDamage += iOrb.passiveAmount + (isIncoming ? incomingFocus : 0);
                    }
                    if (iOrb instanceof Lightning || iOrb instanceof Dark) {
                        evokeDamage += iOrb.evokeAmount + (isIncoming ? incomingFocus : 0);
                    }
                }
                if (i + 1 < Orbs.size()) {
                    Orbs.set(i, Orbs.get(i + 1));
                } else {
                    Orbs.set(i, orb);
                }
            }
        }
        LogHelper();

    }

    public int GetPassiveLightningDamage() {
        int output = 0;
        if (AbstractDungeon.player.maxOrbs > 0 ){
            for (AbstractOrb orb : Orbs) {
                if (orb instanceof Lightning) {
                    output += orb.passiveAmount + incomingFocus;
                }
            }
        }
        return output;
    }

    private void LogHelper(){
        SonicMod.logger.info("--- ORB LOG START ---");
        for (AbstractOrb orb : Orbs){
            SonicMod.logger.info(orb);
        }
        SonicMod.logger.info("--- ORB LOG END ---");

    }
}
