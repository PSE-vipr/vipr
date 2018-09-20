package edu.kit.ipd.pp.prolog.vipr;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.model.ast.CalculateException;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Is;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NumberTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Plus;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Term;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Variable;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Substitution;

public class IsFulfillTest {

    // 1 is 1. Wahr
    @Test
    public void isTest1() throws CalculateException {
        Is is = new Is(new NumberTerm(1), new NumberTerm(1));
        boolean result = is.fulfill();
        assertTrue(result);
    }

    // 1 is 2. Falsch
    @Test
    public void isTest2() throws CalculateException {
        Is is = new Is(new NumberTerm(1), new NumberTerm(2));
        boolean result = is.fulfill();
        assertFalse(result);
    }

    // 3+1 is 4. Falsch
    @Test
    public void isTest3() throws CalculateException {
        Is is = new Is(new Plus(new NumberTerm(3), new NumberTerm(1)), new NumberTerm(4));
        boolean result = is.fulfill();
        assertFalse(result);
    }

    // 4 is 3+1. Wahr
    @Test
    public void isTest4() throws CalculateException {
        Is is = new Is(new NumberTerm(4), new Plus(new NumberTerm(3), new NumberTerm(1)));
        boolean result = is.fulfill();
        assertTrue(result);
    }

    // X is 4, X is 3. Falsch
    @Test
    public void isTest5() throws CalculateException {
        Variable x = new Variable("X");
        Is is = new Is(x, new NumberTerm(4));
        is.fulfill();
        Term term = null;
        for (Substitution substitution : is.getSubstitutions()) {
            term = x.substitute(substitution);
        }
        Is is2 = new Is(term, new NumberTerm(3));
        boolean result = is2.fulfill();
        assertFalse(result);
    }

    // X is 4, X is 4. Wahr
    @Test
    public void isTest6() throws CalculateException {
        Variable x = new Variable("X");
        Is is = new Is(x, new NumberTerm(4));
        is.fulfill();
        Term term = null;
        for (Substitution substitution : is.getSubstitutions()) {
            term = x.substitute(substitution);
        }
        Is is2 = new Is(term, new NumberTerm(4));
        boolean result = is2.fulfill();
        assertTrue(result);
    }
}
