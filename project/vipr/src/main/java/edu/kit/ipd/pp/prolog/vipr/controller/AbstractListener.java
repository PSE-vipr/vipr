package edu.kit.ipd.pp.prolog.vipr.controller;

import java.awt.event.ActionListener;

/**
 * Ein Interface, dass dem ActionListener Interface lediglich die Methode
 * setCalculation hinzufügt.
 */
public interface AbstractListener extends ActionListener {

    /**
     * Fügt diesem AbstractListener eine aktive Instanz der Calculation Klasse
     * hinzu.
     * 
     * @param calculation
     *            Eine aktive Instanz der Calculation Klasse.
     */
    void setCalculation(Calculation calculation);

}
