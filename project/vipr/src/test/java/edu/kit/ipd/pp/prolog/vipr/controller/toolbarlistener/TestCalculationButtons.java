package edu.kit.ipd.pp.prolog.vipr.controller.toolbarlistener;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import java.awt.event.KeyEvent;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.controller.Main;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;

public class TestCalculationButtons extends AssertJSwingJUnitTestCase {

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

    // --- TEST VISIBILITY ON START ---

    @Ignore
    @Test
    public void visibility() {
        window.button("step").requireDisabled();
        window.button("next").requireDisabled();
        window.button("all").requireDisabled();
        window.button("back").requireDisabled();
        window.button("pause").requireDisabled();
    }

    // --- TEST VISIBILITY AFTER QUERY---

    @Ignore
    @Test
    public void afterQuery() {
        window.button("parse").click();
        window.textBox("query").setText("test.");
        window.textBox("query").pressKey(KeyEvent.VK_ENTER);
        window.button("parse").click();

        window.button("step").requireEnabled();
        window.button("next").requireEnabled();
        window.button("all").requireEnabled();
        window.button("back").requireDisabled();
        window.button("pause").requireDisabled();
    }

    // --- TEST VISIBILITY WHILE CALCULATION ---

    @Ignore
    @Test
    public void whileCalculation() {
        createLoop();
        window.button("step").requireDisabled();
        window.button("next").requireDisabled();
        window.button("all").requireDisabled();
        window.button("back").requireDisabled();
        window.button("pause").requireEnabled();
    }

    // --- BUTTON NEXT STEP LISTENER ---

    @Ignore
    @Test
    public void nextStepButton() {
        JTextComponentFixture query = window.textBox("query");
        query.click();
        query.setText("test.");
        query.pressAndReleaseKeys(KeyEvent.VK_ENTER);

        window.button("step").click();

        window.button("step").requireEnabled();
        window.button("next").requireEnabled();
        window.button("all").requireEnabled();
        window.button("back").requireEnabled();
        window.button("pause").requireDisabled();

    }

    // --- BUTTON NEXT SOLUTION LISTENER ---

    @Ignore
    @Test
    public void nextSolutionButton() {
        window.textBox("codeEditor").setText("mann(test).");
        window.button("parse").click();

        JTextComponentFixture query = window.textBox("query");
        query.click();
        query.setText("mann(X).");
        query.pressAndReleaseKeys(KeyEvent.VK_ENTER);

        window.button("next").click();

        window.button("step").requireEnabled();
        window.button("next").requireEnabled();
        window.button("all").requireEnabled();
        window.button("back").requireEnabled();
        window.button("pause").requireDisabled();

    }

    // --- BUTTON ALL SOLUTIONS LISTENER ---

    @Ignore
    @Test
    public void allSolutionsButton() {
        window.textBox("codeEditor").setText("mann(test).");
        window.button("parse").click();

        JTextComponentFixture query = window.textBox("query");
        query.click();
        query.setText("mann(X).");
        query.pressAndReleaseKeys(KeyEvent.VK_ENTER);

        window.button("all").click();
        // geht nur bis zur ersten lösung aufgrund von thread problemen mit jassert

    }

    // --- BUTTON PAUSE LISTENER ---

    @Ignore
    @Test
    public void pauseButton() {
        createLoop();
        window.button("pause").click();
        window.button("pause").requireDisabled();
    }

    // --- BUTTON STEP BACK LISTENER ---

    @Ignore
    @Test
    public void stepBackButton() {
        JTextComponentFixture query = window.textBox("query");
        query.click();
        query.setText("test.");
        query.pressAndReleaseKeys(KeyEvent.VK_ENTER);

        window.button("back").requireDisabled();

        window.button("step").click();
        window.button("back").requireEnabled();
        window.button("back").click();

        window.button("step").requireEnabled();
        window.button("next").requireEnabled();
        window.button("all").requireEnabled();
        window.button("back").requireDisabled();
        window.button("pause").requireDisabled();

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
