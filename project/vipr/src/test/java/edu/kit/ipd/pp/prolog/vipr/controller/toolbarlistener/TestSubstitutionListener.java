package edu.kit.ipd.pp.prolog.vipr.controller.toolbarlistener;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import java.awt.event.KeyEvent;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.controller.Main;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;

public class TestSubstitutionListener extends AssertJSwingJUnitTestCase {

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

    // --- SUBSTITUTION LISTENER ---

    @Ignore
    @Test
    public void substitutionCheckBox() {
        window.textBox("query").setText("test.");
        window.textBox("query").pressKey(KeyEvent.VK_ENTER);
        window.checkBox("subs").click();
    }

    @Ignore
    @Test
    public void substitutionWhileCalculation() {
        createLoop();
        window.textBox("query").setText("test.");
        window.textBox("query").pressKey(KeyEvent.VK_ENTER);
        window.checkBox("subs").click();
    }

    @Ignore
    @Test
    public void substitutionAfterStepBack() {
        window.textBox("query").setText("test.");
        window.textBox("query").pressKey(KeyEvent.VK_ENTER);

        window.button("step").click();
        window.button("back").click();

        window.checkBox("subs").click();
    }

    @Ignore
    @Test
    public void substituitonWithoutGraph() {
        window.checkBox("subs").click();
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
