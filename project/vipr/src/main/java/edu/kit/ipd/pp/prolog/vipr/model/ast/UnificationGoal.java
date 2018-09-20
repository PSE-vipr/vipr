package edu.kit.ipd.pp.prolog.vipr.model.ast;

import java.util.List;

import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Substitution;

/**
 * Repräsentiert ein Unifikationsziel.
 */
public class UnificationGoal extends ArithOrUniGoal {

    /**
     * Erzeugt ein Unifikationsziel mit 2 Termen.
     * 
     * @param lhs
     *            Linker Term.
     * @param rhs
     *            Rechter Term.
     */
    public UnificationGoal(Term lhs, Term rhs) {
        super(lhs, rhs);
    }

    @Override
    public boolean fulfill() {
        List<Substitution> unifier;
        try {
            unifier = getLhs().unify(getRhs());
        } catch (UnifyException e) {
            return false;
        }
        addSubstitutions(unifier);
        return true;
    }

    @Override
    public UnificationGoal getCopy(Term lhs, Term rhs) {
        return new UnificationGoal(lhs, rhs);
    }

    @Override
    public FunctorTerm getOperatorToken() {
        return new FunctorTerm("=");
    }

}
