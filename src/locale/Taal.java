package locale;

import java.util.ResourceBundle;

/**
 *
 * @author Stephan
 */
public class Taal
{

    private static ResourceBundle lang;

    /**
     * Method which adjusts the language, to present the game in the correct language
     * @param taalBundel
     */
    public static void stelTaalIn(String taalBundel)
    {
        lang = ResourceBundle.getBundle(taalBundel);
    }

    /**
     * Method which adjusts the language, to present the game in the correct language
     * @param sleutel
     * @return the strings in the correct language
     */
    public static String vertaal(String sleutel)
    {
        return lang.getString(sleutel);
    }
}
