package edu.kit.ipd.pp.prolog.vipr.controller.toolbarlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.kit.ipd.pp.prolog.vipr.controller.Calculation;
import edu.kit.ipd.pp.prolog.vipr.controller.CalculationManager;
import edu.kit.ipd.pp.prolog.vipr.controller.CalculationType;
import edu.kit.ipd.pp.prolog.vipr.controller.Memento;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Interpreter;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.ToolBar;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.GraphicPane;
import edu.kit.ipd.pp.prolog.vipr.view.textarea.CodeEditor;
import edu.kit.ipd.pp.prolog.vipr.view.textarea.InOut;

/**
 * Dieser Listener ermäglicht die Berechnung einer Anfrage bis zur nächsten
 * Lösung.
 */
public class ButtonNextSolutionListener implements ActionListener {

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
     * Der Zustand des letzten Berechnungsschrittes. Wird für die "Schritt-Zurück"
     * Funktion verwendet.
     */
    private Memento memento;

    /**
     * Die GUIMain. Wird verwendet, um den Mauscursor ändern zu können.
     */
    private GUIMain guiMain;

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
     * @param memento
     *            Der Zustand des letzten Berechnungsschrittes. Wird für die
     *            "Schritt-Zurück" Funktion verwendet.
     * @param guiMain
     *            Die grafische Oberfläche.
     */
    public ButtonNextSolutionListener(Interpreter ip, CodeEditor ce, GraphicPane gp, ToolBar tb, InOut io,
            Memento memento, GUIMain guiMain) {

        this.interpreter = ip;
        this.codeEditor = ce;
        this.graphicPane = gp;
        this.toolBar = tb;
        this.inOut = io;
        this.memento = memento;
        this.guiMain = guiMain;
    }

    /**
     * Startet die Berechnung einer Anfrage. Stoppt bei der nächsten gefundenen
     * Lösung.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Knöpfe aktivieren / deaktivieren
        toolBar.buttonsWhileCalculation();
        graphicPane.reset();
        Calculation calculation = new Calculation(CalculationType.NEXT_SOLUTION, interpreter, codeEditor, graphicPane,
                toolBar, inOut, memento, guiMain);
        CalculationManager.getInstance().updateListener(calculation);
        calculation.execute();

    }

}
