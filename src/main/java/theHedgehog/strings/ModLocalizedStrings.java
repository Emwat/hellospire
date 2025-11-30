package theHedgehog.strings;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.core.Settings;
import org.apache.logging.log4j.Logger;
import theHedgehog.SonicMod;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ModLocalizedStrings {
    private static final Logger logger = SonicMod.logger;
    private static Map<String, SonicChaoGardenStrings> sonicChaoGardens;
    private static Map<String, SonicExtraCardStrings> sonicExtraCards;
    private static Map<String, SonicTalkStrings> sonicTalks;

    public ModLocalizedStrings() {
        long startTime = System.currentTimeMillis();
        Gson gson = new Gson();
        String lang;
        switch (Settings.language) {
            case ENG:
                lang = "eng";
                break;
            case DUT:
                lang = "dut";
                break;
            case EPO:
                lang = "epo";
                break;
            case PTB:
                lang = "ptb";
                break;
            case ZHS:
                lang = "zhs";
                break;
            case ZHT:
                lang = "zht";
                break;
            case FIN:
                lang = "fin";
                break;
            case FRA:
                lang = "fra";
                break;
            case DEU:
                lang = "deu";
                break;
            case GRE:
                lang = "gre";
                break;
            case IND:
                lang = "ind";
                break;
            case ITA:
                lang = "ita";
                break;
            case JPN:
                if (Settings.isConsoleBuild) {
                    lang = "jpn";
                } else {
                    lang = "jpn2";
                }
                break;
            case KOR:
                lang = "kor";
                break;
            case NOR:
                lang = "nor";
                break;
            case POL:
                lang = "pol";
                break;
            case RUS:
                lang = "rus";
                break;
            case SPA:
                lang = "spa";
                break;
            case SRP:
                lang = "srp";
                break;
            case SRB:
                lang = "srb";
                break;
            case THA:
                lang = "tha";
                break;
            case TUR:
                lang = "tur";
                break;
            case UKR:
                lang = "ukr";
                break;
            case VIE:
                lang = "vie";
                break;
            case WWW:
                lang = "www";
                break;
            default:
                lang = "www";
        }

        sonicChaoGardens = gson.fromJson(
                loadJson(SonicMod.localizationPath(lang, "SonicChaoGardenStrings.json")),
                (new TypeToken<Map<String, SonicChaoGardenStrings>>() {}).getType()
        );

        sonicExtraCards = gson.fromJson(
                loadJson(SonicMod.localizationPath(lang, "SonicExtraCardStrings.json")),
                (new TypeToken<Map<String, SonicExtraCardStrings>>() {}).getType()
        );

        sonicTalks = gson.fromJson(
                loadJson(SonicMod.localizationPath(lang, "SonicTalkStrings.json")),
                (new TypeToken<Map<String, SonicTalkStrings>>() {}).getType());

        logger.info("Loc Sonic Strings load time: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    public SonicChaoGardenStrings getChaoGardenString(String name) {
        return sonicChaoGardens.getOrDefault(name, null);
    }

    public SonicExtraCardStrings getExtraCardString(String name) {
        return sonicExtraCards.getOrDefault(name, null);
    }

    public SonicTalkStrings getTalkString(String name) {
        return sonicTalks.getOrDefault(name, null);
    }

    private static String loadJson(String jsonPath) {
        return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
    }
}
