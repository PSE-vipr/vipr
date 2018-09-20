package edu.kit.ipd.pp.prolog.vipr.model.ast;

/**
 * Repräsentiert einen Vergleich zweier Terme mit dem Vergleichsoperator '>'.
 */
public class Greater extends ArithOrUniGoal {

    /**
     * Erzeugt das Vergleichsziel mit 2 Termen.
     * 
     * @param lhs
     *            Linker Term.
     * @param rhs
     *            Rechter Term.
     */
    public Greater(Term lhs, Term rhs) {
        super(lhs, rhs);
    }

    @Override
    public boolean fulfill() throws CalculateException {
        return getCalculatedLhs() > getCalculatedRhs();
    }

    @Override
    public Greater getCopy(Term lhs, Term rhs) {
        return new Greater(lhs, rhs);
    }

    @Override
    public FunctorTerm getOperatorToken() {
        return new FunctorTerm(">");
    }

}
