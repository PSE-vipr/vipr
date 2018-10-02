package edu.kit.ipd.pp.prolog.vipr.controller;

import java.util.prefs.Preferences;

import edu.kit.ipd.pp.prolog.vipr.view.Language;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.Languages;

/**
 * Diese Klasse ermöglicht das Speichern von Einstellungen über den Neustart von
 * VIPR hinaus.
 */
public class PreferenceSettings {

    /**
     * Der Name des Bundles.
     */
    private static final String BUNDLE_NAME = "language";

    /**
     * Die Einstellung für die Schriftgröße.
     */
    private static final String LARGE_FONT = "useLargerFont";

    /**
     * Die Einstellung zum Anzeigen des Hilfe-Fensters.
     */
    private static final String SHOW_HELP = "showHelpDialog";

    /**
     * Speichert die Einstellungen.
     */
    private Preferences prefs;

    /**
     * true, wenn die große Schrift benutzt werden soll, false sonst
     */
    private boolean largeFont;

    /**
     * true, wenn das Hilfe-Fenster angezeigt werden muss, false sonst
     */
    private boolean showHelp;

    /**
     * Ruft die Einstellungen auf oder vergibt beim ersten Mal Standard Werte.
     */
    public PreferenceSettings() {
        prefs = Preferences.userRoot().node(this.getClass().getName());
        largeFont = prefs.getBoolean(LARGE_FONT, false);
        showHelp = prefs.getBoolean(SHOW_HELP, true);
        if (showHelp) {
            prefs.putBoolean(SHOW_HELP, false);
        }
        Language language = Language.getInstance();
        String chosenL = prefs.get(BUNDLE_NAME, "DEUTSCH");
        if (chosenL.equals("DEUTSCH")) {
            language.setCurrentLanguage(Languages.DEUTSCH);

        } else if (chosenL.equals("ENGLISH")) {
            language.setCurrentLanguage(Languages.ENGLISH);
        }
    }

    /**
     * Ändert die Spracheinstellung.
     * 
     * @param language
     *            Die neue Sprache.
     */
    public void changeLanguage(String language) {
        prefs.put(BUNDLE_NAME, language);
    }

    /**
     * Ändert die Schrifteinstellung.
     */
    public void changeFont() {
        if (largeFont) {
            prefs.putBoolean(LARGE_FONT, false);
            largeFont = false;
        } else {
            prefs.putBoolean(LARGE_FONT, true);
            largeFont = true;
        }
    }

    /**
     * Gibt zurück, ob die große Schrift benutzt werden soll.
     * 
     * @return true, wenn die große Schrift benutzt werden soll, false sonst
     */
    public boolean getLargeFont() {
        return largeFont;
    }

    /**
     * Gibt zurück, ob das Hilfe-Fenster angezeigt werden muss.
     * 
     * @return true, wenn das Hilfe-Fenster angezeigt werden muss, false sonst
     */
    public boolean getShowHelp() {
        return showHelp;
    }

}
