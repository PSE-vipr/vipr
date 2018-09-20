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
 * Test T6 aus dem Pflichtenheft. Schrittweise Visualisierung
 */
public class Test6 extends AssertJSwingJUnitTestCase {

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
     * Der Benutzer berechnet mit den Knöpfen der Toolbar Lösungen zu einer Anfrage.
     */
    @Ignore
    @Test

    public void visualization() {
        // T6.1 Benutzer hat Anfrage geparsed und klickt auf nextstep.
        window.menuItem("menu").click();
        window.button("parse").click();
        JTextComponentFixture query = window.textBox("query");
        query.click();
        query.setText("großeltern(X,Y).");
        query.pressAndReleaseKeys(KeyEvent.VK_ENTER);
        window.button("step").click();

        // T6.2 Benutzer berechnet via Shortcut den nächsten Schritt.
        query.pressKey(KeyEvent.VK_CONTROL);
        query.pressKey(KeyEvent.VK_R);
        query.releaseKey(KeyEvent.VK_CONTROL);
        query.releaseKey(KeyEvent.VK_R);

        // T6.3 Benutzer drückt auf nächste Lösung.
        window.button("next").click();
        window.textBox("inOut").requireText(
                Pattern.compile("Pars.*" + "großeltern[(]X,Y[)]..*" + "X = grampa, Y = bart.*", Pattern.DOTALL));

        // T6.4 Benutzer klickt auf alle Lösungen
        window.button("all").click();
        window.textBox("inOut")
                .requireText(Pattern.compile("Pars.*" + "großeltern[(]X,Y[)]..*" + "X = grampa, Y = bart.*"
                        + "X = grampa, Y = lisa.*" + "X = grampa, Y = maggie.*" + "no..*", Pattern.DOTALL));
    }

}