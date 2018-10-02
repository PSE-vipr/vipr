package edu.kit.ipd.pp.prolog.vipr.view.functionality.popups;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.kit.ipd.pp.prolog.vipr.view.Language;

/**
 * Diese Klasse öffnet ein PopUp-Fenster, das Informationen zu VIPR enthält.
 *
 */
@SuppressWarnings("serial")
public class AboutPopUp extends JPanel {

    /**
     * Konstruktor. Öffnet das Fenster mit Informationen.
     * 
     */
    public AboutPopUp() {
        super();
        Language language = Language.getInstance();
        setLayout(new GridLayout(0, 1));
        JLabel version = new JLabel(language.getString("About.version"));
        JLabel textAbout = new JLabel(
                Language.getInstance().getString("About.author") + Language.getInstance().getString("About.use"));
        add(version);
        add(textAbout);
    }
}
