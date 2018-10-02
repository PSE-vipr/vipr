package edu.kit.ipd.pp.prolog.vipr.view.functionality.popups;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.kit.ipd.pp.prolog.vipr.view.Language;

/**
 * Klasse mit Informationen zu VIPR und Prolog.
 *
 */
@SuppressWarnings("serial")
public class HelpPopUp extends JPanel {

    /**
     * Schrift Konstante
     */
    private static final String FONT_ARIAL = "Arial";

    /**
     * Konstruktor der Klasse. Öffnet ein neues Fenster, in dem Erklärungen zur
     * Bedienung und Funktionsweise von VIPR sowie Informationen zu Prolog stehen.
     * 
     */
    public HelpPopUp() {
        super();
        Language language = Language.getInstance();
        setLayout(new GridLayout(0, 1));
        JLabel menuHelp = new JLabel(language.getString("Help.titleMenu") + language.getString("Help.open")
                + language.getString("Help.new") + language.getString("Help.save") + language.getString("Help.parse")
                + language.getString("Help.query"));
        menuHelp.setFont(new Font(FONT_ARIAL, Font.PLAIN, 12));
        add(menuHelp);
        JLabel toolBarHelp = new JLabel(language.getString("Help.titleToolBar") + "\u25b6      "
                + language.getString("Help.step") + "\u25c0      " + language.getString("Help.back") + "\u25b6\u275a   "
                + language.getString("Help.next") + "\u25b6\u25b6\u275a   " + language.getString("Help.all")
                + "\u275a\u275a   " + language.getString("Help.pause"));
        toolBarHelp.setFont(new Font(FONT_ARIAL, Font.PLAIN, 12));
        add(toolBarHelp);
        JLabel graphicHelp = new JLabel(language.getString("Help.titleGraphic") + language.getString("Help.move")
                + language.getString("Help.zoom") + language.getString("Help.png")
                + language.getString("Help.learnMore") + language.getString("Help.web"));
        graphicHelp.setFont(new Font(FONT_ARIAL, Font.PLAIN, 12));
        add(graphicHelp);
    }

}
