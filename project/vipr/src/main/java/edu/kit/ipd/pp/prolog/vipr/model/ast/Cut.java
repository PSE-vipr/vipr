package edu.kit.ipd.pp.prolog.vipr.model.ast;

import java.util.LinkedList;
import java.util.List;

import edu.kit.ipd.pp.prolog.vipr.model.interpreter.CutAR;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Substitution;

/**
 * Repräsentiert einen Cut.
 */
public class Cut extends Goal {

    @Override
    public CutAR createAR(Program program) {
        return new CutAR();
    }

    @Override
    public void renumber(int counter) {
        // Cut kann nicht renumbered werden
    }

    @Override
    public List<Variable> getVariables() {
        return new LinkedList<>();
    }

    @Override
    public void substitute(Substitution substitution) {
        // Cut kann nicht substituiert werden
    }

    @Override
    public List<Term> getTokens() {
        return new LinkedList<>();
    }

    @Override
    public Cut deepCopy() {
        return new Cut();
    }

}
