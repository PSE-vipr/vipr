package edu.kit.ipd.pp.prolog.vipr.model.ast;

import java.util.List;

import edu.kit.ipd.pp.prolog.vipr.model.interpreter.FunctorGoalAR;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Substitution;

/**
 * Repräsentiert ein Funktorziel.
 */
public class FunctorGoal extends Goal {

    /**
     * Der Funktorterm des Funktorziels.
     */
    private FunctorTerm functorTerm;

    /**
     * Erzeugt das Funktorziel mit einem Funktorterm.
     * 
     * @param functorTerm
     *            Der Funktorterm des Ziels.
     */
    public FunctorGoal(FunctorTerm functorTerm) {
        this.functorTerm = functorTerm;
    }

    @Override
    public FunctorGoal deepCopy() {
        return new FunctorGoal(functorTerm.deepCopy());
    }

    /**
     * Unifiziert den Functorterm dieses Ziels mit dem gegebenen Term rhs.
     * 
     * @param rhs
     *            Der Term, mit dem unifiziert werden soll.
     * @return Eine Liste von Substitutionen, die den Unifier darstellt.
     * @throws UnifyException
     *             Wenn die Unifikation fehlschlägt.
     */
    public List<Substitution> unify(Term rhs) throws UnifyException {
        return functorTerm.unify(rhs);
    }

    @Override
    public FunctorGoalAR createAR(Program program) {
        String name = functorTerm.getName();
        int arity = functorTerm.getArity();
        List<Rule> rules = program.searchRules(name, arity);
        return new FunctorGoalAR(this, rules, program);
    }

    @Override
    public void renumber(int counter) {
        functorTerm.renumber(counter);
    }

    @Override
    public List<Variable> getVariables() {
        return functorTerm.getVariables();
    }

    @Override
    public void substitute(Substitution substitution) {
        functorTerm = functorTerm.substitute(substitution);
    }

    @Override
    public List<Term> getTokens() {
        return this.functorTerm.getTokens();

    }

}
