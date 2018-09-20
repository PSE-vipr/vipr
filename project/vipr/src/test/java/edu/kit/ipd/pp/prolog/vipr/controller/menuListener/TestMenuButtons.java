package edu.kit.ipd.pp.prolog.vipr.controller.menuListener;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JFileChooserFixture;
import org.assertj.swing.fixture.JMenuItemFixture;
import org.assertj.swing.fixture.JOptionPaneFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.controller.Main;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;

public class TestMenuButtons extends AssertJSwingJUnitTestCase {

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

    // --- MENU CHECK COMPLETENESS ---

    @Ignore
    @Test
    public void checkMenuCompleteness() {
        JMenuItemFixture menu = window.menuItem("menu");
        // open menu
        menu.click();
        JMenuItemFixture newProlog = window.menuItem("newProlog");
        newProlog.requireVisible();
        JMenuItemFixture load = window.menuItem("load");
        load.requireVisible();
        JMenuItemFixture save = window.menuItem("save");
        save.requireVisible();
        JMenuItemFixture saveAs = window.menuItem("saveAs");
        saveAs.requireVisible();
        JMenuItemFixture export = window.menuItem("export");
        export.requireVisible();
        JMenuItemFixture settings = window.menuItem("settings");
        settings.requireVisible();
        JMenuItemFixture help = window.menuItem("help");
        help.requireVisible();
        JMenuItemFixture about = window.menuItem("about");
        about.requireVisible();
    }

    // --- MENU NEW LISTENER ---

    @Ignore
    @Test
    public void newButton() {
        JMenuItemFixture menu = window.menuItem("menu");
        // open menu
        menu.click();
        JMenuItemFixture newProlog = window.menuItem("newProlog");
        newProlog.click();
        // test for clear codeEditor
        JTextComponentFixture codeEditor = window.textBox("codeEditor");
        codeEditor.requireText("");
    }

    @Ignore
    @Test
    public void newWhileCalculation() {
        createLoop();
        window.menuItem("menu").click();
        window.menuItem("newProlog").click();

        // test for clear codeEditor
        JTextComponentFixture codeEditor = window.textBox("codeEditor");
        codeEditor.requireText("");
    }

    // --- MENU LOAD LISTENER ---

    @Ignore
    @Test
    public void loadButton() {
        JMenuItemFixture menu = window.menuItem("menu");
        // open menu
        menu.click();
        JMenuItemFixture load = window.menuItem("load");
        load.click();
        // test for open fileManager
        JFileChooserFixture fileManager = window.fileChooser();
        fileManager.requireVisible();
    }

    @Ignore
    @Test
    public void loadWhileCalculation() {
        createLoop();
        window.menuItem("menu").click();
        window.menuItem("load").click();

        // test for open fileManager
        JFileChooserFixture fileManager = window.fileChooser();
        fileManager.requireVisible();
    }

    @Ignore
    @Test
    public void loadFile() {
        JMenuItemFixture menu = window.menuItem("menu");
        // open menu
        menu.click();
        JMenuItemFixture load = window.menuItem("load");
        load.click();
        JFileChooserFixture fileManager = window.fileChooser();
        fileManager.selectFile(
                new File(System.getProperty("user.home") + System.getProperty("file.separator") + "test.pl"));
        fileManager.approve();
        // test for text in codeEditor
        JTextComponentFixture codeEditor = window.textBox("codeEditor");
        codeEditor.requireText("test.");
    }

    // --- MENU EXPORT LISTENER ---

    @Ignore
    @Test
    public void exportButton() {
        JMenuItemFixture menu = window.menuItem("menu");
        // open menu
        menu.click();
        JMenuItemFixture export = window.menuItem("export");
        export.click();
        // test for open ErrorPopUp
        JOptionPaneFixture errorPopUp = window.optionPane();
        errorPopUp.requireVisible();
    }

    @Ignore
    @Test
    public void exportWhileCalculation() {
        createLoop();
        window.menuItem("menu").click();
        window.menuItem("export").click();

        // test for open fileManager
        JFileChooserFixture fileManager = window.fileChooser();
        fileManager.requireVisible();
    }

    @Ignore
    @Test
    public void exportGraph() {
        JTextComponentFixture query = window.textBox("query");
        query.click();
        query.setText("test.");
        query.pressAndReleaseKeys(KeyEvent.VK_ENTER);
        // test for test in inOut
        JTextComponentFixture inOut = window.textBox("inOut");
        inOut.requireText(Pattern.compile("test..*", Pattern.DOTALL));

        JMenuItemFixture menu = window.menuItem("menu");
        // open menu
        menu.click();
        JMenuItemFixture export = window.menuItem("export");
        export.click();
        // test for open fileManager
        JFileChooserFixture fileManager = window.fileChooser();
        fileManager.requireVisible();
    }

    // --- MENU SAVE LISTENER ---

    @Ignore
    @Test
    public void saveButton() {
        JMenuItemFixture menu = window.menuItem("menu");
        // open menu
        menu.click();
        JMenuItemFixture newprolog = window.menuItem("newProlog");
        newprolog.click();
        // open menu
        menu.click();
        JMenuItemFixture save = window.menuItem("save");
        save.click();
        // test for open fileManager
        JFileChooserFixture fileManager = window.fileChooser();
        fileManager.requireVisible();
    }

    // --- MENU SAVE AS LISTENER ---

    @Ignore
    @Test
    public void saveAsButton() {
        JMenuItemFixture menu = window.menuItem("menu");
        // open menu
        menu.click();
        JMenuItemFixture saveAs = window.menuItem("saveAs");
        saveAs.click();
        // test for open fileManager
        JFileChooserFixture fileManager = window.fileChooser();
        fileManager.requireVisible();
    }

    // --- MENU HELP LISTENER ---

    @Ignore
    @Test
    public void helpButton() {
        JMenuItemFixture menu = window.menuItem("menu");
        // open menu
        menu.click();
        JMenuItemFixture help = window.menuItem("help");
        help.click();
        // test for open HelpPopUp
        JOptionPaneFixture helpPopUp = window.optionPane();
        helpPopUp.requireVisible();
    }

    // --- MENU ABOUT LISTENER ---

    @Ignore
    @Test
    public void aboutButton() {
        JMenuItemFixture menu = window.menuItem("menu");
        // open menu
        menu.click();
        JMenuItemFixture about = window.menuItem("about");
        about.click();
        // test for open AboutPopUp
        JOptionPaneFixture aboutPopUp = window.optionPane();
        aboutPopUp.requireVisible();
    }

    // --- MENU SETTINGS LISTENER ---

    @Ignore
    @Test
    public void settingsButton() {
        JMenuItemFixture menu = window.menuItem("menu");
        // open menu
        menu.click();
        JMenuItemFixture settings = window.menuItem("settings");
        settings.click();
        // test for open SettingsPopUp
        DialogFixture settingsPopUp = window.dialog();
        settingsPopUp.requireVisible();
    }

    // --- ADDITIONAL STUFF ---

    private void createLoop() {
        window.textBox("codeEditor").setText("a(X):-a(X).");
        window.button("parse").click();
        window.textBox("query").setText("a(X).");
        window.textBox("query").pressKey(KeyEvent.VK_ENTER);
        window.button("next").click();

    }
}