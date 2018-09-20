package edu.kit.ipd.pp.prolog.vipr.model.ast;

import java.util.LinkedList;
import java.util.List;

import edu.kit.ipd.pp.prolog.vipr.model.interpreter.ArithOrUniGoalAR;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Substitution;

/**
 * Bietet eine abstrakte Grundstruktur für Unifikationsziele, Is-Ziele und Ziele
 * mit Vergleichsoperator, also Ziele mit einem linken Term und einem rechten
 * Term.
 */
public abstract class ArithOrUniGoal extends Goal {

    /**
     * Linker Term des Ziels.
     */
    private Term lhs;

    /**
     * Rechter Term des Ziels.
     */
    private Term rhs;

    /**
     * Liste von Substitutionen.
     */
    private List<Substitution> substitutions;

    /**
     * Erzeugt ein ArithOrUniGoal-Objekt mit einem linken Term und einem rechten
     * Term als Parameter.
     * 
     * @param lhs
     *            Linker Term des Ziels.
     * @param rhs
     *            Rechter Term des Ziels.
     */
    public ArithOrUniGoal(Term lhs, Term rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
        substitutions = new LinkedList<>();
    }

    /**
     * Versucht, das Ziel zu erfüllen.
     * 
     * @return true, wenn das Ziel erfüllt ist, false sonst.
     * @throws CalculateException
     *             Wenn ein Term nicht berechnet werden kann.
     */
    public abstract boolean fulfill() throws CalculateException;

    /**
     * Erstellt eine Kopie vom Ziel mit einem linken Term und einem rechten Term und
     * gibt sie zurück.
     * 
     * @param lhs
     *            Linker Term des Ziels.
     * @param rhs
     *            Rechter Term des Ziels.
     * @return Das ArithOrUniGoal Objekt.
     */
    public abstract ArithOrUniGoal getCopy(Term lhs, Term rhs);

    /**
     * Gibt einen Funktor zurück, der den Operator-Token darstellt.
     * 
     * @return Der Operator-Token.
     */
    public abstract FunctorTerm getOperatorToken();

    @Override
    public ArithOrUniGoal deepCopy() {
        return getCopy(lhs.deepCopy(), rhs.deepCopy());
    }

    @Override
    public ArithOrUniGoalAR createAR(Program program) {
        return new ArithOrUniGoalAR(this);
    }

    @Override
    public void renumber(int counter) {
        lhs.renumber(counter);
        rhs.renumber(counter);
    }

    @Override
    public List<Variable> getVariables() {
        List<Variable> variables = lhs.getVariables();
        variables.addAll(rhs.getVariables());
        return variables;
    }

    @Override
    public void substitute(Substitution substitution) {
        lhs = lhs.substitute(substitution);
        rhs = rhs.substitute(substitution);
    }

    /**
     * Gibt die Liste von Substitutionen für das Ziel zurück.
     * 
     * @return Liste von Substitutionen.
     */
    public List<Substitution> getSubstitutions() {
        return substitutions;
    }

    /**
     * Fügt diesem ArithOrUniGoal Substitutionen hinzu.
     * 
     * @param listOfSubstitutions
     *            Die hinzuzufügenden Substitutionen
     */
    public void addSubstitutions(List<Substitution> listOfSubstitutions) {
        substitutions.addAll(listOfSubstitutions);
    }

    /**
     * Gibt den linken Term zurück.
     * 
     * @return Linker Term.
     */
    public Term getLhs() {
        return lhs;
    }

    /**
     * Gibt den rechten Term zurück.
     * 
     * @return Rechter Term.
     */
    public Term getRhs() {
        return rhs;
    }

    /**
     * Gibt das Ergebnis von der Berechnung des linken Terms zurück.
     * 
     * @return Ganzzahliges Ergebnis der Berechnung des linken Terms.
     * @throws CalculateException
     *             Wenn der linke Term nicht berechnet werden kann.
     */
    public int getCalculatedLhs() throws CalculateException {
        return lhs.calculate();
    }

    /**
     * Gibt das Ergebnis von der Berechnung des rechten Terms zurück.
     * 
     * @return Ganzzahliges Ergebnis der Berechnung des rechten Terms.
     * @throws CalculateException
     *             Wenn der rechte Term nicht berechnet werden kann.
     */
    public int getCalculatedRhs() throws CalculateException {
        return rhs.calculate();
    }

    @Override
    public List<Term> getTokens() {
        LinkedList<Term> token = new LinkedList<>();
        token.addAll(this.lhs.getTokens());
        token.add(this.getOperatorToken());
        token.addAll(this.rhs.getTokens());
        return token;

    }

}
