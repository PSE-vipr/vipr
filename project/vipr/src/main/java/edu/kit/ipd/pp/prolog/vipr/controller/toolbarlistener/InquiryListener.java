package edu.kit.ipd.pp.prolog.vipr.controller.toolbarlistener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.text.Highlighter;

import com.mxgraph.view.mxGraph;

import edu.kit.ipd.pp.prolog.vipr.controller.AbstractListener;
import edu.kit.ipd.pp.prolog.vipr.controller.Calculation;
import edu.kit.ipd.pp.prolog.vipr.controller.CalculationManager;
import edu.kit.ipd.pp.prolog.vipr.controller.Memento;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Goal;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Interpreter;
import edu.kit.ipd.pp.prolog.vipr.model.parser.ParseException;
import edu.kit.ipd.pp.prolog.vipr.model.parser.PrologParser;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.ToolBar;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.popups.ErrorPopUp;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.GraphTree;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.GraphicPane;
import edu.kit.ipd.pp.prolog.vipr.view.textarea.CodeEditor;
import edu.kit.ipd.pp.prolog.vipr.view.textarea.InOut;
import edu.kit.ipd.pp.prolog.vipr.view.textarea.QueryField;

/**
 * Dieser Listener ermöglicht das Stellen einer Anfrage an das Prolog-Programm.
 */
public class InquiryListener implements KeyListener, AbstractListener {

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
     * Das Ausgabefeld der grafischen Benutzeroberfläche.
     */
    private InOut inOut;

    /**
     * Das Eingabefeld der grafischen Benutzeroberfläche.
     */
    private QueryField queryField;

    /**
     * Der Zustand des letzten Berechnungsschrittes. Wird für die "Schritt-Zurück"
     * Funktion verwendet.
     */
    private Memento memento;

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
     *            Das Ausgabefeld der grafischen Benutzeroberfläche. Wird zum
     *            Ausgabe von Lösungen verwendet.
     * @param qf
     *            Das Eingabefeld der der grafischen Benutzeroberfläche. Wird zum
     *            Eingabe von Anfragen verwendet.
     * @param memento
     *            Der Zustand des letzten Berechnungsschrittes. Wird für die
     *            "Schritt-Zurück" Funktion verwendet.
     */
    public InquiryListener(Interpreter ip, CodeEditor ce, GraphicPane gp, ToolBar tb, InOut io, QueryField qf,
            Memento memento) {
        this.interpreter = ip;
        this.codeEditor = ce;
        this.graphicPane = gp;
        this.toolBar = tb;
        this.inOut = io;
        this.queryField = qf;
        this.memento = memento;
        CalculationManager.getInstance().addListener(this);
    }

    /**
     * Liest den Text im Ein-/Ausgabefeld ein und erstellt mit diesem einen Parser.
     * Wenn das Parsen erfolgreich ist, wird der Interpreter entsprechend gesetzt
     * und die grafische Oberfläche angepasst. Falls noch kein Programm geparsed
     * wurde oder das Parsen der Anfrage fehlschlägt wird die grafische Oberfläche
     * komplett zurückgesetzt.
     */
    @Override
    public void keyPressed(KeyEvent e) {

        // warten auf taste Enter
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            // falls eine Berechnung läuft, diese abbrechen
            if (calculation != null) {
                calculation.setSkipDone();
                calculation.setPause();
                while (!calculation.isDone()) {
                }
            }

            // input lesen
            String input = queryField.getText();
            queryField.setText("");
            inOut.printOutput(input, Color.BLUE);

            if (!input.matches(".*?\\.")) {
                new ErrorPopUp(ParseException.getTitle(), "You must end your inquiry with a dot");
                reset();
            } else {

                try {
                    // die Anfrage parsen
                    PrologParser parser = new PrologParser(input);
                    List<Goal> listOfGoals = parser.parseGoalList();

                    // den interpreter setzen
                    interpreter.newInquiry(listOfGoals);

                    // neue Grafik erstellen
                    GraphTree graphTree = new GraphTree(
                            interpreter.getRoot().createVisNode(toolBar.getBoxSubs().isSelected()));

                    // die Grafik anzeigen
                    graphicPane.setGraph(graphTree);
                    graphicPane.updateUI();

                    // die highlights löschen
                    Highlighter highlighter = codeEditor.getHighlighter();
                    highlighter.removeAllHighlights();

                    memento.setWentBack(false);

                    // die Knöpfe aktivieren / deaktivieren
                    toolBar.buttonsAfterInquiry();
                } catch (StackOverflowError e1) {
                    new ErrorPopUp("Graph display error", "The graph is too large to be displayed");
                    reset();
                } catch (ParseException e1) {
                    new ErrorPopUp(ParseException.getTitle(), e1.getMessage());
                    reset();
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // wird nicht verwendet

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // wird nicht verwendet

    }

    private void reset() {
        // auf Zustand nach dem Parsen setzen
        interpreter.setNewProgram(interpreter.getProgram());

        // die Grafik zurücksetzen
        graphicPane.setGraph(new mxGraph());
        graphicPane.updateUI();

        // Highlights löschen
        Highlighter highlighter = codeEditor.getHighlighter();
        highlighter.removeAllHighlights();

        // die Knöpfe aktivieren / deaktivieren
        toolBar.buttonsAfterError();
    }

    @Override
    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // nichts tun
    }

}
