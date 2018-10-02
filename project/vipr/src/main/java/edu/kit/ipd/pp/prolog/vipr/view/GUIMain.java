package edu.kit.ipd.pp.prolog.vipr.view;

import java.awt.Rectangle;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.JTextComponent;

import com.mxgraph.view.mxGraph;

import edu.kit.ipd.pp.prolog.vipr.view.functionality.ToolBar;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.GraphicPane;
import edu.kit.ipd.pp.prolog.vipr.view.textarea.CodeEditor;
import edu.kit.ipd.pp.prolog.vipr.view.textarea.InOut;
import edu.kit.ipd.pp.prolog.vipr.view.textarea.QueryField;

/**
 * Die Hauptklasse der grafischen Benutzeroberfläche. Sie erstellt das Fenster
 * mit allen nötigen Komponenten und hält Getter bereit, mit denen darauf
 * zugegriffen werden kann.
 */
@SuppressWarnings("serial")
public class GUIMain extends JFrame {

    /**
     * Gibt an um wie viel Pixel die große Schrift größer ist als die normale
     * Schrift.
     */
    private static final int LARGER_FONT_PLUS = 8;

    /**
     * Speichert die Toolbar der GUI.
     */
    private ToolBar toolBar;

    /**
     * Speichert den Code-Editor der GUI.
     */
    private CodeEditor codeEditor;

    /**
     * Speichert das Ausgabefeld der GUI.
     */
    private InOut inOut;

    /**
     * Bezeichner für das Eingabefeld.
     */
    private JLabel query;

    /**
     * Speichert das Eingabefeld der GUI.
     */
    private QueryField queryField;

    /**
     * Speichert das GraphicPane der GUI.
     */
    private GraphicPane graphicPane;

    /**
     * Scroll-Balken für den Code-Editor.
     */
    private JScrollPane spEditor;

    /**
     * Scroll-Balken für das Ausgabefeld.
     */
    private JScrollPane spInOut;

    /**
     * Konstruktor der GUIMain. Erstellt die GUI, initialisiert die Attribute der
     * Klasse und ordnet die Komponenten an.
     * 
     * @param projectName
     *            Der Name der GUI.
     */
    public GUIMain(String projectName) {
        super(projectName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1200, 800);

        GroupLayout groupLayout = new GroupLayout(getContentPane());
        setLayout(groupLayout);

        this.toolBar = new ToolBar();
        setJMenuBar(this.toolBar);

        this.codeEditor = new CodeEditor(LARGER_FONT_PLUS);
        add(this.codeEditor);
        this.spEditor = new JScrollPane(this.codeEditor);
        addScrollPane(this.spEditor, this.codeEditor);
        TextLineNumber tln = new TextLineNumber(codeEditor);
        spEditor.setRowHeaderView(tln);
        this.codeEditor.showExample();

        this.query = new JLabel();
        Rectangle r = new Rectangle(0, 500, 0, 25);
        this.query.setBounds(r);
        this.query.setText(" ?- ");
        add(this.query);
        this.queryField = new QueryField(LARGER_FONT_PLUS);
        add(this.queryField);

        this.inOut = new InOut(LARGER_FONT_PLUS);
        add(this.inOut);
        this.spInOut = new JScrollPane(this.inOut);
        addScrollPane(this.spInOut, this.inOut);

        this.graphicPane = new GraphicPane(new mxGraph());
        this.graphicPane.getGraphComponent().setBounds(600, 0, 600, 725);
        add(this.graphicPane);

        groupStuff(groupLayout);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Gibt die Toolbar zurück.
     * 
     * @return Die Toolbar der GUI.
     */
    public ToolBar getToolBar() {
        return this.toolBar;
    }

    /**
     * Gibt das GraphicPane zurück.
     * 
     * @return Das GraphicPane der GUI.
     */
    public GraphicPane getGraphicPane() {
        return this.graphicPane;
    }

    /**
     * Gibt den Code-Editor zurück.
     * 
     * @return Den Code-Editor der GUI.
     */
    public CodeEditor getEditor() {
        return this.codeEditor;
    }

    /**
     * Gibt das Ausgabefeld zurück.
     * 
     * @return Das Ausgabefeld der GUI.
     */
    public InOut getInOut() {
        return this.inOut;
    }

    /**
     * Gibt das Eingabefeld zurück.
     * 
     * @return Das Eingabefeld der GUI.
     */
    public QueryField getQueryField() {
        return this.queryField;
    }

    /**
     * Fügt ein ScrollPane einem Textfeld hinzu.
     * 
     * @param scrollpane
     *            Das ScrollPane.
     * @param textArea
     *            Das Textfeld.
     */
    private void addScrollPane(JScrollPane scrollpane, JTextComponent textArea) {
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollpane.setBounds(textArea.getX(), textArea.getY(), textArea.getWidth(), textArea.getHeight());
        scrollpane.setSize(textArea.getWidth(), textArea.getHeight());
        add(scrollpane);
    }

    /**
     * Ordnet die GUI-Bestandteile in der Benutzeroberfläche an mit dem groupLayout.
     * 
     * @param groupLayout
     *            Das groupLayout.
     */
    private void groupStuff(GroupLayout groupLayout) {
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
                .createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING,
                                groupLayout.createSequentialGroup().addGap(1).addComponent(this.spEditor,
                                        GroupLayout.DEFAULT_SIZE, this.spEditor.getWidth(), Short.MAX_VALUE))
                        .addComponent(this.spInOut, GroupLayout.DEFAULT_SIZE, this.spInOut.getWidth(), Short.MAX_VALUE)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(this.query, GroupLayout.DEFAULT_SIZE, this.query.getWidth(),
                                        this.query.getWidth())
                                .addGap(1).addComponent(this.queryField, GroupLayout.DEFAULT_SIZE,
                                        this.queryField.getWidth(), Short.MAX_VALUE)))
                .addPreferredGap(ComponentPlacement.RELATED).addComponent(this.graphicPane.getGraphComponent(),
                        GroupLayout.DEFAULT_SIZE, this.graphicPane.getGraphComponent().getWidth(), Short.MAX_VALUE)
                .addContainerGap()));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
                .createSequentialGroup()
                .addComponent(this.spEditor, GroupLayout.DEFAULT_SIZE, this.spEditor.getHeight(), Short.MAX_VALUE)
                .addGap(1)
                .addComponent(this.spInOut, GroupLayout.DEFAULT_SIZE, this.spInOut.getHeight(), Short.MAX_VALUE)
                .addGap(2)
                .addGroup(groupLayout.createParallelGroup()
                        .addComponent(this.query, GroupLayout.DEFAULT_SIZE, this.query.getHeight(),
                                this.query.getHeight())
                        .addComponent(this.queryField, GroupLayout.DEFAULT_SIZE, this.queryField.getHeight(),
                                this.queryField.getHeight())))
                .addGroup(groupLayout.createSequentialGroup().addComponent(this.graphicPane.getGraphComponent(),
                        GroupLayout.DEFAULT_SIZE, this.graphicPane.getGraphComponent().getHeight(), Short.MAX_VALUE)
                        .addGap(1)));
    }

    /**
     * Wechselt die Schriftgröße.
     */
    public void changeFont() {
        codeEditor.changeFont();
        inOut.changeFont();
    }

}
