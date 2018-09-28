package edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree;

import java.util.LinkedList;
import java.util.List;

import edu.kit.ipd.pp.prolog.vipr.model.interpreter.State;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.substitutiontable.SubstitutionMatrix;

/**
 * Repräsentiert einen Knoten im Visualisierungsbaum.
 */
public abstract class ARVisNode {

    /**
     * Style-Parameter für den Knoten.
     */
    private static final String ROUNDED_CORNER_STYLE = "rounded=false;";
    private static final String NO_ARROW_HEAD_STYLE = "endArrow=none;";
    private static final String FULLFILLED_VERTEX_COLOR = "fillColor=#72E38F;"; // Grünton
    private static final String FAILED_VERTEX_COLOR = "fillColor=#FF4646;"; //Rotton
    private static final String CUTFAILED_VERTEX_COLOR = "fillColor=red;";
    private static final String PENDING_VERTEX_COLOR = "fillColor=white;";
    private static final String UNVISITED_VERTEX_COLOR = "fillColor=white;";
    /**
     * Style-Parameter für die Tabelle innerhalb des Knotens.
     */
    private static final String FULLFILLED_COLOR = "#72E38F"; //Grünton
    private static final String FAILED_COLOR = "#FF4646"; //Rotton
    private static final String PENDING_COLOR = "white";
    private static final String UNVISITED_COLOR = "white";

    /**
     * Style-Parameter für die Darstellung der Knoten und Kanten.
     */
    private String vertexStyle = ROUNDED_CORNER_STYLE;
    private String edgeStyle = NO_ARROW_HEAD_STYLE;

    /**
     * Der Status des Knotens. Wird von dem erzeugenden ActivationRecord-Objekt
     * übernommen.
     */
    private State state;

    private boolean showPending;

    /**
     * Die Kinderknoten. Werden für die Traversierung des Baums benötigt.
     */
    private List<ARVisNode> children = new LinkedList<>();

    /**
     * Matrix, die die Substitutionen in einer Tabellenform anordnen.
     */
    private SubstitutionMatrix subMatrix = new SubstitutionMatrix();

    /**
     * Konstruktor eines ARVisNodes ohne Kinder.
     * 
     * @param state
     *            Der Status des neuen Visualisierungs-Knotens.
     */
    public ARVisNode(State state) {
        this.children = new LinkedList<>();
        this.state = state;
        this.showPending = false;
    }

    /**
     * Konstruktor eines ARVisNodes mit Kindern.
     * 
     * @param children
     *            Die Kinder des Knotens.
     * @param state
     *            Der Status des neuen Visualisierungs-Knotens.
     */
    public ARVisNode(List<ARVisNode> children, State state) {
        this.children = children;
        this.state = state;
        this.showPending = false;

    }

    /**
     * Konstruktor eines ARVisNodes mit Kindern und der Angabe, ob der Knoten als
     * pending visualisiert werden soll.
     * 
     * @param children
     *            Die Kinder des Knotens.
     * @param state
     *            Der Status des neuen Visualisierungs-Knotens.
     * @param showPending
     *            Gibt an, ob der Knoten als pending visualisiert werden soll.
     */
    public ARVisNode(List<ARVisNode> children, State state, boolean showPending) {
        this.children = children;
        this.state = state;
        this.showPending = showPending;

    }

    /**
     * Gibt an ob die Knotengröße in Abhängigkeit zum Inhalt angepasst werden muss.
     * 
     * @return true wenn die Knotengröße angepasst werden muss, false falls die
     *         default-größe genohmmen werden soll.
     */
    public abstract boolean needResize();

    /**
     * Getter für die Kindknoten.
     * 
     * @return die Kindknoten.
     */
    public List<ARVisNode> getChildren() {
        return children;
    }

    /**
     * Getter für den Kanten-Style.
     * 
     * @return den Kanten-Style.
     */
    public String getEdgeStyle() {
        return edgeStyle;
    }

    /**
     * Getter für den Knoten-Style.
     * 
     * @return den Knoten-Style.
     */
    public String getVertexStyle() {
        return vertexStyle;
    }

    /**
     * Setter für den Knoten-Style.
     * 
     * @param style
     *            der neue Knoten-Style.
     */
    public void setVertexStyle(String style) {
        this.vertexStyle = style;
    }

    /**
     * Hilfsmethode, ordnet jedem State den Farb-String für die Tabelle zu.
     * 
     * @param showPending
     * @return Farb-String
     */
    private String stateToTableColor(boolean showPending) {
        switch (state) {
        case FULFILLED:
            return FULLFILLED_COLOR;
        case FAILED:
            if (showPending) {
                return PENDING_COLOR;
            }
            return FAILED_COLOR;
        case PENDING:
            return PENDING_COLOR;
        case UNVISITED:
            return UNVISITED_COLOR;
        default:
            return "";
        }
    }

    /**
     * Setter für die SubstitutionMatrix
     * 
     * @param subMatrix
     *            die neue SubstitutionMatrix
     */
    public void setSubstitutionMatrix(SubstitutionMatrix subMatrix) {
        this.subMatrix = subMatrix;
    }

    /**
     * Erstellt aus der SubstitutionMatrix eine formatierte HTML-Tabelle.
     * 
     * @return HTML-Tabellen Quelltext.
     */
    public String createHtmlTable() {
        return subMatrix.printSubMatrix(this.stateToTableColor(showPending));
    }

    /**
     * Ordnet jedem State den Farb-String für den Knoten zu.
     * 
     * @return Farb-String.
     */
    public String stateToVertexColor() {
        switch (this.state) {
        case FULFILLED:
            return FULLFILLED_VERTEX_COLOR;
        case FAILED:
            return FAILED_VERTEX_COLOR;
        case CUTFAILED:
            return CUTFAILED_VERTEX_COLOR;
        case PENDING:
            return PENDING_VERTEX_COLOR;
        case UNVISITED:
            return UNVISITED_VERTEX_COLOR;
        default:
            return "";
        }
    }

}
