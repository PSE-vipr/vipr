package edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree;

import java.util.List;

import edu.kit.ipd.pp.prolog.vipr.model.ast.FunctorGoal;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.State;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Substitution;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.substitutiontable.SubstitutionMatrix;

/**
 * Repräsentiert einen Funktorenziel-Knoten im Visualisierungsbaum.
 *
 */
public class FunctorGoalARVisNode extends ARVisNode {
    /**
     * Funktor und Regeln sollen mit einer gestrichelten Kante verbunden sein.
     */
    private static final String DASHED_EDGE_STYLE = "dashed=true;";

    /**
     * Konstruktor eines Funktorenziel-Knotens.
     * 
     * @param children
     *            Die Kinder des Knotens.
     * @param substitutions
     *            Die Substitutionen des Knotens.
     * @param state
     *            Der Status des Knotens.
     * @param funcG
     *            Das zum Knoten gehörende Funktorenziel.
     * @param showPending
     *            Gibt an, ob der Knoten als pending visualisiert werden soll.
     */
    public FunctorGoalARVisNode(List<ARVisNode> children, List<Substitution> substitutions, State state,
            FunctorGoal funcG, boolean showPending) {
        // ARVisNode Konstruktor aufruf
        super(children, state, showPending);
        // Erstellen und setzten der SubstitutionMatrix
        SubstitutionMatrix sx = new SubstitutionMatrix(funcG.getTokens(), substitutions);
        this.setSubstitutionMatrix(sx);
    }

    /**
     * Der Kanten-Style wird um die Änderungen erweitert und zurückgegeben.
     * 
     * @return den erweiterten Kanten-Style.
     */
    @Override
    public String getEdgeStyle() {
        return super.getEdgeStyle() + DASHED_EDGE_STYLE;
    }

    /**
     * @see ARVisNode Da FunctorGoal Objekte beliebig viele Substitutionen haben
     *      können, muss die Knotengröße daran angepasst werden.
     * @return true
     */
    @Override
    public boolean needResize() {

        return true;
    }

}
