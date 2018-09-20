package edu.kit.ipd.pp.prolog.vipr.model.ast;

/**
 * Repräsentiert die arithmetische Operation '-'.
 */
public class Minus extends Operator {

    /**
     * Erzeugt die arithmetische Operation mit zwei Termen.
     * 
     * @param lhs
     *            Linker Term.
     * @param rhs
     *            Rechter Term.
     */
    public Minus(Term lhs, Term rhs) {
        super("-", lhs, rhs);
    }

    @Override
    public int operation(int lhs, int rhs) {
        return lhs - rhs;
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
        Minus minus = (Minus) o;
        return getName().equals(minus.getName()) && getParameters().equals(minus.getParameters());
    }

    @Override
    public Minus deepCopy() {
        return new Minus(getParameters().get(0).deepCopy(), getParameters().get(1).deepCopy());
    }

    @Override
    public String toString() {
        return getParameters().get(0) + " - " + getParameters().get(1);
    }

    @Override
    public FunctorTerm getOperatorToken() {
        return new FunctorTerm("-");
    }

}
