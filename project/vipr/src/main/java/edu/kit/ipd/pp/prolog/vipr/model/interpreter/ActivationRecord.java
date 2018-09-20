package edu.kit.ipd.pp.prolog.vipr.model.interpreter;

import edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.ARVisNode;

/**
 * Ein Knoten des Berechnungsbaums.
 *
 */
public abstract class ActivationRecord {

    /**
     * Der Status dieses activation records.
     */
    private State state;

    /**
     * Der vorher besuchte Knoten.
     */
    private ActivationRecord lastNode;

    /**
     * Gibt den letzten ParentAR aller vorher besuchten Knoten zurück.
     * 
     * @return Der letzte ParentAR.
     */
    public abstract ParentAR getLastParentAR();

    /**
     * Gibt den letzten GoalAR aller vorher besuchten Knoten zurück.
     * 
     * @return Der letzte GoalAR.
     */
    public abstract GoalAR getLastGoalAR();

    /**
     * Gibt den aktuellen visRuleAR zurück, falls er existiert.
     * 
     * @return Der aktuelle visRuleAR, falls er existiert, sonst null.
     */
    public abstract RuleAR getVisRuleAR();

    /**
     * Gibt den Elternknoten dieses activation records zurück. Wenn dieser
     * activation record ein Elternknoten ist, wird dieser activation record
     * zurückgegeben.
     * 
     * @return Der Elternknoten.
     */
    public abstract ParentAR getParentAR();

    /**
     * Erzeugt einen Visualisierungsknoten.
     * 
     * @param trans
     *            Gibt an, ob transitive Substitutionen angezeigt werden sollen.
     * @return Der Visualisierungsknoten.
     */
    public abstract ARVisNode createVisNode(boolean trans);

    /**
     * Gibt den Status zurück.
     * 
     * @return Der Status.
     */
    public State getState() {
        return state;
    }

    /**
     * Setzt den Status auf newState.
     * 
     * @param newState
     *            Der neue Status.
     */
    public void setState(State newState) {
        state = newState;
    }

    /**
     * Gibt den vorher besuchten Knoten zurück.
     * 
     * @return Der vorher besuchte Knoten.
     */
    public ActivationRecord getLastNode() {
        return lastNode;
    }

    /**
     * Setzt den vorher besuchten Knoten auf lastNode.
     * 
     * @param lastNode
     *            Der neue vorher besuchte Knoten.
     */
    public void setLastNode(ActivationRecord lastNode) {
        this.lastNode = lastNode;
    }

}
