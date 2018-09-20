package edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree;

import edu.kit.ipd.pp.prolog.vipr.model.interpreter.State;

/**
 * Repräsentiert einen Cut-Knoten im Visualisierungsbaum.
 *
 */
public class CutARVisNode extends ARVisNode {
    /**
     * Text Farbe
     */
    private static final String TEXT_COLOR = "black";

    /**
     * Text Inhalt
     */
    private static final String CUT_TEXT = "!";

    /**
     * Konstruktor eines Cut-Knotens.
     * 
     * @param state
     *            Der Zustand des Knotens.
     */
    public CutARVisNode(State state) {
        super(state);
        this.setVertexStyle(this.getVertexStyle() + this.stateToVertexColor());
    }

    /**
     * Cut Objekte haben keine Substitutionen, deshalb wird statt der Tabelle direkt
     * der Inhalt zurückgegeben.
     * 
     * @return CUT_TEXT in der Farbe TEXT_COLOR
     */
    @Override
    public String createHtmlTable() {
        return "<h2 color='" + TEXT_COLOR + "'>" + CUT_TEXT + "</h2>";
    }

    /**
     * @see ARVisNode Der Inhalt eines CutARVisNode besteht lediglich aus CUT_TEXT
     *      und wird nicht dynamisch größer, daher kann die default Größe verwendet
     *      werden und es wird keine Anpassung benötigt.
     * @return false
     */
    @Override
    public boolean needResize() {
        return false;
    }

}
