package edu.kit.ipd.pp.prolog.vipr.model.ast;

/**
 * Klasse, die den Fehler, dass zu einem Funktorziel keine passende Regel
 * gefunden wurde, darstellt.
 */
@SuppressWarnings("serial")
public class NoRuleException extends Exception {

    /**
     * Der Titel dieser NoRuleException.
     */
    private static final String NR_EXP = "No rule found";

    /**
     * Erzeugt die NoRuleException.
     */
    public NoRuleException() {
        super("Cannot find matching rule");
    }

    /**
     * Gibt den Titel zurück.
     * 
     * @return Der Titel.
     */
    public static String getTitle() {
        return NR_EXP;
    }

}