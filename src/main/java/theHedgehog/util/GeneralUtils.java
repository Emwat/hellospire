package theHedgehog.util;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.stances.WrathStance;

import java.lang.reflect.Field;

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
                if (splitStr[i].equals("NL")) {
                    output.append(splitStr[i]);
                } else {
                    output.append(prepend).append(splitStr[i]);
                }
            } else {
                if (splitStr[i].equals("NL")) {
                    output.append(" ").append(splitStr[i]);
                } else {
                    output.append(" ").append(prepend).append(splitStr[i]);
                }
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

    /// tests if a string is a color
    /// expected parameter is f3acd2; no brackets or hashtags
    public static boolean isNumeric(String cadena) {
        if (cadena.length() == 0 ||
                (cadena.charAt(0) != '-' && Character.digit(cadena.charAt(0), 16) == -1))
            return false;
        if (cadena.length() == 1 && cadena.charAt(0) == '-')
            return false;

        for (int i = 1; i < cadena.length(); i++)
            if (Character.digit(cadena.charAt(i), 16) == -1)
                return false;
        return true;
    }

    /// expected parameter is something like [#f3acd2]
    public static Color getColorOrDefault(String word) {
        // [#f3acd2]
        if (word.length() >= 9 && word.startsWith("[#") && word.charAt(8) == ']') {
            String hex = word.substring(2, 8);
            if (isNumeric(hex)) {
                return Color.valueOf(word.substring(1, 9));
            }
        }

        // [f3acd2ff]
        if (word.length() >= 10 && word.charAt(0) == '[' && word.charAt(9) == ']') {
            // Need to be careful of words like [Continue]
            if (isNumeric(word.substring(1, 9)))
                return Color.valueOf(word.substring(1, 9));
        }

        return null;
    }

    public static boolean doesObjectContainField(Object object, String fieldName) {
        Class<?> objectClass = object.getClass();
        for (Field field : objectClass.getFields()) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    // I'm not sure why this doesn't work. I use Flex (2) + Horsekick (14) and its damage blew up to 60
    public static float getVigorAndMoreAmount(int baseDamage){
        float vigorAndMore = 0;
        for (AbstractPower power : AbstractDungeon.player.powers) {
            vigorAndMore += power.atDamageGive(baseDamage, DamageInfo.DamageType.NORMAL);
            vigorAndMore -= baseDamage;
        }
        return vigorAndMore;
    }

    public static float getBlockAndMoreAmount(int blockAmount){
        float blockAndMore = 0;
        for (AbstractPower power : AbstractDungeon.player.powers) {
            blockAndMore += power.modifyBlock(blockAmount);
            blockAndMore -= blockAmount;
        }
        return blockAndMore;
    }

    // Developed to take advantage of Strength, Vigor, and Rocket Accel
    public static int getVigorAndMoreAmount2(int baseDamage){
        int vigorAndMore = 0;
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power.atDamageGive(baseDamage, DamageInfo.DamageType.NORMAL) == baseDamage + power.amount) {
                vigorAndMore += power.amount;
            } else if (GeneralUtils.doesObjectContainField(power, "amount2")) {
                try {
                    Class<?> someClass = power.getClass();
                    Field field = someClass.getField("amount2");
                    int value = (int)field.get(power);
                    if (power.atDamageGive(baseDamage, DamageInfo.DamageType.NORMAL) >= baseDamage + value){
                        vigorAndMore += value;
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {

                }
            }
        }
        return vigorAndMore;
    }

    public static int getBlockAndMoreAmount2(int blockAmount){
        int blockAndMore = 0;
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power.modifyBlock(blockAmount) == blockAmount + power.amount) {
                blockAndMore += power.amount;
            } else if (GeneralUtils.doesObjectContainField(power, "amount2")) {
                try {
                    Class<?> someClass = power.getClass();
                    Field field = someClass.getField("amount2");
                    int value = (int)field.get(power);
                    if (power.modifyBlock(blockAmount) >= blockAmount + value){
                        blockAndMore += value;
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {

                }
            }
        }
        return blockAndMore;
    }


}
