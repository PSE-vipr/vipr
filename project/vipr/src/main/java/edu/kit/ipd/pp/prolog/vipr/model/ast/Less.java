package edu.kit.ipd.pp.prolog.vipr.model.ast;

/**
 * Repräsentiert einen Vergleich zweier Terme mit dem Vergleichsoperator '<'.
 */
public class Less extends ArithOrUniGoal {

    private static final String LESS_HTML = "&#60;";

    /**
     * Erzeugt das Vergleichsziel mit 2 Termen.
     * 
     * @param lhs
     *            Linker Term.
     * @param rhs
     *            Rechter Term.
     */
    public Less(Term lhs, Term rhs) {
        super(lhs, rhs);
    }

    @Override
    public boolean fulfill() throws CalculateException {
        return getCalculatedLhs() < getCalculatedRhs();
    }

    @Override
    public Less getCopy(Term lhs, Term rhs) {
        return new Less(lhs, rhs);
    }

    @Override
    public FunctorTerm getOperatorToken() {

        return new FunctorTerm(LESS_HTML);
    }

}
