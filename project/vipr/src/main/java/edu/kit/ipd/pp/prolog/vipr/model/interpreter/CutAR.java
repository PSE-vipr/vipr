package edu.kit.ipd.pp.prolog.vipr.model.interpreter;

import edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.CutARVisNode;

/**
 * Ein activation record für einen Cut.
 *
 */
public class CutAR extends GoalAR {

    @Override
    public CutAR nextFulfillStep() {
        setState(State.FULFILLED);
        if (getIsLastGoal()) {
            getParentAR().setState(State.FULFILLED);
        }
        return this;
    }

    @Override
    public void setState(State newState) {
        if (newState == State.FAILED) {
            super.setState(State.CUTFAILED);
        } else {
            super.setState(newState);
        }
    }

    @Override
    public RuleAR getRuleAR() {
        return null;
    }

    @Override
    public void reset() {
        // CutAR muss nicht resettet werden
    }

    @Override
    public void substitute() {
        // CutAR kann nicht substituiert werden
    }

    @Override
    public void renumber() {
        // CutAR kann nicht renumbered werden
    }

    @Override
    public CutARVisNode createVisNode(boolean trans) {
        return new CutARVisNode(this.getState());
    }

    @Override
    public void deleteRuleAR() {
        // CutAR hat kein RuleAR
    }

    @Override
    public boolean canFulfillDifferently() {
        return false;
    }

    @Override
    public void deleteVisRuleAR() {
        // CutAR hat kein VisRuleAR
    }

    @Override
    public RuleAR getVisRuleAR() {
        return null;
    }

}
