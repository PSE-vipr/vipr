package edu.kit.ipd.pp.prolog.vipr.model.ast;

import java.util.List;

import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Substitution;

/**
 * Repräsentiert ein Is-Ziel.
 */
public class Is extends ArithOrUniGoal {

    /**
     * Erzeugt das Is-Ziel mit 2 Termen.
     * 
     * @param lhs
     *            Linker Term.
     * @param rhs
     *            Rechter Term.
     */
    public Is(Term lhs, Term rhs) {
        super(lhs, rhs);
    }

    @Override
    public boolean fulfill() throws CalculateException {
        NumberTerm newRhs = new NumberTerm(getCalculatedRhs());
        List<Substitution> unifier;
        try {
            unifier = getLhs().unify(newRhs);
        } catch (UnifyException e) {
            return false;
        }
        addSubstitutions(unifier);
        return true;
    }

    @Override
    public Is getCopy(Term lhs, Term rhs) {
        return new Is(lhs, rhs);
    }

    @Override
    public FunctorTerm getOperatorToken() {
        return new FunctorTerm("is");
    }

}
