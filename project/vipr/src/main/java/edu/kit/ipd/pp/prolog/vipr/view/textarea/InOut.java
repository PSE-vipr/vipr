package edu.kit.ipd.pp.prolog.vipr.view.textarea;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Diese Klasse ist das Ausgabefeld. Hier werden Lösungen sowie Hinweise
 * textuell ausgegeben.
 *
 */
@SuppressWarnings("serial")
public class InOut extends JTextPane {

    /**
     * Konstruktor des Ausgabefeldes.
     */
    public InOut() {
        super();
        setName("inOut");
        setBounds(0, 500, 600, 225);
        setEditable(false);
    }

    /**
     * Gibt eine Statusmeldung, eine Anfrage, eine gefundene Lösung oder "no."
     * textuell aus.
     * 
     * @param output
     *            Der Text der Ausgabe.
     * @param c
     *            Die Farbe vom Text.
     */
    public void printOutput(String output, Color c) {
        StyledDocument doc = getStyledDocument();
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setForeground(attributeSet, c);
        try {
            doc.insertString(doc.getLength(), output + System.getProperty("line.separator"), attributeSet);
        } catch (BadLocationException exc) {
            setCaretPosition(getDocument().getLength());
            return;
        }
        setCaretPosition(getDocument().getLength());
    }

}
