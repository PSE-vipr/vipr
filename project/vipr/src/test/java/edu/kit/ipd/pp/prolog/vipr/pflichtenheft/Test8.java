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
 * Test T7 aus dem Pflichtenheft. Arithmetik
 */
public class Test8 extends AssertJSwingJUnitTestCase {

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
     * Testet das Arithmetik-Ziel T8.1 "X is 0".
     */
    @Ignore
    @Test

    public void arithGoal1() {
        window.menuItem("menu").click();
        JTextComponentFixture query = window.textBox("query");
        query.click();
        query.setText("X is 0.");
        query.pressAndReleaseKeys(KeyEvent.VK_ENTER);
        window.button("next").click();
        window.textBox("inOut").requireText(Pattern.compile("X is 0..*" + "X = 0.*", Pattern.DOTALL));
    }

    /**
     * Testet das Arithmetik-Ziel T8.2 "X is 1+3".
     */
    @Ignore
    @Test

    public void arithGoal2() {
        window.menuItem("menu").click();
        JTextComponentFixture query = window.textBox("query");
        query.click();
        query.setText("X is 1+3.");
        query.pressAndReleaseKeys(KeyEvent.VK_ENTER);
        window.button("next").click();
        window.textBox("inOut").requireText(Pattern.compile("X is 1[+]3..*" + "X = 4.*", Pattern.DOTALL));
    }

    /**
     * Testet das Arithmetik-Ziel T8.3 "4 is 1+3".
     */
    @Ignore
    @Test

    public void arithGoal3() {
        window.menuItem("menu").click();
        JTextComponentFixture query = window.textBox("query");
        query.click();
        query.setText("4 is 1+3.");
        query.pressAndReleaseKeys(KeyEvent.VK_ENTER);
        window.button("next").click();
        window.textBox("inOut").requireText(Pattern.compile("4 is 1[+]3..*" + "yes.*", Pattern.DOTALL));
    }

    /**
     * Testet das Arithmetik-Ziel T8.4 "5 is 1+3".
     */
    @Ignore
    @Test

    public void arithGoal4() {
        window.menuItem("menu").click();
        JTextComponentFixture query = window.textBox("query");
        query.click();
        query.setText("5 is 1+3.");
        query.pressAndReleaseKeys(KeyEvent.VK_ENTER);
        window.button("next").click();
        window.textBox("inOut").requireText(Pattern.compile("5 is 1[+]3..*" + "no..*", Pattern.DOTALL));
    }

    /**
     * Testet das Arithmetik-Ziel T8.5 "1+3 is 1+3".
     */
    @Ignore
    @Test

    public void arithGoal5() {
        window.menuItem("menu").click();
        JTextComponentFixture query = window.textBox("query");
        query.click();
        query.setText("1+3 is 1+3.");
        query.pressAndReleaseKeys(KeyEvent.VK_ENTER);
        window.button("next").click();
        window.textBox("inOut").requireText(Pattern.compile("1[+]3 is 1[+]3..*" + "no..*", Pattern.DOTALL));
    }

}