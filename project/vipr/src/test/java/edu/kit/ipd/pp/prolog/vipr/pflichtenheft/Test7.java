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
 * Test T7 aus dem Pflichtenheft. Unifikationsziel
 */
public class Test7 extends AssertJSwingJUnitTestCase {

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
     * Testet ein erfolgreiches Unifikationsziel
     */
    @Ignore
    @Test

    public void unificationGoal() {
        window.menuItem("menu").click();
        window.button("parse").click();
        JTextComponentFixture query = window.textBox("query");
        query.click();
        query.setText("vater(homer,bart) = vater(homer,X).");
        query.pressAndReleaseKeys(KeyEvent.VK_ENTER);
        window.button("next").click();
        window.textBox("inOut").requireText(Pattern
                .compile("Pars.*" + "vater[(]homer,bart[)] = vater[(]homer,X[)].*" + "X = bart.*", Pattern.DOTALL));
    }

}