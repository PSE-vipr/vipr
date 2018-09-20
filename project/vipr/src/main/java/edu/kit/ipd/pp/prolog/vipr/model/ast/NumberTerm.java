package edu.kit.ipd.pp.prolog.vipr.model.ast;

import java.util.LinkedList;
import java.util.List;

import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Substitution;

/**
 * Repräsentiert eine Zahl.
 */
public class NumberTerm extends Term {

    /**
     * Der Wert der Zahl.
     */
    private int value;

    /**
     * Erzeugt eine Zahl mit einem Wert.
     * 
     * @param value
     *            Der Wert der Zahl.
     */
    public NumberTerm(int value) {
        this.value = value;
    }

    @Override
    public int calculate() {
        return value;
    }

    @Override
    public List<Variable> getVariables() {
        return new LinkedList<>();
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
        NumberTerm number = (NumberTerm) o;
        return value == number.value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public NumberTerm deepCopy() {
        return new NumberTerm(value);
    }

    @Override
    public NumberTerm substitute(Substitution substitution) {
        return this;
    }

    @Override
    public void renumber(int counter) {
        // NumberTerm kann nicht renumbered werden
    }

    @Override
    public List<Substitution> unifyVar(Term rhs) {
        return null;
    }

    @Override
    public List<Substitution> unifyFunctor(Term rhs) throws UnifyException {
        throw new UnifyException();
    }

    @Override
    public List<Substitution> unifyFunctorWithFunctor(FunctorTerm lhs) throws UnifyException {
        throw new UnifyException();
    }

    @Override
    public List<Term> getTokens() {
        List<Term> parameters = new LinkedList<>();
        parameters.add(this);
        return parameters;
    }

    @Override
    public String toString() {
        return value + "";
    }

}
