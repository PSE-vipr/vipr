package edu.kit.ipd.pp.prolog.vipr.model.ast;

import java.util.LinkedList;
import java.util.List;

/**
 * Repräsentiert eine Liste.
 */
public class ListFunctor extends FunctorTerm {

    /**
     * Erzeugt eine Liste mit Termen.
     * 
     * @param head
     *            Der Kopf der Liste.
     * @param tail
     *            Der angehängte Teil der Liste.
     */
    public ListFunctor(Term head, Term tail) {
        super("[|]", head, tail);
    }

    @Override
    public boolean equals(Object o) {
        if (null == o) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!o.getClass().equals(getClass())) {
            return false;
        }
        ListFunctor list = (ListFunctor) o;
        return getName().equals(list.getName()) && getParameters().equals(list.getParameters());
    }

    @Override
    public ListFunctor deepCopy() {
        return new ListFunctor(getParameters().get(0).deepCopy(), getParameters().get(1).deepCopy());
    }

    @Override
    public String toString() {
        return "[" + getParameters().get(0) + getParameters().get(1).getTailString() + "]";
    }

    @Override
    public String getTailString() {
        return ", " + getParameters().get(0) + getParameters().get(1).getTailString();
    }

    @Override
    public List<Term> getTokens() {
        List<Term> tokens = new LinkedList<>();
        tokens.add(new FunctorTerm("["));
        Term head = getParameters().get(0);
        Term tail = getParameters().get(1);
        tokens.addAll(head.getTokens());
        tokens.addAll(tail.getTailTokens());
        tokens.add(new FunctorTerm("]"));
        return tokens;
    }

    @Override
    public List<Term> getTailTokens() {
        List<Term> tokens = new LinkedList<>();
        Term head = getParameters().get(0);
        Term tail = getParameters().get(1);
        tokens.addAll(head.getTokens());
        tokens.addAll(tail.getTailTokens());
        return tokens;
    }

}
