package edu.kit.ipd.pp.prolog.vipr.view.textarea;

import java.awt.Color;
import java.awt.Font;

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
     * Schrift für den Normale Benutzung
     */
    private Font standardFont;

    /**
     * Schrift für den Beamer Modus
     */
    private Font beamerFont;    

    /**
     * Konstruktor des Ausgabefeldes.
     */
    public InOut(int beamerFontSize) {
        super();
        setName("inOut");
        setBounds(0, 500, 600, 225);
        setEditable(false);
        standardFont = this.getFont();
        beamerFont = new Font(standardFont.getFontName(), standardFont.getStyle(), standardFont.getSize() + beamerFontSize);
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
    
    public void switchMode(boolean beamerMode) {
        if(beamerMode) {
        this.setFont(beamerFont);
        }
        else {
            this.setFont(standardFont);
        }
    }

}
