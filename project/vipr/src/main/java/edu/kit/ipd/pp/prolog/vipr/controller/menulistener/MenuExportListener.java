package edu.kit.ipd.pp.prolog.vipr.controller.menulistener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.mxgraph.util.mxCellRenderer;

import edu.kit.ipd.pp.prolog.vipr.view.FileManager;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.popups.ErrorPopUp;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.GraphicPane;

/**
 * Dieser Listener ermöglicht das Abspeichern des angeziegten Graphen als Bild.
 */
public class MenuExportListener implements ActionListener {

    /**
     * Das Grafikfeld der grafischen Benutzeroberfläche. Wird zum Anzeigen der
     * Grafik verwendet.
     */
    private GraphicPane graphicPane;

    /**
     * Initialisiert den Listener mit allen nötigen Parametern.
     * 
     * @param gp
     *            Das Grafikfeld der grafischen Benutzeroberfläche. Wird zum
     *            Anzeigen der Grafik verwendet.
     */
    public MenuExportListener(GraphicPane gp) {
        this.graphicPane = gp;
    }

    /**
     * Erstellt ein Bild aus dem dargestellten Graph und öffnet den dateimanager zum
     * Speichern.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        BufferedImage image = mxCellRenderer.createBufferedImage(graphicPane.getGraphComponent().getGraph(), null, 1,
                Color.WHITE, true, null);
        if (image != null) {
            try {
                FileManager.getInstance().export(image);
            } catch (IOException e1) {
                new ErrorPopUp("Export Error", "Graph export failed.");
            }
        } else {
            new ErrorPopUp("Graph missing", "No graph for export found.");
        }

    }

}
