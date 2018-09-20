package edu.kit.ipd.pp.prolog.vipr.model.interpreter;

import edu.kit.ipd.pp.prolog.vipr.model.ast.Term;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Variable;

/**
 * Repräsentiert eine Substitution.
 *
 */
public class Substitution {

    /**
     * Die zu ersetzende Variable.
     */
    private Variable var;

    /**
     * Das Substituens.
     */
    private Term substituens;

    /**
     * Erzeugt eine neue Substitution mit der gegebenen Variable und dem gegebenen
     * Substituens.
     * 
     * @param var
     *            Die zu ersetzende Variable.
     * @param substituens
     *            Das Substituens.
     */
    public Substitution(Variable var, Term substituens) {
        this.var = var;
        this.substituens = substituens;
    }

    /**
     * Gibt die Variable zurück.
     * 
     * @return Die Variable.
     */
    public Variable getVar() {
        return var;
    }

    /**
     * Gibt das Substituens zurück.
     * 
     * @return Das Substituens.
     */
    public Term getSubstituens() {
        return substituens;
    }

    @Override
    public String toString() {
        return var + " = " + substituens;

    }

}
