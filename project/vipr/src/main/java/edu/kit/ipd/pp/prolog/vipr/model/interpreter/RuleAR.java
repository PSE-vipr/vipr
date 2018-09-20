package edu.kit.ipd.pp.prolog.vipr.model.interpreter;

import edu.kit.ipd.pp.prolog.vipr.model.ast.FunctorTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Program;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Rule;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.ParentARVisNode;

/**
 * Ein activation record für eine Regel.
 * 
 */
public class RuleAR extends ParentAR {

    /**
     * Die Regel.
     */
    private Rule rule; // mit gesetzter id, nur der FunctorTerm ist umnummeriert, keine durchgeführten
                       // Substitutionen

    /**
     * Erzeugt einen neuen RuleAR mit der gegebenen Regel, mit der Nummer, die die
     * Regel haben soll und mit dem gegebenen vorher besuchten Knoten.
     * 
     * @param rule
     *            Die Regel.
     * @param program
     *            Das Programm.
     * @param lastNode
     *            Der vorher besuchte Knoten.
     * @param id
     *            Die Nummer, die die Regel haben soll.
     */
    public RuleAR(Rule rule, Program program, ActivationRecord lastNode, int id) {
        super(rule.getGoals(), program, lastNode);
        this.rule = rule.deepCopy();
        this.rule.setId(id);
        getFunctorTerm().renumber(getCounter());
    }

    /**
     * Gibt den FunctorTerm der Regel dieses RuleARs zurück.
     * 
     * @return Der FunctorTerm der Regel.
     */
    public FunctorTerm getFunctorTerm() {
        return rule.getFunctorTerm();
    }

    @Override
    public int[] getPos() {
        return rule.getPos();
    }

    @Override
    public ParentARVisNode createVisNode(boolean trans) {
        ParentARVisNode parent = super.createVisNode(trans);
        parent.setRule(this.rule);
        return parent;
    }

}
