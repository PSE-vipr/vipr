package edu.kit.ipd.pp.prolog.vipr.model.interpreter;

import java.util.LinkedList;
import java.util.List;

import edu.kit.ipd.pp.prolog.vipr.model.ast.Goal;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Program;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.ARVisNode;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.ParentARVisNode;

/**
 * Ein Elternknoten von GoalARs des Berechnungsbaums.
 *
 */
public class ParentAR extends ActivationRecord {

    /**
     * Ein Zähler, der speichert, der wievielte ParentAR des Berechnungsbaums dieser
     * ParentAR ist.
     */
    private int counter;

    /**
     * Die activation records für die Teilziele dieses ParentARs.
     */
    private LinkedList<GoalAR> goalARs;

    /**
     * Der allerletzte besuchte activation record.
     */
    private ActivationRecord lastVisitedAR;

    /**
     * Erzeugt einen neuen ParentAR mitsamt den activation records für die gegebenen
     * Teilziele.
     * 
     * @param listOfGoals
     *            Die Teilziele.
     * @param program
     *            Das Programm.
     */
    public ParentAR(List<Goal> listOfGoals, Program program) {
        goalARs = new LinkedList<>();
        counter = 0;
        for (Goal goal : listOfGoals) {
            GoalAR goalAR = goal.createAR(program);
            goalARs.add(goalAR);
            goalAR.setParentAR(this);
        }
        GoalAR goalAR = goalARs.getFirst();
        goalAR.setLastNode(this);
        goalAR.setState(State.PENDING);
        goalARs.getLast().setIsLastGoal();
        setState(State.PENDING);
    }

    /**
     * Erzeugt einen neuen ParentAR mitsamt den activation records für die gegebenen
     * Teilziele. Dabei erhält der ParentAR den gegebenen Knoten als neuen vorher
     * besuchten Knoten.
     * 
     * @param listOfGoals
     *            Die Teilziele.
     * @param program
     *            Das Programm.
     * @param lastNode
     *            Der vorher besuchte Knoten.
     */
    public ParentAR(List<Goal> listOfGoals, Program program, ActivationRecord lastNode) {
        goalARs = new LinkedList<>();
        setLastNode(lastNode);
        counter = lastNode.getLastParentAR().counter + 1;
        for (Goal goal : listOfGoals) {
            GoalAR goalAR = goal.createAR(program);
            goalARs.add(goalAR);
            goalAR.setParentAR(this);
            goalAR.renumber();
        }
        if (!goalARs.isEmpty()) {
            GoalAR goalAR = goalARs.getFirst();
            goalAR.setLastNode(this);
            goalAR.setState(State.PENDING);
            goalARs.getLast().setIsLastGoal();
            setState(State.PENDING);
        } else {
            setState(State.FULFILLED);
        }
    }

    /**
     * Gibt den ersten GoalAR dieses ParentARs zurück, der nicht FULFILLED ist.
     * 
     * @return Der erste GoalAR dieses ParentARs, der nicht FULFILLED ist.
     */
    public GoalAR nextUnfulfilledGoalAR() {
        for (GoalAR goalAR : goalARs) {
            if (goalAR.getState() != State.FULFILLED) {
                return goalAR;
            }
        }
        return null;
    }

    /**
     * Setzt die Ziele dieses ParentARs auf FAILED und löscht ihre Substitutionen
     * und die für sie gefundenen Regeln.
     */
    public void setGoalsFailed() {
        for (GoalAR goalAR : goalARs) {
            goalAR.setState(State.FAILED);
            goalAR.deleteSubstitutions();
            goalAR.deleteRuleAR();
            goalAR.deleteVisRuleAR();
        }
    }

    /**
     * Gibt den Zähler zurück, der speichert, der wievielte ParentAR des
     * Berechnungsbaums dieser ParentAR ist.
     * 
     * @return Der Zähler, der speichert, der wievielte ParentAR des
     *         Berechnungsbaums dieser ParentAR ist.
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Gibt die Anfangs- und die Endposition dieses ParentARs zurück. Gibt als
     * Anfangs- und Endposition 0 zurück, wenn dieser ParentAR die Wurzel ist.
     * 
     * @return Die Anfangs- und die Endposition dieses ParentARs als Array.
     */
    public int[] getPos() {

        return new int[] {0, 0};
    }

    /**
     * Gibt den allerletzten besuchten activation record zurück.
     * 
     * @return Der allerletzte besuchte activation record.
     */
    public ActivationRecord getLastVisitedAR() {
        return lastVisitedAR;
    }

    /**
     * Setzt den allerletzten besuchten activation record auf den gegebenen
     * activation record.
     * 
     * @param lastVisitedAR
     *            Der neue allerletzte besuchte activation record.
     */
    public void setLastVisitedAR(ActivationRecord lastVisitedAR) {
        this.lastVisitedAR = lastVisitedAR;
    }

    @Override
    public ParentAR getParentAR() {
        return this;
    }

    @Override
    public ParentAR getLastParentAR() {
        return this;
    }

    @Override
    public GoalAR getLastGoalAR() {
        ActivationRecord lastNode = getLastNode();
        if (null == lastNode) {
            return null;
        }
        return lastNode.getLastGoalAR();
    }

    @Override
    public ParentARVisNode createVisNode(boolean trans) {
        LinkedList<ARVisNode> children = new LinkedList<>();
        for (GoalAR g : goalARs) {
            children.add(g.createVisNode(trans));
        }

        return new ParentARVisNode(children, this.getState());
    }

    @Override
    public RuleAR getVisRuleAR() {
        return null;
    }

}
