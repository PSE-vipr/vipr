package edu.kit.ipd.pp.prolog.vipr;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.model.ast.CalculateException;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Equal;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Greater;
import edu.kit.ipd.pp.prolog.vipr.model.ast.GreaterOrEqual;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Less;
import edu.kit.ipd.pp.prolog.vipr.model.ast.LessOrEqual;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NotEqual;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NumberTerm;

public class ArithFulfillTest {

    @Test
    public void greaterTest1() throws CalculateException {
        Greater greater = new Greater(new NumberTerm(2), new NumberTerm(1));
        boolean result = greater.fulfill();
        assertTrue(result);
    }

    @Test
    public void greaterTest2() throws CalculateException {
        Greater greater = new Greater(new NumberTerm(1), new NumberTerm(1));
        boolean result = greater.fulfill();
        assertFalse(result);
    }

    @Test
    public void greaterOrEqualTest1() throws CalculateException {
        GreaterOrEqual greaterOrEqual = new GreaterOrEqual(new NumberTerm(2), new NumberTerm(1));
        boolean result = greaterOrEqual.fulfill();
        assertTrue(result);
    }

    @Test
    public void greaterOrEqualTest2() throws CalculateException {
        GreaterOrEqual greaterOrEqual = new GreaterOrEqual(new NumberTerm(2), new NumberTerm(2));
        boolean result = greaterOrEqual.fulfill();
        assertTrue(result);
    }

    @Test
    public void greaterOrEqualTest3() throws CalculateException {
        GreaterOrEqual greaterOrEqual = new GreaterOrEqual(new NumberTerm(2), new NumberTerm(3));
        boolean result = greaterOrEqual.fulfill();
        assertFalse(result);
    }

    @Test
    public void lessOrEqualTest1() throws CalculateException {
        LessOrEqual lessOrEqual = new LessOrEqual(new NumberTerm(2), new NumberTerm(3));
        boolean result = lessOrEqual.fulfill();
        assertTrue(result);
    }

    @Test
    public void lessOrEqualTest2() throws CalculateException {
        LessOrEqual lessOrEqual = new LessOrEqual(new NumberTerm(2), new NumberTerm(2));
        boolean result = lessOrEqual.fulfill();
        assertTrue(result);
    }

    @Test
    public void lessOrEqualTest3() throws CalculateException {
        LessOrEqual lessOrEqual = new LessOrEqual(new NumberTerm(2), new NumberTerm(1));
        boolean result = lessOrEqual.fulfill();
        assertFalse(result);
    }

    @Test
    public void lessTest1() throws CalculateException {
        Less less = new Less(new NumberTerm(2), new NumberTerm(3));
        boolean result = less.fulfill();
        assertTrue(result);
    }

    @Test
    public void lessTest2() throws CalculateException {
        Less less = new Less(new NumberTerm(2), new NumberTerm(2));
        boolean result = less.fulfill();
        assertFalse(result);
    }

    @Test
    public void equalTest1() throws CalculateException {
        Equal equal = new Equal(new NumberTerm(2), new NumberTerm(2));
        boolean result = equal.fulfill();
        assertTrue(result);
    }

    @Test
    public void equalTest2() throws CalculateException {
        Equal equal = new Equal(new NumberTerm(3), new NumberTerm(2));
        boolean result = equal.fulfill();
        assertFalse(result);
    }

    @Test
    public void notEqualTest1() throws CalculateException {
        NotEqual notEqual = new NotEqual(new NumberTerm(1), new NumberTerm(2));
        boolean result = notEqual.fulfill();
        assertTrue(result);
    }

    @Test
    public void notEqualTest2() throws CalculateException {
        NotEqual notEqual = new NotEqual(new NumberTerm(1), new NumberTerm(1));
        boolean result = notEqual.fulfill();
        assertFalse(result);
    }

}
