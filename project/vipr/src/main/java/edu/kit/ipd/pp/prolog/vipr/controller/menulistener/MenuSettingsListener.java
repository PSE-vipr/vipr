package edu.kit.ipd.pp.prolog.vipr.controller.menulistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.kit.ipd.pp.prolog.vipr.controller.PreferenceSettings;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.ToolBar;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.popups.Settings;

/**
 * Dieser Listener öffnet ein Dialogfenster zum Auswählen der
 * Spracheinstellungen. Die verschiedenen Sprachen werden in einem
 * Drop-Down-Menü dargestellt und die aktuelle Sprache wird als erstes
 * angezeigt. Das Bestätigen durch den OK-Knopf löst den LanguageListener aus.
 */
public class MenuSettingsListener implements ActionListener {

    /**
     * Die Toolbar der grafischen Benutzeroberfläche. Wird zum aktualisieren der
     * Labels verwendet.
     */
    private ToolBar toolBar;

    /**
     * Die grafische Benutzeroberfläche. Wird zur Positionierung des Fensters
     * benötigt.
     */
    private GUIMain guiMain;

    /**
     * Die gespeicherten Einstellungen. Werden für die Spracheinstellung verwendet.
     */
    private PreferenceSettings preferenceSettings;

    /**
     * Gibt an ob die BeamerMode Checkbox aktiviert sein soll
     * Standart: nicht aktiviert
     */
    private boolean beamerModeSelected = false;
    /**
     * Initialisiert den Listener mit allen nötigen Parametern.
     * 
     * @param tb
     *            Die Toolbar der grafischen Benutzeroberfläche. Wird zum
     *            aktualisieren der Labels verwendet.
     * @param guiMain
     *            Die grafische Benutzeroberfläche. Wird zur Positionierung des
     *            Fensters benötigt.
     * @param prefs
     *            Die gespeicherten Einstellungen. Werden für die Spracheinstellung
     *            verwendet.
     */
    public MenuSettingsListener(ToolBar tb, GUIMain guiMain, PreferenceSettings prefs) {
        this.toolBar = tb;
        this.guiMain = guiMain;
        this.preferenceSettings = prefs;
    }

    /**
     * Öffnet ein Dialogfenster mit der Frage eine Sprache auszuwählen, mit einem
     * Drop-Down-Menü, in dem die verschiedenen Sprachen angezeigt werden, mit einem
     * Knopf zur Bestätigung der Auswahl.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        new Settings(toolBar, guiMain, preferenceSettings, beamerModeSelected, this);

    }
    
    /**
     * Setter
     * @param beamerModeSelected
     */
    public void setBeamerModeCheckBox(boolean beamerModeSelected) {
        this.beamerModeSelected = beamerModeSelected;
    }

}
