package edu.kit.ipd.pp.prolog.vipr.controller.toolbarlistener;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.text.Highlighter;

import com.mxgraph.view.mxGraph;

import edu.kit.ipd.pp.prolog.vipr.controller.AbstractListener;
import edu.kit.ipd.pp.prolog.vipr.controller.Calculation;
import edu.kit.ipd.pp.prolog.vipr.controller.CalculationManager;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Program;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Interpreter;
import edu.kit.ipd.pp.prolog.vipr.model.parser.ParseException;
import edu.kit.ipd.pp.prolog.vipr.model.parser.PrologParser;
import edu.kit.ipd.pp.prolog.vipr.view.Language;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.ToolBar;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.popups.ErrorPopUp;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.GraphicPane;
import edu.kit.ipd.pp.prolog.vipr.view.textarea.CodeEditor;
import edu.kit.ipd.pp.prolog.vipr.view.textarea.InOut;

/**
 * Dieser Listener ermöglicht das Parsen des Textes im Codefeld.
 */
public class ButtonParseListener implements AbstractListener {

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
     * Die Toolbar der grafischen Benutzeroberfläche.
     */
    private ToolBar toolBar;

    /**
     * Das Ausgabefeld der grafischen Benutzeroberfläche. Wird Anfragen und Lösungen
     * verwendet.
     */
    private InOut inOut;

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
     *            Die Toolbar der grafischen Benutzeroberfläche.
     * @param io
     *            Das Ausgabefeld der grafischen Benutzeroberfläche. Wird Anfragen
     *            und Lösungen verwendet.
     */
    public ButtonParseListener(Interpreter ip, CodeEditor ce, GraphicPane gp, ToolBar tb, InOut io) {
        this.interpreter = ip;
        this.codeEditor = ce;
        this.graphicPane = gp;
        this.toolBar = tb;
        this.inOut = io;
        CalculationManager.getInstance().addListener(this);
    }

    /**
     * Liest den Text im Codefeld und erstellt mit diesem einen Parser. Wenn das
     * Parsen erfolgreich ist, wird der Interpreter entsprechend gesetzt und die
     * grafische Oberfläche angepasst. Bei einem Error wird die grafische Oberfläche
     * komplett zurückgesetzt.
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

        // den aktuellen code laden
        String code = codeEditor.getText();

        // den Interpreter aufsetzen
        try {
            // neuen Parser erstellen und Code parsen
            PrologParser parser = new PrologParser(code);
            Program program = parser.parse();

            // Interpreter zurücksetzen und neues Programm setzen
            interpreter.setNewProgram(program);

            // die Grafik resetten
            graphicPane.setGraph(new mxGraph());
            graphicPane.updateUI();

            // die highlights löschen
            Highlighter highlighter = codeEditor.getHighlighter();
            highlighter.removeAllHighlights();

            // die Knöpfe aktivieren / deaktivieren
            toolBar.buttonsAfterParse();

            // Statusmeldung
            inOut.printOutput(Language.getInstance().getString("Parse.complete"), Color.GREEN.darker());

            // Text nun aktuell
            codeEditor.textUpToDate(true);

        } catch (ParseException e1) {
            // Fehler PopUp
            new ErrorPopUp(ParseException.getTitle(), e1.getMessage());
            reset();
        }

    }

    private void reset() {
        // Parsen fehlgeschlagen
        // die Grafik resetten
        graphicPane.setGraph(new mxGraph());
        graphicPane.updateUI();

        // Highlights löschen
        Highlighter highlighter = codeEditor.getHighlighter();
        highlighter.removeAllHighlights();

        interpreter.setNewProgram(interpreter.getProgram());

        // die Knöpfe aktivieren /deaktivieren
        toolBar.buttonsAfterError();
    }

    @Override
    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }

}
