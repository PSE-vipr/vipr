package edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.substitutiontable;

import java.util.LinkedList;
import java.util.List;

public class Entry {

    /**
     * Tiefe in der sich der Entry in der Matrix befindet
     */
    private int depth;

    /**
     * Den String der in der HTML-Tabelle angezeigt werden soll
     */
    private String label;

    /**
     * Die Spalten die unter den Tabelleneintrag liegen
     */
    private List<Entry> children = new LinkedList<>();

    /**
     * In wie viele Spalten sich dieser Entry insgesamt aufteilt
     */
    private int splitInto = 1;

    /**
     * Die HTML-Farbe des Entry
     */
    private String currentColor;

    /**
     * Konstruktor
     * 
     * @param depth
     *            Tiefe des Entry
     * @param label
     *            der Text des Tabelleneintrags
     */
    public Entry(int depth, String label) {
        this.depth = depth;
        this.label = label;

    }

    /**
     * Setter
     * 
     * @param children
     *            Spalten unter dem Entry
     */
    public void setChildren(List<Entry> children) {
        this.children = children;
        splitInto = 0;

        // aktualisiert splitInto
        for (Entry e : children) {
            splitInto += e.splitInto;
        }

    }

    /**
     * Getter
     * 
     * @return Spalten unter dem Entry
     */
    public List<Entry> getChildren() {
        return children;
    }

    /**
     * Setter
     * 
     * @param label
     *            was gesetzt wird
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Getter
     * 
     * @return label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Getter
     * 
     * @return depth
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Getter
     * 
     * @return splitInto
     */
    public int getSplitInto() {
        return splitInto;
    }

    /**
     * Setter
     * 
     * @param color
     *            Die Farbe
     */
    public void setColor(String color) {
        this.currentColor = color;
    }

    /**
     * Getter
     * 
     * @return currentColor
     */
    public String getColor() {
        return currentColor;
    }

}
