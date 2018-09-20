package edu.kit.ipd.pp.prolog.vipr.controller.toolbarlistener;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JOptionPaneFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.controller.Main;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;

public class TestInquiryListener extends AssertJSwingJUnitTestCase {

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

    // --- INQUIRY LISTENER ---

    @Ignore
    @Test
    public void inquiry() {
        window.textBox("query").setText("test.");
        window.textBox("query").pressKey(KeyEvent.VK_ENTER);

        window.textBox("inOut").requireText(Pattern.compile("test..*", Pattern.DOTALL));

    }

    @Ignore
    @Test
    public void inquiryWhileCalculation() {
        createLoop();
        window.textBox("query").setText("test.");
        window.textBox("query").pressKey(KeyEvent.VK_ENTER);

        switch (window.button("parse").text()) {
        case "Parse":
            window.textBox("inOut").requireText(Pattern.compile(".*Parsing successful.*", Pattern.DOTALL));
            break;
        case "Parsen":
            window.textBox("inOut").requireText(Pattern.compile(".*Parsen erfolgreich.*", Pattern.DOTALL));
            break;

        default:
            break;
        }

    }

    @Ignore
    @Test
    public void inquiryError() {
        window.textBox("query").setText("error");
        window.textBox("query").pressKey(KeyEvent.VK_ENTER);

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
