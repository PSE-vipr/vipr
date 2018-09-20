package edu.kit.ipd.pp.prolog.vipr.controller.menulistener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Highlighter;

import edu.kit.ipd.pp.prolog.vipr.view.functionality.ToolBar;
import edu.kit.ipd.pp.prolog.vipr.view.textarea.CodeEditor;

/**
 * Dieser Listener ermöglicht das Reagieren auf Veränderungen im Codefeld.
 * Veränderungen ermöglichen erneutes Parsen und deaktiviert das
 * Codehighlighting.
 */
public class TextUpdateListener implements DocumentListener {

    /**
     * Das Codefeld der grafischen Benutzeroberfläche. Wird zum Eingeben eines
     * Prolog-Programms verwendet.
     */
    private CodeEditor codeEditor;

    /**
     * Die Toolbar der grafischen Benutzeroberfläche. Wird zum Aktualisieren der
     * Labels verwendet.
     */
    private ToolBar toolBar;

    /**
     * Initialisiert den Listener mit allen nötigen Parametern.
     * 
     * @param ce
     *            Das Codefeld der grafischen Benutzeroberfläche. Wird zum Eingeben
     *            eines Prolog-Programms verwendet.
     * @param tb
     *            Die Toolbar der grafischen Benutzeroberfläche. Wird zum
     *            Aktualisieren der Labels verwendet.
     */
    public TextUpdateListener(CodeEditor ce, ToolBar tb) {
        this.codeEditor = ce;
        this.toolBar = tb;
    }

    /**
     * Bei Veränderungen im Codefeld wird das Parsen ermöglicht und eine
     * Hilfsvariable gesetzt.
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        textChanged();
    }

    /**
     * Bei Veränderungen im Codefeld wird das Parsen ermöglicht und eine
     * Hilfsvariable gesetzt.
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        textChanged();

    }

    /**
     * Bei Veränderungen im Codefeld wird das Parsen ermöglicht und eine
     * Hilfsvariable gesetzt.
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        textChanged();
    }

    private void textChanged() {
        toolBar.buttonsAfterText();
        codeEditor.textUpToDate(false);
        // Highlights löschen
        Highlighter highlighter = codeEditor.getHighlighter();
        highlighter.removeAllHighlights();
    }

}
