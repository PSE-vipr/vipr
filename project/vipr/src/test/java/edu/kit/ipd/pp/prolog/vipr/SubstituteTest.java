package edu.kit.ipd.pp.prolog.vipr;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.model.ast.FunctorTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NumberTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Term;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Variable;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Substitution;

public class SubstituteTest {

    /**
     * Testet die Methode substitute() in NumberTerm.
     */
    @Test
    public void substituteNumberTerm() {
        NumberTerm n1 = new NumberTerm(1);
        Variable v1 = new Variable("X");
        assertEquals(n1.substitute(new Substitution(v1, n1)), new NumberTerm(1));
    }

    /**
     * Testet die Methode substitute() in Variable. Der Fall, wenn die Variable, die
     * substituiert werden soll, nicht mit der Variable übereinstimmt, auf der die
     * Methode aufgerufen wird.
     */
    @Test
    public void substituteVariableIfNotEquals() {
        Variable v1 = new Variable("X");
        Variable v2 = new Variable("Y");
        assertEquals(v1.substitute(new Substitution(v2, new Variable("Z"))), new Variable("X"));
    }

    /**
     * Testet die Methode substitute() in Variable. Der Fall, wenn die Variable, die
     * substituiert werden soll, mit der Variable übereinstimmt, auf der die Methode
     * aufgerufen wird.
     */
    @Test
    public void substituteVariableIfEquals() {
        Variable v1 = new Variable("X");
        Variable v2 = new Variable("Z");
        assertEquals(v1.substitute(new Substitution(v1, v2)), v2);
    }

    /**
     * Testet die Methode substitute() in FunctorTerm. Der Fall, wenn die Methode
     * auf einem FunctorTerm ohne Parameter aufgerufen wird.
     */
    @Test
    public void substituteFunctorTermWithoutParam() {
        FunctorTerm ft = new FunctorTerm("a");
        Variable v1 = new Variable("X");
        Variable v2 = new Variable("Y");
        assertEquals(ft.substitute(new Substitution(v1, v2)), new FunctorTerm("a"));
    }

    /**
     * Testet die Methode substitute() in FunctorTerm. Der Fall, wenn die Methode
     * auf einem FunctorTerm mit zwei Parametern aufgerufen wird. Die Parameter sind
     * Variablen, die erste Variable wird substituiert.
     */
    @Test
    public void substituteFunctorTermWithTwoParam() {
        FunctorTerm ft = new FunctorTerm("a", new Variable("X"), new Variable("Y"));
        Variable v1 = new Variable("X");
        Variable v2 = new Variable("Y");
        assertEquals(ft.substitute(new Substitution(v1, v2)), new FunctorTerm("a", v2, v2));
    }

    /**
     * Testet die Methode substitute() in FunctorTerm. Der Fall, wenn die Methode
     * auf einem FunctorTerm mit zwei Parametern aufgerufen wird. Die Parameter sind
     * Variablen, die zweite Variable wird substituiert.
     */
    @Test
    public void substituteSecondParam() {
        FunctorTerm ft = new FunctorTerm("a", new Variable("X"), new Variable("Y"));
        Variable v1 = new Variable("X");
        Variable v2 = new Variable("Y");
        assertEquals(ft.substitute(new Substitution(v2, v1)), new FunctorTerm("a", v1, v1));
    }

    /**
     * Testet die Methode substitute() in FunctorTerm. Der Fall, wenn die Methode
     * auf einem FunctorTerm mit einer Liste von Parametern aufgerufen wird. Die
     * Parameter sind FunctorTerm ohne Parameter, NumberTerm und Variable. Die
     * Variable wird durch NumberTerm substituiert.
     */
    @Test
    public void substituteFunctorTermWithParamList() {
        LinkedList<Term> paramList = new LinkedList<Term>();

        FunctorTerm a = new FunctorTerm("a");
        paramList.add(a);
        NumberTerm n1 = new NumberTerm(1);
        paramList.add(n1);
        Variable v1 = new Variable("X");
        paramList.add(v1);

        FunctorTerm ft = new FunctorTerm("b", paramList);

        LinkedList<Term> paramListNew = new LinkedList<Term>();
        paramListNew.add(a);
        paramListNew.add(n1);
        paramListNew.add(n1);

        assertEquals(ft.substitute(new Substitution(v1, n1)), new FunctorTerm("b", paramListNew));

    }
}
