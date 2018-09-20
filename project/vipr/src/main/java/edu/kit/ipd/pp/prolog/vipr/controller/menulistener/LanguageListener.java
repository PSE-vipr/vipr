package edu.kit.ipd.pp.prolog.vipr.controller.menulistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import edu.kit.ipd.pp.prolog.vipr.controller.PreferenceSettings;
import edu.kit.ipd.pp.prolog.vipr.view.Language;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.Languages;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.ToolBar;
import edu.kit.ipd.pp.prolog.vipr.view.textarea.QueryField;

/**
 * Dieser Listener ermöglicht das Auswählen einer Sprache. Nachdem eine Sprache
 * ausgewählt und bestätigt wurde, startet die Methode actionPerformed.
 * Daraufhin wird die Spracheinstellung angepasst und die Labels der grafischen
 * Benutzeroberfläche werden aktualisiert.
 */
public class LanguageListener implements ActionListener {

    /**
     * Das Drop-Down-Menü, das die verschiedenen Sprachen zur Auswahl beinhaltet.
     */
    private JComboBox<Languages> jComboBox;

    /**
     * Der Dialog auf dem sich das Drop-Down-Menü und der Bestätigungsknopf
     * befinden.
     */
    private JDialog jDialog;

    /**
     * Die Toolbar der grafischen Benutzeroberfläche. Wird zum Aktualisieren der
     * Labels verwendet.
     */
    private ToolBar toolBar;

    /**
     * Das Eingabefeld der grafischen Benutzeroberfläche. Wird zum Aktualisieren der
     * Sprache verwendet.
     */
    private QueryField queryField;

    /**
     * Die gespeicherten Einstellungen. Werden für die Spracheinstellung verwendet.
     */
    private PreferenceSettings preferenceSettings;

    /**
     * Initialisiert den Listener mit allen nötigen Parametern.
     * 
     * @param jComboBox
     *            Das Drop-Down-Menü, das die verschiedenen Sprachen zur Auswahl
     *            beinhaltet.
     * @param diag
     *            Der Dialog auf dem sich das Drop-Down-Menü und der
     *            Bestätigungsknopf befinden.
     * @param tb
     *            Die Toolbar der grafischen Benutzeroberfläche. Wird zum
     *            Aktualisieren der Labels verwendet.
     * @param qf
     *            Das Einabefeld der grafischen Benutzeroberfläche. Wird zum
     *            Aktualisieren der Sprache verwendet.
     * @param prefs
     *            Die gespeicherten Einstellungen. Werden für die Spracheinstellung
     *            verwendet.
     */
    public LanguageListener(JComboBox<Languages> jComboBox, JDialog diag, ToolBar tb, QueryField qf,
            PreferenceSettings prefs) {
        this.jComboBox = jComboBox;
        this.jDialog = diag;
        this.toolBar = tb;
        this.queryField = qf;
        this.preferenceSettings = prefs;
    }

    /**
     * Setzt die Spracheinstellung auf die ausgewählte Sprache und aktualisiert alle
     * Labels.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
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
        toolBar.updateLabels();
        queryField.updateLabels();

        // Schließt das Dialogfenster
        jDialog.dispose();
    }

}
