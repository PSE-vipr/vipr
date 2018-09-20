package edu.kit.ipd.pp.prolog.vipr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.State;
import edu.kit.ipd.pp.prolog.vipr.model.parser.ParseException;
import edu.kit.ipd.pp.prolog.vipr.model.parser.PrologParser;

public class InterpreterTest {

    @Test
    public void queryWithoutVars() {
        // Vorbereitung
        Program prg = null;
        List<Goal> goalList = null;
        try {
            PrologParser p = new PrologParser("a. test :- a, !, 1 < 2.");
            prg = p.parse();
            goalList = new LinkedList<>();
            goalList = new PrologParser("test.").parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (prg == null || goalList == null) {
            fail("Fehler beim parsen");
        }
        Interpreter i = new Interpreter();
        i.setNewProgram(prg);
        assertEquals(prg, i.getProgram());
        assertFalse(i.getInquiryExists());
        i.newInquiry(goalList);
        assertTrue(i.getInquiryExists());
        try {
            i.nextStep();
            assertFalse(i.getInquiryFailed());
            assertEquals(3, i.getPosHighlight()[0]);
            assertEquals(23, i.getPosHighlight()[1]);
            i.nextStep();
            i.nextStep();
            i.nextStep();
            i.nextStep();
            assertFalse(i.getInquiryFailed());
            assertTrue(i.getFoundOutput());
            assertEquals("yes", i.getOutput());
            i.nextStep();
            assertFalse(i.getFoundOutput());
            i.nextStep();
            i.nextStep();
            i.nextStep();
            assertTrue(i.getInquiryFailed());
            assertTrue(i.getFoundOutput());
            assertEquals("no.", i.getOutput());

        } catch (CalculateException | NoRuleException e) {
            fail("Fehler bei der Ausführung");
        }
    }

    @Test
    public void queryVars() {
        // Vorbereitung
        Program prg = null;
        List<Goal> goalList = null;
        try {
            PrologParser p = new PrologParser("a(X, Y) :- X is 7-1, Y = a. test(X, Y) :- a(X, Y), !, 1 < 2.");
            prg = p.parse();
            goalList = new LinkedList<>();
            goalList = new PrologParser("test(X, Y).").parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (prg == null || goalList == null) {
            fail("Fehler beim parsen");
        }
        Interpreter i = new Interpreter();
        i.setNewProgram(prg);
        assertEquals(prg, i.getProgram());
        assertFalse(i.getInquiryExists());
        i.newInquiry(goalList);
        assertTrue(i.getInquiryExists());
        try {
            i.nextStep();
            assertFalse(i.getInquiryFailed());
            assertEquals(28, i.getPosHighlight()[0]);
            assertEquals(60, i.getPosHighlight()[1]);
            i.nextStep();
            i.nextStep();
            i.nextStep();
            i.nextStep();
            i.nextStep();
            i.nextStep();
            i.nextStep();
            assertFalse(i.getInquiryFailed());
            assertTrue(i.getFoundOutput());
            assertEquals("X = 6, Y = a", i.getOutput());
            i.nextStep();
            assertFalse(i.getFoundOutput());
            i.nextStep();
            i.nextStep();
            i.nextStep();
            assertTrue(i.getInquiryFailed());
            assertTrue(i.getFoundOutput());
            assertEquals("no.", i.getOutput());

        } catch (CalculateException | NoRuleException e) {
            fail("Fehler bei der Ausführung");
        }
    }

    @Test
    public void queryCut() {
        // Vorbereitung
        Program prg = null;
        List<Goal> goalList = null;
        try {
            PrologParser p = new PrologParser("test.");
            prg = p.parse();
            goalList = new LinkedList<>();
            goalList = new PrologParser("test, !, 3 > 2.").parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (prg == null || goalList == null) {
            fail("Fehler beim parsen");
        }
        Interpreter i = new Interpreter();
        i.setNewProgram(prg);
        assertEquals(prg, i.getProgram());
        assertFalse(i.getInquiryExists());
        i.newInquiry(goalList);
        assertTrue(i.getInquiryExists());
        try {
            i.nextStep();
            assertFalse(i.getInquiryFailed());
            assertEquals(0, i.getPosHighlight()[0]);
            assertEquals(5, i.getPosHighlight()[1]);
            i.nextStep();
            i.nextStep();
            assertFalse(i.getInquiryFailed());
            assertTrue(i.getFoundOutput());
            assertEquals("yes", i.getOutput());
            i.nextStep();
            assertFalse(i.getFoundOutput());
            i.nextStep();
            i.nextStep();
            assertTrue(i.getInquiryFailed());
            assertTrue(i.getFoundOutput());
            assertEquals("no.", i.getOutput());

        } catch (CalculateException | NoRuleException e) {
            fail("Fehler bei der Ausführung");
        }

    }

    @Test
    public void queryArithFail() {
        // Vorbereitung
        Program prg = null;
        List<Goal> goalList = null;
        try {
            PrologParser p = new PrologParser("");
            prg = p.parse();
            goalList = new LinkedList<>();
            goalList = new PrologParser("1 > 2.").parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (prg == null || goalList == null) {
            fail("Fehler beim parsen");
        }
        Interpreter i = new Interpreter();
        i.setNewProgram(prg);
        assertEquals(prg, i.getProgram());
        assertFalse(i.getInquiryExists());
        i.newInquiry(goalList);
        assertTrue(i.getInquiryExists());
        try {
            i.nextStep();
            assertFalse(i.getInquiryFailed());
            assertEquals(0, i.getPosHighlight()[0]);
            assertEquals(0, i.getPosHighlight()[1]);
            i.nextStep();
            assertTrue(i.getInquiryFailed());
            assertTrue(i.getFoundOutput());
            assertEquals("no.", i.getOutput());

        } catch (CalculateException | NoRuleException e) {
            fail("Fehler bei der Ausführung");
        }

    }

    @Test(expected = CalculateException.class)
    public void queryCalculateException() throws CalculateException {
        // Vorbereitung
        Program prg = null;
        List<Goal> goalList = null;
        try {
            PrologParser p = new PrologParser("");
            prg = p.parse();
            goalList = new LinkedList<>();
            goalList = new PrologParser("X is 2 + Y.").parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (prg == null || goalList == null) {
            fail("Fehler beim parsen");
        }
        Interpreter i = new Interpreter();
        i.setNewProgram(prg);
        assertEquals(prg, i.getProgram());
        assertFalse(i.getInquiryExists());
        i.newInquiry(goalList);
        assertTrue(i.getInquiryExists());
        try {
            i.nextStep();
        } catch (NoRuleException e) {
            fail("Fehler bei der Ausführung");
        }
    }

    @Test(expected = NoRuleException.class)
    public void queryNoRuleException() throws NoRuleException {
        // Vorbereitung
        Program prg = null;
        List<Goal> goalList = null;
        try {
            PrologParser p = new PrologParser("");
            prg = p.parse();
            goalList = new LinkedList<>();
            goalList = new PrologParser("notExisting.").parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (prg == null || goalList == null) {
            fail("Fehler beim parsen");
        }
        Interpreter i = new Interpreter();
        i.setNewProgram(prg);
        assertEquals(prg, i.getProgram());
        assertFalse(i.getInquiryExists());
        i.newInquiry(goalList);
        assertTrue(i.getInquiryExists());
        try {
            i.nextStep();
        } catch (CalculateException e) {
            fail("Fehler bei der Ausführung");
        }
    }

    @Test
    public void queryLastGoalFunctor() {
        // Vorbereitung
        Program prg = null;
        List<Goal> goalList = null;
        try {
            PrologParser p = new PrologParser("a. b. b :- 1 < 2. test :- a, !, b.");
            prg = p.parse();
            goalList = new LinkedList<>();
            goalList = new PrologParser("test.").parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (prg == null || goalList == null) {
            fail("Fehler beim parsen");
        }
        Interpreter i = new Interpreter();
        i.setNewProgram(prg);
        assertEquals(prg, i.getProgram());
        assertFalse(i.getInquiryExists());
        i.newInquiry(goalList);
        assertTrue(i.getInquiryExists());
        try {
            i.nextStep();
            assertFalse(i.getInquiryFailed());
            assertEquals(18, i.getPosHighlight()[0]);
            assertEquals(34, i.getPosHighlight()[1]);
            i.nextStep();
            i.nextStep();
            i.nextStep();
            i.nextStep();
            assertFalse(i.getInquiryFailed());
            assertTrue(i.getFoundOutput());
            assertEquals("yes", i.getOutput());
            i.nextStep();
            assertFalse(i.getFoundOutput());
            i.nextStep();
            i.nextStep();
            i.nextStep();
            i.nextStep();
            i.nextStep();
            assertFalse(i.getInquiryFailed());
            assertTrue(i.getFoundOutput());
            assertEquals("yes", i.getOutput());
            i.nextStep();
            assertFalse(i.getFoundOutput());
            i.nextStep();
            i.nextStep();
            i.nextStep();
            i.nextStep();
            assertTrue(i.getInquiryFailed());
            assertTrue(i.getFoundOutput());
            assertEquals("no.", i.getOutput());

        } catch (CalculateException | NoRuleException e) {
            fail("Fehler bei der Ausführung");
        }
    }

    @Test
    public void queryTwoFunctorGoals() {
        // Vorbereitung
        Program prg = null;
        List<Goal> goalList = null;
        try {
            PrologParser p = new PrologParser("a. a :- 1 > 0. b.");
            prg = p.parse();
            goalList = new LinkedList<>();
            goalList = new PrologParser("a, b.").parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (prg == null || goalList == null) {
            fail("Fehler beim parsen");
        }
        Interpreter i = new Interpreter();
        i.setNewProgram(prg);
        assertEquals(prg, i.getProgram());
        assertFalse(i.getInquiryExists());
        i.newInquiry(goalList);
        assertTrue(i.getInquiryExists());
        try {
            i.nextStep();
            assertFalse(i.getInquiryFailed());
            assertEquals(0, i.getPosHighlight()[0]);
            assertEquals(3, i.getPosHighlight()[1]);
            i.nextStep();
            assertFalse(i.getInquiryFailed());
            assertTrue(i.getFoundOutput());
            assertEquals("yes", i.getOutput());
            i.nextStep();
            assertFalse(i.getFoundOutput());
            i.nextStep();
            i.nextStep();
            i.nextStep();
            i.nextStep();
            i.nextStep();
            i.nextStep();
            assertFalse(i.getInquiryFailed());
            assertTrue(i.getFoundOutput());
            assertEquals("yes", i.getOutput());
            i.nextStep();
            assertFalse(i.getFoundOutput());
            i.nextStep();
            i.nextStep();
            i.nextStep();
            assertTrue(i.getInquiryFailed());
            assertTrue(i.getFoundOutput());
            assertEquals("no.", i.getOutput());

        } catch (CalculateException | NoRuleException e) {
            fail("Fehler bei der Ausführung");
        }
    }

    @Test
    public void queryLastGoalCut() {
        // Vorbereitung
        Program prg = null;
        List<Goal> goalList = null;
        try {
            PrologParser p = new PrologParser("");
            prg = p.parse();
            goalList = new LinkedList<>();
            goalList = new PrologParser("1 < 2, !.").parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (prg == null || goalList == null) {
            fail("Fehler beim parsen");
        }
        Interpreter i = new Interpreter();
        i.setNewProgram(prg);
        assertEquals(prg, i.getProgram());
        assertFalse(i.getInquiryExists());
        i.newInquiry(goalList);
        assertTrue(i.getInquiryExists());
        try {
            i.nextStep();
            assertFalse(i.getInquiryFailed());
            assertEquals(0, i.getPosHighlight()[0]);
            assertEquals(0, i.getPosHighlight()[1]);
            i.nextStep();
            assertFalse(i.getInquiryFailed());
            assertTrue(i.getFoundOutput());
            assertEquals("yes", i.getOutput());
            i.nextStep();
            assertFalse(i.getFoundOutput());
            i.nextStep();
            assertTrue(i.getInquiryFailed());
            assertTrue(i.getFoundOutput());
            assertEquals("no.", i.getOutput());

        } catch (CalculateException | NoRuleException e) {
            fail("Fehler bei der Ausführung");
        }
    }

    @Test
    public void queryFirstGoalFails() {
        // Vorbereitung
        Program prg = null;
        List<Goal> goalList = null;
        try {
            PrologParser p = new PrologParser("a. test :- 2 < 1, a, 1 < 4, !.");
            prg = p.parse();
            goalList = new LinkedList<>();
            goalList = new PrologParser("test.").parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (prg == null || goalList == null) {
            fail("Fehler beim parsen");
        }
        Interpreter i = new Interpreter();
        i.setNewProgram(prg);
        assertEquals(prg, i.getProgram());
        assertFalse(i.getInquiryExists());
        i.newInquiry(goalList);
        assertTrue(i.getInquiryExists());
        try {
            i.nextStep();
            assertFalse(i.getInquiryFailed());
            assertEquals(3, i.getPosHighlight()[0]);
            assertEquals(30, i.getPosHighlight()[1]);
            i.nextStep();
            i.nextStep();
            assertFalse(i.getFoundOutput());

        } catch (CalculateException | NoRuleException e) {
            fail("Fehler bei der Ausführung");
        }
        try {
            i.getRoot().createVisNode(false);

        } catch (NullPointerException e) {
            fail("Fehler beim Erstellen der ARVisNodes: NullPointerException");
        }
        try {
            i.nextStep();
            assertTrue(i.getInquiryFailed());
            assertTrue(i.getFoundOutput());
            assertEquals("no.", i.getOutput());

        } catch (CalculateException | NoRuleException e) {
            fail("Fehler bei der Ausführung");
        }
    }

    @Test
    public void stateTest() {
        assertEquals(State.PENDING, State.valueOf("PENDING"));
        assertEquals(State.FULFILLED, State.valueOf("FULFILLED"));
        assertEquals(State.FAILED, State.valueOf("FAILED"));
        assertEquals(State.UNVISITED, State.valueOf("UNVISITED"));
        assertEquals(State.CUTFAILED, State.valueOf("CUTFAILED"));
    }

}
