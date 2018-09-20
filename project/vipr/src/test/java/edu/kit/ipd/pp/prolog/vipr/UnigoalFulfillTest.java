package edu.kit.ipd.pp.prolog.vipr;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.model.ast.CalculateException;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NumberTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Plus;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Term;
import edu.kit.ipd.pp.prolog.vipr.model.ast.UnificationGoal;
import edu.kit.ipd.pp.prolog.vipr.model.ast.UnifyException;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Variable;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Substitution;

public class UnigoalFulfillTest {

    // 1+2=1+2. Wahr
    @Test
    public void unificationGoalTest1() {
        UnificationGoal unificationGoal = new UnificationGoal(new Plus(new NumberTerm(1), new NumberTerm(2)),
                new Plus(new NumberTerm(1), new NumberTerm(2)));
        boolean result = unificationGoal.fulfill();
        assertTrue(result);
    }

    // 1+2=2+1. Falsch
    @Test
    public void unificationGoalTest2() {
        UnificationGoal unificationGoal = new UnificationGoal(new Plus(new NumberTerm(1), new NumberTerm(2)),
                new Plus(new NumberTerm(2), new NumberTerm(1)));
        boolean result = unificationGoal.fulfill();
        assertFalse(result);
    }

    // X = X. Wahr
    @Test
    public void unificationGoalTest3() throws UnifyException, CalculateException {
        Variable x = new Variable("X");
        UnificationGoal unigoal1 = new UnificationGoal(x, x);
        boolean result = unigoal1.fulfill();
        assertTrue(result);
    }

    // X = 5, X = 5. Wahr
    @Test
    public void unificationGoalTest4() throws UnifyException, CalculateException {

        Variable x = new Variable("X");
        UnificationGoal unigoal = new UnificationGoal(x, new NumberTerm(5));
        unigoal.fulfill();
        Term termOne = null;
        for (Substitution substitution : unigoal.getSubstitutions()) {
            termOne = x.substitute(substitution);
        }
        UnificationGoal unigoal2 = new UnificationGoal(termOne, new NumberTerm(5));
        boolean result = unigoal2.fulfill();
        assertTrue(result);
    }

    // X is 5, X = 4. Falsch
    @Test
    public void unificationGoalTest5() throws UnifyException, CalculateException {
        Variable x = new Variable("X");
        UnificationGoal unigoal = new UnificationGoal(x, new NumberTerm(5));
        unigoal.fulfill();
        Term termOne = null;
        for (Substitution substitution : unigoal.getSubstitutions()) {
            termOne = x.substitute(substitution);
        }
        UnificationGoal unigoal2 = new UnificationGoal(termOne, new NumberTerm(4));
        boolean result = unigoal2.fulfill();
        assertFalse(result);
    }

    // X = 5, Y = 3, X = Y. Falsch
    @Test
    public void unificationGoalTest6() throws UnifyException, CalculateException {

        Variable x = new Variable("X");
        UnificationGoal unigoal1 = new UnificationGoal(x, new NumberTerm(5));
        unigoal1.fulfill();
        Variable y = new Variable("Y");
        UnificationGoal unigoal2 = new UnificationGoal(y, new NumberTerm(3));
        unigoal2.fulfill();
        Term termOne = null;
        Term termTwo = null;
        for (Substitution substitution : unigoal1.getSubstitutions()) {
            termOne = x.substitute(substitution);
        }
        for (Substitution substitution : unigoal2.getSubstitutions()) {
            termTwo = y.substitute(substitution);
        }
        UnificationGoal unificationGoal = new UnificationGoal(termOne, termTwo);
        boolean result = unificationGoal.fulfill();
        assertFalse(result);
    }

    // X = 5, Y = 5, X = Y. Falsch
    @Test
    public void unificationGoalTest7() throws UnifyException, CalculateException {

        Variable x = new Variable("X");
        UnificationGoal unigoal1 = new UnificationGoal(x, new NumberTerm(5));
        unigoal1.fulfill();
        Variable y = new Variable("Y");
        UnificationGoal unigoal2 = new UnificationGoal(y, new NumberTerm(5));
        unigoal2.fulfill();
        Term termOne = null;
        Term termTwo = null;
        for (Substitution substitution : unigoal1.getSubstitutions()) {
            termOne = x.substitute(substitution);
        }
        for (Substitution substitution : unigoal2.getSubstitutions()) {
            termTwo = y.substitute(substitution);
        }
        UnificationGoal unificationGoal = new UnificationGoal(termOne, termTwo);
        boolean result = unificationGoal.fulfill();
        assertTrue(result);
    }

}
