package edu.kit.ipd.pp.prolog.vipr;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.mxgraph.model.mxCell;

import edu.kit.ipd.pp.prolog.vipr.model.ast.CalculateException;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Goal;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NoRuleException;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Program;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Interpreter;
import edu.kit.ipd.pp.prolog.vipr.model.parser.ParseException;
import edu.kit.ipd.pp.prolog.vipr.model.parser.PrologParser;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.GraphTree;

public class GraphTreeTest {

    @Test
    public void test() {
        // Vorbereitung
        Program prg = null;
        List<Goal> goalList = null;
        try {
            PrologParser p = new PrologParser("a(c,b). d :- a(X,Y), !, 2 < 5. ");
            prg = p.parse();
            goalList = new LinkedList<>();
            goalList = new PrologParser("d.").parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (prg == null || goalList == null) {
            fail("Fehler beim parsen");
        }
        Interpreter i = new Interpreter();
        i.setNewProgram(prg);
        i.newInquiry(goalList);
        try {
            i.nextStep();
            i.nextStep();
            i.nextStep();
            i.nextStep();
            i.nextStep();

        } catch (CalculateException | NoRuleException e) {
            fail("Fehler bei der Ausführung");
        }
        // GraphTree Test
        GraphTree gt = new GraphTree();
        assertTrue(!gt.isHtmlLabels()); // Beim Leeren Graph keine HTML möglich
        gt = new GraphTree(i.getRoot().createVisNode(false));
        assertTrue(gt != null);
        assertTrue(gt.isHtmlLabels()); // HTML sollte erlaubt sein
        Object[] cells = gt.getChildVertices(gt.getDefaultParent());
        mxCell rootCell = (mxCell) cells[0];
        assertTrue(rootCell.getId().equals("root")); // Wurzel Knoten ist mit id root versehen
        assertTrue(rootCell.isVertex()); // Wurzel sollte ein Knoten ssein
        assertTrue(cells.length == 7); // Wurzel + Anfrage + Regel zur Anfrage + 3 Teilziele der Regel + gefundener
                                       // Fakt = 7 Knoten
        assertTrue(cells.length == gt.getChildEdges(gt.getDefaultParent()).length + 1); // Baumstruktur n Knoten, n - 1
                                                                                        // Kanten
    }

}
