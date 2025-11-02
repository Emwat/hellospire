// package hellospire.util;
//
// import com.badlogic.gdx.Gdx;
// import com.google.gson.reflect.TypeToken;
// import com.megacrit.cardcrawl.localization.LocalizedStrings;
// import com.megacrit.cardcrawl.localization.MonsterStrings;
// import com.megacrit.cardcrawl.localization.PowerStrings;
// import hellospire.SonicMod;
//
// import java.io.File;
// import java.lang.reflect.Type;
// import java.nio.charset.StandardCharsets;
// import java.util.Map;
//
// import static basemod.BaseMod.gson;
//
// public class ExtraStrings {
//
//     private static Map<String, SonicEventStrings> sonicEventStrings;
//
//     public ExtraStrings(){
//         String langPackDir = "localization" + File.separator + "eng";
//
//         String path = langPackDir + File.separator + "SonicEventStrings.json";
//         Type type = (new TypeToken<Map<String, SonicEventStrings>>() {
//         }).getType();
//         sonicEventStrings = (Map)gson.fromJson(loadJson(path), type);
//     }
//
//     public SonicEventStrings getSonicEventStrings(String sonicEventName) {
//         if (sonicEventStrings.containsKey(sonicEventName)) {
//             return (SonicEventStrings)sonicEventStrings.get(sonicEventName);
//         } else {
//             SonicMod.logger.info("[ERROR] SonicEventString: " + sonicEventName + " not found");
//             return SonicEventStrings.getMockSonicEventString();
//         }
//     }
//
//     private static String loadJson(String jsonPath) {
//         return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
//     }
//
// }
//
