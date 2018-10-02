package edu.kit.ipd.pp.prolog.vipr.view.functionality.popups;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.kit.ipd.pp.prolog.vipr.controller.PreferenceSettings;
import edu.kit.ipd.pp.prolog.vipr.controller.menulistener.LanguageListener;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;
import edu.kit.ipd.pp.prolog.vipr.view.Language;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.Languages;

/**
 * In dieser Klasse können in einem neuem Fenster Einstellungen vorgenommen
 * werden.
 */
@SuppressWarnings("serial")
public class Settings extends JDialog {

    /**
     * Die Check-Box zum Einstellen der Schriftgröße.
     */
    private JCheckBox fontCheckBox;

    /**
     * Konstruktor, der das Einstellungen-Fenster öffnet.
     * 
     * @param guiMain
     *            Die grafische Benutzeroberfläche.
     * @param preferenceSettings
     *            Die ausgewählten und gespeicherten Einstellungen.
     */
    public Settings(GUIMain guiMain, PreferenceSettings preferenceSettings) {

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

        // Das Drop-Down-Menü, die Schriftgrößen-Check-Box und den OK-Knopf hinzufügen
        jop.add(jcChoose);
        fontCheckBox = new JCheckBox(language.getString("Text.larger"));
        fontCheckBox.setSelected(preferenceSettings.getLargeFont());
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        jp.add(fontCheckBox, BorderLayout.LINE_START);
        jop.add(jp);

        JButton ok = new JButton("OK");
        ok.setName("OK");
        jop.add(ok);

        getContentPane().add(jop);
        pack();
        setLocationRelativeTo(guiMain);
        setResizable(false);

        // Der Listener für den OK-Knopf
        LanguageListener languageListener = new LanguageListener(jcChoose, this, preferenceSettings, guiMain);
        ok.addActionListener(languageListener);

        setVisible(true);
    }

    /**
     * Gibt zurück, ob die große Schrift ausgewählt ist.
     * 
     * @return true, wenn die große Schrift ausgewählt ist, false sonst
     */
    public boolean isLargeFontSelected() {
        return fontCheckBox.isSelected();
    }

}
