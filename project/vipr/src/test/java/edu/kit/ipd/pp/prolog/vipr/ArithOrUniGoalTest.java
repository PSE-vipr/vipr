package edu.kit.ipd.pp.prolog.vipr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.model.ast.Equal;
import edu.kit.ipd.pp.prolog.vipr.model.ast.FunctorTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.GreaterOrEqual;
import edu.kit.ipd.pp.prolog.vipr.model.ast.LessOrEqual;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NotEqual;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NumberTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.UnificationGoal;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Variable;

public class ArithOrUniGoalTest {

    @Test
    public void UnificationGoalGetCopyTest() {
        UnificationGoal ug1 = new UnificationGoal(new Variable("X"), new NumberTerm(1));
        assertEquals(ug1.getClass(), ug1.deepCopy().getClass());
        assertEquals(ug1.getLhs(), ug1.deepCopy().getLhs());
        assertEquals(ug1.getRhs(), ug1.deepCopy().getRhs());
    }

    @Test
    public void UnificationGoalGetOperatorTokenTest() {
        UnificationGoal ug1 = new UnificationGoal(new Variable("X"), new FunctorTerm("ft"));
        assertEquals(new FunctorTerm("="), ug1.getOperatorToken());
    }

    @Test
    public void GreaterOrEqualGetCopyTest() {
        GreaterOrEqual ge1 = new GreaterOrEqual(new NumberTerm(2), new NumberTerm(1));
        assertEquals(ge1.getClass(), ge1.deepCopy().getClass());
        assertEquals(ge1.getLhs(), ge1.deepCopy().getLhs());
        assertEquals(ge1.getRhs(), ge1.deepCopy().getRhs());
    }

    @Test
    public void GreaterOrEqualGetOperatorTokenTest() {
        GreaterOrEqual ge1 = new GreaterOrEqual(new NumberTerm(2), new NumberTerm(1));
        assertEquals(new FunctorTerm(">="), ge1.getOperatorToken());
    }

    @Test
    public void EqualGetCopyTest() {
        Equal e1 = new Equal(new NumberTerm(2), new NumberTerm(1));
        assertEquals(e1.getClass(), e1.deepCopy().getClass());
        assertEquals(e1.getLhs(), e1.deepCopy().getLhs());
        assertEquals(e1.getRhs(), e1.deepCopy().getRhs());
    }

    @Test
    public void EqualGetOperatorTokenTest() {
        Equal e1 = new Equal(new NumberTerm(2), new NumberTerm(1));
        assertEquals(new FunctorTerm("=:="), e1.getOperatorToken());
    }

    @Test
    public void NotEqualGetCopyTest() {
        NotEqual ne1 = new NotEqual(new NumberTerm(2), new NumberTerm(1));
        assertEquals(ne1.getClass(), ne1.deepCopy().getClass());
        assertEquals(ne1.getLhs(), ne1.deepCopy().getLhs());
        assertEquals(ne1.getRhs(), ne1.deepCopy().getRhs());
    }

    @Test
    public void NotEqualGetOperatorTokenTest() {
        NotEqual ne1 = new NotEqual(new NumberTerm(2), new NumberTerm(1));
        assertEquals(new FunctorTerm("=\\="), ne1.getOperatorToken());
    }

    @Test
    public void LessOrEqualGetCopyTest() {
        LessOrEqual le1 = new LessOrEqual(new NumberTerm(2), new NumberTerm(1));
        assertEquals(le1.getClass(), le1.deepCopy().getClass());
        assertEquals(le1.getLhs(), le1.deepCopy().getLhs());
        assertEquals(le1.getRhs(), le1.deepCopy().getRhs());
    }

    @Test
    public void LessOrEqualGetOperatorTokenTest() {
        LessOrEqual le1 = new LessOrEqual(new NumberTerm(2), new NumberTerm(1));
        assertEquals(new FunctorTerm("=&#60;"), le1.getOperatorToken());
    }
}
