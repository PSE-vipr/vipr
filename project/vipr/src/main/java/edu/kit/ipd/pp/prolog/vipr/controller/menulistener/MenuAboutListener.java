package edu.kit.ipd.pp.prolog.vipr.controller.menulistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;
import edu.kit.ipd.pp.prolog.vipr.view.Language;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.popups.AboutPopUp;

/**
 * Dieser Listener ermöglicht das Anzeigen eines Impressum-Fensters.
 */
public class MenuAboutListener implements ActionListener {

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
    public MenuAboutListener(GUIMain guiMain) {
        this.guiMain = guiMain;
    }

    /**
     * Erstellt das Fenster mit dem Text in der ausgewählten Sprache.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // Das Fenster mit Titel und Text.
        JOptionPane.showMessageDialog(this.guiMain, new AboutPopUp(), Language.getInstance().getString("About.title"),
                JOptionPane.INFORMATION_MESSAGE);
    }

}
