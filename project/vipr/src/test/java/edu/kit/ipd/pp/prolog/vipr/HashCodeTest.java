package edu.kit.ipd.pp.prolog.vipr;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.model.ast.FunctorTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.ListFunctor;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Minus;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Multiplication;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NumberTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Plus;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Term;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Variable;

public class HashCodeTest {
    int random;
    int random2;

    @Before
    public void setUp() {
        random = (int) (Math.random() * 1000);
        random2 = (int) (Math.random() * 1000);
    }

    @Test
    public void numberTermHashCode() {
        assertEquals(new NumberTerm(random).hashCode(), new NumberTerm(random).hashCode());
    }

    @Test
    public void variablehashCode() {
        Variable v = new Variable("TEST");
        Variable v2 = new Variable("TEST");
        v.renumber(random);
        v2.renumber(random);
        assertEquals(v.hashCode(), v2.hashCode());
    }

    @Test
    public void functorTermhashCodse() {
        LinkedList<Term> para = new LinkedList<>();
        LinkedList<Term> para2 = new LinkedList<>();
        para.add(new NumberTerm(random));
        para2.add(new NumberTerm(random));
        Variable v = new Variable("XYZ");
        Variable v2 = new Variable("XYZ");
        v.renumber(random2);
        v2.renumber(random2);
        para.add(v);
        para2.add(v2);
        FunctorTerm ft1 = new FunctorTerm("test", para);
        FunctorTerm ft2 = new FunctorTerm("test", para2);
        assertEquals(ft1.hashCode(), ft2.hashCode());
    }

    @Test
    public void listFunctorHashCode() {

        Variable v = new Variable("XYZ");
        Variable v2 = new Variable("XYZ");
        v.renumber(random2);
        v2.renumber(random2);
        ListFunctor lt1 = new ListFunctor(new NumberTerm(random), v);
        ListFunctor lt2 = new ListFunctor(new NumberTerm(random), v2);
        assertEquals(lt1.hashCode(), lt2.hashCode());

    }

    @Test
    public void plusHashCode() {
        Plus p = new Plus(new NumberTerm(random), new NumberTerm(random2));
        Plus p2 = new Plus(new NumberTerm(random), new NumberTerm(random2));
        assertEquals(p.hashCode(), p2.hashCode());
    }

    @Test
    public void minusHashCode() {
        Minus m = new Minus(new NumberTerm(random), new NumberTerm(random2));
        Minus m2 = new Minus(new NumberTerm(random), new NumberTerm(random2));
        assertEquals(m.hashCode(), m2.hashCode());
    }

    @Test
    public void MultiplicationHashCode() {
        Multiplication m = new Multiplication(new NumberTerm(random), new NumberTerm(random2));
        Multiplication m2 = new Multiplication(new NumberTerm(random), new NumberTerm(random2));
        assertEquals(m.hashCode(), m2.hashCode());
    }
}
