package edu.kit.ipd.pp.prolog.vipr;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.model.ast.CalculateException;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Goal;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NoRuleException;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Program;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Interpreter;
import edu.kit.ipd.pp.prolog.vipr.model.parser.ParseException;
import edu.kit.ipd.pp.prolog.vipr.model.parser.PrologParser;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.ARVisNode;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.ArithOrUniGoalARVisNode;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.CutARVisNode;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.FunctorGoalARVisNode;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.ParentARVisNode;

public class VisTreeTest {

    @Test
    public void functorGoalARVisNodeTest() {
        // Vorbereitung
        Program prg = null;
        List<Goal> goalList = null;
        try {
            PrologParser p = new PrologParser("a(5).");
            prg = p.parse();
            goalList = new LinkedList<>();
            goalList = new PrologParser("a(X).").parseGoalList();
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
        } catch (CalculateException | NoRuleException e) {
            fail("Fehler bei der Ausführung");
        }
        // FunctorGoalARVisNode und PArentARVisNode Tests

        ARVisNode vNode = i.getRoot().createVisNode(false);
        // Wurzel
        assertTrue(vNode instanceof ParentARVisNode);

        assertTrue(vNode.createHtmlTable() == null);
        // Anfrage
        ARVisNode child = vNode.getChildren().get(0);
        assertTrue(child instanceof FunctorGoalARVisNode);
        assertTrue(child.needResize() == true);
        assertTrue(child.getVertexStyle().equals("rounded=false;"));
        assertTrue(child.getEdgeStyle().equals("endArrow=none;" + "dashed=true;"));
        // Regel zur Anfrage
        ARVisNode grandChild = child.getChildren().get(0);
        assertTrue(grandChild instanceof ParentARVisNode);
        assertTrue(grandChild.createHtmlTable() != null);
        assertTrue(grandChild.getVertexStyle()
                .equals(grandChild.stateToVertexColor() + "shape=ellipse;perimeter=ellipsePerimeter;"));

    }

    @Test
    public void arithOrUniGoalARVisNodeTest() {
        // Vorbereitung
        List<Goal> goalList = null;
        try {

            goalList = new LinkedList<>();
            goalList = new PrologParser("X is 3 + 5, X > 3.").parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (goalList == null) {
            fail("Fehler beim parsen");
        }
        Interpreter i = new Interpreter();
        i.newInquiry(goalList);
        try {
            i.nextStep();
        } catch (CalculateException | NoRuleException e) {
            fail("Fehler bei der Ausführung");
        }
        // ArithOrUniGoalARVisNode Test
        ARVisNode vNode = i.getRoot().createVisNode(false);
        ARVisNode firstGoal = vNode.getChildren().get(0);
        ARVisNode secondGoal = vNode.getChildren().get(1);
        assertTrue(firstGoal instanceof ArithOrUniGoalARVisNode && secondGoal instanceof ArithOrUniGoalARVisNode);
        assertTrue(firstGoal.needResize() == true);

    }

    @Test
    public void arithOrUniGoalARVisNodeTestTwo() {
        // Vorbereitung
        List<Goal> goalList = null;
        try {

            goalList = new LinkedList<>();
            goalList = new PrologParser("X is 3 + 5, X > 3.").parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (goalList == null) {
            fail("Fehler beim parsen");
        }
        Interpreter i = new Interpreter();
        i.newInquiry(goalList);
        try {
            i.nextStep();
        } catch (CalculateException | NoRuleException e) {
            fail("Fehler bei der Ausführung");
        }
        // ArithOrUniGoalARVisNode Test
        ARVisNode vNode = i.getRoot().createVisNode(true);
        ARVisNode firstGoal = vNode.getChildren().get(0);
        ARVisNode secondGoal = vNode.getChildren().get(1);
        assertTrue(firstGoal instanceof ArithOrUniGoalARVisNode && secondGoal instanceof ArithOrUniGoalARVisNode);
        assertTrue(firstGoal.needResize() == true);

    }

    @Test
    public void cutARVisNode() {
        // Vorbereitung
        Program prg = null;
        List<Goal> goalList = null;
        try {
            PrologParser p = new PrologParser("a:- 1 < 2, !, 1 < 2.");
            prg = p.parse();
            goalList = new LinkedList<>();
            goalList = new PrologParser("a.").parseGoalList();
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
            i.nextStep(); // Anfrage er füllt
        } catch (CalculateException | NoRuleException e) {
            fail("Fehler bei der Ausführung");
        }

        // CUTARVisNode Test
        ARVisNode vNode = i.getRoot().createVisNode(false);
        ARVisNode functorChild = vNode.getChildren().get(0);
        ARVisNode ruleChild = functorChild.getChildren().get(0);
        ARVisNode cut = ruleChild.getChildren().get(1);
        assertTrue(cut instanceof CutARVisNode);
        assertTrue(cut.needResize() == false);
        assertTrue(cut.createHtmlTable() != null);

        try {
            i.nextStep(); // Reerfüllung
            i.nextStep();// CUT FAILED

        } catch (CalculateException | NoRuleException e) {
            fail("Fehler bei der Ausführung");
        }
        vNode = i.getRoot().createVisNode(false);
        cut = vNode.getChildren().get(0).getChildren().get(0).getChildren().get(1);
        assertTrue(cut.stateToVertexColor().contains("red"));

    }

    @Test
    public void noMoreSolutionVisTree() {
        // Vorbereitung
        Program prg = null;
        List<Goal> goalList = null;
        try {
            PrologParser p = new PrologParser("a(c,b). d :- a(X,Y), 2 < 5. ");
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
            i.nextStep(); // Anfrage erzielt
            i.nextStep(); // Reerfüllung beginnt
            i.nextStep();
            i.nextStep();
            i.nextStep();
        } catch (CalculateException | NoRuleException e) {
            fail("Fehler bei der Ausführung");
        }
        // Tests
        ARVisNode root = i.getRoot().createVisNode(false);
        ARVisNode child = root.getChildren().get(0);
        assertTrue(child.getChildren().size() == 0);

    }

    @Test
    public void noMoreSolutionVisTreeTwo() {
        // Vorbereitung
        Program prg = null;
        List<Goal> goalList = null;
        try {
            PrologParser p = new PrologParser("a(c,b). d :- a(X,Y), 2 < 5. ");
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
            i.nextStep(); // Anfrage erzielt
            i.nextStep(); // Reerfüllung beginnt
            i.nextStep();
            i.nextStep();
            i.nextStep();
        } catch (CalculateException | NoRuleException e) {
            fail("Fehler bei der Ausführung");
        }
        // Tests
        ARVisNode root = i.getRoot().createVisNode(true);
        ARVisNode child = root.getChildren().get(0);
        assertTrue(child.getChildren().size() == 0);

    }

    @Test
    public void stateToTableTest() {
        Program prg = null;
        List<Goal> goalList = null;
        try {
            PrologParser p = new PrologParser(
                    "vater(g,h). vater(a,b). vater(b,c). großvater(X,Y):- vater(X,Z), vater(Z,Y).");
            prg = p.parse();
            goalList = new LinkedList<>();
            goalList = new PrologParser("großvater(X,Y).").parseGoalList();
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

        } catch (CalculateException | NoRuleException e) {
            fail("Fehler bei der Ausführung");
        }
        ARVisNode root = i.getRoot().createVisNode(false);
        ARVisNode child = root.getChildren().get(0);
        ARVisNode Rchild = child.getChildren().get(0);
        assertTrue(Rchild.getChildren().get(1).createHtmlTable().contains("white")); // Unvisited COLOR
        assertTrue(child.createHtmlTable().contains("white")); // PENDING COLOR
        try {
            i.nextStep();
            i.nextStep();

        } catch (CalculateException | NoRuleException e) {
            fail("Fehler bei der Ausführung");
        }
        root = i.getRoot().createVisNode(false);
        child = root.getChildren().get(0);
        Rchild = child.getChildren().get(0);
        assertTrue(Rchild.getChildren().get(1).createHtmlTable().contains("white")); // FAILED show Pending color

        try {
            i.nextStep();
            i.nextStep();
            i.nextStep();
            i.nextStep();

        } catch (CalculateException | NoRuleException e) {
            fail("Fehler bei der Ausführung");
        }
        // FAILED ohne show Pending
    }

}
