package edu.kit.ipd.pp.prolog.vipr.view.graphic;

import java.awt.Dimension;

import javax.swing.JPanel;

import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

/**
 * Diese Klasse repräsentiert das Grafikfeld der GUI.
 *
 */
@SuppressWarnings("serial")
public class GraphicPane extends JPanel {

    /**
     * Listener, der das Zoomen mit dem Mausrad realisiert.
     */
    private ZoomGraphListener zoomGraphListener;

    /**
     * Beinhaltet die eigentliche Grafik und dessen Darstellung.
     */
    private mxGraphComponent graphComponent;

    private double diff = 0;

    private boolean needReset = false;

    /**
     * Konstruktor des Grafikfeldes.
     * 
     * @param graph
     *            Der Graph, mit dem das Grafikfeld initialisiert wird.
     */
    public GraphicPane(mxGraph graph) {
        this.setName("graphicPane");

        graphComponent = new mxGraphComponent(graph);
        this.add(graphComponent);

        // Erstellung und Zuweisung der Listener
        zoomGraphListener = new ZoomGraphListener(graphComponent);

        graphComponent.addMouseWheelListener(zoomGraphListener);

        // Grafik soll nicht editierbar sein
        graphComponent.setEnabled(false);

        this.getGraphComponent().getViewport().addChangeListener(e -> needReset = true);
    }

    /**
     * Getter für den Zoom-Listener.
     * 
     * @return den Zoom Listener
     */
    public ZoomGraphListener getZoomGraphListener() {
        return this.zoomGraphListener;
    }

    /**
     * Setzt die Größe des Panels und des mxGraphComponent
     */
    @Override
    public void setPreferredSize(Dimension d) {
        super.setPreferredSize(d);
        graphComponent.setPreferredSize(d);

    }

    /**
     * Getter für den mxGraphComponent
     * 
     * @return den mxGraphComponent
     */
    public mxGraphComponent getGraphComponent() {
        return this.graphComponent;
    }

    /**
     * Setzt die neue Grafik
     * 
     * @param graph
     *            die neue Grafik
     */
    public void setGraph(mxGraph graph) {
        graphComponent.setGraph(graph);
        // Lädt das Grafikfeld neu um das Anzeigen zu ermöglichen
        graphComponent.refresh();

        // Setzt die Grafik zentral.
        double widthLayout = graphComponent.getLayoutAreaSize().getWidth();
        double heightLayout = graphComponent.getLayoutAreaSize().getHeight();

        double width = graph.getGraphBounds().getWidth();

        if (widthLayout - width > 0) {
            graph.getModel().setGeometry(graph.getDefaultParent(),
                    new mxGeometry((widthLayout - (width - diff)) / 2, 10, widthLayout, heightLayout));
            diff = 0;
            this.needReset = false;
        } else {
            this.needReset = true;
            diff = widthLayout - width;
            graph.getModel().setGeometry(graph.getDefaultParent(), new mxGeometry(0, 10, widthLayout, heightLayout));
        }

    }

    /**
     * Stellt die Grafik auf ursprüngliche Größe und Position zurück. Wenn Größe und
     * Position nicht verändert wurden, passiert nichts.
     */
    public void reset() {
        if (needReset) {
            this.graphComponent.zoomTo(1, true);
            this.setGraph(new GraphTree());
        }

    }
}
