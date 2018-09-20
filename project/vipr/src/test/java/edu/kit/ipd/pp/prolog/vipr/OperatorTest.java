package edu.kit.ipd.pp.prolog.vipr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.model.ast.FunctorTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Minus;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Multiplication;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NumberTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Plus;

public class OperatorTest {

    @Test
    public void MultiplicationDeepCopyTest() {
        Multiplication mult1 = new Multiplication(new NumberTerm(1), new NumberTerm(2));
        Multiplication mult2 = mult1.deepCopy();
        assertEquals(mult1, mult2);
    }

    @Test
    public void MultiplicationToStringTest() {
        Multiplication mult1 = new Multiplication(new NumberTerm(1), new NumberTerm(2));
        assertEquals("1 * 2", mult1.toString());
    }

    @Test
    public void MultiplicationGetOperatorTokenTest() {
        Multiplication mult1 = new Multiplication(new NumberTerm(1), new NumberTerm(2));
        assertEquals(new FunctorTerm("*"), mult1.getOperatorToken());
    }

    @Test
    public void MultiplicationEqualsTest() {
        Multiplication mult1 = new Multiplication(new NumberTerm(1), new NumberTerm(2));
        Multiplication mult2 = new Multiplication(new NumberTerm(1), new NumberTerm(2));
        assertTrue(mult1.equals(mult2));
    }

    @Test
    public void MultiplicationEqualsOtherParamTest() {
        Multiplication mult1 = new Multiplication(new NumberTerm(1), new NumberTerm(2));
        Multiplication mult2 = new Multiplication(new NumberTerm(3), new NumberTerm(4));
        assertFalse(mult1.equals(mult2));
    }

    @Test
    public void MultiplicationEqualsOtherOperatorAndParamTest() {
        Multiplication mult1 = new Multiplication(new NumberTerm(1), new NumberTerm(2));
        Minus mult2 = new Minus(new NumberTerm(3), new NumberTerm(4));
        assertFalse(mult1.equals(mult2));
    }

    @Test
    public void MultiplicationEqualsOtherOperatorTest() {
        Multiplication mult1 = new Multiplication(new NumberTerm(1), new NumberTerm(2));
        Minus mult2 = new Minus(new NumberTerm(1), new NumberTerm(2));
        assertFalse(mult1.equals(mult2));
    }

    @Test
    public void MultiplicationEqualsNullTest() {
        Multiplication mult1 = new Multiplication(new NumberTerm(1), new NumberTerm(2));
        assertFalse(mult1.equals(null));
    }

    @Test
    public void MinusDeepCopyTest() {
        Minus minus1 = new Minus(new NumberTerm(1), new NumberTerm(2));
        Minus minus2 = minus1.deepCopy();
        assertEquals(minus1, minus2);
    }

    @Test
    public void MinusToStringTest() {
        Minus minus1 = new Minus(new NumberTerm(2), new NumberTerm(1));
        assertEquals("2 - 1", minus1.toString());
    }

    @Test
    public void MinusGetOperatorTokenTest() {
        Minus minus1 = new Minus(new NumberTerm(1), new NumberTerm(2));
        assertEquals(new FunctorTerm("-"), minus1.getOperatorToken());
    }

    @Test
    public void MinusEqualsTest() {
        Minus m1 = new Minus(new NumberTerm(3), new NumberTerm(2));
        Minus m2 = new Minus(new NumberTerm(3), new NumberTerm(2));
        assertTrue(m1.equals(m2));
    }

    @Test
    public void MinusEqualsOtherParamTest() {
        Minus m1 = new Minus(new NumberTerm(1), new NumberTerm(2));
        Minus m2 = new Minus(new NumberTerm(3), new NumberTerm(4));
        assertFalse(m1.equals(m2));
    }

    @Test
    public void MinusEqualsOtherOperatorAndParamTest() {
        Minus m1 = new Minus(new NumberTerm(1), new NumberTerm(2));
        Plus m2 = new Plus(new NumberTerm(3), new NumberTerm(4));
        assertFalse(m1.equals(m2));
    }

    @Test
    public void MinusEqualsOtherOperatorTest() {
        Minus m1 = new Minus(new NumberTerm(1), new NumberTerm(2));
        Plus m2 = new Plus(new NumberTerm(1), new NumberTerm(2));
        assertFalse(m1.equals(m2));
    }

    @Test
    public void MinusEqualsNullTest() {
        Minus m1 = new Minus(new NumberTerm(1), new NumberTerm(2));
        assertFalse(m1.equals(null));
    }

    @Test
    public void PlusToStringTest() {
        Plus plus1 = new Plus(new NumberTerm(2), new NumberTerm(1));
        assertEquals("2 + 1", plus1.toString());
    }

    @Test
    public void PlusEqualsNullTest() {
        Plus p1 = new Plus(new NumberTerm(1), new NumberTerm(2));
        assertFalse(p1.equals(null));
    }
}
