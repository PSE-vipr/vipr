package edu.kit.ipd.pp.prolog.vipr.model.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import edu.kit.ipd.pp.prolog.vipr.model.ast.ArithOrUniGoal;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Cut;
import edu.kit.ipd.pp.prolog.vipr.model.ast.EmptyList;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Equal;
import edu.kit.ipd.pp.prolog.vipr.model.ast.FunctorGoal;
import edu.kit.ipd.pp.prolog.vipr.model.ast.FunctorTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Goal;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Greater;
import edu.kit.ipd.pp.prolog.vipr.model.ast.GreaterOrEqual;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Is;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Less;
import edu.kit.ipd.pp.prolog.vipr.model.ast.LessOrEqual;
import edu.kit.ipd.pp.prolog.vipr.model.ast.ListFunctor;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Minus;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Multiplication;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NotEqual;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NumberTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Plus;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Program;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Rule;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Term;
import edu.kit.ipd.pp.prolog.vipr.model.ast.UnificationGoal;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Variable;
import edu.kit.ipd.pp.prolog.vipr.model.parser.Token.TokenType;

/**
 * Parsed ein String-Programm und übergibt die Daten für den AST.
 */
public class PrologParser {

    private static final String COLUMN = "; column: "; // Konstante
    private static final String ERR_LINE = "Error in line: "; // Konstante
    private static final String LINE_SEP = System.getProperty("line.separator"); // Konstante

    /**
     * Der Lexer verarbeitet das Programm in die Tokens.
     */
    private final PrologLexer lexer;

    /**
     * Das im Moment betrachtete Token.
     */
    private Token token;

    /**
     * Der Konstruktor mit dem String-Programm als Parameter.
     * 
     * @param program
     *            Das Programm als String, das geparsed werden soll.
     * @throws ParseException
     *             Wenn das Programm nicht syntaktisch gültig ist.
     */
    public PrologParser(String program) throws ParseException {
        this.lexer = new PrologLexer(program);
        nextToken();
    }

    private void nextToken() throws ParseException {
        token = lexer.nextToken();
    }

    private void expect(TokenType type) throws ParseException {
        if (token.getType() != type) {
            throw new ParseException(
                    ERR_LINE + token.getLine() + LINE_SEP + "Expected '" + type + "', got '" + token + "'");
        }
        nextToken();
    }

    /**
     * Parsed den String, der im Konstruktor übergeben wurde, als Programm.
     * 
     * @return Das Program-Objekt.
     * @throws ParseException
     *             Wenn das Programm nicht syntaktisch gültig ist.
     */
    public Program parse() throws ParseException {
        return parseProgram();
    }

    private Program parseProgram() throws ParseException {
        List<Rule> rules = new LinkedList<>();
        while (token.getType() != TokenType.EOF) {
            int startPos = token.getPos();
            Rule rule = parseRule();
            int endPos = token.getPos();
            rule.setPos(startPos, endPos);
            rules.add(rule);
        }
        return new Program(rules);
    }

    private Rule parseRule() throws ParseException {
        FunctorTerm lhs = parseFunctor();
        if (token.getType() == TokenType.DOT) {
            nextToken();
            return new Rule(lhs);
        }
        expect(TokenType.COLON_MINUS);
        List<Goal> goals = parseGoalList();
        return new Rule(lhs, goals);
    }

    /**
     * Parsed eine nicht leere Goal-Liste.
     * 
     * @return Die Goal-Liste.
     * @throws ParseException
     *             Wenn ein Parsefehler auftritt.
     */
    public List<Goal> parseGoalList() throws ParseException {
        List<Goal> goals = new LinkedList<>();
        goals.add(parseGoal());
        while (token.getType() != TokenType.DOT) {
            expect(TokenType.COMMA);
            goals.add(parseGoal());
        }
        nextToken();
        return goals;
    }

    private Goal parseGoal() throws ParseException {
        switch (token.getType()) {
        case IDENTIFIER:
            FunctorTerm f = parseFunctor();
            if (token.getType() == TokenType.COMMA || token.getType() == TokenType.DOT) {
                return new FunctorGoal(f);
            } else {
                return parseGoalRest(f);
            }
        case VARIABLE:
        case NUMBER:
        case LP:
        case LB:
            Term t = parseTerm();
            return parseGoalRest(t);
        case EXCLAMATION:
            nextToken();
            return new Cut();
        default:
            throw new ParseException(ERR_LINE + token.getLine() + COLUMN + token.getCol() + LINE_SEP
                    + "Expected a term, got '" + token + "'");
        }
    }

    private FunctorTerm parseFunctor() throws ParseException {
        switch (token.getType()) {
        case IDENTIFIER:
            String name = token.getText();
            nextToken();
            if (token.getType() != TokenType.LP) {
                return new FunctorTerm(name);
            }
            expect(TokenType.LP);
            List<Term> terms = new LinkedList<>();
            terms.add(parseTerm());
            while (token.getType() != TokenType.RP) {
                expect(TokenType.COMMA);
                terms.add(parseTerm());
            }
            nextToken();
            return new FunctorTerm(name, terms);
        case LB:
            return parseList();
        default:
            throw new ParseException(ERR_LINE + token.getLine() + COLUMN + token.getCol() + LINE_SEP
                    + "Expected functor, got '" + token + "'");
        }
    }

    private ArithOrUniGoal parseGoalRest(Term t) throws ParseException {
        Term lhs = parseTerm(Optional.of(t));
        Term rhs;
        switch (token.getType()) {
        case IS:
            nextToken();
            rhs = parseTerm();
            return new Is(lhs, rhs);
        case EQ:
            nextToken();
            rhs = parseTerm();
            return new UnificationGoal(lhs, rhs);
        case LESS:
            nextToken();
            rhs = parseTerm();
            return new Less(lhs, rhs);
        case EQ_LESS:
            nextToken();
            rhs = parseTerm();
            return new LessOrEqual(lhs, rhs);
        case GREATER:
            nextToken();
            rhs = parseTerm();
            return new Greater(lhs, rhs);
        case GREATER_EQ:
            nextToken();
            rhs = parseTerm();
            return new GreaterOrEqual(lhs, rhs);
        case EQ_COLON_EQ:
            nextToken();
            rhs = parseTerm();
            return new Equal(lhs, rhs);
        case EQ_BS_EQ:
            nextToken();
            rhs = parseTerm();
            return new NotEqual(lhs, rhs);
        default:
            throw new ParseException(ERR_LINE + token.getLine() + COLUMN + token.getCol() + LINE_SEP
                    + "Expected 'is' or '=' or arithmetic comparison, got '" + token + "'");
        }
    }

    private Term parseTerm() throws ParseException {
        return parseTerm(Optional.empty());
    }

    private Term parseTerm(Optional<Term> maybeTerm) throws ParseException {
        Term t = parseSummand(maybeTerm);
        while (token.getType() == TokenType.PLUS || token.getType() == TokenType.MINUS) {
            if (token.getType() == TokenType.PLUS) {
                nextToken();
                Term t2 = parseSummand(Optional.empty());
                t = new Plus(t, t2);
            } else {
                nextToken();
                Term t2 = parseSummand(Optional.empty());
                t = new Minus(t, t2);
            }
        }
        return t;
    }

    private Term parseSummand(Optional<Term> maybeTerm) throws ParseException {
        Term t = parseFactor(maybeTerm);
        while (token.getType() == TokenType.STAR) {
            nextToken();
            Term t2 = parseFactor(Optional.empty());
            t = new Multiplication(t, t2);
        }
        return t;
    }

    private Term parseFactor(Optional<Term> maybeTerm) throws ParseException {
        if (maybeTerm.isPresent()) {
            return maybeTerm.get();
        }
        switch (token.getType()) {
        case IDENTIFIER:
        case LB:
            return parseFunctor();
        case NUMBER:
            return parseNumber();
        case VARIABLE:
            String name = token.getText();
            nextToken();
            return new Variable(name);
        case LP:
            nextToken();
            Term t = parseTerm();
            expect(TokenType.RP);
            return t;
        default:
            throw new ParseException(ERR_LINE + token.getLine() + COLUMN + token.getCol() + LINE_SEP
                    + "Expected a term, got '" + token + "'");
        }
    }

    private NumberTerm parseNumber() throws ParseException {
        int n = Integer.parseInt(token.getText());
        nextToken();
        return new NumberTerm(n);
    }

    private FunctorTerm parseList() throws ParseException {
        expect(TokenType.LB);
        switch (token.getType()) {
        case RB:
            nextToken();
            return new EmptyList();
        case IDENTIFIER:
        case NUMBER:
        case VARIABLE:
        case LB:
            Term t = parseTerm();
            return parseListRest(t);
        default:
            throw new ParseException(ERR_LINE + token.getLine() + COLUMN + token.getCol() + LINE_SEP
                    + "Expected a list, got '" + token + "'");
        }
    }

    private ListFunctor parseListRest(Term t) throws ParseException {
        Term rhs;
        switch (token.getType()) {
        case RB:
            nextToken();
            rhs = new EmptyList();
            break;
        case COMMA:
            nextToken();
            Term t2 = parseTerm();
            rhs = parseListRest(t2);
            break;
        case BAR:
            nextToken();
            rhs = parseTerm();
            expect(TokenType.RB);
            break;
        default:
            throw new ParseException(ERR_LINE + token.getLine() + COLUMN + token.getCol() + LINE_SEP
                    + "Expected a list remainder, got '" + token + "'");
        }
        return new ListFunctor(t, rhs);
    }

}
