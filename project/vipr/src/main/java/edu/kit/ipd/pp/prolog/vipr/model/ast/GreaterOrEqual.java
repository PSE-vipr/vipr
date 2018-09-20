package edu.kit.ipd.pp.prolog.vipr.model.ast;

/**
 * Repräsentiert einen Vergleich zweier Terme mit dem Vergleichsoperator '>='.
 */
public class GreaterOrEqual extends ArithOrUniGoal {

    /**
     * Erzeugt das Vergleichsziel mit 2 Termen.
     * 
     * @param lhs
     *            Linker Term.
     * @param rhs
     *            Rechter Term.
     */
    public GreaterOrEqual(Term lhs, Term rhs) {
        super(lhs, rhs);
    }

    @Override
    public boolean fulfill() throws CalculateException {
        return getCalculatedLhs() >= getCalculatedRhs();
    }

    @Override
    public GreaterOrEqual getCopy(Term lhs, Term rhs) {
        return new GreaterOrEqual(lhs, rhs);
    }

    @Override
    public FunctorTerm getOperatorToken() {
        return new FunctorTerm(">=");
    }

}
