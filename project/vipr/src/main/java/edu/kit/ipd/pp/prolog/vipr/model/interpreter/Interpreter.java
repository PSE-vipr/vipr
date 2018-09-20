package edu.kit.ipd.pp.prolog.vipr.model.interpreter;

import java.util.LinkedList;
import java.util.List;

import edu.kit.ipd.pp.prolog.vipr.model.ast.CalculateException;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Goal;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NoRuleException;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Program;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Rule;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Term;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Variable;

/**
 * Der Interpreter. Beinhaltet die Logik, um eine Anfrage schrittweise
 * abzuarbeiten.
 *
 */
public class Interpreter {

    /**
     * Das Programm.
     */
    private Program program;

    /**
     * Die Wurzel des Berechnungsbaums.
     */
    private ParentAR root;

    /**
     * Die Variablen der Anfrage.
     */
    private List<Variable> inquiryVariables;

    /**
     * Die Anfangsposition von der Regel, die gehighlighted werden soll.
     */
    private int startPosHighlight;

    /**
     * Die Endposition von der Regel, die gehighlighted werden soll.
     */
    private int endPosHighlight;

    /**
     * true, wenn eine Anfrage existiert, false sonst
     */
    private boolean inquiryExists;

    /**
     * true, wenn output gefunden wurde, false sonst
     */
    private boolean foundOutput;

    /**
     * true, falls die Anfrage fehlgeschlagen ist, false sonst.
     */
    private boolean inquiryFailed;

    /**
     * Erzeugt einen Interpreter.
     */
    public Interpreter() {
        setNewProgram(null);
    }

    /**
     * Startet eine neue Anfrage mit den gegebenen Zielen.
     * 
     * @param listOfGoals
     *            Die Ziele der Anfrage.
     */
    public void newInquiry(List<Goal> listOfGoals) {
        // zurücksetzen
        setNewProgram(this.program);

        // neue Anfrage initialisieren
        List<Variable> vars = new LinkedList<>();
        for (Goal goal : listOfGoals) {
            vars.addAll(goal.getVariables());
        }
        for (Variable var : vars) {
            if (!inquiryVariables.contains(var)) {
                inquiryVariables.add(var);
            }
        }
        root = new ParentAR(listOfGoals, program);
        root.setLastVisitedAR(root.nextUnfulfilledGoalAR());
        inquiryExists = true;
    }

    /**
     * Führt den nächsten Berechnungsschritt aus.
     * 
     * @throws CalculateException
     *             Wenn ein Term nicht berechnet werden kann.
     * @throws NoRuleException
     *             Wenn zu einem Funktorziel keine passende Regel gefunden werden
     *             kann.
     */
    public void nextStep() throws CalculateException, NoRuleException {
        if (root.getState() == State.FULFILLED) {
            foundOutput = false;
            GoalAR lastVisitedGoalAR = root.getLastVisitedAR().getLastGoalAR();
            lastVisitedGoalAR.setState(State.FAILED);
            if (lastVisitedGoalAR.canFulfillDifferently()) {
                ((FunctorGoalAR) lastVisitedGoalAR).setShowPending();
            }
            lastVisitedGoalAR.updateStates();
            lastVisitedGoalAR.deleteRuleAR();
            setPos(lastVisitedGoalAR);
            return;
        }
        GoalAR goalAR = nextGoalAR(root);
        RuleAR ruleAR = goalAR.getRuleAR();
        if (null != ruleAR) {
            goalAR.setState(State.FULFILLED);
            if (goalAR.getIsLastGoal()) {
                goalAR.getParentAR().setState(State.FULFILLED);
            }
            setPos(goalAR);
        } else {
            switch (goalAR.getState()) {
            case PENDING:
                root.setLastVisitedAR(goalAR.nextFulfillStep());
                setPos(root.getLastVisitedAR());
                break;
            case UNVISITED:
                goalAR.setLastNode(root.getLastVisitedAR());
                goalAR.substitute();
                root.setLastVisitedAR(goalAR.nextFulfillStep());
                setPos(root.getLastVisitedAR());
                break;
            case FAILED:
                root.setLastVisitedAR(goalAR.goBack());
                setPos(root.getLastVisitedAR());
                break;
            case CUTFAILED:
                FunctorGoalAR functorGoalAR = (FunctorGoalAR) goalAR.getParentAR().getLastNode();
                if (null == functorGoalAR) {
                    root.setState(State.FAILED);
                    root.setGoalsFailed();
                    root.setLastVisitedAR(root);
                    setPos(root);
                    break;
                }
                functorGoalAR.cutfail();
                root.setLastVisitedAR(functorGoalAR);
                setPos(functorGoalAR);
                break;
            default:
            }
        }
        switch (root.getState()) {
        case FULFILLED:
            foundOutput = true;
            break;
        case FAILED:
            inquiryFailed = true;
            foundOutput = true;
            break;
        default:
        }
    }

    /**
     * Gibt den Output zurück.
     * 
     * @return Der Output.
     */
    public String getOutput() {
        if (inquiryFailed) {
            return "no.";
        }
        List<Substitution> substitutions = new LinkedList<>();
        List<Term> terms = new LinkedList<>();
        terms.addAll(inquiryVariables);
        for (Substitution sub : root.getLastVisitedAR().getLastGoalAR().allPreviousSubstitutions()) {
            List<Term> newTerms = new LinkedList<>();
            for (Term term : terms) {
                newTerms.add(term.substitute(sub));
            }
            terms = newTerms;
        }
        for (int i = 0; i < inquiryVariables.size(); i++) {
            substitutions.add(new Substitution(inquiryVariables.get(i), terms.get(i)));
        }
        if (substitutions.isEmpty()) {
            return "yes";
        }
        StringBuilder output = null;
        for (Substitution substitution : substitutions) {
            if (null == output) {
                output = new StringBuilder(substitution.toString());
            } else {
                output.append(", " + substitution);
            }
        }
        return output.toString();
    }

    /**
     * Gibt die Wurzel des Berechnungsbaums zurück.
     * 
     * @return Die Wurzel des Berechnungsbaums.
     */
    public ParentAR getRoot() {
        return root;
    }

    /**
     * Gibt die Anfangs- und die Endposition von der Regel, die gehighlighted werden
     * soll, zurück.
     * 
     * @return Die Anfangs- und die Endposition von der Regel, die gehighlighted
     *         werden soll.
     */
    public int[] getPosHighlight() {
        return new int[] {startPosHighlight, endPosHighlight};
    }

    /**
     * Gibt zurück, ob die Anfrage fehlgeschlagen ist.
     * 
     * @return true, wenn die Anfrage fehlgeschlagen ist, false sonst
     */
    public boolean getInquiryFailed() {
        return inquiryFailed;
    }

    /**
     * Setzt die Positionen zum Highlighten.
     * 
     * @param activationRecord
     *            Legt die Regel fest, die gehighlighted werden soll.
     */
    private void setPos(ActivationRecord activationRecord) {
        ActivationRecord aRecord = activationRecord;
        RuleAR visRuleAR = aRecord.getVisRuleAR();
        if (null != visRuleAR && visRuleAR.getState() == State.FAILED) {
            aRecord = visRuleAR;
        }
        int[] pos = aRecord.getParentAR().getPos();
        startPosHighlight = pos[0];
        endPosHighlight = pos[1];
    }

    /**
     * Gibt ausgehend vom gegebenen parent das nächste GoalAR zurück, das nicht
     * FULFILLED ist.
     * 
     * @param parent
     *            Der ParentAR von dem aus das nächste GoalAR, das nicht FULFILLED
     *            ist, gesucht wird.
     * @return Das nächste GoalAR, das nicht FULFILLED ist.
     */
    private GoalAR nextGoalAR(ParentAR parent) {
        GoalAR goalAR = parent.nextUnfulfilledGoalAR();
        ParentAR newParent = goalAR.getRuleAR();
        if (null == newParent || newParent.getState() == State.FULFILLED) {
            return goalAR;
        }
        return nextGoalAR(newParent);
    }

    /**
     * Setzt den Interpreter auf das neue Programm.
     * 
     * @param program
     *            Das neue Programm.
     */
    public void setNewProgram(Program program) {
        if (program == null) {
            List<Rule> emptyList = new LinkedList<>();
            Program emptyProgram = new Program(emptyList);
            this.program = emptyProgram;
        } else {

            // Interpreter neu Aufsetzen.
            this.program = program;

        }
        this.inquiryVariables = new LinkedList<>();

        // den Rest nullen / invalide machen
        this.root = null;

        this.startPosHighlight = 0;

        this.endPosHighlight = 0;

        this.inquiryFailed = false;

        this.inquiryExists = false;

        this.foundOutput = false;

    }

    /**
     * Gibt zurück, ob eine Anfrage existiert.
     * 
     * @return true, wenn eine Anfrage existiert, false sonst
     */
    public boolean getInquiryExists() {
        return this.inquiryExists;
    }

    /**
     * Gibt das Programm zurück.
     * 
     * @return Das Programm.
     */
    public Program getProgram() {
        return this.program;
    }

    /**
     * Gibt zurück, ob Output gefunden wurde.
     * 
     * @return true, wenn Output gefunden wurde, false sonst
     */
    public boolean getFoundOutput() {
        return this.foundOutput;
    }

}
