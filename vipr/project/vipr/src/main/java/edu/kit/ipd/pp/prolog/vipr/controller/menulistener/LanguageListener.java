package edu.kit.ipd.pp.prolog.vipr.controller.menulistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import edu.kit.ipd.pp.prolog.vipr.controller.PreferenceSettings;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;
import edu.kit.ipd.pp.prolog.vipr.view.Language;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.Languages;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.popups.Settings;

/**
 * Dieser Listener ermöglicht das Auswählen einer Sprache und einer
 * Schriftgröße.
 */
public class LanguageListener implements ActionListener {

    /**
     * Die grafische Benutzeroberfläche.
     */
    private GUIMain guiMain;

    /**
     * Das Drop-Down-Menü, das die verschiedenen Sprachen zur Auswahl beinhaltet.
     */
    private JComboBox<Languages> jComboBox;

    /**
     * Die Einstellungen.
     */
    private Settings settings;

    /**
     * Die gespeicherten Einstellungen.
     */
    private PreferenceSettings preferenceSettings;

    /**
     * Initialisiert den Listener mit allen nötigen Parametern.
     * 
     * @param jComboBox
     *            Das Drop-Down-Menü, das die verschiedenen Sprachen zur Auswahl
     *            beinhaltet.
     * @param settings
     *            Die Einstellungen.
     * @param prefs
     *            Die gespeicherten Einstellungen.
     * @param guiMain
     *            Die GUI.
     */
    public LanguageListener(JComboBox<Languages> jComboBox, Settings settings, PreferenceSettings prefs,
            GUIMain guiMain) {
        this.guiMain = guiMain;
        this.jComboBox = jComboBox;
        this.settings = settings;
        this.preferenceSettings = prefs;
    }

    /**
     * Setzt die Sprach- und die Schrifteinstellungen und aktualisiert alle Labels.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Sprache
        Language language = Language.getInstance();

        // Testet welche Sprache wurde ausgewählt wurde
        String chosenL = String.valueOf(jComboBox.getSelectedItem());

        switch (chosenL) {
        case "DEUTSCH":
            language.setCurrentLanguage(Languages.DEUTSCH);
            preferenceSettings.changeLanguage(chosenL);
            break;
        case "ENGLISH":
            language.setCurrentLanguage(Languages.ENGLISH);
            preferenceSettings.changeLanguage(chosenL);
            break;
        default:
        }

        // Aktualisiert die Labels
        guiMain.getToolBar().updateLabels();
        guiMain.getQueryField().updateLabels();

        // Aktualisiert die Schrifteinstellung
        if (settings.isLargeFontSelected() != preferenceSettings.getLargeFont()) {
            preferenceSettings.changeFont();
            guiMain.changeFont();
        }

        // Schließt das Dialogfenster
        settings.dispose();

    }

}
