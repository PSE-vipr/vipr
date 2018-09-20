package edu.kit.ipd.pp.prolog.vipr.model.ast;

import java.util.LinkedList;
import java.util.List;

/**
 * Repräsentiert ein Prolog-Programm.
 */
public class Program {

    /**
     * Die Regeln dieses Prolog-Programms.
     */
    private List<Rule> rules;

    /**
     * Erzeugt ein Programm mit Regeln.
     * 
     * @param listOfRules
     *            Eine Liste mit allen Regeln des Programms.
     */
    public Program(List<Rule> listOfRules) {
        rules = listOfRules;
    }

    /**
     * Sucht nach Regeln mit einem bestimmten Namen und einer bestimmten Anzahl an
     * Parametern.
     * 
     * @param name
     *            Der Name, den Regeln haben sollen.
     * @param arity
     *            Die Anzahl an Parametern, die Regeln haben sollen.
     * @return Die gefundenen Regeln.
     */
    public List<Rule> searchRules(String name, int arity) {
        List<Rule> result = new LinkedList<>();
        for (Rule rule : rules) {
            if (rule.getName().equals(name) && rule.getArity() == arity) {
                result.add(rule);
            }
        }
        return result;
    }

}
