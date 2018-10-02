package edu.kit.ipd.pp.prolog.vipr.controller.menulistener;

import java.awt.event.ActionEvent;

import javax.swing.text.Highlighter;

import com.mxgraph.view.mxGraph;

import edu.kit.ipd.pp.prolog.vipr.controller.AbstractListener;
import edu.kit.ipd.pp.prolog.vipr.controller.Calculation;
import edu.kit.ipd.pp.prolog.vipr.controller.CalculationManager;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Interpreter;
import edu.kit.ipd.pp.prolog.vipr.view.FileManager;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.ToolBar;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.GraphicPane;
import edu.kit.ipd.pp.prolog.vipr.view.textarea.CodeEditor;

/**
 * Dieser Listener ermöglicht das Erstellen eines neuen Prolog-Programms. Zeigt
 * ein leeres Codefeld an und setzt den gespeicherten Dateipfad zurück.
 */
public class MenuNewListener implements AbstractListener {

    /**
     * Der Interpreter. Wird zur Abarbeitung einer Anfrage auf ein Prolog-Programm
     * verwendet.
     */
    private Interpreter interpreter;

    /**
     * Das Codefeld der grafischen Benutzeroberfläche. Wird zum Eingeben eines
     * Prolog-Programms verwendet.
     */
    private CodeEditor codeEditor;

    /**
     * Das Grafikfeld der grafischen Benutzeroberfläche. Wird zum Anzeigen der
     * Grafik verwendet.
     */
    private GraphicPane graphicPane;

    /**
     * Die Toolbar der grafischen Benutzeroberfläche. Wird zum Aktualisieren der
     * Labels verwendet.
     */
    private ToolBar toolBar;

    /**
     * Die aktuelle Berechnung.
     */
    private Calculation calculation;

    /**
     * Initialisiert den Listener mit allen nötigen Parametern.
     * 
     * @param ip
     *            Der Interpreter. Wird zur Abarbeitung einer Anfrage auf ein
     *            Prolog-Programm verwendet.
     * @param ce
     *            Das Codefeld der grafischen Benutzeroberfläche. Wird zum Eingeben
     *            eines Prolog-Programms verwendet.
     * @param gp
     *            Das Grafikfeld der grafischen Benutzeroberfläche. Wird zum
     *            Anzeigen der Grafik verwendet.
     * @param tb
     *            Die Toolbar der grafischen Benutzeroberfläche. Wird zum
     *            Aktualisieren der Labels verwendet.
     */
    public MenuNewListener(Interpreter ip, CodeEditor ce, GraphicPane gp, ToolBar tb) {
        this.interpreter = ip;
        this.codeEditor = ce;
        this.graphicPane = gp;
        this.toolBar = tb;
        CalculationManager.getInstance().addListener(this);
    }

    /**
     * Setzt alles zurück. Leert das Codefeld.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // falls eine Berechnung läuft, diese abbrechen
        if (calculation != null) {
            calculation.setSkipDone();
            calculation.setPause();
            while (!calculation.isDone()) {
            }
        }
        // Dateipfad löschen
        FileManager fm = FileManager.getInstance();
        fm.resetPath();

        // Grafik löschen
        graphicPane.setGraph(new mxGraph());
        graphicPane.updateUI();

        // Highlights löschen
        Highlighter highlighter = codeEditor.getHighlighter();
        highlighter.removeAllHighlights();

        // Rest zurücksetzen
        interpreter.setNewProgram(null);

        // Codefeld leeren
        codeEditor.clearContent();

        toolBar.buttonsAfterParse();
    }

    @Override
    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }

}
