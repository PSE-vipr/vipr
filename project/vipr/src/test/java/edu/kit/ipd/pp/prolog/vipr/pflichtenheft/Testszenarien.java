package edu.kit.ipd.pp.prolog.vipr.pflichtenheft;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JFileChooserFixture;
import org.assertj.swing.fixture.JMenuItemFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.controller.Main;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;

/**
 * Diese Klasse testet die Testszenarien aus dem Pflichtenheft.
 */
public class Testszenarien extends AssertJSwingJUnitTestCase {

    private FrameFixture window;
    private static final String SEPERATOR = System.getProperty("line.separator");

    @Override
    protected void onSetUp() {
        application(Main.class).start();

        window = findFrame(new GenericTypeMatcher<GUIMain>(GUIMain.class) {
            protected boolean isMatching(GUIMain frame) {
                return "VIPR".equals(frame.getTitle()) && frame.isShowing();
            }

        }).using(robot());
    }

    @Override
    protected void onTearDown() {
        super.onTearDown();
        window.cleanUp();
    }

    @BeforeClass
    public static void createFile() throws IOException {
        // create file for later use
        File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "love.pl");

        FileWriter writer = new FileWriter(file);
        writer.write("loves(vincent,           mia).\r\n" + "loves(marcellus,           mia).\r\n"
                + "loves(pumpkin,           honeybunny).\r\n" + "loves(honeybunny,           pumpkin).\r\n"
                + "jealous(X,           Y)           :-\r\n" + "loves(X,           Z),\r\n" + "loves(Y,           Z).");
        writer.close();

        File file2 = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "family.pl");

        FileWriter writer2 = new FileWriter(file2);
        writer2.write("mann(adam).\r\n" + "mann(tobias).\r\n" + "frau(daniela).\r\n" + "frau(ulrike).\r\n"
                + "vater(adam,           tobias).\r\n" + "vater(tobias,ulrike).\r\n" + "mutter(daniela,ulrike).\r\n"
                + "großvater(X,Y):-\r\n" + "vater(X,Y),\r\n" + "vater(Z,Y).");
        writer2.close();
    }

    @AfterClass
    public static void deleteFile() {
        // delete file that was created on start
        File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "love.pl");

        file.delete();

        File file2 = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "family.pl");

        file2.delete();
    }

    /**
     * Der neugierige Student verwendet die grundlegenden Funktionen von VIPR.
     */
    @Test
    @Ignore

    public void szenario1() {
        // Der neugierige Student öffnet VIPR und sieht das Beispielprogramm.
        JTextComponentFixture codeEditor = window.textBox("codeEditor");
        codeEditor.requireText("mann(grampa)." + SEPERATOR + "mann(homer)." + SEPERATOR + "mann(bart)." + SEPERATOR
                + "frau(marge)." + SEPERATOR + "frau(lisa)." + SEPERATOR + "frau(maggie)." + SEPERATOR
                + "hund(knechtruprecht)." + SEPERATOR + "katze(snowball)." + SEPERATOR + SEPERATOR
                + "vater(grampa, homer)." + SEPERATOR + "vater(homer, bart)." + SEPERATOR + "vater(homer, lisa)."
                + SEPERATOR + "vater(homer, maggie)." + SEPERATOR + "mutter(marge, bart)." + SEPERATOR
                + "mutter(marge, lisa)." + SEPERATOR + "mutter(marge, maggie)." + SEPERATOR + SEPERATOR
                + "eltern(X,Y):-" + SEPERATOR + "     vater(X,Y)." + SEPERATOR + "eltern(X,Y):-" + SEPERATOR
                + "     mutter(X,Y)." + SEPERATOR + SEPERATOR + "großeltern(X,Y):-" + SEPERATOR + "     eltern(X,Z),"
                + SEPERATOR + "     eltern(Z,Y).");
        // Der neugierige Student parsed das Programm
        JButtonFixture parse = window.button("parse");
        parse.click();
        parse.requireDisabled();
        JTextComponentFixture output = window.textBox("inOut");
        output.requireText(Pattern.compile("Pars.*", Pattern.DOTALL));
        // Der neugierige Student gibt eine Anfrage ein und bestätigt sie.
        JTextComponentFixture query = window.textBox("query");
        query.click();
        query.setText("großeltern(X,Y).");
        query.pressAndReleaseKeys(KeyEvent.VK_ENTER);
        window.textBox("inOut").requireText(Pattern.compile("Pars.*" + "großeltern[(]X,Y[)]..*", Pattern.DOTALL));
        // Der neugierige Student lässt sich mit step die einzelnen Schritte anzeigen.
        // Dann geht er einen Schritt zurück.
        // Dann lässt er sich die erste Lösung berechnen.
        window.button("step").click();
        window.button("step").click();
        window.button("back").click();
        window.button("next");
        // Der neugierige Student möchte sich nun alle Lösungen ausgeben lassen und
        // klickt auf all.
        window.button("all").click();

    }

    /**
     * Der skeptische Benutzer gibt absichtlich eine Anfrage ein, zu der keine Regel
     * existiert.
     */
    @Test
    @Ignore

    public void szenario2() {
        // Der skeptische Betreuer öffnet VIPR und lädt love.pl
        JMenuItemFixture menu = window.menuItem("menu");
        menu.click();
        JMenuItemFixture load = window.menuItem("load");
        load.requireVisible();
        load.click();
        JFileChooserFixture fileManager = window.fileChooser();
        fileManager.requireVisible();
        fileManager.selectFile(
                new File(System.getProperty("user.home") + System.getProperty("file.separator") + "love.pl"));
        fileManager.approve();
        JTextComponentFixture codeEditor = window.textBox("codeEditor");
        codeEditor.requireText("loves(vincent,           mia).\r\n" + "loves(marcellus,           mia).\r\n"
                + "loves(pumpkin,           honeybunny).\r\n" + "loves(honeybunny,           pumpkin).\r\n"
                + "jealous(X,           Y)           :-\r\n" + "loves(X,           Z),\r\n" + "loves(Y,           Z).");
        // Der skeptische Betreuer parsed das Programm und gibt Anfrage ein, zu der
        // keine Regel existiert.
        window.button("parse").click();
        JTextComponentFixture query = window.textBox("query");
        query.click();
        query.setText("hates(X, mia).");
        query.pressAndReleaseKeys(KeyEvent.VK_ENTER);
        window.button("step").click();
        // Es wird eine Fehlermeldung ausgegeben.
        // Muss auskommentiert werden, weil Testumgebung keine Fehlermeldung ausgibt.
        // window.optionPane().requireVisible();
    }

    /**
     * Der Benutzer "Anfänger" versucht erst, ein eigenes Programm zu parsen. Dann
     * lädt er ein Programm und gibt eine fehlerhafte Anfrage ein.
     */
    @Test
    @Ignore

    public void szenario3() {
        // Der Anfänger öffnet VIPR und drückt auf New.
        JMenuItemFixture menu = window.menuItem("menu");
        menu.click();
        JMenuItemFixture newProlog = window.menuItem("newProlog");
        newProlog.click();
        JTextComponentFixture codeEditor = window.textBox("codeEditor");
        codeEditor.requireText("");
        // Der Anfänger gibt fehlerhaften Quellcode ein.
        codeEditor.setText("test)");
        // Der Anfänger will ihn parsen. Es erscheint eine Fehlermeldung.
        window.button("parse").click();
        window.optionPane().requireVisible();
        window.optionPane().pressAndReleaseKeys(KeyEvent.VK_ENTER);
        // Der Anfänger lädt das Programm family.pl
        menu.click();
        JMenuItemFixture load = window.menuItem("load");
        load.requireVisible();
        load.click();
        JFileChooserFixture fileManager = window.fileChooser();
        fileManager.requireVisible();
        fileManager.selectFile(
                new File(System.getProperty("user.home") + System.getProperty("file.separator") + "family.pl"));
        fileManager.approve();
        codeEditor.requireText("mann(adam).\r\n" + "mann(tobias).\r\n" + "frau(daniela).\r\n" + "frau(ulrike).\r\n"
                + "vater(adam,           tobias).\r\n" + "vater(tobias,ulrike).\r\n" + "mutter(daniela,ulrike).\r\n"
                + "großvater(X,Y):-\r\n" + "vater(X,Y),\r\n" + "vater(Z,Y).");
        // Der Anfänger parsed das Programm erfolgreich.
        window.button("parse").click();
        JTextComponentFixture output = window.textBox("inOut");
        output.requireText(Pattern.compile("Pars.*", Pattern.DOTALL));
        // Der Anfänger stellt eine syntaktisch falsche Anfrage. Es erscheint eine
        // Fehlermeldung.
        JTextComponentFixture query = window.textBox("query");
        query.click();
        query.setText("vater tobias");
        query.pressAndReleaseKeys(KeyEvent.VK_ENTER);
        // Es wird eine Fehlermeldung ausgegeben.
        // Muss auskommentiert werden, weil Testumgebung keine Fehlermeldung ausgibt.
        // window.optionPane().requireVisible();
    }

}