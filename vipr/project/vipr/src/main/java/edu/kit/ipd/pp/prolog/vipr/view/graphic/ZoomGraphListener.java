package edu.kit.ipd.pp.prolog.vipr.view.graphic;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JScrollPane;

import com.mxgraph.swing.mxGraphComponent;

/**
 * Dieser Listener realisiert das Vergrößern und Verkleinern der Grafik mit dem
 * Mausrad.
 *
 */
public class ZoomGraphListener implements MouseWheelListener {

    private mxGraphComponent graphComponent;

    /**
     * Konstruktor des Listeners.
     * 
     * @param graphComponent
     *            Die Grafik-Komponente, auf der der Graph dargestellt wird.
     */
    public ZoomGraphListener(mxGraphComponent graphComponent) {
        this.graphComponent = graphComponent;
        this.graphComponent.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.graphComponent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            zoomIn();
        } else if (e.getWheelRotation() > 0) {
            zoomOut();
        }
        e.consume();
    }

    /**
     * Vergrößert die Grafik um eine Stufe.
     */
    private void zoomIn() {
        if (this.graphComponent.getGraph().getView().getScale() < 2.5) {
            if (this.graphComponent.getGraph().getView().getScale() > 0.2) {
                this.graphComponent.zoom(1.1);
            } else {
                this.graphComponent.zoom(2);
            }
        }
    }

    /**
     * Verkleinert die Grafik um eine Stufe, sofern dabei die Mindestgröße nicht
     * unterschritten wird.
     */
    private void zoomOut() {
        if (this.graphComponent.getGraph().getView().getScale() > 0.05) {
            this.graphComponent.zoom(0.9);
        }
    }
}
