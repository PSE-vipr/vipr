package edu.kit.ipd.pp.prolog.vipr.view.graphic.vistree.substitutiontable;

import java.util.LinkedList;
import java.util.List;

import edu.kit.ipd.pp.prolog.vipr.model.ast.Term;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Substitution;

public class SubstitutionMatrix {

    /**
     * Textfarbe innerhalb der Tabelle
     */
    private static final String TEXT_COLOR = "black";
    /**
     * Hintergrundfarbe von Tabelleneinträgen, die eine Substitution darstellen.
     */
    private static final String SUB_COLOR = "yellow";
    /**
     * Hintergrundfarbe von leeren Tabelleneinträgen
     */
    private static final String SPARSE_COLOR = "?"; // default lightblue color

    /**
     * Liste von Entry, jeder Entry repräsentiert eine Spalte
     */
    private List<Entry> matrix;

    /**
     * Die Anzahl an Einträgen, die die größte Spalte besitzt.
     */
    private int maxDepth = 0;

    /**
     * Konstruktor
     * 
     * @param list
     *            der Tabellenkopf in Tokens
     * @param allSubs
     *            Substitutionen die angeordnet werden sollen erstellt die
     *            matrixförmige Anordnung
     */
    public SubstitutionMatrix(List<Term> list, List<Substitution> allSubs) {
        matrix = createSubMatrix(list, allSubs, 0);
        this.sparse();
    }

    /**
     * Konstruktor für leere Tabelle
     */
    public SubstitutionMatrix() {
        matrix = new LinkedList<>();
    }

    /**
     * 
     * @param list
     *            die momentane Zeile in Tokens
     * @param allSubs
     *            Substitutionen nach denen geordnet wird
     * @param depth
     *            die Tiefe der momentanen Zeile
     * @return Liste von Entry die die Matrix ab/unter der momentanen Zeile
     *         darstellt
     */
    private List<Entry> createSubMatrix(List<Term> list, List<Substitution> allSubs, int depth) {
        List<Entry> gather = new LinkedList<>();
        // aktualisiert gegebenfalls die maxDepth
        if (depth > maxDepth) {
            this.maxDepth = depth;
        }
        // Speichert sich den letzten Eintrag
        Entry lastEntry = new Entry(-1, "");
        // Geht jeden Token der momentanen Zeile durch
        for (Term t : list) {
            String label = t.toString();

            if (label.equals("(") || label.equals("[")) {
                lastEntry = new Entry(-1, label);
                continue; // überspringt Verarbeitung, "(" und "[" sollen nicht zu einem Tabelleneintrag
                          // werden
            }

            if (lastEntry.getLabel().equals("(") || lastEntry.getLabel().equals("[")) {
                // setzt die "(" falls aktueller Term erster Parameter von einem Funktor ist
                // setzt die "[" falls aktueller Term erstes Element von einer Liste ist
                label = lastEntry.getLabel() + " " + label;
                lastEntry = new Entry(-1, "");
            }

            if (label.equals(")") || label.equals("]") || label.equals("|")) {
                // setzt die ")" falls aktueller Term letzter Parameter von einem Funktor ist
                // setzt die "]" falls aktueller Term letztes Element von einer Liste ist
                // setzt die "|" falls aktueller Term Kopf von einer Liste ist
                lastEntry.setLabel(lastEntry.getLabel() + " " + label);
                continue; // überspringt Verarbeitung, ")", "]" und "|" sollen nicht zu einem
                          // Tabelleneintrag
                          // werden
            }
            // erstellt einen Entry für den Token
            Entry entry = new Entry(depth, label);

            // aktualisiert letzten Eintrag
            lastEntry = entry;

            entry.setColor(SUB_COLOR);
            gather.add(entry);
            // prüft ob Token substitutiert wird, wenn ja wird für jede Substitution diese
            // Funktion rekursiv aufgerufen
            for (Substitution sub : allSubs) {
                if (t.equals(sub.getVar())) {
                    entry.setChildren(createSubMatrix(sub.getSubstituens().getTokens(), allSubs, depth + 1));

                }
            }
        }
        // Gibt die Teilmatrix zurück
        return gather;
    }

    /**
     * Füllt die gesamte Matrix mit leeren Tabelleneinträgen auf
     */
    public void sparse() {
        sparseMatrix(matrix);
    }

    /**
     * Füllt eine Teilmatrix mit leeren Tabelleneinträgen auf
     * 
     * @param entries
     *            Liste von Entry die eine Matrix repräsentieren
     */
    private void sparseMatrix(List<Entry> entries) {
        // Durch jede Entry (Spalte) iterieren
        for (Entry e : entries) {
            // Entry zwischenspeichern
            Entry tempEntry = e;

            // Falls die Spalte noch gefüllte Zeilen hat, wird mit rekursiven Aufruf nach
            // unten navigiert
            if (!tempEntry.getChildren().isEmpty()) {
                sparseMatrix(tempEntry.getChildren());

            } else {

                // Erstellt solange Tabelleneinträge unter der Spalte bis die Spalte so groß ist
                // wie die größte Spalte in matrix
                LinkedList<Entry> tempChildren;
                Entry tempChild;
                while (tempEntry.getDepth() < maxDepth) {
                    tempChild = new Entry(tempEntry.getDepth() + 1, "");

                    // Setzt Sparse Farbe
                    tempChild.setColor(SPARSE_COLOR);

                    tempChildren = new LinkedList<>();
                    tempChildren.add(tempChild);
                    tempEntry.setChildren(tempChildren);
                    tempEntry = tempChild;
                }
            }
        }
    }

    /**
     * Erstellt aus der Matrix einen HTML-String der die Substitution-Tabelle
     * darstellt
     * 
     * @param stateColor
     *            die Farbe in dem der tabellenkopf visualisiert wird
     * @return HTML-String der die Substitution-Tabelle darstellt
     */
    public String printSubMatrix(String stateColor) {
        // Tabellenanfang und erster Zeilanfang
        String html = "";
        List<Entry> currentDepth = new LinkedList<>();
        // Einfügen des Tabellenkopfes
        StringBuilder htmlBuild = new StringBuilder("<table border='1'><tr>");
        for (Entry e : matrix) {
            htmlBuild.append("<th color='" + TEXT_COLOR + "' bgcolor='" + stateColor + "' scope='col' colspan='"
                    + e.getSplitInto() + "'>" + e.getLabel() + "</th>");
            currentDepth.add(e);
        }
        // Ende der ersten Zeile
        htmlBuild.append("</tr>");
        // Einfügen des Tabellen Inhalts
        html = printRecursion(currentDepth, htmlBuild.toString());

        // Tabellenende
        html += "</table>";
        return html;
    }

    /**
     * Erstellt mit Breitensuche den HTML-String. Jeder rekursive Aufruf fügt eine
     * Zeile hinzu.
     * 
     * @param currentDepthEntry
     * @param html
     *            bisherige HTML-String
     * @return bisherige HTML-String um eine Zeile erweitert
     */
    private String printRecursion(List<Entry> currentDepthEntry, String html) {
        String complHtml = "";
        // Neuer Zeilenanfang
        StringBuilder newHtml = new StringBuilder("<tr>");
        List<Entry> currentDepth = new LinkedList<>();
        // Alle Einträge in der neuen Zeile
        for (Entry e : currentDepthEntry) {
            for (Entry ec : e.getChildren()) {

                newHtml.append("<th color='" + TEXT_COLOR + "'  bgcolor='" + ec.getColor() + "' scope='col' colspan='"
                        + ec.getSplitInto() + "'>" + ec.getLabel() + "</th>");
                currentDepth.add(ec);
            }
        }
        // Zeilenende
        newHtml.append("</tr>");
        // String komplexität reduzieren durch unötige String zeilen
        if (newHtml.toString().equals("<tr></tr>")) {
            newHtml = new StringBuilder("");
        }
        complHtml = html + newHtml.toString();

        // Falls nächste Zeile existiert diese hinzufügen
        if (!currentDepth.isEmpty()) {
            return printRecursion(currentDepth, complHtml);
        }
        return complHtml;
    }

}
