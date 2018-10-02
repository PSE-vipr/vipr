package edu.kit.ipd.pp.prolog.vipr.view.functionality;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import edu.kit.ipd.pp.prolog.vipr.view.Language;

/**
 * Diese Klasse enthält das ausklappbare Menü des Programms. Es hält das Menü
 * und die verschiedenen Menü-Einträge.
 *
 */
@SuppressWarnings("serial")
public class Menu extends JMenu {

    /**
     * Menüeintrag zum Erstellen einer neuen, leeren Prolog-Datei.
     */
    private JMenuItem newProlog;

    /**
     * Menüeintrag zum Laden einer existierenden Prolog-Datei.
     */
    private JMenuItem load;

    /**
     * Menüeintrag zum Speichern einer schon einmal gespeicherten Prolog-Datei.
     */
    private JMenuItem save;

    /**
     * Menüeintrag zum Speichern einer bisher noch nicht gespeicherten Prolog-Datei.
     */
    private JMenuItem saveAs;

    /**
     * Menüeintrag zum Exportieren der Grafik als PNG.
     */
    private JMenuItem export;

    /**
     * Menüeintrag zum Öffnen des Settings-Fensters.
     */
    private JMenuItem settings;

    /**
     * Menüeintrag zum Öffnen des Help-Fensters.
     */
    private JMenuItem help;

    /**
     * Menüeintrag zum Öffnen des About-Fensters.
     */
    private JMenuItem about;

    /**
     * Konstruktor für das Menü. Erstellt das Menü und initialisiert die
     * Menüeinträge.
     * 
     * @param s
     *            der Name des Menüs.
     */
    public Menu(String s) {

        super(s);
        this.setName("menu");

        this.newProlog = new JMenuItem();
        newProlog.setName("newProlog");

        this.load = new JMenuItem();
        load.setName("load");

        this.save = new JMenuItem();
        save.setName("save");

        this.saveAs = new JMenuItem();
        saveAs.setName("saveAs");

        this.export = new JMenuItem();
        export.setName("export");

        this.settings = new JMenuItem();
        settings.setName("settings");

        this.help = new JMenuItem();
        help.setName("help");

        this.about = new JMenuItem();
        about.setName("about");

        updateLabels();

        add(this.newProlog);
        add(this.load);
        add(this.save);
        add(this.saveAs);
        add(this.export);
        add(this.settings);
        add(this.help);
        add(this.about);

    }

    /**
     * Methode zum Ausgrauen von Menü-Einträgen. Wenn die Funktion eines Eintrags zu
     * einem Zeitpunkt nicht möglich ist, wird er ausgegraut dargestellt und kann
     * nicht betätigt werden.
     * 
     * @param i
     *            Der Eintrag, der deaktiviert werden soll.
     */
    public void setEnabled(JMenuItem i) {
        i.setEnabled(false);
    }

    /**
     * Setzt die Beschriftung der Menü-Einträge auf die gewählte Sprache.
     * 
     */
    public void updateLabels() {
        Language language = Language.getInstance();
        this.newProlog.setText(language.getString("Menu.new"));
        this.load.setText(language.getString("Menu.open"));
        this.save.setText(language.getString("Menu.save"));
        this.saveAs.setText(language.getString("Menu.saveAs"));
        this.export.setText(language.getString("Menu.export"));
        this.settings.setText(language.getString("Menu.sett"));
        this.help.setText(language.getString("Menu.help"));
        this.about.setText(language.getString("Menu.about"));
    }

    /**
     * Gibt den Menüeintrag "New" zurück.
     * 
     * @return Menüeintrag "New"
     */
    public JMenuItem getNew() {
        return this.newProlog;
    }

    /**
     * Gibt den Menüeintrag "Open" zurück.
     * 
     * @return Menüeintrag "Open"
     */
    public JMenuItem getOpen() {
        return this.load;
    }

    /**
     * Gibt den Menüeintrag "Save" zurück.
     * 
     * @return Menüeintrag "Save"
     */
    public JMenuItem getSave() {
        return this.save;
    }

    /**
     * Gibt den Menüeintrag "Save As" zurück.
     * 
     * @return Menüeintrag "Save As"
     */
    public JMenuItem getSaveAs() {
        return this.saveAs;
    }

    /**
     * Gibt den Menüeintrag "Export PNG" zurück.
     * 
     * @return Menüeintrag "Export PNG"
     */
    public JMenuItem getExport() {
        return this.export;
    }

    /**
     * Gibt den Menüeintrag "Settings" zurück.
     * 
     * @return Menüeintrag "Settings"
     */
    public JMenuItem getSettings() {
        return this.settings;
    }

    /**
     * Gibt den Menüeintrag "Help" zurück.
     * 
     * @return Menüeintrag "Help"
     */
    public JMenuItem getHelp() {
        return this.help;
    }

    /**
     * Gibt den Menüeintrag "About" zurück.
     * 
     * @return Menüeintrag "About"
     */
    public JMenuItem getAbout() {
        return this.about;
    }

}
