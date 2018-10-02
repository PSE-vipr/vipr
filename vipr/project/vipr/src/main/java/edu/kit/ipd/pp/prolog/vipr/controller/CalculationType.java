package edu.kit.ipd.pp.prolog.vipr.controller;

/**
 * Dieses Enum unterscheidet verschiedene Berechnungsarten.
 */
public enum CalculationType {

    /**
     * Es wird nur der nächste Schritt berechnet.
     */
    NEXT_STEP,

    /**
     * Es wird bis zur nächsten Lösung gerechnet.
     */
    NEXT_SOLUTION,

    /**
     * Es wird bis zum Schluss gerechnet.
     */
    ALL_SOLUTIONS
}
