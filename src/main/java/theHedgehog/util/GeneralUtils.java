package theHedgehog.util;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class GeneralUtils {
    public static String arrToString(Object[] arr) {
        if (arr == null)
            return null;
        if (arr.length == 0)
            return "";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length - 1; ++i) {
            sb.append(arr[i]).append(", ");
        }
        sb.append(arr[arr.length - 1]);
        return sb.toString();
    }

    public static String removePrefix(String ID) {
        return ID.substring(ID.indexOf(":") + 1);
    }

    public static String ColorWord(String prepend, String str) {
        String[] splitStr = str.split("\\s+");
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < splitStr.length; i++) {
            if (i == 0) {
                output.append(prepend).append(splitStr[i]);
            } else {
                output.append(" ").append(prepend).append(splitStr[i]);
            }
        }
        return output.toString();
    }

    public static boolean isCardBottled(AbstractCard wantedCard) {
        return wantedCard.inBottleFlame || wantedCard.inBottleLightning || wantedCard.inBottleTornado;
    }

    public static boolean isIndeedWithoutADoubtInCombat() {
        return (AbstractDungeon.player != null && AbstractDungeon.currMapNode != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT);
    }

    public static Color IdentifyWordColor(String word) {
        // All Colors
        // HashMap<String, Color> COLOR_MAP = new HashMap<>();
        // COLOR_MAP.put("r", Settings.RED_TEXT_COLOR.cpy());
        // COLOR_MAP.put("g", Settings.GREEN_TEXT_COLOR.cpy());
        // COLOR_MAP.put("b", Settings.BLUE_TEXT_COLOR.cpy());
        // COLOR_MAP.put("p", Settings.PURPLE_COLOR.cpy());
        // COLOR_MAP.put("y", Settings.GOLD_COLOR.cpy());
        //
        // if (word.length() > 2 && word.startsWith("#")) {
        //     String key = String.valueOf(word.charAt(1));
        //     if (COLOR_MAP.containsKey(key)) {
        //         return COLOR_MAP.get(key);
        //     }
        // }

        // [] based colour keys
        if (word.length() > 10 && word.startsWith("[")) {
            return Color.valueOf(word.substring(1,10));
        }

        return null;
    }

    public static String CapitalizeFirstLetter(String s){
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
