package edu.kit.ipd.pp.prolog.vipr.pflichtenheft;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import java.awt.event.KeyEvent;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JFileChooserFixture;
import org.assertj.swing.fixture.JMenuItemFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.controller.Main;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;

/**
 * Test T3 aus dem Pflichtenheft. Quellcode editieren und speichern.
 */
public class Test3 extends AssertJSwingJUnitTestCase {

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

    /**
     * Der Benutzer erstellt per Shortcut eine neue Prolog-Datei.
     */

    @Ignore
    @Test

    public void newPrologWithShortcut() {
        // T3.1 Der Benutzer drückt Strg+N und leert damit das Codefeld.
        JTextComponentFixture codeEditor = window.textBox("codeEditor");
        codeEditor.pressKey(KeyEvent.VK_CONTROL);
        codeEditor.pressKey(KeyEvent.VK_N);
        codeEditor.releaseKey(KeyEvent.VK_CONTROL);
        codeEditor.releaseKey(KeyEvent.VK_N);
        codeEditor.requireText("");
    }

    /**
     * Der Benutzer erstellt eine neue Prolog-Datei, editiert den Quellcode und
     * speichert ihn ab.
     */

    @Ignore
    @Test

    public void newProlog() {
        // T3.2 Der Benutzer leert das Codefeld über das Menü.
        JMenuItemFixture menu = window.menuItem("menu");
        menu.click();
        JMenuItemFixture newProlog = window.menuItem("newProlog");
        newProlog.click();
        JTextComponentFixture codeEditor = window.textBox("codeEditor");
        codeEditor.requireText("");

        // T3.3 Der Benutzer gibt Quellcode ein.
        codeEditor.setText("mother(marge, lisa).\nmother(marge, bart).");
        codeEditor.requireText("mother(marge, lisa).\nmother(marge, bart).");

        // T3.4 Benutzer wählt Save as
        menu.click();
        JMenuItemFixture saveAs = window.menuItem("saveAs");
        saveAs.click();
        JFileChooserFixture fileManager = window.fileChooser();
        fileManager.requireVisible();

    }

}