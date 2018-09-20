package edu.kit.ipd.pp.prolog.vipr.model.ast;

/**
 * Klasse, die darstellt, dass eine Unifikation fehlgeschlagen ist.
 */
@SuppressWarnings("serial")
public class UnifyException extends Exception {

    /**
     * Erzeugt die UnifyException.
     */
    public UnifyException() {
        super("Unification failed.");
    }
}