package edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree;

import java.util.List;

import edu.kit.ipd.pp.prolog.vipr.model.ast.ArithOrUniGoal;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.State;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Substitution;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.substitutiontable.SubstitutionMatrix;

/**
 * Repräsentiert einen Arithmetik-Knoten oder einen Unifikations-Knoten im
 * Visualisierungsbaum.
 *
 */
public class ArithOrUniGoalARVisNode extends ARVisNode {

    /**
     * Konstruktor für einen Arithmetik-Knoten oder einen Unifikations-Knoten.
     * 
     * @param substitutions
     *            Die Substitutionen eines Knotens.
     * @param state
     *            Der Zustand des Knotens.
     * @param aoug
     *            Das zugehörige Arithmetik-Ziel oder Unifikations-Ziel.
     */
    public ArithOrUniGoalARVisNode(List<Substitution> substitutions, State state, ArithOrUniGoal aoug) {
        // Aufruf des ARVisNode Konstruktors
        super(state);
        // erstellt und setzt SubstitutionMatix
        SubstitutionMatrix sx = new SubstitutionMatrix(aoug.getTokens(), substitutions);
        this.setSubstitutionMatrix(sx);

    }

    /**
     * @see ARVisNode Da ArithOrUniGoal Objekte beliebig viele Substitutionen haben
     *      können, muss die Knotengröße daran angepasst werden.
     * @return true
     */
    @Override
    public boolean needResize() {
        return true;
    }

}
