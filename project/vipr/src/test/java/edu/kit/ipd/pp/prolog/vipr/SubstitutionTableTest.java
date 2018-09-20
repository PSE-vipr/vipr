package edu.kit.ipd.pp.prolog.vipr;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.model.ast.EmptyList;
import edu.kit.ipd.pp.prolog.vipr.model.ast.FunctorTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.ListFunctor;
import edu.kit.ipd.pp.prolog.vipr.model.ast.NumberTerm;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Term;
import edu.kit.ipd.pp.prolog.vipr.model.ast.Variable;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Substitution;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.substitutiontable.Entry;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.substitutiontable.SubstitutionMatrix;

public class SubstitutionTableTest {
    // Testet die Korrektheit der Erstellung der SubstitutionsMatrix indem der
    // HTML-String auf die Label geprüft wird
    @Test
    public void constructTest() {
        // Speichert output String
        String HTML_TABLE;
        // Variabenummer; Sollte nicht 0 sein
        final int varNum = 2;

        // erwartete HTML-Tabelle nach Zeilen aufgeteilt
        final String tableLine = "<table border='1'>";
        // ohne paramter von HTML-Elementen um test allgemein gültig zu konstruieren
        final String headLine = "<tr><th>f</th><th>( X_2</th><th>[ 2</th><th>7 ] )</th></tr>";
        final String substitutionLine = "<tr><th></th><th>adam</th><th></th><th></th></tr>";

        final String endLine = "</table>";

        // erstellt Variabel die substituiert wird
        Variable v = new Variable("X");
        // setzt die Variabelnummer auf varNum
        v.renumber(varNum);

        // erstellt Liste mit NumberTerm
        NumberTerm num = new NumberTerm(7);
        NumberTerm num2 = new NumberTerm(2);
        ListFunctor lf = new ListFunctor(num, new EmptyList());
        ListFunctor lf2 = new ListFunctor(num2, lf);
        // erstellt FunctoTerm f(X,7)
        LinkedList<Term> parameter = new LinkedList<>();
        parameter.add(v);
        parameter.add(lf2);
        FunctorTerm ft = new FunctorTerm("f", parameter);

        // Erstellt FunctorTerm der variable ersetzten soll: X->adam
        FunctorTerm fact = new FunctorTerm("adam");
        Substitution s = new Substitution(v, fact);

        // Erstellt Term-Liste für SubstitutionMatrix
        LinkedList<Term> termList = new LinkedList<>();
        termList.addAll(ft.getTokens());

        // Erstellt Substitutions-Liste für SubstitutionMatrix
        LinkedList<Substitution> subList = new LinkedList<>();
        subList.add(s);

        // Erstellt SubstitutionMatrix
        SubstitutionMatrix sm = new SubstitutionMatrix(termList, subList);
        //// Erstellt HTML-String; state Farbe hier egal
        HTML_TABLE = sm.printSubMatrix("green");

        assertTrue(HTML_TABLE.substring(0, tableLine.length()).equals(tableLine));
        // Überprüftenteil abschneiden
        HTML_TABLE = HTML_TABLE.substring(tableLine.length(), HTML_TABLE.length());

        // Löschen die parameter in <th>
        int cutC = HTML_TABLE.indexOf("<th ") + 3;
        HTML_TABLE = HTML_TABLE.substring(0, cutC) + HTML_TABLE.substring(HTML_TABLE.indexOf(">", cutC));
        cutC = HTML_TABLE.indexOf("<th ") + 3;
        HTML_TABLE = HTML_TABLE.substring(0, cutC) + HTML_TABLE.substring(HTML_TABLE.indexOf(">", cutC));
        cutC = HTML_TABLE.indexOf("<th ") + 3;
        HTML_TABLE = HTML_TABLE.substring(0, cutC) + HTML_TABLE.substring(HTML_TABLE.indexOf(">", cutC));
        cutC = HTML_TABLE.indexOf("<th ") + 3;
        HTML_TABLE = HTML_TABLE.substring(0, cutC) + HTML_TABLE.substring(HTML_TABLE.indexOf(">", cutC));
        // vergleicht erhaltene Kopfzeile mit erwarteter Kopfzeile
        assertTrue(HTML_TABLE.substring(0, headLine.length()).equals(headLine));

        // Kopfezeile abschneiden
        HTML_TABLE = HTML_TABLE.substring(headLine.length(), HTML_TABLE.length());

        // Löschen die parameter in <th>
        cutC = HTML_TABLE.indexOf("<th ") + 3;
        HTML_TABLE = HTML_TABLE.substring(0, cutC) + HTML_TABLE.substring(HTML_TABLE.indexOf(">", cutC));
        cutC = HTML_TABLE.indexOf("<th ") + 3;
        HTML_TABLE = HTML_TABLE.substring(0, cutC) + HTML_TABLE.substring(HTML_TABLE.indexOf(">", cutC));
        cutC = HTML_TABLE.indexOf("<th ") + 3;
        HTML_TABLE = HTML_TABLE.substring(0, cutC) + HTML_TABLE.substring(HTML_TABLE.indexOf(">", cutC));
        cutC = HTML_TABLE.indexOf("<th ") + 3;
        HTML_TABLE = HTML_TABLE.substring(0, cutC) + HTML_TABLE.substring(HTML_TABLE.indexOf(">", cutC));

        // vergleicht erhaltene Substitutionszeile mit erwarteter Substitonszeile
        assertTrue(HTML_TABLE.substring(0, substitutionLine.length()).equals(substitutionLine));

        // Substitutionszeile abschneiden
        HTML_TABLE = HTML_TABLE.substring(substitutionLine.length(), HTML_TABLE.length());

        // vergleicht erhaltene Endzeile mit erwarteter Endzeile
        assertTrue(HTML_TABLE.substring(0, endLine.length()).equals(endLine));

    }

    @Test
    public void splitIntoTest() {
        Entry parent = new Entry(0, "p");
        LinkedList<Entry> children = new LinkedList<>();

        children.add(new Entry(1, "child 1"));

        Entry middleChild = new Entry(1, "child 2");
        LinkedList<Entry> grandChildren = new LinkedList<>();
        grandChildren.add(new Entry(2, "grandchildren 1"));
        grandChildren.add(new Entry(2, "grandchildren 2"));
        middleChild.setChildren(grandChildren);
        children.add(middleChild);
        children.add(new Entry(1, "child 3"));

        parent.setChildren(children);

        assertTrue(middleChild.getSplitInto() == 2);
        assertTrue(parent.getSplitInto() == 4);

    }
}
