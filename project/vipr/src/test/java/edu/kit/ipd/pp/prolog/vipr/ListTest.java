package edu.kit.ipd.pp.prolog.vipr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.model.ast.EmptyList;
import edu.kit.ipd.pp.prolog.vipr.model.ast.FunctorTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.ListFunctor;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NumberTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Term;

public class ListTest {

    @Test
    public void ListFunctorToStringTest() {
        ListFunctor lf = new ListFunctor(new NumberTerm(1), new ListFunctor(new NumberTerm(2), new NumberTerm(3)));
        assertEquals("[1, 2|3]", lf.toString());
    }

    @Test
    public void ListFunctorEqualsNullTest() {
        ListFunctor lf = new ListFunctor(new NumberTerm(1), new ListFunctor(new NumberTerm(2), new NumberTerm(3)));
        assertFalse(lf.equals(null));
    }

    @Test
    public void EmptyListDeepCopyTest() {
        EmptyList el1 = new EmptyList();
        assertEquals(new EmptyList(), el1.deepCopy());
    }

    @Test
    public void EmptyListToStringTest() {
        EmptyList el1 = new EmptyList();
        assertEquals("[]", el1.toString());
    }

    @Test
    public void EmptyListGetTailStringTest() {
        EmptyList el1 = new EmptyList();
        assertEquals("", el1.getTailString());
    }

    @Test
    public void EmptyListGetTokensTest() {
        EmptyList el1 = new EmptyList();
        List<Term> tokens = new LinkedList<Term>();
        List<Term> tokens2 = new LinkedList<Term>();
        tokens2.add(new FunctorTerm("|"));
        tokens.add(new FunctorTerm("["));
        tokens.add(new FunctorTerm("]"));

        assertEquals(tokens, el1.getTokens());

    }
}
