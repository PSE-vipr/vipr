package edu.kit.ipd.pp.prolog.vipr.controller.menulistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import edu.kit.ipd.pp.prolog.vipr.controller.PreferenceSettings;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;
import edu.kit.ipd.pp.prolog.vipr.view.Language;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.Languages;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.ToolBar;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.popups.Settings;
import edu.kit.ipd.pp.prolog.vipr.view.textarea.QueryField;

/**
 * Dieser Listener ermöglicht das Auswählen einer Sprache. Nachdem eine Sprache
 * ausgewählt und bestätigt wurde, startet die Methode actionPerformed.
 * Daraufhin wird die Spracheinstellung angepasst und die Labels der grafischen
 * Benutzeroberfläche werden aktualisiert.
 */
public class LanguageListener implements ActionListener {
    
    /**
     * Die GUI. Wird zum ändern der Schrift für Beamermode benutzt
     */
    private GUIMain guiMain;

    /**
     * Das Drop-Down-Menü, das die verschiedenen Sprachen zur Auswahl beinhaltet.
     */
    private JComboBox<Languages> jComboBox;

    /**
     * Der Dialog auf dem sich das Drop-Down-Menü und der Bestätigungsknopf
     * befinden.
     */
    private Settings setting;

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
     *Der Settinglistener, der die Setting-pop ups erzeugt, wird benötigt damit bei erzeugten Setting-pop-ups gegenfalls
     *die Beamermoduscheckbox aktiviert ist (bzw. nicht aktiviert ist) 
     */
    private MenuSettingsListener menuSetList;
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
    public LanguageListener(JComboBox<Languages> jComboBox, Settings setting, ToolBar tb, QueryField qf,
            PreferenceSettings prefs, GUIMain guiMain, MenuSettingsListener menuSetList) {
        this.guiMain = guiMain;
        this.jComboBox = jComboBox;
        this.setting = setting;
        this.toolBar = tb;
        this.queryField = qf;
        this.preferenceSettings = prefs;
        this.menuSetList = menuSetList;
    }


    /**
     * Setzt die Spracheinstellung auf die ausgewählte Sprache und aktualisiert alle
     * Labels.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //Sprache
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
        
        //BeamerMode
        guiMain.switchMode(this.setting.isBeamerModeSelected());
        //Merkt sich die evtl Umstellung der Chackbox
        menuSetList.setBeamerModeCheckBox(this.setting.isBeamerModeSelected());

        // Schließt das Dialogfenster
        setting.dispose();
        
        
    }

}
