package edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree;

import java.util.List;

import edu.kit.ipd.pp.prolog.vipr.model.ast.Rule;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.State;

/**
 * Repräsentiert einen Parent-Knoten im Visualisierungsbaum.
 *
 */
public class ParentARVisNode extends ARVisNode {

    /**
     * Die Schriftfarbe
     */
    private static final String TEXT_COLOR = "black";

    /**
     * Die darunter liegende Regel. Wird im Falle der Wurzel nie gesetzt
     */
    private Rule rule;

    /**
     * Konstruktor für einen Parent-Knoten.
     * 
     * @param children
     *            Die Kinder des Knotens.
     * @param state
     *            Der Zustand des Knotens.
     */
    public ParentARVisNode(List<ARVisNode> children, State state) {
        super(children, state);
        // Knotenform soll elliptisch sein um Regel und Funktoren zu unterscheiden
        this.setVertexStyle(this.stateToVertexColor() + "shape=ellipse;perimeter=ellipsePerimeter;");
    }

    /**
     * Setter für die Regeln. Wird von der Wurzel nicht aufgerufen.
     * 
     * @param rule
     *            die neue Regel.
     */
    public void setRule(Rule rule) {
        this.rule = rule;
    }

    /**
     * Bei Rule Objekte (und die Wurzel) werden keine Substitutionen angezeigt,
     * deshalb wird statt der Tabelle direkt der Inhalt zurückgegeben.
     * 
     * @return den Regel-String in der TEXT_COLOR oder '?' für die Wurzel
     */
    @Override
    public String createHtmlTable() {

        if (rule != null) {
            return "<h4 color='" + TEXT_COLOR + "'>" + rule.toString() + "</h4>";
        }
        // Es wurde keine Regel gesetzt.
        return null;
    }

    /**
     * Der Knoten einer Regel muss in abhängigkeit der Länge dieser Regel angepasst
     * werden.
     * 
     * @return true
     */
    @Override
    public boolean needResize() {

        return true;
    }

}
