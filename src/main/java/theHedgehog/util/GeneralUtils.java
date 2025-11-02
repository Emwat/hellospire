package theHedgehog.util;

import com.megacrit.cardcrawl.cards.AbstractCard;

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
}
