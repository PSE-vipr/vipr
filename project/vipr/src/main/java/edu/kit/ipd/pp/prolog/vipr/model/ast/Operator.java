package edu.kit.ipd.pp.prolog.vipr.model.ast;

import java.util.LinkedList;
import java.util.List;

/**
 * Bietet eine abstrakte Gundstruktur für eine arithmetische Operation.
 */
public abstract class Operator extends FunctorTerm {

    /**
     * Erzeugt eine arithmetische Operation mit zwei Termen.
     * 
     * @param name
     *            Der Name der arithmetischen Operation.
     * @param lhs
     *            Der linke Term.
     * @param rhs
     *            Der rechte Term.
     */
    public Operator(String name, Term lhs, Term rhs) {
        super(name, lhs, rhs);
    }

    /**
     * Gibt einen Funktor zurück, der den Operator-Token darstellt.
     * 
     * @return Der Operator-Token.
     */
    public abstract FunctorTerm getOperatorToken();

    /**
     * Berechnet das Ergebnis der Operation auf den beiden Zahlen.
     * 
     * @param lhs
     *            Die linke Zahl.
     * @param rhs
     *            Die rechte Zahl.
     * @return Das Ergebnis der Operation.
     */
    public abstract int operation(int lhs, int rhs);

    @Override
    public abstract Operator deepCopy();

    @Override
    public int calculate() throws CalculateException {
        Term lhs = getParameters().get(0);
        Term rhs = getParameters().get(1);
        return operation(lhs.calculate(), rhs.calculate());
    }

    @Override
    public List<Term> getTokens() {
        List<Term> tokens = new LinkedList<>();
        Term lhs = getParameters().get(0);
        Term rhs = getParameters().get(1);
        tokens.addAll(lhs.getTokens());
        tokens.add(this.getOperatorToken());
        tokens.addAll(rhs.getTokens());
        return tokens;

    }

}
