package edu.kit.ipd.pp.prolog.vipr.view.functionality.popups;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Klasse zum Anzeigen von Fehlermeldungen in einem PopUp-Fenster.
 *
 */
@SuppressWarnings("serial")
public class ErrorPopUp extends JFrame {

    /**
     * Konstruktor der Klasse. Öffnet ein neues PopUp-Fenster, in dem die
     * Fehlermeldung angzeigt wird.
     * 
     * @param title
     *            Der Titel des PopUp Fensters
     * @param message
     *            Die Fehlermeldung, die angezeigt werden soll
     */
    public ErrorPopUp(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

}
