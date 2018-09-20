package edu.kit.ipd.pp.prolog.vipr.model.interpreter;

import java.util.LinkedList;
import java.util.List;

import edu.kit.ipd.pp.prolog.vipr.model.ast.FunctorGoal;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NoRuleException;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Program;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Rule;
import edu.kit.ipd.pp.prolog.vipr.model.ast.UnifyException;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.ARVisNode;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.FunctorGoalARVisNode;

/**
 * Ein activation record für ein FunctorGoal.
 *
 */
public class FunctorGoalAR extends GoalAR {

    /**
     * Eine id, die speichert, die wievielte Regel von rules für den currentRuleAR
     * verwendet wird.
     */
    private int id;

    /**
     * Das unveränderte Ziel.
     */
    private FunctorGoal originalGoal;

    /**
     * Das aktuelle Ziel.
     */
    private FunctorGoal currentGoal;

    /**
     * Die zum Ziel passenden Regeln des Programms.
     */
    private List<Rule> rules;

    /**
     * Der aktuelle RuleAR.
     */
    private RuleAR currentRuleAR;

    /**
     * Der RuleAR für die Visualisierung.
     */
    private RuleAR visRuleAR;

    /**
     * Das Programm.
     */
    private Program program;

    /**
     * true, wenn dieses FunctorGoalAR in der Grafik als PENDING angezeigt werden
     * soll, obwohl der Status FAILED ist, false sonst
     */
    private boolean showPending;

    /**
     * Erzeugt einen neuen FunctorGoalAR mit dem gegebenen Ziel, den gegebenen
     * Regeln und dem Programm.
     * 
     * @param goal
     *            Das Ziel.
     * @param listOfRules
     *            Die zum Ziel passenden Regeln.
     * @param program
     *            Das Programm.
     */
    public FunctorGoalAR(FunctorGoal goal, List<Rule> listOfRules, Program program) {
        originalGoal = goal.deepCopy();
        currentGoal = goal.deepCopy();
        rules = listOfRules;
        this.program = program;
        showPending = false;
        id = 0;
    }

    @Override
    public ActivationRecord nextFulfillStep() throws NoRuleException {
        if (rules.isEmpty()) {
            setState(State.FAILED);
            throw new NoRuleException();
        }
        Rule rule = rules.get(id);
        currentRuleAR = new RuleAR(rule, program, this, id);
        visRuleAR = currentRuleAR;
        List<Substitution> unifier;
        try {
            unifier = currentGoal.unify(currentRuleAR.getFunctorTerm());
        } catch (UnifyException e) {
            setState(State.FAILED);
            if (canFulfillDifferently()) {
                showPending = true;
            }
            deleteRuleAR();
            return this;
        }
        addSubstitutions(unifier);
        GoalAR goalAR = currentRuleAR.nextUnfulfilledGoalAR();
        if (null == goalAR) {
            setState(State.FULFILLED);
            if (getIsLastGoal()) {
                getParentAR().setState(State.FULFILLED);
            }
            return currentRuleAR;
        }
        goalAR.substitute();
        setState(State.PENDING);
        return goalAR;
    }

    @Override
    public RuleAR getRuleAR() {
        return currentRuleAR;
    }

    @Override
    public void deleteRuleAR() {
        if (null != visRuleAR) {
            visRuleAR.setState(State.FAILED);
            visRuleAR.setGoalsFailed();
        }
        currentRuleAR = null;
    }

    @Override
    public void reset() {
        deleteVisRuleAR();
        showPending = false;
        deleteSubstitutions();
        currentGoal = originalGoal.deepCopy();
        renumber();
        if (!canFulfillDifferently()) {
            id = 0;
            return;
        }
        substitute();
        id++;
    }

    @Override
    public void substitute() {
        for (Substitution substitution : allPreviousSubstitutions()) {
            currentGoal.substitute(substitution);
        }
    }

    @Override
    public void renumber() {
        currentGoal.renumber(getParentAR().getCounter());
    }

    @Override
    public FunctorGoalARVisNode createVisNode(boolean trans) {
        List<Substitution> subs;
        if (null == this.getLastNode() || trans) {
            subs = this.allSubstitutions();
        } else {
            subs = this.allPreviousSubstitutions();
        }
        LinkedList<ARVisNode> children = new LinkedList<>();
        if (this.visRuleAR != null) {
            children.add(this.visRuleAR.createVisNode(trans));
        }
        FunctorGoal visGoal = this.originalGoal.deepCopy();
        visGoal.renumber(this.getParentAR().getCounter());
        return new FunctorGoalARVisNode(children, subs, this.getState(), visGoal, showPending);
    }

    /**
     * Setzt showPending auf true.
     */
    public void setShowPending() {
        showPending = true;
    }

    @Override
    public boolean canFulfillDifferently() {
        return id < rules.size() - 1;
    }

    /**
     * Lässt dieses Ziel in Folge eines Cuts fehlschlagen.
     */
    public void cutfail() {
        setState(State.FAILED);
        id = rules.size();
        deleteRuleAR();
        visRuleAR.setGoalsFailed();
    }

    @Override
    public void deleteVisRuleAR() {
        showPending = false;
        visRuleAR = null;
    }

    @Override
    public RuleAR getVisRuleAR() {
        return visRuleAR;
    }

}
