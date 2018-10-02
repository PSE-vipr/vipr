package edu.kit.ipd.pp.prolog.vipr.view;

import java.io.Serializable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import edu.kit.ipd.pp.prolog.vipr.view.functionality.Languages;

/**
 * Repräsentiert die Sprache der Benutzeroberfläche.
 *
 */
@SuppressWarnings("serial")
public final class Language implements Serializable {

    /**
     * Die einzige Instanz der Klasse.
     */
    private static Language language;

    /**
     * Der Name des Bundles.
     */
    private static final String BUNDLE_NAME = "language";

    /**
     * Die aktuelle Sprache.
     */
    private Languages currentLanguage;

    /**
     * Ressource, die Sprach-Daten enthält.
     */
    private ResourceBundle resourceBundle;

    /**
     * Privater Konstruktor.
     */
    private Language() {
        setCurrentLanguage(Languages.DEUTSCH);
    }

    /**
     * Gibt eine Instanz von der Sprache der Benutzeroberfläche zurück.
     * 
     * @return eine Instanz von der Sprache der Benutzeroberfläche.
     */
    public static synchronized Language getInstance() {
        if (language == null) {
            language = new Language();
            return language;
        } else {
            return language;
        }

    }

    /**
     * Gibt die aktuelle Sprache zurück.
     * 
     * @return Die aktuelle Sprache.
     */
    public Languages getCurrentLanguage() {
        return this.currentLanguage;
    }

    /**
     * Legt die aktuelle Sprache fest.
     * 
     * @param l
     *            Die neue aktuelle Sprache.
     */
    public void setCurrentLanguage(Languages l) {
        resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, l.getLocale());
        currentLanguage = l;
    }

    /**
     * Gibt den zum Schlüssel passenden String zurück.
     * 
     * @param key
     *            Der Schlüssel
     * @return Der zum Schlüssel passende String.
     */
    public String getString(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

}
