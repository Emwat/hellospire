package theHedgehog.strings;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.core.Settings;
import org.apache.logging.log4j.Logger;
import theHedgehog.SonicMod;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ModLocalizedStrings {
    private static final Logger logger = SonicMod.logger;
    private static Map<String, SonicChaoGardenStrings> sonicChaoGardens;
    private static Map<String, SonicExtraCardStrings> sonicExtraCards;
    private static Map<String, SonicTalkStrings> sonicTalks;

    public ModLocalizedStrings() {
        long startTime = System.currentTimeMillis();
        Gson gson = new Gson();
        String lang = SonicMod.getLangString();

        // English is always loaded first; the active language then overrides it key by key,
        // so a language that is missing an entry (or a whole file) falls back to English.
        sonicChaoGardens = loadWithFallback(gson, lang, "SonicChaoGardenStrings.json",
                (new TypeToken<Map<String, SonicChaoGardenStrings>>() {}).getType());
        sonicExtraCards = loadWithFallback(gson, lang, "SonicExtraCardStrings.json",
                (new TypeToken<Map<String, SonicExtraCardStrings>>() {}).getType());
        sonicTalks = loadWithFallback(gson, lang, "SonicTalkStrings.json",
                (new TypeToken<Map<String, SonicTalkStrings>>() {}).getType());

        logger.info("Loc Sonic Strings load time: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    private static <T> Map<String, T> loadWithFallback(Gson gson, String lang, String file, Type type) {
        Map<String, T> result = gson.fromJson(loadJson(SonicMod.localizationPath("eng", file)), type);
        if (result == null) {
            result = new HashMap<>();
        }
        if (!"eng".equals(lang)) {
            String path = SonicMod.localizationPath(lang, file);
            try {
                if (Gdx.files.internal(path).exists()) {
                    Map<String, T> translated = gson.fromJson(loadJson(path), type);
                    if (translated != null) {
                        result.putAll(translated);
                    }
                }
            } catch (Exception e) {
                logger.warn("Could not load " + path + ", using English.", e);
            }
        }
        return result;
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

    public static String langFolder(){
        if (Settings.language == Settings.GameLanguage.JPN) {
            return Settings.isConsoleBuild ? "jpn" : "jpn2";
        }

        return Settings.language.toString().toLowerCase();
    }
}
