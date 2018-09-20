package edu.kit.ipd.pp.prolog.vipr.controller.toolbarlistener;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import java.awt.event.KeyEvent;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JOptionPaneFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.controller.Main;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;

public class TestParseButtonListener extends AssertJSwingJUnitTestCase {

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

    // --- BUTTON PARSE LISTENER ---

    @Ignore
    @Test
    public void parseButton() {
        window.button("parse").click();
        window.button("parse").requireDisabled();

    }

    @Ignore
    @Test
    public void parseWhileCalculation() {
        createLoop();
        window.textBox("codeEditor").setText("test.");

        window.button("parse").click();
        window.button("parse").requireDisabled();
    }

    @Ignore
    @Test
    public void parseError() {
        window.textBox("codeEditor").setText("error");
        window.button("parse").click();

        // test for open ErrorPopUp
        JOptionPaneFixture errorPopUp = window.optionPane();
        errorPopUp.requireVisible();
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
