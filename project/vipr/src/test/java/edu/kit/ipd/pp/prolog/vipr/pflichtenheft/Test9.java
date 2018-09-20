package edu.kit.ipd.pp.prolog.vipr.pflichtenheft;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.controller.Main;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;

/**
 * Test T9 aus dem Pflichtenheft. Listen
 */
public class Test9 extends AssertJSwingJUnitTestCase {

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
     * Testet die Berechnung einer Anfrage mit Listen.
     */
    @Ignore
    @Test

    public void lists() {
        // T9.1 Der Benutzer parsed das Listen-Programm.
        window.menuItem("menu").click();
        String list = "append([], X, X)." + System.getProperty("line.separator") + "append([X | Xs], Ys, [X | Zs]) :-"
                + System.getProperty("line.separator") + "append(Xs, Ys, Zs)." + System.getProperty("line.separator");
        window.textBox("codeEditor").setText(list);
        window.button("parse").click();
        // T9.2 Der Benutzer gibt eine Anfrage ein.
        JTextComponentFixture query = window.textBox("query");
        query.click();
        query.setText("append(X, Y, [1, 2, 3]).");
        query.pressAndReleaseKeys(KeyEvent.VK_ENTER);
        // T9.3 Der Benutzer lässt sich die erste Lösung berechnen und ausgeben.
        window.button("next").click();
        window.textBox("inOut").requireText(Pattern.compile(
                "Pars.*" + "append[(]X, Y, \\[1, 2, 3\\][)]..*" + "X = \\[\\], Y = \\[1, 2, 3\\].*", Pattern.DOTALL));
    }

}