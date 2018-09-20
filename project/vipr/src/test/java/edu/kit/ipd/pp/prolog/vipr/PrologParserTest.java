package edu.kit.ipd.pp.prolog.vipr;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.Random;

import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.model.ast.EmptyList;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Equal;
import edu.kit.ipd.pp.prolog.vipr.model.ast.FunctorGoal;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Goal;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Greater;
import edu.kit.ipd.pp.prolog.vipr.model.ast.GreaterOrEqual;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Is;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Less;
import edu.kit.ipd.pp.prolog.vipr.model.ast.LessOrEqual;
import edu.kit.ipd.pp.prolog.vipr.model.ast.ListFunctor;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NotEqual;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Program;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Rule;
import edu.kit.ipd.pp.prolog.vipr.model.ast.UnificationGoal;
import edu.kit.ipd.pp.prolog.vipr.model.parser.ParseException;
import edu.kit.ipd.pp.prolog.vipr.model.parser.PrologParser;

public class PrologParserTest {
    private final String LINE_SEP = System.getProperty("line.separator");

    @Test
    public void emptyParseTest() {
        Program prg = null;
        try {
            PrologParser p = new PrologParser("");
            prg = p.parse();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (prg == null) {
            fail("Fehler beim parsen");
        }
        // Zufälliger String
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        // Zufällige Nummer
        int random = (int) (10 * Math.random());
        assertTrue(prg.searchRules(generatedString, random).size() == 0);
    }

    @Test
    public void functorParseTest() {
        Program prg = null;

        try {
            PrologParser p = new PrologParser("a(wz). a(4). b(4). d. c(X,Y):- a(X), b(Y).");
            prg = p.parse();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (prg == null) {
            fail("Fehler beim parsen");
        }

        assertTrue(prg.searchRules("a", 1).size() == 2);
        assertTrue(prg.searchRules("a", 2).size() == 0);
        assertTrue(prg.searchRules("a", 0).size() == 0);
        assertTrue(prg.searchRules("a", 1).get(0) instanceof Rule);
        assertTrue(prg.searchRules("d", 0).size() == 1);
        assertTrue(prg.searchRules("c", 2).size() == 1);
        assertTrue(prg.searchRules("c", 2).get(0).getGoals().size() == 2);
        assertTrue(prg.searchRules("c", 2).get(0).getGoals().get(0) instanceof FunctorGoal);
        assertTrue(prg.searchRules("c", 2).get(0).getGoals().get(1) instanceof FunctorGoal);
    }

    @Test
    public void isGoalParseTest() {

        LinkedList<Goal> goalList = null;
        try {

            PrologParser g = new PrologParser("X is 7 + (4 - 1) * 6.");
            goalList = (LinkedList<Goal>) g.parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (goalList == null) {
            fail("Fehler beim parsen");
        }

        assertTrue(goalList.size() == 1);
        assertTrue(goalList.get(0) instanceof Is);
    }

    @Test
    public void lessGoalParseTest() {

        LinkedList<Goal> goalList = null;
        try {

            PrologParser g = new PrologParser("1 < 2.");
            goalList = (LinkedList<Goal>) g.parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (goalList == null) {
            fail("Fehler beim parsen");
        }

        assertTrue(goalList.size() == 1);
        assertTrue(goalList.get(0) instanceof Less);
    }

    @Test
    public void lessEQGoalParseTest() {

        LinkedList<Goal> goalList = null;
        try {

            PrologParser g = new PrologParser("4 =< 2.");
            goalList = (LinkedList<Goal>) g.parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (goalList == null) {
            fail("Fehler beim parsen");
        }

        assertTrue(goalList.size() == 1);
        assertTrue(goalList.get(0) instanceof LessOrEqual);
    }

    @Test
    public void greaterEQGoalParseTest() {

        LinkedList<Goal> goalList = null;
        try {

            PrologParser g = new PrologParser("4 >= 2.");
            goalList = (LinkedList<Goal>) g.parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (goalList == null) {
            fail("Fehler beim parsen");
        }

        assertTrue(goalList.size() == 1);
        assertTrue(goalList.get(0) instanceof GreaterOrEqual);
    }

    @Test
    public void greaterGoalParseTest() {

        LinkedList<Goal> goalList = null;
        try {

            PrologParser g = new PrologParser("4 > 2.");
            goalList = (LinkedList<Goal>) g.parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (goalList == null) {
            fail("Fehler beim parsen");
        }

        assertTrue(goalList.size() == 1);
        assertTrue(goalList.get(0) instanceof Greater);
    }

    @Test
    public void equalGoalParseTest() {

        LinkedList<Goal> goalList = null;
        try {

            PrologParser g = new PrologParser("2 =:= 1.");
            goalList = (LinkedList<Goal>) g.parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (goalList == null) {
            fail("Fehler beim parsen");
        }

        assertTrue(goalList.size() == 1);
        assertTrue(goalList.get(0) instanceof Equal);
    }

    @Test
    public void notEqualGoalParseTest() {

        LinkedList<Goal> goalList = null;
        try {

            PrologParser g = new PrologParser("2 =\\= 1.");
            goalList = (LinkedList<Goal>) g.parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (goalList == null) {
            fail("Fehler beim parsen");
        }

        assertTrue(goalList.size() == 1);
        assertTrue(goalList.get(0) instanceof NotEqual);
    }

    @Test
    public void uniGoalParseTest() {

        LinkedList<Goal> goalList = null;
        try {

            PrologParser g = new PrologParser("Z = 10.");
            goalList = (LinkedList<Goal>) g.parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (goalList == null) {
            fail("Fehler beim parsen");
        }

        assertTrue(goalList.size() == 1);
        assertTrue(goalList.get(0) instanceof UnificationGoal);
    }

    @Test(expected = ParseException.class)
    public void failGoalParseTest() throws ParseException {
        PrologParser g = new PrologParser("?%$*#d");
        g.parse();
    }

    @Test(expected = ParseException.class)
    public void failProgramParseTest() throws ParseException {
        PrologParser p = new PrologParser("?%$*#d");
        p.parse();

    }

    @Test
    public void commentParseTest() {
        Program prg = null;

        try {
            PrologParser p = new PrologParser("% a. b. c. d(2). " + LINE_SEP + " % " + LINE_SEP + " % " + LINE_SEP
                    + " f. " + LINE_SEP + " %     ");
            prg = p.parse();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (prg == null) {
            fail("Fehler beim parsen");
        }
        System.out.println(LINE_SEP);
        assertTrue(prg.searchRules("a", 0).size() == 0);
        assertTrue(prg.searchRules("d", 1).size() == 0);
        assertTrue(prg.searchRules("f", 0).size() == 1);

    }

    @Test(expected = ParseException.class)
    public void nullTokenParseTest() throws ParseException {
        PrologParser g = new PrologParser("Z <<<< 5.");
        g.parseGoalList();
    }

    @Test
    public void listParseTest() {

        Program prg = null;
        try {

            PrologParser p = new PrologParser("a([1, 2, 3 | [4 , 5]]). [x, y, z]. [].");
            prg = p.parse();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (prg == null) {
            fail("Fehler beim parsen");
        }
        assertTrue(prg.searchRules("a", 1).size() == 1);
        assertTrue(prg.searchRules("a", 1).get(0).getFunctorTerm().getParameters().get(0) instanceof ListFunctor);
        assertTrue(prg.searchRules("[|]", 2).size() == 1);
        assertTrue(prg.searchRules("[]", 0).size() == 1);
        assertTrue(prg.searchRules("[]", 0).get(0).getFunctorTerm() instanceof EmptyList);
    }

    @Test(expected = ParseException.class)
    public void expectErrorParseTest() throws ParseException {
        PrologParser p = new PrologParser("a(X):+ a(X).");
        p.parse();
    }

    @Test(expected = ParseException.class)
    public void unknownGoalParseTest() throws ParseException {
        PrologParser g = new PrologParser(">");
        g.parseGoalList();

    }

    @Test(expected = ParseException.class)
    public void goalRestParseErrorTest() throws ParseException {
        PrologParser g = new PrologParser("a :- 4 :- 7");
        g.parse();

    }

    @Test(expected = ParseException.class)
    public void parseFunctorErrorTest() throws ParseException {
        PrologParser g = new PrologParser("=:=");
        g.parse();

    }

    @Test(expected = ParseException.class)
    public void parseListErrorTest() throws ParseException {
        PrologParser g = new PrologParser("[=<].");
        g.parse();

    }

    @Test(expected = ParseException.class)
    public void parseListRestErrorTest() throws ParseException {
        PrologParser g = new PrologParser("[[1] =< 2].");
        g.parse();

    }

    @Test
    public void parseGoalRestTest() {

        LinkedList<Goal> goalList = null;
        try {

            PrologParser p = new PrologParser("hans = X.");
            goalList = (LinkedList<Goal>) p.parseGoalList();
        } catch (ParseException e) {
            fail("Programm konnte nicht geparsed werden");
        }
        if (goalList == null) {
            fail("Fehler beim parsen");
        }

        assertTrue(goalList.size() == 1);
        assertTrue(goalList.getFirst() instanceof UnificationGoal);
    }

    @Test(expected = ParseException.class)
    public void parseFactorErrorTest() throws ParseException {
        PrologParser p = new PrologParser("X is ).");
        p.parseGoalList();

    }

    @Test(expected = ParseException.class)
    public void parseTokenErrorTest() throws ParseException {
        PrologParser p = new PrologParser("whatisthis!?..");
        p.parseGoalList();

    }

}
