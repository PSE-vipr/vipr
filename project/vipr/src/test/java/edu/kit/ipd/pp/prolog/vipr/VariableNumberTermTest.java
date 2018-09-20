package edu.kit.ipd.pp.prolog.vipr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.LinkedList;

import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.model.ast.FunctorTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NumberTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Term;
import edu.kit.ipd.pp.prolog.vipr.model.ast.UnifyException;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Variable;

public class VariableNumberTermTest {

    @Test
    public void VariableEqualsNullTest() {
        Variable v1 = new Variable("X");
        assertFalse(v1.equals(null));
    }

    @Test
    public void NumberTermEqualsNullTest() {
        NumberTerm nt1 = new NumberTerm(0);
        assertFalse(nt1.equals(null));
    }

    @Test(expected = UnifyException.class)
    public void VariableUnifyFunctorTest() throws UnifyException {
        Variable v1 = new Variable("X");
        v1.unifyFunctor(new FunctorTerm("ft"));
    }

    @Test(expected = UnifyException.class)
    public void VariableUnifyFunctorWithFunctorTest() throws UnifyException {
        Variable v1 = new Variable("X");
        v1.unifyFunctorWithFunctor(new FunctorTerm("ft"));
    }

    @Test
    public void TailTokenTest() {
        Variable v1 = new Variable("X");
        LinkedList<Term> tailTokens = new LinkedList<>();
        tailTokens.add(new FunctorTerm("|"));
        tailTokens.addAll(v1.getTokens());
        assertEquals(v1.getTailTokens(), tailTokens);
    }
}
