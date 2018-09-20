package edu.kit.ipd.pp.prolog.vipr.model.ast;

import java.util.List;

import edu.kit.ipd.pp.prolog.vipr.model.interpreter.GoalAR;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Substitution;

/**
 * Bietet eine abstrakte Grundstruktur für ein Ziel.
 */
public abstract class Goal {

    /**
     * Erzeugt eine aktive Instanz, die in einem Abarbeitungsbaum verwendet werden
     * kann.
     * 
     * @param program
     *            Das Programm mit Regeln wird übergeben, muss aber nicht von jedem
     *            Ziel verwendet werden.
     * @return Die aktive Instanz des Ziels.
     */
    public abstract GoalAR createAR(Program program);

    /**
     * Nummeriert die Variablen dieses Ziels um. Setzt die ids aller Variablen
     * dieses Ziels auf die gegebene Zahl.
     * 
     * @param counter
     *            Die Zahl, auf die die Variablen gesetzt werden sollen.
     */
    public abstract void renumber(int counter);

    /**
     * Gibt die Variablen dieses Ziels zurück.
     * 
     * @return Die Variablen dieses Ziels.
     */
    public abstract List<Variable> getVariables();

    /**
     * Wendet die gegebene Substitution auf dieses Ziel an. Ersetzt also Variablen
     * dieses Ziels gemäß der gegebenen Substitution.
     * 
     * @param substitution
     *            Die anzuwendende Substitution
     */
    public abstract void substitute(Substitution substitution);

    /**
     * Gibt eine tiefe Kopie dieses Ziels zurück.
     * 
     * @return Die tiefe Kopie.
     */
    public abstract Goal deepCopy();

    /**
     * Gibt eine Liste von Termen zurück, die die Tokens dieses Ziels darstellen.
     * 
     * @return Die Tokens.
     */
    public abstract List<Term> getTokens();

}
