package edu.kit.ipd.pp.prolog.vipr.view.functionality.popups;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import edu.kit.ipd.pp.prolog.vipr.controller.PreferenceSettings;
import edu.kit.ipd.pp.prolog.vipr.controller.menulistener.LanguageListener;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;
import edu.kit.ipd.pp.prolog.vipr.view.Language;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.Languages;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.ToolBar;

/**
 * In dieser Klasse können in einem neuem Fenster Einstellungen vorgenommen
 * werden.
 */
@SuppressWarnings("serial")
public class Settings extends JDialog {

    /**
     * Konstruktor, der das Settings-Fenster öffnet.
     * 
     * @param toolBar
     *            der Toolbar der Benutzeroberfläche.
     * @param guiMain
     *            die grafische Benutzeroberfläche.
     * @param preferenceSettings
     *            die ausgewählte und gespeicherte Einstellungen.
     */
    public Settings(ToolBar toolBar, GUIMain guiMain, PreferenceSettings preferenceSettings) {
        super(guiMain, Language.getInstance().getString("Settings.title"), true);

        // Das Drop-Down-Menü mit den Sprachen
        Language language = Language.getInstance();
        JComboBox<Languages> jcChoose = new JComboBox<>(Languages.values());
        // Die Aktuelle Sprache als erstes anzeigen
        jcChoose.setSelectedItem(language.getCurrentLanguage());

        jcChoose.setEditable(false);

        // Die Oberfläche zum Anzeigen der verschiedenen Komponenten
        Object[] options = new Object[] {};
        JOptionPane jop = new JOptionPane(language.getString("Language.select"), JOptionPane.QUESTION_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION, null, options, null);

        // Das Drop-Down-Menü und der OK-Knopf hinzufügen
        jop.add(jcChoose);
        JButton ok = new JButton("OK");
        ok.setName("OK");
        jop.add(ok);

        getContentPane().add(jop);
        pack();
        setLocationRelativeTo(guiMain);
        setResizable(false);

        // Der Listener für den OK-Knopf
        LanguageListener languageListener = new LanguageListener(jcChoose, this, toolBar, guiMain.getQueryField(),
                preferenceSettings);
        ok.addActionListener(languageListener);

        setVisible(true);
    }
}
