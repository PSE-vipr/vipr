package edu.kit.ipd.pp.prolog.vipr.model.ast;

import java.util.LinkedList;
import java.util.List;

/**
 * Repräsentiert eine leere Liste.
 */
public class EmptyList extends FunctorTerm {

    /**
     * Erzeugt eine leere Liste.
     */
    public EmptyList() {
        super("[]");
    }

    @Override
    public EmptyList deepCopy() {
        return new EmptyList();
    }

    @Override
    public String toString() {
        return "[]";
    }

    @Override
    public String getTailString() {
        return "";
    }

    @Override
    public List<Term> getTokens() {
        List<Term> tokens = new LinkedList<>();
        tokens.add(new FunctorTerm("["));
        tokens.add(new FunctorTerm("]"));
        return tokens;
    }

    @Override
    public List<Term> getTailTokens() {
        return new LinkedList<>();
    }

}
