package edu.kit.ipd.pp.prolog.vipr;

import static org.junit.Assert.assertTrue;

import java.awt.Dimension;

import org.junit.Ignore;
import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.view.graphic.GraphTree;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.GraphicPane;

public class GraphPaneTest {

    @Ignore
    @Test
    public void setGetTest() {
        GraphicPane gp = new GraphicPane(new GraphTree());
        assertTrue(gp != null);
        assertTrue(gp.getGraphComponent().getGraph() != null);
        gp.setGraph(new GraphTree());
        assertTrue(gp.getGraphComponent().getGraph() != null);
        assertTrue(!gp.getGraphComponent().isEnabled());
        gp.setPreferredSize(new Dimension(200, 200));
        assertTrue(gp.getPreferredSize().equals(new Dimension(200, 200)));
        assertTrue(gp.getGraphComponent().getPreferredSize().equals(new Dimension(200, 200)));
    }

}
