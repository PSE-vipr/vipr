package edu.kit.ipd.pp.prolog.vipr.model.ast;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Substitution;

/**
 * Repräsentiert einen Funktorterm.
 */
public class FunctorTerm extends Term {

    /**
     * Der Name des Funktorterms.
     */
    private String name;

    /**
     * Die Parameter des Funktorterms.
     */
    private List<Term> parameters;

    /**
     * Erzeugt den Funktorterm mit einem Namen.
     * 
     * @param name
     *            Der Name des Funktors.
     */
    public FunctorTerm(String name) {
        this.name = name;
        parameters = new LinkedList<>();
    }

    /**
     * Erzeugt den Funktorterm mit einem Namen und zwei Termen.
     * 
     * @param name
     *            Der Name des Funktors.
     * @param firstTerm
     *            Der erste Parameter des Funktors.
     * @param secondTerm
     *            Der zweite Parameter des Funktors.
     */
    public FunctorTerm(String name, Term firstTerm, Term secondTerm) {
        this.name = name;
        parameters = new LinkedList<>();
        parameters.add(firstTerm);
        parameters.add(secondTerm);
    }

    /**
     * Erzeugt den Funktorterm mit einem Namen und einer Liste von Termen.
     * 
     * @param name
     *            Der Name des Funktors.
     * @param listOfTerms
     *            Die Parameter des Funktors als Terme.
     */
    public FunctorTerm(String name, List<Term> listOfTerms) {
        this.name = name;
        parameters = listOfTerms;
    }

    /**
     * Gibt den Namen des Funktors zurück.
     * 
     * @return Der Name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt die Anzahl an Parametern des Funktors zurück.
     * 
     * @return Die Anzahl an Parametern.
     */
    public int getArity() {
        return parameters.size();
    }

    /**
     * Gibt die Parameter des Funktors zurück.
     * 
     * @return Die Parameter.
     */
    public List<Term> getParameters() {
        return parameters;
    }

    @Override
    public int calculate() throws CalculateException {
        throw new CalculateException("Cannot calculate functor");
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
        FunctorTerm term = (FunctorTerm) o;
        return name.equals(term.name) && parameters.equals(term.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parameters);
    }

    @Override
    public FunctorTerm deepCopy() {
        FunctorTerm copy = new FunctorTerm(name);
        List<Term> copyParameters = new LinkedList<>();
        for (Term parameter : parameters) {
            copyParameters.add(parameter.deepCopy());
        }
        copy.parameters = copyParameters;
        return copy;
    }

    @Override
    public List<Variable> getVariables() {
        List<Variable> variables = new LinkedList<>();
        for (Term term : parameters) {
            variables.addAll(term.getVariables());
        }
        return variables;
    }

    @Override
    public FunctorTerm substitute(Substitution substitution) {
        List<Term> newParameters = new LinkedList<>();
        for (Term parameter : parameters) {
            newParameters.add(parameter.substitute(substitution));
        }
        parameters = newParameters;
        return this;
    }

    @Override
    public void renumber(int counter) {
        for (Term parameter : parameters) {
            parameter.renumber(counter);
        }
    }

    @Override
    public List<Substitution> unifyVar(Term rhs) {
        return null;
    }

    @Override
    public List<Substitution> unifyFunctor(Term rhs) throws UnifyException {
        return rhs.unifyFunctorWithFunctor(this);
    }

    @Override
    public List<Substitution> unifyFunctorWithFunctor(FunctorTerm lhs) throws UnifyException {
        if (name.equals(lhs.name) && getArity() == lhs.getArity()) {
            // lhs und rhs sind Funktoren mit gleichem Namen und gleicher Subtermanzahl n
            List<Substitution> substitutions = new LinkedList<>();
            FunctorTerm lhsCopy = lhs.deepCopy();
            FunctorTerm rhsCopy = deepCopy();
            for (int i = 0; i < getArity(); i++) {
                List<Substitution> unifier = lhsCopy.parameters.get(i).unify(rhsCopy.parameters.get(i));
                substitutions.addAll(unifier);
                for (Substitution substitution : substitutions) {
                    lhsCopy.substitute(substitution);
                    rhsCopy.substitute(substitution);
                }
            }
            return substitutions;
        }
        throw new UnifyException();
    }

    @Override
    public List<Term> getTokens() {
        FunctorTerm identifier = new FunctorTerm(name);
        FunctorTerm openParathesis = new FunctorTerm("(");
        FunctorTerm closeParathesis = new FunctorTerm(")");
        List<Term> paras = new LinkedList<>();
        paras.add(identifier);

        if (!parameters.isEmpty()) {
            paras.add(openParathesis);
        }

        for (Term para : parameters) {
            paras.addAll(para.getTokens());
        }
        if (!parameters.isEmpty()) {
            paras.add(closeParathesis);
        }

        return paras;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(name);
        if (!parameters.isEmpty())
            result.append("(");

        for (int i = 0; i < parameters.size() - 1; i++) {
            Term term = parameters.get(i);
            result.append(term + ", ");
        }
        if (!parameters.isEmpty()) {
            result.append(parameters.get(parameters.size() - 1) + ")");
        }
        return result.toString();
    }

}
