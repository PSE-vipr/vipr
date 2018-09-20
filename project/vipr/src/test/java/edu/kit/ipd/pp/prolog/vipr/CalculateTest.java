package edu.kit.ipd.pp.prolog.vipr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.model.ast.CalculateException;
import edu.kit.ipd.pp.prolog.vipr.model.ast.FunctorTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Minus;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Multiplication;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NumberTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Plus;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Variable;

public class CalculateTest {

    // Calculate auf Variable wirft Exception.
    @Test(expected = CalculateException.class)
    public void variableTest1() throws CalculateException {
        Variable variable = new Variable("var");
        variable.calculate();
    }

    // Numberterm mit Wert 10. Gibt diesen bei calculate zurück.
    @Test
    public void numberTermTest1() {
        NumberTerm numberTerm = new NumberTerm(10);
        int result = numberTerm.calculate();
        assertEquals(10, result);
    }

    // Numberterm mit Wert (5+3)*(4-2)=16. Gibt diesen bei calculate zurück.
    @Test
    public void numberTermTest2() throws CalculateException {
        Multiplication multiplication = new Multiplication(new Plus(new NumberTerm(5), new NumberTerm(3)),
                new Minus(new NumberTerm(4), new NumberTerm(2)));
        int result = multiplication.calculate();
        assertEquals(16, result);
    }

    // Calculate auf Funktorterm wirft Exception.
    @Test(expected = CalculateException.class)
    public void functorTermTest1() throws CalculateException {
        FunctorTerm functorTerm = new FunctorTerm("functerm");
        functorTerm.calculate();
    }
}
