package edu.kit.ipd.pp.prolog.vipr.model.ast;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Substitution;

/**
 * Repräsentiert eine Variable.
 */
public class Variable extends Term {

    /**
     * Der Name der Variable.
     */
    private String name;

    /**
     * Die Nummer der Variable.
     */
    private int id;

    /**
     * Erzeugt eine Variable mit einem Namen.
     * 
     * @param name
     *            Der Name der Variable.
     */
    public Variable(String name) {
        this.name = name;
        id = 0;
    }

    @Override
    public int calculate() throws CalculateException {
        throw new CalculateException("Uninstanciated Variable");
    }

    @Override
    public List<Variable> getVariables() {
        List<Variable> variables = new LinkedList<>();
        variables.add(this);
        return variables;
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
        Variable var = (Variable) o;
        return name.equals(var.name) && id == var.id;
    }

    @Override
    public Variable deepCopy() {
        Variable var = new Variable(name);
        var.id = id;
        return var;
    }

    @Override
    public Term substitute(Substitution substitution) {
        if (equals(substitution.getVar())) {
            return substitution.getSubstituens().deepCopy();
        }
        return this;
    }

    @Override
    public void renumber(int counter) {
        id = counter;
    }

    @Override
    public List<Substitution> unifyVar(Term rhs) throws UnifyException {
        if (rhs.getVariables().contains(this)) {
            // Gleichung kann ohne unendliche Terme nicht gelöst werden
            throw new UnifyException();
        }
        List<Substitution> substitutions = new LinkedList<>();
        substitutions.add(new Substitution(this, rhs));
        return substitutions;
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
        String s = name;
        if (id != 0) {
            s = s + "_" + id;
        }
        return s;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

}
