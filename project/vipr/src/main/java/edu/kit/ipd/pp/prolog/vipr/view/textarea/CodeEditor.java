package edu.kit.ipd.pp.prolog.vipr.view.textarea;

import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/**
 * Diese Klasse stellt den Code-Editor dar. Sie erweitert die Funktionen der
 * JTextArea. In dem Code-Editor kann der Nutzer Prolog-Dateien bearbeiten.
 *
 */
@SuppressWarnings("serial")
public class CodeEditor extends JTextArea {

    private static final String SEPERATOR = System.getProperty("line.separator");

    /**
     * Highlighter, der verwendet wird, um die bei der Berechnung betrachtete Regel
     * zu markieren.
     */
    private Highlighter yellowPainter;

    /**
     * Diese Variable speichert, ob der Code seit dem letzten Speichern verändert
     * wurde.
     */
    private boolean textUpToDate;
    
    /**
     * Schrift für den Normale Benutzung
     */
    private Font standardFont;

    /**
     * Schrift für den Beamer Modus
     */
    private Font beamerFont;
    /**
     * Konstruktor des Code-Editors.
     */
    public CodeEditor(int beamerFontSize) {
        super();
        setName("codeEditor");
        setBounds(0, 0, 600, 500);
        yellowPainter = new DefaultHighlighter();
        yellowPainter.install(this);
        standardFont = this.getFont();
        beamerFont = new Font(standardFont.getFontName(), standardFont.getStyle(), standardFont.getSize() + beamerFontSize);
    }

    /**
     * Löscht den Inhalt des Code-Editors. Wird aufgerufen, wenn der Nutzer eine
     * neue Prolog-Datei öffnet.
     */
    public void clearContent() {
        setText(null);
    }

    /**
     * Zeigt das Beispielprogramm im CodeEditor.
     */
    public void showExample() {
        this.setText("mann(grampa)." + SEPERATOR + "mann(homer)." + SEPERATOR + "mann(bart)." + SEPERATOR
                + "frau(marge)." + SEPERATOR + "frau(lisa)." + SEPERATOR + "frau(maggie)." + SEPERATOR
                + "hund(knechtruprecht)." + SEPERATOR + "katze(snowball)." + SEPERATOR + SEPERATOR
                + "vater(grampa, homer)." + SEPERATOR + "vater(homer, bart)." + SEPERATOR + "vater(homer, lisa)."
                + SEPERATOR + "vater(homer, maggie)." + SEPERATOR + "mutter(marge, bart)." + SEPERATOR
                + "mutter(marge, lisa)." + SEPERATOR + "mutter(marge, maggie)." + SEPERATOR + SEPERATOR
                + "eltern(X,Y):-" + SEPERATOR + "     vater(X,Y)." + SEPERATOR + "eltern(X,Y):-" + SEPERATOR
                + "     mutter(X,Y)." + SEPERATOR + SEPERATOR + "großeltern(X,Y):-" + SEPERATOR + "     eltern(X,Z),"
                + SEPERATOR + "     eltern(Z,Y).");
    }

    /**
     * Bei Änderungen im Quellcode im CodeEditor wird die Variable textUpToDate auf
     * false gesetzt. Wird der Code geparsed, wird die Variable textUpToDate auf
     * true gesetzt.
     * 
     * @param b
     *            Der Wert, auf den textUpToDate gesetzt werden soll.
     */
    public void textUpToDate(boolean b) {
        this.textUpToDate = b;
    }

    /**
     * Gibt zurück, ob der Quellcode im CodeEditor seit dem letzten Parsen nicht
     * geändert wurde.
     * 
     * @return true, wenn der Quellcode in CodeEditor seit dem letzten Parsen nicht
     *         geändert wurde, false sonst
     */
    public boolean isTextUpToDate() {
        return this.textUpToDate;
    }

    @Override
    public Highlighter getHighlighter() {
        return this.yellowPainter;
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
