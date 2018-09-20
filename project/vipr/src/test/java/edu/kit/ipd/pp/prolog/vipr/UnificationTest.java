package edu.kit.ipd.pp.prolog.vipr;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.model.ast.EmptyList;
import edu.kit.ipd.pp.prolog.vipr.model.ast.FunctorTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.ListFunctor;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Minus;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Multiplication;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NumberTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Operator;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Plus;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Term;
import edu.kit.ipd.pp.prolog.vipr.model.ast.UnifyException;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Variable;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Substitution;

public class UnificationTest {

    // --- VARIBALE UNIFICATION ---

    @Test
    public void unifyVarWithSameName() throws UnifyException {
        Variable x1 = new Variable("X");
        Variable x2 = new Variable("X");

        List<Substitution> substitutions = x1.unify(x2);
        assertEquals(substitutions, new LinkedList<Substitution>());
    }

    @Test
    public void unifyVarWithVar() throws UnifyException {
        Variable x = new Variable("X");
        Variable y = new Variable("Y");

        List<Substitution> substitutions = x.unify(y);
        assertEquals(substitutions.get(0).getVar(), x);
        assertEquals(substitutions.get(0).getSubstituens(), y);
    }

    @Test
    public void unifyVarWithNum() throws UnifyException {
        Variable x = new Variable("X");
        NumberTerm one = new NumberTerm(1);

        List<Substitution> substitutions = x.unify(one);
        assertEquals(substitutions.get(0).getVar(), x);
        assertEquals(substitutions.get(0).getSubstituens(), one);
    }

    @Test
    public void unifyVarWithFunctor0Param() throws UnifyException {
        Variable x = new Variable("X");
        FunctorTerm functor = new FunctorTerm("functor");

        List<Substitution> substitutions = x.unify(functor);
        assertEquals(substitutions.get(0).getVar(), x);
        assertEquals(substitutions.get(0).getSubstituens(), functor);
    }

    @Test
    public void unifyVarWithFunctor2Param() throws UnifyException {
        Variable x = new Variable("X");
        FunctorTerm functor = new FunctorTerm("functor", new Variable("A"), new Variable("B"));

        List<Substitution> substitutions = x.unify(functor);
        assertEquals(substitutions.get(0).getVar(), x);
        assertEquals(substitutions.get(0).getSubstituens(), functor);
    }

    @Test
    public void unifyVarWithFunctor3Param() throws UnifyException {
        Variable x = new Variable("X");
        List<Term> listOfTerms = new LinkedList<Term>();
        listOfTerms.add(new Variable("A"));
        listOfTerms.add(new Variable("B"));
        listOfTerms.add(new Variable("C"));
        FunctorTerm functor = new FunctorTerm("functor", listOfTerms);

        List<Substitution> substitutions = x.unify(functor);
        assertEquals(substitutions.get(0).getVar(), x);
        assertEquals(substitutions.get(0).getSubstituens(), functor);
    }

    @Test
    public void unifyVarWithList() throws UnifyException {
        Variable x = new Variable("X");
        ListFunctor list = new ListFunctor(new Variable("A"), new Variable("B"));

        List<Substitution> substitutions = x.unify(list);
        assertEquals(substitutions.get(0).getVar(), x);
        assertEquals(substitutions.get(0).getSubstituens(), list);
    }

    @Test
    public void unifyVarWithEmptyList() throws UnifyException {
        Variable x = new Variable("X");
        EmptyList list = new EmptyList();

        List<Substitution> substitutions = x.unify(list);
        assertEquals(substitutions.get(0).getVar(), x);
        assertEquals(substitutions.get(0).getSubstituens(), list);
    }

    @Test
    public void unifyVarWithOperatorPlus() throws UnifyException {
        Variable x = new Variable("X");
        Plus plus = new Plus(new NumberTerm(1), new NumberTerm(2));

        List<Substitution> substitutions = x.unify(plus);
        assertEquals(substitutions.get(0).getVar(), x);
        assertEquals(substitutions.get(0).getSubstituens(), plus);
    }

    @Test
    public void unifyVarWithOperatorMinus() throws UnifyException {
        Variable x = new Variable("X");
        Minus minus = new Minus(new NumberTerm(1), new NumberTerm(2));

        List<Substitution> substitutions = x.unify(minus);
        assertEquals(substitutions.get(0).getVar(), x);
        assertEquals(substitutions.get(0).getSubstituens(), minus);
    }

    @Test
    public void unifyVarWithOperatorMult() throws UnifyException {
        Variable x = new Variable("X");
        Multiplication mult = new Multiplication(new NumberTerm(1), new NumberTerm(2));

        List<Substitution> substitutions = x.unify(mult);
        assertEquals(substitutions.get(0).getVar(), x);
        assertEquals(substitutions.get(0).getSubstituens(), mult);
    }

    @Test(expected = UnifyException.class)
    public void unifyVarWithFunctorContainingVar() throws UnifyException {
        Variable x = new Variable("X");
        FunctorTerm functor = new FunctorTerm("functor", x, x);

        x.unify(functor);
    }

    @Test(expected = UnifyException.class)
    public void unifyVarWithOperatorContainingVar() throws UnifyException {
        Variable x = new Variable("X");
        Operator operator = new Plus(x, x);

        x.unify(operator);
    }

    @Test(expected = UnifyException.class)
    public void unifyVarWithListContainingVar() throws UnifyException {
        Variable x = new Variable("X");
        ListFunctor list = new ListFunctor(x, x);

        x.unify(list);
    }

    // --- NUMBER UNIFICATION ---

    @Test
    public void unifyNumWithSameNum() throws UnifyException {
        NumberTerm one1 = new NumberTerm(1);
        NumberTerm one2 = new NumberTerm(1);

        List<Substitution> substitutions = one1.unify(one2);
        assertEquals(substitutions, new LinkedList<Substitution>());
    }

    @Test
    public void unifyNumWithVar() throws UnifyException {
        NumberTerm one = new NumberTerm(1);
        Variable x = new Variable("X");

        List<Substitution> substitutions = one.unify(x);
        assertEquals(substitutions.get(0).getVar(), x);
        assertEquals(substitutions.get(0).getSubstituens(), one);
    }

    @Test(expected = UnifyException.class)
    public void unifyNumWithNum() throws UnifyException {
        NumberTerm one = new NumberTerm(1);
        NumberTerm two = new NumberTerm(2);

        one.unify(two);
    }

    @Test(expected = UnifyException.class)
    public void unifyNumWithFunctor0Param() throws UnifyException {
        NumberTerm one = new NumberTerm(1);
        FunctorTerm functor = new FunctorTerm("functor");

        one.unify(functor);
    }

    @Test(expected = UnifyException.class)
    public void unifyNumWithFunctor2Param() throws UnifyException {
        NumberTerm one = new NumberTerm(1);
        FunctorTerm functor = new FunctorTerm("functor", new Variable("A"), new Variable("B"));

        one.unify(functor);
    }

    @Test(expected = UnifyException.class)
    public void unifyNumWithFunctor3Param() throws UnifyException {
        NumberTerm one = new NumberTerm(1);
        List<Term> listOfTerms = new LinkedList<Term>();
        listOfTerms.add(new Variable("A"));
        listOfTerms.add(new Variable("B"));
        listOfTerms.add(new Variable("C"));
        FunctorTerm functor = new FunctorTerm("functor", listOfTerms);

        one.unify(functor);
    }

    @Test(expected = UnifyException.class)
    public void unifyNumWithList() throws UnifyException {
        NumberTerm one = new NumberTerm(1);
        ListFunctor list = new ListFunctor(new Variable("A"), new Variable("B"));

        one.unify(list);
    }

    @Test(expected = UnifyException.class)
    public void unifyNumWithEmptyList() throws UnifyException {
        NumberTerm one = new NumberTerm(1);
        EmptyList list = new EmptyList();

        one.unify(list);
    }

    @Test(expected = UnifyException.class)
    public void unifyNumWithOperatorPlus() throws UnifyException {
        NumberTerm one = new NumberTerm(1);
        Plus plus = new Plus(new NumberTerm(1), new NumberTerm(2));

        one.unify(plus);
    }

    @Test(expected = UnifyException.class)
    public void unifyNumWithOperatorMinus() throws UnifyException {
        NumberTerm one = new NumberTerm(1);
        Minus minus = new Minus(new NumberTerm(1), new NumberTerm(2));

        one.unify(minus);
    }

    @Test(expected = UnifyException.class)
    public void unifyNumWithOperatorMult() throws UnifyException {
        NumberTerm one = new NumberTerm(1);
        Multiplication mult = new Multiplication(new NumberTerm(1), new NumberTerm(2));

        one.unify(mult);
    }

    // --- FUNCTOR (0 PARAM) UNIFICATION ---

    @Test
    public void unifyF0WithSameFunctor() throws UnifyException {
        FunctorTerm functor1 = new FunctorTerm("functor");
        FunctorTerm functor2 = new FunctorTerm("functor");

        List<Substitution> substitutions = functor1.unify(functor2);
        assertEquals(substitutions, new LinkedList<Substitution>());
    }

    @Test
    public void unifyF0WithVar() throws UnifyException {
        FunctorTerm functor = new FunctorTerm("functor");
        Variable x = new Variable("X");

        List<Substitution> substitutions = functor.unify(x);
        assertEquals(substitutions.get(0).getVar(), x);
        assertEquals(substitutions.get(0).getSubstituens(), functor);
    }

    @Test(expected = UnifyException.class)
    public void unifyF0WithNum() throws UnifyException {
        FunctorTerm functor = new FunctorTerm("functor");
        NumberTerm two = new NumberTerm(2);

        functor.unify(two);
    }

    @Test(expected = UnifyException.class)
    public void unifyF0WithFunctor0Param() throws UnifyException {
        FunctorTerm functor1 = new FunctorTerm("functor1");
        FunctorTerm functor2 = new FunctorTerm("functor2");

        functor1.unify(functor2);
    }

    @Test(expected = UnifyException.class)
    public void unifyF0WithFunctor2Param() throws UnifyException {
        FunctorTerm functor1 = new FunctorTerm("functor");
        FunctorTerm functor2 = new FunctorTerm("functor", new Variable("A"), new Variable("B"));

        functor1.unify(functor2);
    }

    @Test(expected = UnifyException.class)
    public void unifyF0WithFunctor3Param() throws UnifyException {
        FunctorTerm functor1 = new FunctorTerm("functor");
        List<Term> listOfTerms = new LinkedList<Term>();
        listOfTerms.add(new Variable("A"));
        listOfTerms.add(new Variable("B"));
        listOfTerms.add(new Variable("C"));
        FunctorTerm functor2 = new FunctorTerm("functor", listOfTerms);

        functor1.unify(functor2);
    }

    @Test(expected = UnifyException.class)
    public void unifyF0WithList() throws UnifyException {
        FunctorTerm functor = new FunctorTerm("functor");
        ListFunctor list = new ListFunctor(new Variable("A"), new Variable("B"));

        functor.unify(list);
    }

    @Test(expected = UnifyException.class)
    public void unifyF0WithEmptyList() throws UnifyException {
        FunctorTerm functor = new FunctorTerm("functor");
        EmptyList list = new EmptyList();

        functor.unify(list);
    }

    @Test(expected = UnifyException.class)
    public void unifyF0WithOperatorPlus() throws UnifyException {
        FunctorTerm functor = new FunctorTerm("functor");
        Plus plus = new Plus(new NumberTerm(1), new NumberTerm(2));

        functor.unify(plus);
    }

    @Test(expected = UnifyException.class)
    public void unifyF0WithOperatorMinus() throws UnifyException {
        FunctorTerm functor = new FunctorTerm("functor");
        Minus minus = new Minus(new NumberTerm(1), new NumberTerm(2));

        functor.unify(minus);
    }

    @Test(expected = UnifyException.class)
    public void unifyF0WithOperatorMult() throws UnifyException {
        FunctorTerm functor = new FunctorTerm("functor");
        Multiplication mult = new Multiplication(new NumberTerm(1), new NumberTerm(2));

        functor.unify(mult);
    }

    // --- FUNCTOR (2 PARAM) UNIFICATION ---

    @Test
    public void unifyF2WithSameFunctor() throws UnifyException {
        FunctorTerm functor1 = new FunctorTerm("functor", new Variable("A"), new Variable("B"));
        FunctorTerm functor2 = new FunctorTerm("functor", new Variable("A"), new Variable("B"));

        List<Substitution> substitutions = functor1.unify(functor2);
        assertEquals(substitutions, new LinkedList<Substitution>());
    }

    @Test
    public void unifyF2WithVar() throws UnifyException {
        FunctorTerm functor = new FunctorTerm("functor", new Variable("A"), new Variable("B"));
        Variable x = new Variable("X");

        List<Substitution> substitutions = functor.unify(x);
        assertEquals(substitutions.get(0).getVar(), x);
        assertEquals(substitutions.get(0).getSubstituens(), functor);
    }

    @Test(expected = UnifyException.class)
    public void unifyF2WithNum() throws UnifyException {
        FunctorTerm functor = new FunctorTerm("functor", new Variable("A"), new Variable("B"));
        NumberTerm two = new NumberTerm(2);

        functor.unify(two);
    }

    @Test(expected = UnifyException.class)
    public void unifyF2WithFunctor0Param() throws UnifyException {
        FunctorTerm functor1 = new FunctorTerm("functor", new Variable("A"), new Variable("B"));
        FunctorTerm functor2 = new FunctorTerm("functor2");

        functor1.unify(functor2);
    }

    @Test
    public void unifyF2WithFunctor2ParamV1() throws UnifyException {
        Variable a = new Variable("A");
        Variable b = new Variable("B");
        Variable c = new Variable("C");
        Variable d = new Variable("D");

        FunctorTerm functor1 = new FunctorTerm("functor", a, b);
        FunctorTerm functor2 = new FunctorTerm("functor", c, d);

        List<Substitution> substitutions = functor1.unify(functor2);
        assertEquals(substitutions.get(0).getVar(), a);
        assertEquals(substitutions.get(0).getSubstituens(), c);
        assertEquals(substitutions.get(1).getVar(), b);
        assertEquals(substitutions.get(1).getSubstituens(), d);
    }

    @Test
    public void unifyF2WithFunctor2ParamV2() throws UnifyException {
        Variable a = new Variable("A");
        Variable b = new Variable("B");
        FunctorTerm c = new FunctorTerm("c");
        FunctorTerm d = new FunctorTerm("d");

        FunctorTerm functor1 = new FunctorTerm("functor", a, b);
        FunctorTerm functor2 = new FunctorTerm("functor", c, d);

        List<Substitution> substitutions = functor1.unify(functor2);
        assertEquals(substitutions.get(0).getVar(), a);
        assertEquals(substitutions.get(0).getSubstituens(), c);
        assertEquals(substitutions.get(1).getVar(), b);
        assertEquals(substitutions.get(1).getSubstituens(), d);
    }

    @Test(expected = UnifyException.class)
    public void unifyF2WithFunctor2ParamV3() throws UnifyException {
        FunctorTerm a = new FunctorTerm("a");
        FunctorTerm b = new FunctorTerm("b");
        FunctorTerm c = new FunctorTerm("c");
        FunctorTerm d = new FunctorTerm("d");

        FunctorTerm functor1 = new FunctorTerm("functor", a, b);
        FunctorTerm functor2 = new FunctorTerm("functor", c, d);

        functor1.unify(functor2);
    }

    @Test(expected = UnifyException.class)
    public void unifyF2WithFunctor2ParamV5() throws UnifyException {
        FunctorTerm a = new FunctorTerm("a");
        FunctorTerm b = new FunctorTerm("b");
        Variable c = new Variable("C");
        FunctorTerm d = new FunctorTerm("d");

        FunctorTerm functor1 = new FunctorTerm("functor", a, b);
        FunctorTerm functor2 = new FunctorTerm("functor", c, d);

        functor1.unify(functor2);
    }

    @Test(expected = UnifyException.class)
    public void unifyF2WithFunctor3Param() throws UnifyException {
        FunctorTerm functor1 = new FunctorTerm("functor", new Variable("A"), new Variable("B"));
        List<Term> listOfTerms = new LinkedList<Term>();
        listOfTerms.add(new Variable("A"));
        listOfTerms.add(new Variable("B"));
        listOfTerms.add(new Variable("C"));
        FunctorTerm functor2 = new FunctorTerm("functor", listOfTerms);

        functor1.unify(functor2);
    }

    @Test(expected = UnifyException.class)
    public void unifyF2WithList() throws UnifyException {
        FunctorTerm functor = new FunctorTerm("functor", new Variable("A"), new Variable("B"));
        ListFunctor list = new ListFunctor(new Variable("A"), new Variable("B"));

        functor.unify(list);
    }

    @Test(expected = UnifyException.class)
    public void unifyF2WithEmptyList() throws UnifyException {
        FunctorTerm functor = new FunctorTerm("functor", new Variable("A"), new Variable("B"));
        EmptyList list = new EmptyList();

        functor.unify(list);
    }

    @Test(expected = UnifyException.class)
    public void unifyF2WithOperatorPlus() throws UnifyException {
        FunctorTerm functor = new FunctorTerm("functor", new Variable("A"), new Variable("B"));
        Plus plus = new Plus(new NumberTerm(1), new NumberTerm(2));

        functor.unify(plus);
    }

    @Test(expected = UnifyException.class)
    public void unifyF2WithOperatorMinus() throws UnifyException {
        FunctorTerm functor = new FunctorTerm("functor", new Variable("A"), new Variable("B"));
        Minus minus = new Minus(new NumberTerm(1), new NumberTerm(2));

        functor.unify(minus);
    }

    @Test(expected = UnifyException.class)
    public void unifyF2WithOperatorMult() throws UnifyException {
        FunctorTerm functor = new FunctorTerm("functor", new Variable("A"), new Variable("B"));
        Multiplication mult = new Multiplication(new NumberTerm(1), new NumberTerm(2));

        functor.unify(mult);
    }

    // --- FUNCTOR (3 PARAM) UNIFICATION ---

    @Test
    public void unifyF3WithSameFunctor() throws UnifyException {
        List<Term> listOfTerms1 = new LinkedList<Term>();
        listOfTerms1.add(new Variable("A"));
        listOfTerms1.add(new Variable("B"));
        listOfTerms1.add(new Variable("C"));
        FunctorTerm functor1 = new FunctorTerm("functor", listOfTerms1);

        List<Term> listOfTerms2 = new LinkedList<Term>();
        listOfTerms2.add(new Variable("A"));
        listOfTerms2.add(new Variable("B"));
        listOfTerms2.add(new Variable("C"));
        FunctorTerm functor2 = new FunctorTerm("functor", listOfTerms2);

        List<Substitution> substitutions = functor1.unify(functor2);
        assertEquals(substitutions, new LinkedList<Substitution>());
    }

    @Test
    public void unifyF3WithVar() throws UnifyException {
        List<Term> listOfTerms = new LinkedList<Term>();
        listOfTerms.add(new Variable("A"));
        listOfTerms.add(new Variable("B"));
        listOfTerms.add(new Variable("C"));
        FunctorTerm functor = new FunctorTerm("functor", listOfTerms);
        Variable x = new Variable("X");

        List<Substitution> substitutions = functor.unify(x);
        assertEquals(substitutions.get(0).getVar(), x);
        assertEquals(substitutions.get(0).getSubstituens(), functor);
    }

    @Test(expected = UnifyException.class)
    public void unifyF3WithNum() throws UnifyException {
        List<Term> listOfTerms = new LinkedList<Term>();
        listOfTerms.add(new Variable("A"));
        listOfTerms.add(new Variable("B"));
        listOfTerms.add(new Variable("C"));
        FunctorTerm functor = new FunctorTerm("functor", listOfTerms);
        NumberTerm two = new NumberTerm(2);

        functor.unify(two);
    }

    @Test(expected = UnifyException.class)
    public void unifyF3WithFunctor0Param() throws UnifyException {
        List<Term> listOfTerms = new LinkedList<Term>();
        listOfTerms.add(new Variable("A"));
        listOfTerms.add(new Variable("B"));
        listOfTerms.add(new Variable("C"));
        FunctorTerm functor1 = new FunctorTerm("functor", listOfTerms);
        FunctorTerm functor2 = new FunctorTerm("functor2");

        functor1.unify(functor2);
    }

    @Test(expected = UnifyException.class)
    public void unifyF3WithFunctor2Param() throws UnifyException {
        Variable a = new Variable("A");
        Variable b = new Variable("B");
        Variable c = new Variable("C");
        Variable d = new Variable("D");
        Variable e = new Variable("E");

        List<Term> listOfTerms = new LinkedList<Term>();
        listOfTerms.add(a);
        listOfTerms.add(b);
        listOfTerms.add(c);
        FunctorTerm functor1 = new FunctorTerm("functor", listOfTerms);

        FunctorTerm functor2 = new FunctorTerm("functor", d, e);

        functor1.unify(functor2);
    }

    @Test
    public void unifyF3WithFunctor3ParamV1() throws UnifyException {
        Variable a = new Variable("A");
        Variable b = new Variable("B");
        Variable c = new Variable("C");
        Variable d = new Variable("D");
        Variable e = new Variable("E");
        Variable f = new Variable("F");

        List<Term> listOfTerms1 = new LinkedList<Term>();
        listOfTerms1.add(a);
        listOfTerms1.add(b);
        listOfTerms1.add(c);
        FunctorTerm functor1 = new FunctorTerm("functor", listOfTerms1);

        List<Term> listOfTerms2 = new LinkedList<Term>();
        listOfTerms2.add(d);
        listOfTerms2.add(e);
        listOfTerms2.add(f);
        FunctorTerm functor2 = new FunctorTerm("functor", listOfTerms2);

        List<Substitution> substitutions = functor1.unify(functor2);
        assertEquals(substitutions.get(0).getVar(), a);
        assertEquals(substitutions.get(0).getSubstituens(), d);
        assertEquals(substitutions.get(1).getVar(), b);
        assertEquals(substitutions.get(1).getSubstituens(), e);
        assertEquals(substitutions.get(2).getVar(), c);
        assertEquals(substitutions.get(2).getSubstituens(), f);
    }

    @Test
    public void unifyF3WithFunctor3ParamV2() throws UnifyException {
        Variable a = new Variable("A");
        Variable b = new Variable("B");
        Variable c = new Variable("C");
        FunctorTerm d = new FunctorTerm("d");
        FunctorTerm e = new FunctorTerm("e");
        FunctorTerm f = new FunctorTerm("f");

        List<Term> listOfTerms1 = new LinkedList<Term>();
        listOfTerms1.add(a);
        listOfTerms1.add(b);
        listOfTerms1.add(c);
        FunctorTerm functor1 = new FunctorTerm("functor", listOfTerms1);

        List<Term> listOfTerms2 = new LinkedList<Term>();
        listOfTerms2.add(d);
        listOfTerms2.add(e);
        listOfTerms2.add(f);
        FunctorTerm functor2 = new FunctorTerm("functor", listOfTerms2);

        List<Substitution> substitutions = functor1.unify(functor2);
        assertEquals(substitutions.get(0).getVar(), a);
        assertEquals(substitutions.get(0).getSubstituens(), d);
        assertEquals(substitutions.get(1).getVar(), b);
        assertEquals(substitutions.get(1).getSubstituens(), e);
        assertEquals(substitutions.get(2).getVar(), c);
        assertEquals(substitutions.get(2).getSubstituens(), f);
    }

    @Test(expected = UnifyException.class)
    public void unifyF3WithFunctor3ParamV3() throws UnifyException {
        FunctorTerm a = new FunctorTerm("a");
        FunctorTerm b = new FunctorTerm("b");
        FunctorTerm c = new FunctorTerm("c");
        FunctorTerm d = new FunctorTerm("d");
        FunctorTerm e = new FunctorTerm("e");
        FunctorTerm f = new FunctorTerm("f");

        List<Term> listOfTerms1 = new LinkedList<Term>();
        listOfTerms1.add(a);
        listOfTerms1.add(b);
        listOfTerms1.add(c);
        FunctorTerm functor1 = new FunctorTerm("functor", listOfTerms1);

        List<Term> listOfTerms2 = new LinkedList<Term>();
        listOfTerms2.add(d);
        listOfTerms2.add(e);
        listOfTerms2.add(f);
        FunctorTerm functor2 = new FunctorTerm("functor", listOfTerms2);

        functor1.unify(functor2);
    }

    @Test(expected = UnifyException.class)
    public void unifyF3WithFunctor4Param() throws UnifyException {
        Variable a = new Variable("A");
        Variable b = new Variable("B");
        Variable c = new Variable("C");
        Variable d = new Variable("D");
        Variable e = new Variable("E");
        Variable f = new Variable("F");
        Variable g = new Variable("g");

        List<Term> listOfTerms1 = new LinkedList<Term>();
        listOfTerms1.add(a);
        listOfTerms1.add(b);
        listOfTerms1.add(c);
        FunctorTerm functor1 = new FunctorTerm("functor", listOfTerms1);

        List<Term> listOfTerms2 = new LinkedList<Term>();
        listOfTerms2.add(d);
        listOfTerms2.add(e);
        listOfTerms2.add(f);
        listOfTerms2.add(g);
        FunctorTerm functor2 = new FunctorTerm("functor", listOfTerms2);

        functor1.unify(functor2);
    }

    @Test(expected = UnifyException.class)
    public void unifyF3WithList() throws UnifyException {
        List<Term> listOfTerms = new LinkedList<Term>();
        listOfTerms.add(new Variable("A"));
        listOfTerms.add(new Variable("B"));
        listOfTerms.add(new Variable("C"));
        FunctorTerm functor = new FunctorTerm("functor", listOfTerms);
        ListFunctor list = new ListFunctor(new Variable("A"), new Variable("B"));

        functor.unify(list);
    }

    @Test(expected = UnifyException.class)
    public void unifyF3WithEmptyList() throws UnifyException {
        List<Term> listOfTerms = new LinkedList<Term>();
        listOfTerms.add(new Variable("A"));
        listOfTerms.add(new Variable("B"));
        listOfTerms.add(new Variable("C"));
        FunctorTerm functor = new FunctorTerm("functor", listOfTerms);
        EmptyList list = new EmptyList();

        functor.unify(list);
    }

    @Test(expected = UnifyException.class)
    public void unifyF3WithOperatorPlus() throws UnifyException {
        List<Term> listOfTerms = new LinkedList<Term>();
        listOfTerms.add(new Variable("A"));
        listOfTerms.add(new Variable("B"));
        listOfTerms.add(new Variable("C"));
        FunctorTerm functor = new FunctorTerm("functor", listOfTerms);
        Plus plus = new Plus(new NumberTerm(1), new NumberTerm(2));

        functor.unify(plus);
    }

    @Test(expected = UnifyException.class)
    public void unifyF3WithOperatorMinus() throws UnifyException {
        List<Term> listOfTerms = new LinkedList<Term>();
        listOfTerms.add(new Variable("A"));
        listOfTerms.add(new Variable("B"));
        listOfTerms.add(new Variable("C"));
        FunctorTerm functor = new FunctorTerm("functor", listOfTerms);
        Minus minus = new Minus(new NumberTerm(1), new NumberTerm(2));

        functor.unify(minus);
    }

    @Test(expected = UnifyException.class)
    public void unifyF3WithOperatorMult() throws UnifyException {
        List<Term> listOfTerms = new LinkedList<Term>();
        listOfTerms.add(new Variable("A"));
        listOfTerms.add(new Variable("B"));
        listOfTerms.add(new Variable("C"));
        FunctorTerm functor = new FunctorTerm("functor", listOfTerms);
        Multiplication mult = new Multiplication(new NumberTerm(1), new NumberTerm(2));

        functor.unify(mult);
    }

    // --- LIST UNIFICATION ---

    @Test
    public void unifyListWithSameList() throws UnifyException {
        ListFunctor list1 = new ListFunctor(new Variable("A"), new Variable("B"));
        ListFunctor list2 = new ListFunctor(new Variable("A"), new Variable("B"));

        List<Substitution> substitutions = list1.unify(list2);
        assertEquals(substitutions, new LinkedList<Substitution>());
    }

    @Test
    public void unifyListWithVar() throws UnifyException {
        ListFunctor list = new ListFunctor(new Variable("A"), new Variable("B"));
        Variable x = new Variable("X");

        List<Substitution> substitutions = list.unify(x);
        assertEquals(substitutions.get(0).getVar(), x);
        assertEquals(substitutions.get(0).getSubstituens(), list);
    }

    @Test(expected = UnifyException.class)
    public void unifyListWithNum() throws UnifyException {
        ListFunctor list = new ListFunctor(new Variable("A"), new Variable("B"));
        NumberTerm two = new NumberTerm(2);

        list.unify(two);
    }

    @Test(expected = UnifyException.class)
    public void unifyListWithFunctor0Param() throws UnifyException {
        ListFunctor list = new ListFunctor(new Variable("A"), new Variable("B"));
        FunctorTerm functor = new FunctorTerm("functor");

        list.unify(functor);
    }

    @Test(expected = UnifyException.class)
    public void unifyListWithFunctor2Param() throws UnifyException {
        ListFunctor list = new ListFunctor(new Variable("A"), new Variable("B"));
        FunctorTerm functor = new FunctorTerm("functor", new Variable("A"), new Variable("B"));

        list.unify(functor);
    }

    @Test(expected = UnifyException.class)
    public void unifyListWithFunctor3Param() throws UnifyException {
        ListFunctor list = new ListFunctor(new Variable("A"), new Variable("B"));
        List<Term> listOfTerms = new LinkedList<Term>();
        listOfTerms.add(new Variable("A"));
        listOfTerms.add(new Variable("B"));
        listOfTerms.add(new Variable("C"));
        FunctorTerm functor = new FunctorTerm("functor", listOfTerms);

        list.unify(functor);
    }

    @Test
    public void unifyListWithListV1() throws UnifyException {
        Variable a = new Variable("A");
        Variable b = new Variable("B");
        Variable c = new Variable("C");
        Variable d = new Variable("D");
        ListFunctor list1 = new ListFunctor(a, b);
        ListFunctor list2 = new ListFunctor(c, d);

        List<Substitution> substitutions = list1.unify(list2);
        assertEquals(substitutions.get(0).getVar(), a);
        assertEquals(substitutions.get(0).getSubstituens(), c);
        assertEquals(substitutions.get(1).getVar(), b);
        assertEquals(substitutions.get(1).getSubstituens(), d);
    }

    @Test
    public void unifyListWithListV2() throws UnifyException {
        Variable a = new Variable("A");
        Variable b = new Variable("B");
        FunctorTerm c = new FunctorTerm("c");
        FunctorTerm d = new FunctorTerm("d");
        ListFunctor list1 = new ListFunctor(a, b);
        ListFunctor list2 = new ListFunctor(c, d);

        List<Substitution> substitutions = list1.unify(list2);
        assertEquals(substitutions.get(0).getVar(), a);
        assertEquals(substitutions.get(0).getSubstituens(), c);
        assertEquals(substitutions.get(1).getVar(), b);
        assertEquals(substitutions.get(1).getSubstituens(), d);
    }

    @Test(expected = UnifyException.class)
    public void unifyListWithListV3() throws UnifyException {
        FunctorTerm a = new FunctorTerm("a");
        FunctorTerm b = new FunctorTerm("b");
        FunctorTerm c = new FunctorTerm("c");
        FunctorTerm d = new FunctorTerm("d");
        ListFunctor list1 = new ListFunctor(a, b);
        ListFunctor list2 = new ListFunctor(c, d);

        list1.unify(list2);
    }

    @Test(expected = UnifyException.class)
    public void unifyListWithEmptyList() throws UnifyException {
        ListFunctor list1 = new ListFunctor(new Variable("A"), new Variable("B"));
        EmptyList list2 = new EmptyList();

        list1.unify(list2);
    }

    @Test(expected = UnifyException.class)
    public void unifyListWithOperatorPlus() throws UnifyException {
        ListFunctor list = new ListFunctor(new Variable("A"), new Variable("B"));
        Plus plus = new Plus(new NumberTerm(1), new NumberTerm(2));

        list.unify(plus);
    }

    @Test(expected = UnifyException.class)
    public void unifyListWithOperatorMinus() throws UnifyException {
        ListFunctor list = new ListFunctor(new Variable("A"), new Variable("B"));
        Minus minus = new Minus(new NumberTerm(1), new NumberTerm(2));

        list.unify(minus);
    }

    @Test(expected = UnifyException.class)
    public void unifyListWithOperatorMult() throws UnifyException {
        ListFunctor list = new ListFunctor(new Variable("A"), new Variable("B"));
        Multiplication mult = new Multiplication(new NumberTerm(1), new NumberTerm(2));

        list.unify(mult);
    }

    // --- EMPTY LIST UNIFICATION ---

    @Test
    public void unifyEmptyListWithSameList() throws UnifyException {
        EmptyList list1 = new EmptyList();
        EmptyList list2 = new EmptyList();

        List<Substitution> substitutions = list1.unify(list2);
        assertEquals(substitutions, new LinkedList<Substitution>());
    }

    @Test
    public void unifyEmptyListWithVar() throws UnifyException {
        EmptyList list = new EmptyList();
        Variable x = new Variable("X");

        List<Substitution> substitutions = list.unify(x);
        assertEquals(substitutions.get(0).getVar(), x);
        assertEquals(substitutions.get(0).getSubstituens(), list);
    }

    @Test(expected = UnifyException.class)
    public void unifyEmptyListWithNum() throws UnifyException {
        EmptyList list = new EmptyList();
        NumberTerm two = new NumberTerm(2);

        list.unify(two);
    }

    @Test(expected = UnifyException.class)
    public void unifyEmptyListWithFunctor0Param() throws UnifyException {
        EmptyList list = new EmptyList();
        FunctorTerm functor = new FunctorTerm("functor");

        list.unify(functor);
    }

    @Test(expected = UnifyException.class)
    public void unifyEmptyListWithFunctor2Param() throws UnifyException {
        EmptyList list = new EmptyList();
        FunctorTerm functor = new FunctorTerm("functor", new Variable("A"), new Variable("B"));

        list.unify(functor);
    }

    @Test(expected = UnifyException.class)
    public void unifyEmptyListWithFunctor3Param() throws UnifyException {
        EmptyList list = new EmptyList();
        List<Term> listOfTerms = new LinkedList<Term>();
        listOfTerms.add(new Variable("A"));
        listOfTerms.add(new Variable("B"));
        listOfTerms.add(new Variable("C"));
        FunctorTerm functor = new FunctorTerm("functor", listOfTerms);

        list.unify(functor);
    }

    @Test(expected = UnifyException.class)
    public void unifyEmptyListWithList() throws UnifyException {
        EmptyList list1 = new EmptyList();
        ListFunctor list2 = new ListFunctor(new Variable("A"), new Variable("B"));

        list1.unify(list2);
    }

    @Test(expected = UnifyException.class)
    public void unifyEmptyListWithOperatorPlus() throws UnifyException {
        EmptyList list = new EmptyList();
        Plus plus = new Plus(new NumberTerm(1), new NumberTerm(2));

        list.unify(plus);
    }

    @Test(expected = UnifyException.class)
    public void unifyEmptyListWithOperatorMinus() throws UnifyException {
        EmptyList list = new EmptyList();
        Minus minus = new Minus(new NumberTerm(1), new NumberTerm(2));

        list.unify(minus);
    }

    @Test(expected = UnifyException.class)
    public void unifyEmptyListWithOperatorMult() throws UnifyException {
        EmptyList list = new EmptyList();
        Multiplication mult = new Multiplication(new NumberTerm(1), new NumberTerm(2));

        list.unify(mult);
    }

    // --- OPERATOR (PLUS) UNIFICATION ---

    @Test
    public void unifyOperatorWithSameNum() throws UnifyException {
        Operator operator1 = new Plus(new NumberTerm(1), new NumberTerm(2));
        Operator operator2 = new Plus(new NumberTerm(1), new NumberTerm(2));

        List<Substitution> substitutions = operator1.unify(operator2);
        assertEquals(substitutions, new LinkedList<Substitution>());
    }

    @Test
    public void unifyOperatorWithVar() throws UnifyException {
        Operator operator = new Plus(new NumberTerm(1), new NumberTerm(2));
        Variable x = new Variable("X");

        List<Substitution> substitutions = operator.unify(x);
        assertEquals(substitutions.get(0).getVar(), x);
        assertEquals(substitutions.get(0).getSubstituens(), operator);
    }

    @Test(expected = UnifyException.class)
    public void unifyOperatorWithNum() throws UnifyException {
        Operator operator = new Plus(new NumberTerm(1), new NumberTerm(2));
        NumberTerm two = new NumberTerm(2);

        operator.unify(two);
    }

    @Test(expected = UnifyException.class)
    public void unifyOperatorWithFunctor0Param() throws UnifyException {
        Operator operator = new Plus(new NumberTerm(1), new NumberTerm(2));
        FunctorTerm functor = new FunctorTerm("functor");

        operator.unify(functor);
    }

    @Test(expected = UnifyException.class)
    public void unifyMinusWithFunctor0Param() throws UnifyException {
        Operator operator = new Minus(new NumberTerm(3), new NumberTerm(2));
        FunctorTerm functor = new FunctorTerm("functor");

        operator.unify(functor);
    }

    @Test(expected = UnifyException.class)
    public void unifyMultiplicationWithFunctor0Param() throws UnifyException {
        Operator operator = new Multiplication(new NumberTerm(3), new NumberTerm(2));
        FunctorTerm functor = new FunctorTerm("functor");

        operator.unify(functor);
    }

    @Test(expected = UnifyException.class)
    public void unifyOperatorWithFunctor2Param() throws UnifyException {
        Operator operator = new Plus(new NumberTerm(1), new NumberTerm(2));
        FunctorTerm functor = new FunctorTerm("functor", new Variable("A"), new Variable("B"));

        operator.unify(functor);
    }

    @Test(expected = UnifyException.class)
    public void unifyOperatorWithFunctor3Param() throws UnifyException {
        Operator operator = new Plus(new NumberTerm(1), new NumberTerm(2));
        List<Term> listOfTerms = new LinkedList<Term>();
        listOfTerms.add(new Variable("A"));
        listOfTerms.add(new Variable("B"));
        listOfTerms.add(new Variable("C"));
        FunctorTerm functor = new FunctorTerm("functor", listOfTerms);

        operator.unify(functor);
    }

    @Test(expected = UnifyException.class)
    public void unifyOperatorWithList() throws UnifyException {
        Operator operator = new Plus(new NumberTerm(1), new NumberTerm(2));
        ListFunctor list = new ListFunctor(new Variable("A"), new Variable("B"));

        operator.unify(list);
    }

    @Test(expected = UnifyException.class)
    public void unifyOperatorWithEmptyList() throws UnifyException {
        Operator operator = new Plus(new NumberTerm(1), new NumberTerm(2));
        EmptyList list = new EmptyList();

        operator.unify(list);
    }

    @Test(expected = UnifyException.class)
    public void unifyOperatorWithOperatorPlus() throws UnifyException {
        Operator operator = new Plus(new NumberTerm(1), new NumberTerm(2));
        Plus plus = new Plus(new NumberTerm(2), new NumberTerm(1));

        operator.unify(plus);
    }

    @Test
    public void unifyOperatorWithOperatorPlusV1() throws UnifyException {
        Variable a = new Variable("A");
        Variable b = new Variable("B");
        Variable c = new Variable("C");
        Variable d = new Variable("D");
        Operator operator = new Plus(a, b);
        Plus plus = new Plus(c, d);

        List<Substitution> substitutions = operator.unify(plus);
        assertEquals(substitutions.get(0).getVar(), a);
        assertEquals(substitutions.get(0).getSubstituens(), c);
        assertEquals(substitutions.get(1).getVar(), b);
        assertEquals(substitutions.get(1).getSubstituens(), d);
    }

    @Test
    public void unifyOperatorWithOperatorPlusV2() throws UnifyException {
        Variable a = new Variable("A");
        Variable b = new Variable("B");
        FunctorTerm c = new FunctorTerm("c");
        FunctorTerm d = new FunctorTerm("d");
        Operator operator = new Plus(a, b);
        Plus plus = new Plus(c, d);

        List<Substitution> substitutions = operator.unify(plus);
        assertEquals(substitutions.get(0).getVar(), a);
        assertEquals(substitutions.get(0).getSubstituens(), c);
        assertEquals(substitutions.get(1).getVar(), b);
        assertEquals(substitutions.get(1).getSubstituens(), d);
    }

    @Test(expected = UnifyException.class)
    public void unifyOperatorWithOperatorPlusV3() throws UnifyException {
        FunctorTerm a = new FunctorTerm("a");
        FunctorTerm b = new FunctorTerm("b");
        FunctorTerm c = new FunctorTerm("c");
        FunctorTerm d = new FunctorTerm("d");
        Operator operator = new Plus(a, b);
        Plus plus = new Plus(c, d);

        operator.unify(plus);
    }

    @Test(expected = UnifyException.class)
    public void unifyOperatorWithOperatorMinus() throws UnifyException {
        Operator operator = new Plus(new NumberTerm(1), new NumberTerm(2));
        Minus minus = new Minus(new NumberTerm(1), new NumberTerm(2));

        operator.unify(minus);
    }

    @Test(expected = UnifyException.class)
    public void unifyOperatorWithOperatorMult() throws UnifyException {
        Operator operator = new Plus(new NumberTerm(1), new NumberTerm(2));
        Multiplication mult = new Multiplication(new NumberTerm(1), new NumberTerm(2));

        operator.unify(mult);
    }

}
