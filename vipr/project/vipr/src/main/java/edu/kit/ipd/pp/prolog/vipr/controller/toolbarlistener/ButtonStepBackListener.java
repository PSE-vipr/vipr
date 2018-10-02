package edu.kit.ipd.pp.prolog.vipr.controller.toolbarlistener;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.text.Highlighter;

import com.mxgraph.view.mxGraph;

import edu.kit.ipd.pp.prolog.vipr.controller.Memento;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Interpreter;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.ToolBar;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.popups.ErrorPopUp;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.GraphTree;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.GraphicPane;
import edu.kit.ipd.pp.prolog.vipr.view.textarea.CodeEditor;

/**
 * Dieser Listener ermöglicht das Anzeigen des letzten Berechnungsschrittes.
 */
public class ButtonStepBackListener implements ActionListener {

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
     * Die GUIMain. Wird verwendet, um den Mauscursor ändern zu können.
     */
    private GUIMain guiMain;

    /**
     * Die Toolbar der grafischen Benutzeroberfläche.
     */
    private ToolBar toolBar;

    /**
     * Der Zustand des letzten Berechnungsschrittes. Wird für die "Schritt-Zurück"
     * Funktion verwendet.
     */
    private Memento memento;

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
     * @param memento
     *            Der Zustand des letzten Berechnungsschrittes. Wird für die
     *            "Schritt-Zurück" Funktion verwendet.
     * @param guiMain
     *            Die grafische Oberfläche.
     */
    public ButtonStepBackListener(Interpreter ip, CodeEditor ce, GraphicPane gp, ToolBar tb, Memento memento,
            GUIMain guiMain) {
        this.interpreter = ip;
        this.codeEditor = ce;
        this.graphicPane = gp;
        this.toolBar = tb;
        this.memento = memento;
        this.guiMain = guiMain;
    }

    /**
     * Lädt den Zustand des letzten Berechnungsschrittes und aktualisiert die
     * grafische Obefläche entsprechend.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            guiMain.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            graphicPane.reset();
            // vorherige Grafik holen
            GraphTree graphTree = memento.getGraph(toolBar.getBoxSubs().isSelected());

            memento.setWentBack(true);

            // die Grafik anzeigen
            graphicPane.setGraph(graphTree);
            graphicPane.updateUI();

            // die Highlights entfernen
            Highlighter highlighter = codeEditor.getHighlighter();
            highlighter.removeAllHighlights();

            // die Knöpfe aktivieren / deaktivieren
            toolBar.buttonsAfterInquiry();

        } catch (StackOverflowError e1) {
            new ErrorPopUp("Graph display error", "The graph is too large to be displayed");
            reset();
        }
        guiMain.setCursor(Cursor.getDefaultCursor());

    }

    private void reset() {
        // Berechnung fehlgeschlagen
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

}
