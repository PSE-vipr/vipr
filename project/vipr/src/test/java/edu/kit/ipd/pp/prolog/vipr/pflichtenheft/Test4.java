package edu.kit.ipd.pp.prolog.vipr.pflichtenheft;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JMenuItemFixture;
import org.assertj.swing.fixture.JOptionPaneFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.controller.Main;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;

/**
 * Test T4 aus dem Pflichtenheft. Datei Parsen
 */
public class Test4 extends AssertJSwingJUnitTestCase {

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
     * Benutzer parsed über den Knopf eine gültige Datei.
     */

    @Ignore
    @Test

    public void parseCodeWithButton() {
        // T4.1 Benutzer drückt Parse. Der Quellcode wird geparsed.
        JMenuItemFixture menu = window.menuItem("menu");
        menu.click();
        JButtonFixture parse = window.button("parse");
        parse.click();
        parse.requireDisabled();
        JTextComponentFixture output = window.textBox("inOut");
        output.requireText(Pattern.compile("Pars.*", Pattern.DOTALL));
    }

    /**
     * Benutzer parsed über den Shortcut eine gültige Datei.
     */

    @Ignore
    @Test

    public void parseCodeWithShortcut() {
        // T4.1 Benutzer drückt Parse. Der Quellcode wird geparsed.
        JTextComponentFixture output = window.textBox("inOut");
        JButtonFixture parse = window.button("parse");
        output.pressKey(KeyEvent.VK_CONTROL);
        output.pressKey(KeyEvent.VK_P);
        output.releaseKey(KeyEvent.VK_CONTROL);
        output.releaseKey(KeyEvent.VK_P);
        parse.requireDisabled();
        output.requireText(Pattern.compile("Pars.*", Pattern.DOTALL));
    }

    /**
     * Benutzer parsed eine ungültige Datei.
     */
    @Ignore
    @Test

    public void parseIllegalCode() {
        // T4.3 Benutzer gibt falschen Quellcode ein. Beim Parsen kommt ein Fehler.
        JMenuItemFixture menu = window.menuItem("menu");
        menu.click();
        JButtonFixture parse = window.button("parse");
        JTextComponentFixture codeEditor = window.textBox("codeEditor");
        codeEditor.deleteText();
        codeEditor.enterText("hello(World");
        parse.click();
        JOptionPaneFixture errorPopUp = window.optionPane();
        errorPopUp.requireVisible();
    }

}