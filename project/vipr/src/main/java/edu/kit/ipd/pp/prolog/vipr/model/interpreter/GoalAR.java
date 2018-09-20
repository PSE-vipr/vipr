package edu.kit.ipd.pp.prolog.vipr.model.interpreter;

import java.util.LinkedList;
import java.util.List;

import edu.kit.ipd.pp.prolog.vipr.model.ast.CalculateException;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NoRuleException;

/**
 * Ein activation record für ein Ziel.
 *
 */
public abstract class GoalAR extends ActivationRecord {

    /**
     * Der Elternknoten dieses GoalARs.
     */
    private ParentAR parentAR;

    /**
     * true, wenn dieses Ziel das letzte Teilziel des Elternknoten ist, false sonst.
     */
    private boolean isLastGoal;

    /**
     * Die Substitutionen dieses GoalAR.
     */
    private List<Substitution> substitutions;

    /**
     * Erzeugt einen neuen GoalAR.
     */
    public GoalAR() {
        substitutions = new LinkedList<>();
        isLastGoal = false;
        setState(State.UNVISITED);
    }

    /**
     * Der nächste Schritt beim Versuch, das Ziel zu erfüllen.
     * 
     * @return Der letzte besuchte activation record.
     * @throws CalculateException
     *             Wenn ein Term nicht berechnet werden kann.
     * @throws NoRuleException
     *             Wenn zu einem Funktorziel keine passende Regel gefunden werden
     *             kann.
     */
    public abstract ActivationRecord nextFulfillStep() throws CalculateException, NoRuleException;

    /**
     * Gibt den aktuellen RuleAR zurück, falls er existiert.
     * 
     * @return Der aktuelle RuleAR, falls er existiert, sonst null.
     */
    public abstract RuleAR getRuleAR();

    /**
     * Löscht den aktuellen RuleAR, falls er existiert.
     */
    public abstract void deleteRuleAR();

    /**
     * Löscht den aktuellen visRuleAR, falls er existiert.
     */
    public abstract void deleteVisRuleAR();

    /**
     * Setzt diesen GoalAR zurück, so dass er erneut erfüllt werden kann.
     */
    public abstract void reset();

    /**
     * Wendet alle bisherigen Substitutionen auf das Ziel an.
     */
    public abstract void substitute();

    /**
     * Nummeriert die Variablen des aktuellen Ziels um. Setzt die ids aller
     * Variablen des aktuellen Ziels auf den counter des Elternknotens dieses
     * GoalARs.
     */
    public abstract void renumber();

    /**
     * Überprüft, ob dieser GoalAR anders erfüllt werden kann.
     * 
     * @return true, falls dieser GoalAR anders erfüllt werden kann; sonst false
     */
    public abstract boolean canFulfillDifferently();

    /**
     * Gibt alle Substitutionen des Berechnungsbaums zurück.
     * 
     * @return Alle Substitutionen.
     */
    public List<Substitution> allSubstitutions() {
        ParentAR parent = getParentAR();
        ActivationRecord lastNode = parent.getLastNode();
        while (null != lastNode) {
            parent = lastNode.getParentAR();
            lastNode = parent.getLastNode();
        }
        GoalAR lastVisitedGoalAR = parent.getLastVisitedAR().getLastGoalAR();
        if (null == lastVisitedGoalAR) {
            return new LinkedList<>();
        }
        return lastVisitedGoalAR.allPreviousSubstitutions();
    }

    /**
     * Gibt die Substitutionen dieses GoalARs und aller zuvor besuchten GoalARs
     * zurück.
     * 
     * @return Alle bisherigen Substitutionen.
     */
    public List<Substitution> allPreviousSubstitutions() {
        List<Substitution> result = new LinkedList<>();
        result.addAll(substitutions);
        for (GoalAR goalAR = getLastNode().getLastGoalAR(); null != goalAR; goalAR = goalAR.getLastNode()
                .getLastGoalAR()) {
            result.addAll(0, goalAR.substitutions);
        }
        return result;
    }

    /**
     * Fügt diesem GoalAR Substitutionen hinzu.
     * 
     * @param listOfSubstitutions
     *            Die hinzuzufügenden Substitutionen.
     */
    public void addSubstitutions(List<Substitution> listOfSubstitutions) {
        substitutions.addAll(listOfSubstitutions);
    }

    /**
     * Löscht die Substitutionen dieses GoalAR.
     */
    public void deleteSubstitutions() {
        substitutions = new LinkedList<>();
    }

    @Override
    public ParentAR getParentAR() {
        return parentAR;
    }

    /**
     * Setzt den Elternknoten dieses GoalARs auf den gegebenen Elternknoten.
     * 
     * @param parentAR
     *            Der neue Elternknoten dieses GoalARs.
     */
    public void setParentAR(ParentAR parentAR) {
        this.parentAR = parentAR;
    }

    /**
     * Gibt isLastGoal zurück.
     * 
     * @return true, wenn dieses Ziel das letzte Teilziel des Elternknoten ist,
     *         false sonst.
     */
    public boolean getIsLastGoal() {
        return isLastGoal;
    }

    /**
     * Setzt isLastGoal auf true.
     */
    public void setIsLastGoal() {
        isLastGoal = true;
    }

    /**
     * Aktualisiert die Status des Berechnungsbaums, wenn dieses Ziel nicht erfüllt
     * ist.
     */
    public void updateStates() {
        ParentAR parent = getParentAR();
        parent.setState(State.PENDING);
        GoalAR lastGoalAR = parent.getLastGoalAR();
        if (null == lastGoalAR) {
            return;
        }
        lastGoalAR.setState(State.PENDING);
        lastGoalAR.updateStates();
    }

    /**
     * Geht zum letzten GoalAR zurück, falls dieses Ziel nicht anders erfüllt werden
     * kann.
     * 
     * @return Der letzte besuchte activation record.
     */
    public ActivationRecord goBack() {
        if (!canFulfillDifferently()) {
            reset();
            setState(State.UNVISITED);
            ActivationRecord lastNode = getLastNode();
            GoalAR lastGoalAR = lastNode.getLastGoalAR();
            if (null == lastGoalAR) {
                lastNode.setState(State.FAILED);
                ((ParentAR) lastNode).setGoalsFailed();
                return lastNode;
            }
            lastGoalAR.setState(State.FAILED);
            if (lastGoalAR.canFulfillDifferently()) {
                ((FunctorGoalAR) lastGoalAR).setShowPending();
            }
            lastGoalAR.updateStates();
            lastGoalAR.deleteRuleAR();
            return lastGoalAR;
        } else {
            reset();
            setState(State.PENDING);
            return this;
        }
    }

    @Override
    public ParentAR getLastParentAR() {
        return getLastNode().getLastParentAR();
    }

    @Override
    public GoalAR getLastGoalAR() {
        return this;
    }

}
