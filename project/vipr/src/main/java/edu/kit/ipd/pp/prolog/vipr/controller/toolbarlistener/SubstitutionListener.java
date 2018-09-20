package edu.kit.ipd.pp.prolog.vipr.controller.toolbarlistener;

import java.awt.Cursor;
import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;

import edu.kit.ipd.pp.prolog.vipr.controller.AbstractListener;
import edu.kit.ipd.pp.prolog.vipr.controller.Calculation;
import edu.kit.ipd.pp.prolog.vipr.controller.CalculationManager;
import edu.kit.ipd.pp.prolog.vipr.controller.Memento;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Interpreter;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.popups.ErrorPopUp;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.GraphTree;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.GraphicPane;

/**
 * Dieser Listener ermöglicht das Ändern der Anzeige von transitiven
 * Substitutionen.
 */
public class SubstitutionListener implements AbstractListener {

    /**
     * Der Interpreter. Wird zur Abarbeitung einer Anfrage auf ein Prolog-Programm
     * verwendet.
     */
    private Interpreter interpreter;

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
     * @param gp
     *            Das Grafikfeld der grafischen Benutzeroberfläche. Wird zum
     *            Anzeigen der Grafik verwendet.
     * @param memento
     *            Der Zustand des letzten Berechnungsschrittes. Wird für die
     *            "Schritt-Zurück" Funktion verwendet.
     * @param guiMain
     *            Die grafische Oberfläche.
     */
    public SubstitutionListener(Interpreter ip, GraphicPane gp, Memento memento, GUIMain guiMain) {
        this.interpreter = ip;
        this.graphicPane = gp;
        this.memento = memento;
        this.guiMain = guiMain;
        CalculationManager.getInstance().addListener(this);
    }

    /**
     * Ändert die Darstellung des Graphen (mit oder ohne transitiven Substitutionen)
     * Falls ein Graph angezeigt wird wird dieser mit der neuen Einstellung
     * aktualisiert.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        guiMain.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        JCheckBox box = (JCheckBox) e.getSource();

        boolean isSelected = box.isSelected();
        box.setSelected(isSelected);

        if (calculation != null && !calculation.isDone()) {
            return;
        }

        if (interpreter.getInquiryExists()) {
            try {
                // neue Grafik erstellen
                GraphTree graphTree;

                if (memento.getWentBack()) {
                    graphTree = memento.getGraph(isSelected);
                } else {
                    graphTree = new GraphTree(interpreter.getRoot().createVisNode(isSelected));
                }

                // die Grafik anzeigen
                graphicPane.setGraph(graphTree);
                graphicPane.updateUI();

            } catch (StackOverflowError e1) {
                new ErrorPopUp("Graph display error", "The graph is too large to be displayed");
            }

        }
        // else muss keine Grafik aktualisiert werden
        guiMain.setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }

}
