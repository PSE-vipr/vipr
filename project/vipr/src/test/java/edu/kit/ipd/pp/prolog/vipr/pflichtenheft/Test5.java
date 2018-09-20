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
 * Test T5 aus dem Pflichtenheft. Anfragen eingeben und bearbeiten
 */
public class Test5 extends AssertJSwingJUnitTestCase {

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
     * Syntaktisch falsche Anfrage.
     */
    @Ignore
    @Test

    public void wrongSyntax() {
        // T5.2 Benutzer gibt eine syntaktisch falsche Anfrage ein.
        window.menuItem("menu").click();
        window.button("parse").click();
        JTextComponentFixture query = window.textBox("query");
        query.click();
        query.setText("2 => 1.");
        query.pressAndReleaseKeys(KeyEvent.VK_ENTER);
        window.optionPane().requireVisible();
    }

    /**
     * Syntaktisch korrekte Anfrage.
     */
    @Ignore
    @Test

    public void correctSyntax() {
        // T5.3 Benutzer gibt eine syntaktisch korrekte Anfrage ein.
        window.menuItem("menu").click();
        window.button("parse").click();
        JTextComponentFixture query = window.textBox("query");
        query.click();
        query.setText("großeltern(X,Y).");
        query.pressAndReleaseKeys(KeyEvent.VK_ENTER);
        window.textBox("inOut").requireText(Pattern.compile("Pars.*" + "großeltern[(]X,Y[)]..*", Pattern.DOTALL));
    }

}