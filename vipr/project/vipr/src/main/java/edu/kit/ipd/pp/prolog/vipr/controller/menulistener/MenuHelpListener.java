package edu.kit.ipd.pp.prolog.vipr.controller.menulistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;
import edu.kit.ipd.pp.prolog.vipr.view.Language;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.popups.HelpPopUp;

/**
 * Dieser Listener ermöglicht das Anzeigen eines Hilfe-Fensters.
 */
public class MenuHelpListener implements ActionListener {

    /**
     * Die grafische Benutzeroberfläche. Wird zur Positionierung des Fensters
     * benötigt.
     */
    private GUIMain guiMain;

    /**
     * Initialisiert den Listener mit allen nötigen Parametern.
     * 
     * @param guiMain
     *            Die grafische Benutzeroberfläche. Wird zur Positionierung des
     *            Fensters benötigt.
     */
    public MenuHelpListener(GUIMain guiMain) {
        this.guiMain = guiMain;
    }

    /**
     * Öffnet das Dialog-Fenster zum Anzeigen der Hilfe.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(guiMain, setScrollPane(), Language.getInstance().getString("Help.title"),
                JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * Erstellt das Feld für den hilfreichen Kontent.
     * 
     * @return Das Feld mit Inhalt.
     */
    private JScrollPane setScrollPane() {
        JScrollPane sp = new JScrollPane(new HelpPopUp());
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.setBorder(null);
        return sp;
    }

}
