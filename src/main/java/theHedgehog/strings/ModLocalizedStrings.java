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
        String lang = "eng";

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

    public static String langFolder(){
        if (Settings.language == Settings.GameLanguage.JPN) {
            return Settings.isConsoleBuild ? "jpn" : "jpn2";
        }

        return Settings.language.toString().toLowerCase();
    }
}
