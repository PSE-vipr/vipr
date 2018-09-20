package edu.kit.ipd.pp.prolog.vipr.model.parser;

/**
 * Klasse, die einen Parser-Fehler darstellt.
 */
@SuppressWarnings("serial")
public class ParseException extends Exception {

    /**
     * Der Titel dieser ParseException.
     */
    private static final String PRS_EXP = "Parse Error";

    /**
     * Erzeugt die ParseException mit der Nachricht.
     * 
     * @param message
     *            Die Nachricht.
     */
    public ParseException(String message) {
        super(message);
    }

    /**
     * Gibt den Titel zurück.
     * 
     * @return Der Titel.
     */
    public static String getTitle() {
        return PRS_EXP;
    }

}
