package edu.kit.ipd.pp.prolog.vipr.model.ast;

/**
 * Klasse, die einen Berechnungsfehler darstellt.
 */
@SuppressWarnings("serial")
public class CalculateException extends Exception {

    /**
     * Der Titel dieser CalculateException.
     */
    private static final String CLC_EXP = "Calculation Error";

    /**
     * Erzeugt die CalculateException mit der Nachricht.
     * 
     * @param message
     *            Die Nachricht.
     */
    public CalculateException(String message) {
        super(message);
    }

    /**
     * Gibt den Titel zurück.
     * 
     * @return Der Titel.
     */
    public static String getTitle() {
        return CLC_EXP;
    }

}
