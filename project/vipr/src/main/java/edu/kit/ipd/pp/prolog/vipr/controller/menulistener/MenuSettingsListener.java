package edu.kit.ipd.pp.prolog.vipr.controller.menulistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.kit.ipd.pp.prolog.vipr.controller.PreferenceSettings;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.popups.Settings;

/**
 * Dieser Listener öffnet das Einstellungs-Fenster zum Auswählen der Sprache und
 * der Schriftgröße. Das Bestätigen durch den OK-Knopf löst den LanguageListener
 * aus.
 */
public class MenuSettingsListener implements ActionListener {

    /**
     * Die grafische Benutzeroberfläche.
     */
    private GUIMain guiMain;

    /**
     * Die gespeicherten Einstellungen.
     */
    private PreferenceSettings preferenceSettings;

    /**
     * Initialisiert den Listener mit allen nötigen Parametern.
     * 
     * @param guiMain
     *            Die grafische Benutzeroberfläche.
     * @param prefs
     *            Die gespeicherten Einstellungen.
     */
    public MenuSettingsListener(GUIMain guiMain, PreferenceSettings prefs) {
        this.guiMain = guiMain;
        this.preferenceSettings = prefs;
    }

    /**
     * Öffnet das Einstellungs-Fenster.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        new Settings(guiMain, preferenceSettings);

    }

}
