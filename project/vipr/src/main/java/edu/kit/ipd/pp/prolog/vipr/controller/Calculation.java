package edu.kit.ipd.pp.prolog.vipr.controller;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import com.mxgraph.view.mxGraph;

import edu.kit.ipd.pp.prolog.vipr.model.ast.CalculateException;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NoRuleException;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Interpreter;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.ToolBar;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.popups.ErrorPopUp;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.GraphTree;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.GraphicPane;
import edu.kit.ipd.pp.prolog.vipr.view.textarea.CodeEditor;
import edu.kit.ipd.pp.prolog.vipr.view.textarea.InOut;

/**
 * Stellt eine Berechnung dar.
 *
 */
public class Calculation extends SwingWorker<String, Object> {

    /**
     * Error Nachricht Konstanten
     */
    private static final String GRAPH_DIS_ERR = "Graph display error";
    private static final String GRAPH_TOO_LARGE_ERR = "The graph is too large to be displayed";

    /**
     * Die Einstellung dieser Berechnung. Wird für das Abbrechen einer Berechnung
     * verwendet.
     */
    private CalculationType cType;

    /**
     * Der Interpreter. Wird zur Abarbeitung einer Anfrage auf einem Prolog-Programm
     * verwendet.
     */
    private Interpreter interpreter;

    /**
     * Das Codefeld der grafischen Benutzeroberfläche. Wird zum Eingeben eines
     * Prolog-Programms verwendet.
     */
    private CodeEditor codeEditor;

    /**
     * Das Anzeigefeld der grafischen Benutzeroberfläche. Wird zum Anzeigen der
     * Grafik verwendet.
     */
    private GraphicPane graphicPane;

    /**
     * Die Toolbar der grafischen Benutzeroberfläche.
     */
    private ToolBar toolBar;

    /**
     * Das Ausgabefeld der grafischen Benutzeroberfläche. Wird für Anfragen und
     * Lösungen verwendet.
     */
    private InOut inOut;

    /**
     * Wird zur Erkennung einer zurückzusetzenden Oberfäche verwendet.
     */
    private boolean mustReset;

    /**
     * Wird verwendet, um gegebenenfalls in done() nichts zu tun.
     */
    private boolean skipDone;

    /**
     * Wird verwendet, um diese Berechnung anzuhalten.
     */
    private boolean pause;

    /**
     * Wird zur Erkennung einer zurückzusetzenden Grafik verwendet.
     */
    private boolean resetGraph;

    /**
     * Die GUIMain. Wird verwendet, um den Mauscursor ändern zu können.
     */
    private GUIMain guiMain;

    /**
     * Der Zustand des letzten Berechnungsschrittes. Wird für die "Schritt-Zurück"
     * Funktion verwendet.
     */
    private Memento memento;

    /**
     * Initialisiert die Berechnung mit allen nötigen Parametern.
     * 
     * @param cType
     *            Die Einstellung der Berechnung. Wird für das Abbrechen einer
     *            Berechnung verwendet.
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
     *            Das Ausgabefeld der grafischen Benutzeroberfläche. Wird für
     *            Anfragen und Lösungen verwendet.
     * @param memento
     *            Der Zustand des letzten Berechnungsschrittes. Wird für die
     *            "Schritt-Zurück" Funktion verwendet.
     * @param guiMain
     *            Die grafische Oberfläche.
     */
    public Calculation(CalculationType cType, Interpreter ip, CodeEditor ce, GraphicPane gp, ToolBar tb, InOut io,
            Memento memento, GUIMain guiMain) {
        this.cType = cType;
        this.interpreter = ip;
        this.codeEditor = ce;
        this.graphicPane = gp;
        this.toolBar = tb;
        this.inOut = io;
        this.memento = memento;
        this.mustReset = false;
        this.skipDone = false;
        this.pause = false;
        this.guiMain = guiMain;
    }

    /**
     * Falls eine Berechnung fehlschlägt wird die grafische Oberfläche
     * zurückgesetzt.
     */
    private void reset() {
        // Berechnung fehlgeschlagen

        if (resetGraph) {
            // auf Zustand nach dem Parsen setzen
            interpreter.setNewProgram(interpreter.getProgram());

            // die Grafik zurücksetzen
            graphicPane.setGraph(new mxGraph());
        } else {
            try {
                // neue Grafik erstellen
                GraphTree graphTree = new GraphTree(
                        interpreter.getRoot().createVisNode(toolBar.getBoxSubs().isSelected()));
                // die Grafik anzeigen
                graphicPane.setGraph(graphTree);
            } catch (StackOverflowError e) {
                new ErrorPopUp(GRAPH_DIS_ERR, GRAPH_TOO_LARGE_ERR);
                resetGraph = true;
                reset();
                return;
            }
        }
        graphicPane.updateUI();

        // Highlights löschen
        Highlighter highlighter = codeEditor.getHighlighter();
        highlighter.removeAllHighlights();

        // die Knöpfe aktivieren / deaktivieren
        toolBar.buttonsAfterError();
    }

    /**
     * Berechnet eine Anfrage auf einem Prolog-Programm bis eine Abbruchbedingung in
     * Kraft tritt.
     * 
     * @return Null.
     * @throws Exception
     *             Falls der Thread unterbrochen wird.
     */
    @Override
    protected String doInBackground() throws Exception {
        guiMain.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        do {
            // führe nächsten Schritt aus
            if (memento.getWentBack()) {
                memento.setWentBack(false);
            } else {
                try {
                    // Speichert im Memento die Visualisierungs-Wurzelknoten.
                    memento.setRootVisNode(interpreter.getRoot());
                    interpreter.nextStep();
                } catch (CalculateException e1) {
                    new ErrorPopUp(CalculateException.getTitle(), e1.getMessage());

                    mustReset = true;
                    resetGraph = false;
                    return null;
                } catch (NoRuleException e1) {
                    new ErrorPopUp(NoRuleException.getTitle(), e1.getMessage());

                    mustReset = true;
                    resetGraph = false;
                    return null;
                } catch (StackOverflowError e1) {
                    new ErrorPopUp(GRAPH_DIS_ERR, GRAPH_TOO_LARGE_ERR);

                    mustReset = true;
                    resetGraph = true;
                    return null;
                }

                if (interpreter.getFoundOutput()) {
                    String output = interpreter.getOutput();
                    inOut.printOutput(output, Color.BLACK);
                }
            }

            // Abbruchbedingung
            switch (cType) {
            case NEXT_STEP:
                pause = true;
                break;

            case NEXT_SOLUTION:
                if (interpreter.getFoundOutput()) {
                    pause = true;
                }
                break;

            case ALL_SOLUTIONS:
                if (interpreter.getInquiryFailed()) {
                    pause = true;
                }
                break;
            default:
            }

        } while (!pause);

        return null;
    }

    /**
     * Aktualisiert die grafische Benutzeroberfläche dem Endzustand der Berechnung
     * entsprechend. Wird nach jeder Berechnung (doInBackground) ausgeführt.
     */
    @Override
    protected void done() {
        if (!skipDone) {
            if (!mustReset) {
                try {
                    // neue Grafik erstellen
                    GraphTree graphTree = new GraphTree(
                            interpreter.getRoot().createVisNode(toolBar.getBoxSubs().isSelected()));

                    // die Grafik anzeigen
                    graphicPane.setGraph(graphTree);
                    graphicPane.updateUI();

                    // die Regel highlighten
                    codeEditor.getHighlighter().removeAllHighlights();
                    if (codeEditor.isTextUpToDate()) { // nur wenn der Text nicht verändert wurde.
                        setHighlighter();
                    }

                    // Knöpfe aktivieren / deaktivieren
                    if (interpreter.getInquiryFailed()) {
                        toolBar.buttonsAfterNo();
                    } else {
                        toolBar.buttonsAfterStep();
                    }
                } catch (StackOverflowError e) {
                    new ErrorPopUp(GRAPH_DIS_ERR, GRAPH_TOO_LARGE_ERR);
                    resetGraph = true;
                    reset();
                }
            } else {
                reset();
            }
        }
        guiMain.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * Setzt skipDone auf true.
     */
    public void setSkipDone() {
        skipDone = true;
    }

    /**
     * Setzt pause auf true.
     */
    public void setPause() {
        pause = true;
    }

    /**
     * Versucht, den Highlighter neu zu setzen.
     */
    private void setHighlighter() {
        HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
        try {
            int[] pos = interpreter.getPosHighlight();
            codeEditor.getHighlighter().addHighlight(pos[0], pos[1], painter);
        } catch (BadLocationException e1) {
            codeEditor.getHighlighter().removeAllHighlights();
        }
    }

}
