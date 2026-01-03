package theHedgehog.events;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.commons.lang3.StringUtils;
import theHedgehog.SonicMod;
import theHedgehog.strings.SonicChaoGardenStrings;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static basemod.BaseMod.gson;

public class ChaoGardenEventHelperExternal {

    private static final String ChaoGardenStringsJSON = "SonicChaoGardenStrings.json";
    private static Map<String, SonicChaoGardenStrings> sonicChaoGardens;
    private static final String[] knownIDs = new String[] { "the Ironclad", "the Silent", "the Defect", "the Watcher", "the Hedgehog", "Tails" , "Knuckles", "Amy", "Rouge"};

    public ChaoGardenEventHelperExternal() {
        sonicChaoGardens = new HashMap<>();
        loadSonicChaoGardenStrings();
    }

    public SonicChaoGardenStrings GetSonicChaoGardenString(String key) {
        return sonicChaoGardens.get(key);
    }

    public String makeID(String id) {
        AbstractPlayer p = AbstractDungeon.player;
        // if (Arrays.asList(knownIDs).contains(id)) {
        //     return null;
        // }
        //
        // if (p instanceof CustomPlayer) {
        //     try {
        //         return ReflectionHacks.getPrivate(p, CustomPlayer.class, "ID") + ":" + id;
        //     } catch (Exception ignored) {
        //
        //     }
        // }

        try {
            for (AbstractCard card : p.masterDeck.group) {
                if (card.rarity.equals(AbstractCard.CardRarity.BASIC)) {
                    return card.cardID.substring(0, card.cardID.indexOf(":") + 1) + id;
                }
            }
        } catch (Exception ignored) {

        }
        return null;
    }

    private void loadSonicChaoGardenStrings() {
        for (ModInfo modinfo : Loader.MODINFOS) {
            if (modinfo.ID.equals("BlueHedgehog")){
                continue;
            }
            Map<String, SonicChaoGardenStrings> configs = loadStringsFromJar(modinfo.jarURL, Settings.language);
            if (configs == null) configs = loadStringsFromJar(modinfo.jarURL, Settings.GameLanguage.ENG);
            if (configs == null) continue;
            SonicMod.logger.info("config find in {}", modinfo.ID);

            for (Map.Entry<String, SonicChaoGardenStrings> entry : configs.entrySet()) {
                String key = entry.getKey();
                SonicChaoGardenStrings strings = entry.getValue();
                sonicChaoGardens.put(key, strings);
            }
        }
    }

    private Map<String, SonicChaoGardenStrings> loadStringsFromJar(URL jarURL, Settings.GameLanguage lang) {
        // Log.logger.info("jarURL = {}", jarURL);
        Gson gson = new Gson();
        Type type = (new TypeToken<Map<String, SonicChaoGardenStrings>>() {
        }).getType();

        try {
            String file = jarURL + "!/" + getModdedLocalizationFilePath(ChaoGardenStringsJSON, Settings.language);
            // Log.logger.info("location = {}", file);
            URL eyeLocations = new URL("jar", "", file);
            try (InputStream in = eyeLocations.openStream()) {
                return gson.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), type);
            }
        } catch (Exception ex) {
            if (!(ex instanceof FileNotFoundException)) {
                SonicMod.logger.warn("Failed to load strings from " + jarURL, ex);
            }
        }
        return null;
    }

    private static String getModdedLocalizationFilePath(String file, Settings.GameLanguage lang) {
        String language = lang.toString().toLowerCase();
        return SonicMod.resourcesPath() + "/localization/" + language + "/" + file;
    }
}
