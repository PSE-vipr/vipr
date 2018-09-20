package edu.kit.ipd.pp.prolog.vipr.model.ast;

import java.util.LinkedList;
import java.util.List;

import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Substitution;

/**
 * Ein Term.
 */
public abstract class Term {

    /**
     * Berechnet den Term.
     * 
     * @return Das Ergebnis als Zahl.
     * @throws CalculateException
     *             Wenn der Term nicht berechnet werden kann.
     */
    public abstract int calculate() throws CalculateException;

    /**
     * Gibt die Variablen des Terms zurück.
     * 
     * @return Die Variablen.
     */
    public abstract List<Variable> getVariables();

    /**
     * Gibt einen Term zurück, auf den die gegebene Substitution angewandt wurde.
     * 
     * @param substitution
     *            Die anzuwendende Substitution.
     * @return Der Term, auf den die Substitution angewandt wurde.
     */
    public abstract Term substitute(Substitution substitution);

    /**
     * Gibt eine tiefe Kopie dieses Terms zurück.
     * 
     * @return Die Tiefe Kopie dieses Terms.
     */
    public abstract Term deepCopy();

    /**
     * Nummeriert die Variablen dieses Terms um. Setzt die ids aller Variablen
     * dieses Terms auf die gegebene Zahl.
     * 
     * @param counter
     *            Die Zahl, auf die die Variablen gesetzt werden sollen.
     */
    public abstract void renumber(int counter);

    /**
     * Unifiziert eine Variable mit einem Term.
     * 
     * @param rhs
     *            Der Term, mit dem unifiziert werden soll.
     * @return Eine Liste von Substitutionen, die den Unifier darstellt. Null, wenn
     *         diese Methode auf einem Term aufgerufen wird, der keine Variable ist.
     * @throws UnifyException
     *             Wenn die Unifikation fehlschlägt.
     */
    public abstract List<Substitution> unifyVar(Term rhs) throws UnifyException;

    /**
     * Unifiziert einen Funktorterm mit einem Term.
     * 
     * @param rhs
     *            Der Term, mit dem unifiziert werden soll.
     * @return Eine Liste von Substitutionen, die den Unifier darstellt.
     * @throws UnifyException
     *             Wenn die Unifikation fehlschlägt oder wenn diese Methode auf
     *             einem Term aufgerufen wird, der kein Funktorterm ist.
     */
    public abstract List<Substitution> unifyFunctor(Term rhs) throws UnifyException;

    /**
     * Unifiziert einen Funktorterm mit einem Funktorterm.
     * 
     * @param lhs
     *            Der Funktorterm, mit dem unifiziert werden soll.
     * @return Eine Liste von Substitutionen, die den Unifier darstellt.
     * @throws UnifyException
     *             Wenn die Unifikation fehlschlägt oder wenn diese Methode auf
     *             einem Term aufgerufen wird, der kein Funktorterm ist.
     */
    public abstract List<Substitution> unifyFunctorWithFunctor(FunctorTerm lhs) throws UnifyException;

    /**
     * Gibt eine Liste von Termen zurück, die die Tokens dieses Terms darstellen.
     * 
     * @return Die Tokens.
     */
    public abstract List<Term> getTokens();

    /**
     * Gibt eine Liste von Termen zurück, die Tokens repräsentieren, die diesen Term
     * als Ende einer Liste darstellen.
     * 
     * @return Die Tokens, die diesen Term als Ende einer Liste darstellen.
     */
    public List<Term> getTailTokens() {
        List<Term> tokens = new LinkedList<>();
        tokens.add(new FunctorTerm("|"));
        tokens.addAll(getTokens());
        return tokens;
    }

    /**
     * Gibt einen String zurück, der diesen Term als Ende einer Liste darstellt.
     * 
     * @return Der String, der diesen Term als Ende einer Liste darstellt.
     */
    public String getTailString() {
        return "|" + toString();
    }

    /**
     * Unifiziert diesen Term mit dem gegebenen Term rhs.
     * 
     * @param rhs
     *            Der Term, mit dem unifiziert werden soll.
     * @return Eine Liste von Substitutionen, die den Unifier darstellt.
     * @throws UnifyException
     *             Wenn die Unifikation fehlschlägt.
     */
    public List<Substitution> unify(Term rhs) throws UnifyException {
        List<Substitution> unifier = new LinkedList<>();
        List<Substitution> substitutions;

        if (equals(rhs)) { // syntaktische Gleichheit
            // Gleichung ist schon erfüllt
            return unifier;
        }
        if ((substitutions = unifyVar(rhs)) != null) {
            unifier.addAll(substitutions);
        } else if ((substitutions = rhs.unifyVar(this)) != null) {
            unifier.addAll(substitutions);
        } else {
            unifier.addAll(unifyFunctor(rhs));
        }
        return unifier;
    }

}
