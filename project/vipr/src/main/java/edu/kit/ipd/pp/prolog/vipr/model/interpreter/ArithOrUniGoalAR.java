package edu.kit.ipd.pp.prolog.vipr.model.interpreter;

import java.util.List;

import edu.kit.ipd.pp.prolog.vipr.model.ast.ArithOrUniGoal;
import edu.kit.ipd.pp.prolog.vipr.model.ast.CalculateException;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.ArithOrUniGoalARVisNode;

/**
 * Ein activation record für ein arithmetisches Ziel oder ein Unifikationsziel.
 *
 */
public class ArithOrUniGoalAR extends GoalAR {

    /**
     * Das unveränderte Ziel.
     */
    private ArithOrUniGoal originalGoal;

    /**
     * Das aktuelle Ziel.
     */
    private ArithOrUniGoal currentGoal;

    /**
     * Erzeugt einen neuen ArithOrUniGoalAR mit dem gegebenen Ziel.
     * 
     * @param goal
     *            Das Ziel des ArithOrUniGoalAR.
     */
    public ArithOrUniGoalAR(ArithOrUniGoal goal) {
        originalGoal = goal.deepCopy();
        currentGoal = goal.deepCopy();
    }

    @Override
    public ArithOrUniGoalAR nextFulfillStep() throws CalculateException {
        try {
            if (currentGoal.fulfill()) {
                addSubstitutions(currentGoal.getSubstitutions());
                setState(State.FULFILLED);
                if (getIsLastGoal()) {
                    getParentAR().setState(State.FULFILLED);
                }
            } else {
                setState(State.FAILED);
            }
            return this;
        } catch (CalculateException e) {
            setState(State.FAILED);
            throw e;
        }
    }

    @Override
    public RuleAR getRuleAR() {
        return null;
    }

    @Override
    public void reset() {
        deleteSubstitutions();
        currentGoal = originalGoal.deepCopy();
        renumber();
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
    public ArithOrUniGoalARVisNode createVisNode(boolean trans) {
        List<Substitution> subs;
        if (null == this.getLastNode() || trans) {
            subs = this.allSubstitutions();
        } else {
            subs = this.allPreviousSubstitutions();
        }
        ArithOrUniGoal visGoal = this.originalGoal.deepCopy();
        visGoal.renumber(this.getParentAR().getCounter());
        return new ArithOrUniGoalARVisNode(subs, this.getState(), visGoal);
    }

    @Override
    public void deleteRuleAR() {
        // ArithOrUniGoalAR hat kein RuleAr
    }

    @Override
    public boolean canFulfillDifferently() {
        return false;
    }

    @Override
    public void deleteVisRuleAR() {
        // ArithOrUniGoalAR hat kein VisRuleAr
    }

    @Override
    public RuleAR getVisRuleAR() {
        return null;
    }

}
