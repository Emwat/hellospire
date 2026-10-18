package theHedgehog.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

import static theHedgehog.SonicMod.*;

public class TextureLoader {
    private static final HashMap<String, Texture> textures = new HashMap<>();
    // Paths that failed to load. Without this, every getTexture() of a missing file hit the
    // filesystem again and logged the same stack trace every time.
    private static final HashSet<String> failedPaths = new HashSet<>();
    // Result of the "does this card have its own art?" filesystem check, per card.
    private static final HashMap<String, String> cardTexturePaths = new HashMap<>();

    /**
     * @param filePath - String path to the texture you want to load relative to resources,
     * Example: imagePath("missing.png")
     * @return <b>com.badlogic.gdx.graphics.Texture</b> - The texture from the path provided, or a "missing image" texture if it doesn't exist.
     */
    public static Texture getTexture(final String filePath) {
        return getTexture(filePath, true);
    }

    /**
     * @param filePath - String path to the texture you want to load relative to resources,
     * Example: imagePath("missing.png")
     * @param linear - Whether the image should use a linear or nearest filter for scaling.
     * @return <b>com.badlogic.gdx.graphics.Texture</b> - The texture from the path provided, or a "missing image" texture if it doesn't exist.
     */
    public static Texture getTexture(final String filePath, boolean linear) {
        if (textures.get(filePath) == null) {
            if (!failedPaths.contains(filePath)) {
                try {
                    loadTexture(filePath, linear);
                } catch (GdxRuntimeException e) {
                    logger.info("Failed to find texture " + filePath, e);
                    failedPaths.add(filePath);
                }
            }
            if (failedPaths.contains(filePath)) {
                Texture missing = getTextureNull(imagePath("missing.png"), false);
                if (missing == null) {
                    logger.info("missing.png is missing, should be at " + imagePath("missing.png"));
                }
                return missing;
            }
        }
        Texture t = textures.get(filePath);
        if (t != null && t.getTextureObjectHandle() == 0) {
            textures.remove(filePath);
            t = getTexture(filePath, linear);
        }
        return t;
    }

    /**
     * @param filePath - String path to the texture you want to load relative to resources,
     * Example: imagePath("missing.png")
     * @return <b>com.badlogic.gdx.graphics.Texture</b> - The texture from the path provided, or null if it doesn't exist.
     */
    public static Texture getTextureNull(final String filePath) {
        return getTextureNull(filePath, true);
    }

    /**
     * @param filePath - String path to the texture you want to load relative to resources,
     * Example: imagePath("missing.png")
     * @param linear - Whether the image should use a linear or nearest filter for scaling.
     * @return <b>com.badlogic.gdx.graphics.Texture</b> - The texture from the path provided, or null if it doesn't exist.
     */
    public static Texture getTextureNull(final String filePath, boolean linear) {
        if (!textures.containsKey(filePath)) {
            try {
                loadTexture(filePath, linear);
            } catch (GdxRuntimeException e) {
                textures.put(filePath, null);
            }
        }
        Texture t = textures.get(filePath);
        if (t != null && t.getTextureObjectHandle() == 0) {
            textures.remove(filePath);
            t = getTextureNull(filePath, linear);
        }
        return t;
    }

    public static String getCardTextureString(final String cardName, final AbstractCard.CardType cardType)
    {
        String cacheKey = cardType.name() + "/" + cardName;
        String cached = cardTexturePaths.get(cacheKey);
        if (cached != null) {
            return cached;
        }

        String textureString = imagePath("cards/" + cardType.name().toLowerCase(Locale.ROOT) + "/" + cardName + ".png");

        FileHandle h = Gdx.files.internal(textureString);
        if (!h.exists())
        {
            switch (cardType) {
                case ATTACK:
                    textureString = imagePath("cards/attack/default.png");
                    break;
                case POWER:
                    textureString = imagePath("cards/power/default.png");
                    break;
                default:
                    textureString = imagePath("cards/skill/default.png");
                    break;
            }
        }

        cardTexturePaths.put(cacheKey, textureString);
        return textureString;
    }

    private static void loadTexture(final String textureString) throws GdxRuntimeException {
        loadTexture(textureString, false);
    }

    private static void loadTexture(final String textureString, boolean linearFilter) throws GdxRuntimeException {
        Texture texture = new Texture(textureString);
        if (linearFilter)
        {
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        else
        {
            texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }
        logger.info("Loaded texture " + textureString);
        textures.put(textureString, texture);
    }

    public static Texture getPowerTexture(final String powerName)
    {
        String textureString = powerPath(powerName + ".png");
        return getTexture(textureString);
    }
    public static Texture getHiDefPowerTexture(final String powerName)
    {
        String textureString = powerPath("large/" + powerName + ".png");
        return getTextureNull(textureString);
    }
}