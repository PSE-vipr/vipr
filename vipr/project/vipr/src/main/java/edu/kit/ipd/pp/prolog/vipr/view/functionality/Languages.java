package edu.kit.ipd.pp.prolog.vipr.view.functionality;

import java.util.Locale;

/**
 * In diesem Enum werden alle unterstützten Sprachen gespeichert.
 *
 */
public enum Languages {

    /**
     * Deutsche Sprache
     */
    DEUTSCH(Locale.GERMAN),

    /**
     * Englische Sprache
     */
    ENGLISH(Locale.ENGLISH);

    /**
     * Das locale.
     */
    private Locale locale;

    /**
     * Privater Konstruktor.
     * 
     * @param l
     *            Das locale.
     */
    private Languages(Locale l) {
        this.locale = l;
    }

    /**
     * Gibt das locale zurück.
     * 
     * @return Das locale.
     */
    public Locale getLocale() {
        return this.locale;
    }

}
