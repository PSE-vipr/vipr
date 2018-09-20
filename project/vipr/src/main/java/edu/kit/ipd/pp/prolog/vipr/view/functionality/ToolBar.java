package edu.kit.ipd.pp.prolog.vipr.view.functionality;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JMenuBar;

import edu.kit.ipd.pp.prolog.vipr.view.Language;

/**
 * Diese Klasse enthält die Toolbar des Programms. Sie besteht aus dem Menü
 * sowie den Knöpfen zum Steuern der Berechnung von Anfragen.
 *
 */
@SuppressWarnings("serial")
public class ToolBar extends JMenuBar {

    /**
     * Knopf zum Zurückspringen zum letzten Abarbeitungszustand.
     */
    private JButton back;

    /**
     * Knopf zum Berechnen der nächsten Lösung der aktuellen Anfrage.
     */
    private JButton next;

    /**
     * Knopf zum Berechnen des nächsten Abarbeitungsschrittes der aktuellen Anfrage.
     */
    private JButton step;

    /**
     * Knopf zum Berechnen aller Lösungen der aktuellen Anfrage.
     */
    private JButton all;

    /**
     * Knopf zum Anhalten der aktuellen Berechnung.
     */
    private JButton pause;

    /**
     * Knopf zum Parsen des im Code-Editor stehenden Programmcodes.
     */
    private JButton parse;

    /**
     * Menü des Programms.
     */
    private Menu myMenu;

    /**
     * Checkbox zum Auswählen, ob transitive Substitutionen angezeigt werden oder
     * nicht.
     */
    private JCheckBox transSubstitutions;

    /**
     * Konstruktor für die Toolbar. Erstellt die Toolbar, initialisiert die Knöpfe
     * und erstellt ein Menü.
     */
    public ToolBar() {
        this.setName("toolbar");
        GridLayout g = new GridLayout();
        setLayout(g);
        JMenuBar left = new JMenuBar();
        JMenuBar right = new JMenuBar();
        right.setLayout(g);
        this.back = new JButton("\u25c0");
        this.back.setName("back");
        this.pause = new JButton("\u275a\u275a");
        this.pause.setName("pause");
        this.step = new JButton("\u25b6");
        this.step.setName("step");
        this.next = new JButton("\u25b6\u275a");
        this.next.setName("next");
        this.all = new JButton("\u25b6\u25b6\u275a");
        this.all.setName("all");
        // zur abkürzung
        Language language = Language.getInstance();
        this.parse = new JButton();
        this.parse.setName("parse");
        this.parse.setPreferredSize(new Dimension(120, 10));
        this.myMenu = new Menu(language.getString("Menu.menu"));
        this.myMenu.setPreferredSize(new Dimension(120, 10));
        this.transSubstitutions = new JCheckBox();
        this.transSubstitutions.setName("subs");

        updateLabels();

        add(left);
        add(right);

        left.add(this.myMenu);
        left.add(this.parse);
        left.add(Box.createGlue());
        left.add(this.transSubstitutions);

        right.add(this.back);
        right.add(this.pause);
        right.add(this.step);
        right.add(this.next);
        right.add(this.all);
    }

    /**
     * Setzt die Beschriftung der Knöpfe auf die gewählte Sprache.
     * 
     */
    public void updateLabels() {

        Language language = Language.getInstance();
        this.parse.setText(language.getString("ToolBar.parse"));
        this.myMenu.setText(language.getString("Menu.menu"));
        this.transSubstitutions.setText(language.getString("ToolBar.subs"));
        buttonsToolTip(language);
        this.myMenu.updateLabels();
    }

    // Alle Funktionen zum Knöpfe aktivieren/deaktivieren

    /**
     * Einstellung nach Neu und nach dem Parsen. Nur neue Anfrage möglich.
     */
    public void buttonsAfterParse() {
        this.parse.setEnabled(false);
        this.back.setEnabled(false);
        this.pause.setEnabled(false);
        this.step.setEnabled(false);
        this.next.setEnabled(false);
        this.all.setEnabled(false);
    }

    /**
     * Einstellung nach einem Fehler. Nur neue Anfrage und gegebenenfalls Parsen
     * möglich.
     */
    public void buttonsAfterError() {
        // this.parse.setEnabled(false); not changed
        this.back.setEnabled(false);
        this.pause.setEnabled(false);
        this.step.setEnabled(false);
        this.next.setEnabled(false);
        this.all.setEnabled(false);
    }

    /**
     * Einstellung nach Laden und beim Start
     */
    public void buttonsAfterLoad() {
        this.parse.setEnabled(true);
        this.back.setEnabled(false);
        this.pause.setEnabled(false);
        this.step.setEnabled(false);
        this.next.setEnabled(false);
        this.all.setEnabled(false);
    }

    /**
     * Einstellung nach neuer Anfrage oder nach Schritt zurück
     */
    public void buttonsAfterInquiry() {
        // this.parse.setEnabled(false); not changed
        this.back.setEnabled(false);
        this.pause.setEnabled(false);
        this.step.setEnabled(true);
        this.next.setEnabled(true);
        this.all.setEnabled(true);
    }

    /**
     * Einstellung nach nächstem Schritt
     */
    public void buttonsAfterStep() {
        // this.parse.setEnabled(false); not changed
        this.back.setEnabled(true);
        this.pause.setEnabled(false);
        this.step.setEnabled(true);
        this.next.setEnabled(true);
        this.all.setEnabled(true);
    }

    /**
     * Einstellung nach "no."
     */
    public void buttonsAfterNo() {
        // this.parse.setEnabled(false); not changed
        this.back.setEnabled(true);
        this.pause.setEnabled(false);
        this.step.setEnabled(false);
        this.next.setEnabled(false);
        this.all.setEnabled(false);
    }

    /**
     * Einstellung nach Textveränderung
     */
    public void buttonsAfterText() {
        this.parse.setEnabled(true);
    }

    /**
     * Einstellung während einer Lösungsberechnung
     */
    public void buttonsWhileCalculation() {
        // this.parse.setEnabled(false); not changed
        this.back.setEnabled(false);
        this.pause.setEnabled(true);
        this.step.setEnabled(false);
        this.next.setEnabled(false);
        this.all.setEnabled(false);
    }

    /**
     * Einstellung während einer Lösungsberechnung
     */
    public void buttonsWhileNext() {
        // this.parse.setEnabled(false); not changed
        this.back.setEnabled(false);
        this.pause.setEnabled(false);
        this.step.setEnabled(false);
        this.next.setEnabled(false);
        this.all.setEnabled(false);
    }

    /**
     * Gibt das Menü des Programms zurück.
     * 
     * @return Das Menü.
     */
    public Menu getMenu() {
        return this.myMenu;
    }

    /**
     * Gibt den Knopf "Step Back" zurück.
     * 
     * @return Knopf "Step Back".
     */
    public JButton getBackButton() {
        return this.back;
    }

    /**
     * Gibt den Knopf "Next Solution" zurück.
     * 
     * @return Knopf "Next Solution".
     */
    public JButton getNextButton() {
        return this.next;
    }

    /**
     * Gibt den Knopf "Step" zurück.
     * 
     * @return Knopf "Step".
     */
    public JButton getStepButton() {
        return this.step;
    }

    /**
     * Gibt den Knopf "All Solutions" zurück.
     * 
     * @return Knopf "All Solutions".
     */
    public JButton getAllButton() {
        return this.all;
    }

    /**
     * Gibt den Knopf "Pause" zurück.
     * 
     * @return Knopf "Pause".
     */
    public JButton getPauseButton() {
        return this.pause;
    }

    /**
     * Gibt den Knopf "Parse" zurück.
     * 
     * @return Knopf "Parse".
     */
    public JButton getParseButton() {
        return this.parse;
    }

    /**
     * Gibt den Checkbox "Transitive substitutions" zurück.
     * 
     * @return Checkbox "Transitive substitutions".
     */
    public JCheckBox getBoxSubs() {
        return this.transSubstitutions;
    }

    private void buttonsToolTip(Language language) {
        this.back.setToolTipText(language.getString("Buttons.stepBack"));
        this.pause.setToolTipText(language.getString("Buttons.pause"));
        this.step.setToolTipText(language.getString("Buttons.nextStep"));
        this.next.setToolTipText(language.getString("Buttons.nextSolution"));
        this.all.setToolTipText(language.getString("Buttons.allSolutions"));
    }

}
