package edu.kit.ipd.pp.prolog.vipr.model.interpreter;

/**
 * Repräsentiert den Status eines activation records.
 *
 */
public enum State {

    /**
     * Zustand, der beschreibt, dass der activation record noch in Bearbeitung ist.
     */
    PENDING,

    /**
     * Zustand, der beschreibt, dass der activation record erfüllt ist.
     */
    FULFILLED,

    /**
     * Zustand, der beschreibt, dass der activation record fehlgeschlagen ist.
     */
    FAILED,

    /**
     * Zustand, der beschreibt, dass der activation record noch nicht besucht wurde.
     */
    UNVISITED,

    /**
     * Zustand, der beschreibt, dass der activation record in Folge eines Cuts
     * fehlgeschlagen ist.
     */
    CUTFAILED
}
