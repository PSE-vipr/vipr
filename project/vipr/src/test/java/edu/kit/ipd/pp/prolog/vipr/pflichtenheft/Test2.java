package edu.kit.ipd.pp.prolog.vipr.pflichtenheft;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.fixture.FrameFixture;
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
 * Test T2 aus dem Pflichtenheft. Dateien laden
 */
public class Test2 extends AssertJSwingJUnitTestCase {

    private FrameFixture window;

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
        File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "test.pl");

        FileWriter writer = new FileWriter(file);
        writer.write("test.");
        writer.close();
    }

    @AfterClass
    public static void deleteFile() {
        // delete file that was created on start
        File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "test.pl");

        file.delete();
    }

    /**
     * Lädt über den Filemanager eine Prolog-Datei.
     * 
     * @throws IOException
     */

    @Test
    @Ignore

    public void loadProgram() throws IOException {
        // T2.1 Dateimanager öffnen
        JMenuItemFixture menu = window.menuItem("menu");
        menu.click();
        JMenuItemFixture load = window.menuItem("load");
        load.requireVisible();
        load.click();
        JFileChooserFixture fileManager = window.fileChooser();
        fileManager.requireVisible();

        // T2.2 Prolog-Datei auswählen und anzeigen
        fileManager.selectFile(
                new File(System.getProperty("user.home") + System.getProperty("file.separator") + "test.pl"));
        fileManager.approve();
        JTextComponentFixture codeEditor = window.textBox("codeEditor");
        codeEditor.requireText("test.");

    }

}