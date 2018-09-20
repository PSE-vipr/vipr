package edu.kit.ipd.pp.prolog.vipr.model.ast;

/**
 * Repräsentiert die arithmetische Operation '+'.
 */
public class Plus extends Operator {

    /**
     * Erzeugt eine arithmetische Operation mit zwei Termen.
     * 
     * @param lhs
     *            Linker Term.
     * @param rhs
     *            Rechter Term.
     */
    public Plus(Term lhs, Term rhs) {
        super("+", lhs, rhs);
    }

    @Override
    public int operation(int lhs, int rhs) {
        return lhs + rhs;
    }

    @Override
    public boolean equals(Object o) {
        if (null == o) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!o.getClass().equals(getClass())) {
            return false;
        }
        Plus plus = (Plus) o;
        return getName().equals(plus.getName()) && getParameters().equals(plus.getParameters());
    }

    @Override
    public Plus deepCopy() {
        return new Plus(getParameters().get(0).deepCopy(), getParameters().get(1).deepCopy());
    }

    @Override
    public String toString() {
        return getParameters().get(0) + " + " + getParameters().get(1);
    }

    @Override
    public FunctorTerm getOperatorToken() {
        return new FunctorTerm("+");
    }

}
