package edu.kit.ipd.pp.prolog.vipr.view.graphic;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.view.mxGraph;

import edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.ARVisNode;

/**
 * Diese Klasse repräsentiert den Graphen, der den Abarbeitungszustand
 * visualisiert.
 *
 */
public class GraphTree extends mxGraph {

    /**
     * Text innerhalb des Wurzel-Knotens
     */
    private static final String ROOT_TEXT = "?";
    /**
     * Schriftfarbe innerhalb des Wurzel-Knotens
     */
    private static final String TEXT_COLOR = "black";
    /**
     * HTML Formatierung des Wurzel-Knotens
     */
    private static final String ROOT_HTML_TEXT = "<h2 color='" + TEXT_COLOR + "'>" + ROOT_TEXT + "</h2>";

    /**
     * Default parent
     */
    private Object parent;

    /**
     * Konstruktor des Graphen.
     * 
     * @param rootVis
     *            Wurzel des Visualiesierungsbaumes
     * 
     */
    public GraphTree(ARVisNode rootVis) {
        // erlaubt das Nutzen von HTML
        this.setHtmlLabels(true);

        // Layout welches Knoten Baumartig anordnet
        final mxCompactTreeLayout layout;
        layout = new mxCompactTreeLayout(this, false);
        layout.setUseBoundingBox(false);
        layout.setEdgeRouting(false);
        layout.setLevelDistance(30);
        layout.setNodeDistance(10);

        parent = this.getDefaultParent();

        this.getModel().beginUpdate();

        try {
            // Einfügen des Wurzel-Knotens
            Object treeRootVertex = this.insertVertex(parent, "root", ROOT_HTML_TEXT, 0, 0, 60, 40,
                    rootVis.getVertexStyle());
            // Andere Knoten rekursiv einfügen und mit Kanten verbinden
            paintGraph(rootVis, treeRootVertex);
            // Layout anwenden
            layout.execute(parent);
        } finally {
            // Erstellung abschließen
            this.getModel().endUpdate();
        }

    }

    /**
     * Konstruktor der eine leere Grafik erstellt
     */
    public GraphTree() {

    }

    /**
     * Rekursive Hilfsmethode die den Visualisierungsbaum traversiert und die Knoten
     * mit ihren Kanten einfügt
     * 
     * @param currentNode
     *            der momentan betrachtete Knoten im Visualisierungsbaum
     * @param currentNodeVertex
     *            der Knoten in der Grafik der currentNode repräsentiert, wird als
     *            Referenz benötigt um die Kanten einzufügen
     */
    private void paintGraph(ARVisNode currentNode, Object currentNodeVertex) {
        // Alle Kind-Knoten abgehen
        for (ARVisNode child : currentNode.getChildren()) {
            // Kindknoten in die Grafik einfügen
            Object childVertex = this.insertVertex(parent, null, child.createHtmlTable(), 0, 0, 60, 40,
                    child.getVertexStyle());
            // Falls Knotengröße angepasst werden muss
            if (child.needResize()) {
                this.updateCellSize(childVertex);
            }
            // Kante vom Kind zum Elternknoten zeichnen
            this.insertEdge(parent, null, "", currentNodeVertex, childVertex, currentNode.getEdgeStyle());
            // Die Funktion für die 'Enkel'-Knoten aufrufen
            paintGraph(child, childVertex);

        }

    }

}