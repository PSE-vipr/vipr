package edu.kit.ipd.pp.prolog.vipr.controller;

import edu.kit.ipd.pp.prolog.vipr.model.interpreter.ParentAR;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.GraphTree;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.ParentARVisNode;

/**
 * Speichert die Grafik für die "Schritt-Zurück" Funktion.
 *
 */
public class Memento {

    /**
     * Die Grafik mit transitiven Substitutionen.
     */
    private GraphTree graphWithTransSubs;

    /**
     * Die Grafik ohne transitiven Substitutionen.
     */
    private GraphTree graphWithoutTransSubs;

    /**
     * Zeigt an, dass ein Schritt zurückgegangen wurde.
     */
    private boolean wentBack;

    /**
     * Wurzelknoten für die Visualisierung der Grafik mit transitiven
     * Substitutionen.
     */
    private ParentARVisNode rootVisNodeTrue;

    /**
     * Wurzelknoten für die Visualisierung der Grafik ohne transitive
     * Substitutionen.
     */
    private ParentARVisNode rootVisNodeFalse;

    /**
     * Erzeugt einen Memento.
     */
    public Memento() {
        wentBack = false;
    }

    /**
     * Setter für den Wurzelknoten für die Visualisierung der Grafik.
     * 
     * @param root
     *            Der Wurzelknoten.
     */
    public void setRootVisNode(ParentAR root) {
        this.rootVisNodeTrue = root.createVisNode(true);
        this.rootVisNodeFalse = root.createVisNode(false);
        graphWithTransSubs = null;
        graphWithoutTransSubs = null;
    }

    /**
     * Gibt die Grafik zurück.
     * 
     * @param trans
     *            Zeigt an, ob die Grafik mit oder ohne transiiven Substitutionen
     *            zurückgegeben werden soll.
     * @return Die Grafik.
     */
    public GraphTree getGraph(boolean trans) {
        if (trans) {
            if (null == graphWithTransSubs) {
                graphWithTransSubs = new GraphTree(rootVisNodeTrue);
            }
            return graphWithTransSubs;
        }
        if (null == graphWithoutTransSubs) {
            graphWithoutTransSubs = new GraphTree(rootVisNodeFalse);
        }
        return graphWithoutTransSubs;
    }

    /**
     * Setzt wentBack auf den gegebenen Wert.
     * 
     * @param wentBack
     *            Der neue Wert von wentBack.
     */
    public void setWentBack(boolean wentBack) {
        this.wentBack = wentBack;
    }

    /**
     * Gibt zurück, ob ein Schritt zurückgegangen wurde.
     * 
     * @return true, wenn ein Schritt zurückgegangen wurde, false sonst
     */
    public boolean getWentBack() {
        return wentBack;
    }

}
