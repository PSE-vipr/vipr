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
 * Dieser Listener ermöglicht das Laden einer Prolog-Datei. Öffnet den
 * Dateimanager und zeigt den text der ausgewählten datei im Codefeld an.
 */
public class MenuLoadListener implements AbstractListener {

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
    public MenuLoadListener(Interpreter ip, CodeEditor ce, GraphicPane gp, ToolBar tb) {
        this.interpreter = ip;
        this.codeEditor = ce;
        this.graphicPane = gp;
        this.toolBar = tb;
        CalculationManager.getInstance().addListener(this);
    }

    /**
     * Öffnet den Dateimanager und liest den Text der ausgewählten Datei ein. Der
     * Text wird im Codefeld angezeigt und die restliche grafische
     * Benutzeroberfläche wird zurückgesetzt.
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
        FileManager fm = FileManager.getInstance();
        // Text einlesen
        String text = fm.loadFile();
        // Alles zurücksetzen und Text anzeigen
        if (text != null) {
            // Grafik löschen
            graphicPane.setGraph(new mxGraph());
            graphicPane.updateUI();

            // Highlights löschen
            Highlighter highlighter = codeEditor.getHighlighter();
            highlighter.removeAllHighlights();

            // Rest zurücksetzen
            interpreter.setNewProgram(null);

            // Text anzeigen
            codeEditor.setText(text);

            if (text.equals("")) {
                toolBar.buttonsAfterParse();
            }
            toolBar.buttonsAfterLoad();
        }
    }

    @Override
    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }

}
