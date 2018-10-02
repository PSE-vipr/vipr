package edu.kit.ipd.pp.prolog.vipr.controller;

import java.util.LinkedList;
import java.util.List;

/**
 * Diese Klasse verwaltet die Beziehungen zwischen AbstractListenern und aktiven
 * Instanzen der Calculation Klasse.
 */
public final class CalculationManager {

    /**
     * Die einzige Instanz dieser Klasse.
     */
    private static CalculationManager calculationManager;

    /**
     * Alle Listener die sich zum Verwalten angemeldet haben.
     */
    private List<AbstractListener> managedListeners;

    /**
     * privater Konstruktor, da Singleton
     */
    private CalculationManager() {
        managedListeners = new LinkedList<>();
    }

    /**
     * Gibt die einzige Instanz dieser Klasse zurück oder erstellt eine neue, falls
     * keine existiert.
     * 
     * @return die einzige Instnanz dieser Klasse.
     */
    public static synchronized CalculationManager getInstance() {
        if (calculationManager == null) {
            calculationManager = new CalculationManager();
            return calculationManager;
        } else {
            return calculationManager;
        }
    }

    /**
     * Fügt neue Listener zur Liste der zu verwaltenden Lisener hinzu.
     * 
     * @param abstractListener
     *            Ein Listener, der sich anmelden will.
     */
    public void addListener(AbstractListener abstractListener) {
        managedListeners.add(abstractListener);
    }

    /**
     * Informiert alle angemeldeten Listener über die Existenz einer neuen Instanz
     * der Calculation Klasse.
     * 
     * @param calculation
     *            Die neue Instanz.
     */
    public void updateListener(Calculation calculation) {
        for (AbstractListener abstractListener : managedListeners) {
            abstractListener.setCalculation(calculation);
        }
    }
}
