package edu.kit.ipd.pp.prolog.vipr.model.ast;

import java.util.LinkedList;
import java.util.List;

/**
 * Repräsentiert eine Regel.
 */
public class Rule {

    /**
     * Die Nummer der Regel.
     */
    private int id;

    /**
     * Die Position im Quellcode, in der die Regel anfängt.
     */
    private int startPos;

    /**
     * Die Position im Quellcode, in der die Regel aufhört.
     */
    private int endPos;

    /**
     * Der Funktorterm der Regel.
     */
    private FunctorTerm functorTerm;

    /**
     * Die Teilziele der Regel.
     */
    private List<Goal> goals;

    /**
     * Initialisiert eine Regel mit Funktorterm.
     * 
     * @param functorTerm
     *            Der Funktorterm der Regel.
     */
    public Rule(FunctorTerm functorTerm) {
        this.functorTerm = functorTerm;
        goals = new LinkedList<>();
        id = 0;
    }

    /**
     * Initialisiert eine Regel mit Funktorterm und Teilzielen.
     * 
     * @param functorTerm
     *            Der Funktorterm der Regel.
     * @param listOfGoals
     *            Eine Liste mit Teilzielen der Regel.
     */
    public Rule(FunctorTerm functorTerm, List<Goal> listOfGoals) {
        this.functorTerm = functorTerm;
        goals = listOfGoals;
        id = 0;
    }

    /**
     * Gibt eine tiefe Kopie des Rule-Objekts zurück.
     * 
     * @return tiefe Kopie des Rule-Objekts.
     */
    public Rule deepCopy() {
        Rule rule = new Rule(functorTerm.deepCopy());
        rule.setPos(startPos, endPos);
        rule.setId(id);
        return rule;
    }

    /**
     * Legt die Anfangs- und Endposition der Regel im Quellcode fest.
     * 
     * @param startPos
     *            Die Position im Quellcode, an der die Regel anfängt.
     * @param endPos
     *            Die Position im Quellcode, an der die Regel aufhört.
     */
    public void setPos(int startPos, int endPos) {
        this.startPos = startPos;
        this.endPos = endPos;
    }

    /**
     * Gibt den Namen der Regel zurück.
     * 
     * @return Der Name der Regel.
     */
    public String getName() {
        return functorTerm.getName();
    }

    /**
     * Gibt die Anzahl der Parameter der Regel zurück.
     * 
     * @return Anzahl der Parameter.
     */
    public int getArity() {
        return functorTerm.getArity();
    }

    /**
     * Gibt den Funktorterm der Regel zurück.
     * 
     * @return Der Funktorterm der Regel.
     */
    public FunctorTerm getFunctorTerm() {
        return functorTerm;
    }

    /**
     * Gibt die Teilziele der Regel zurück.
     * 
     * @return Liste von Teilzielen.
     */
    public List<Goal> getGoals() {
        return goals;
    }

    /**
     * Gibt die Anfangs- und die Endposition der Regel im Quellcode als Array
     * zurück.
     * 
     * @return Das Array mit der Anfangs- und der Endposition.
     */
    public int[] getPos() {
        return new int[] {startPos, endPos};
    }

    /**
     * Legt die Nummer der Regel fest.
     * 
     * @param id
     *            Die neue Nummer der Regel.
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String result = functorTerm.toString();
        int outputId = id + 1;
        if (result.indexOf('(') == -1) {
            return result + "_" + outputId;
        }
        return result.replaceFirst("\\(", "_" + outputId + "(");
    }

}
