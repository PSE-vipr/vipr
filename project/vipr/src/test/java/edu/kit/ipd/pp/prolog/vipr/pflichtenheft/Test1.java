package edu.kit.ipd.pp.prolog.vipr.pflichtenheft;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.controller.Main;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;

/**
 * Test T1 aus dem Pflichtenheft. Startet VIPR und zeigt das Beispielprogramm.
 */
public class Test1 extends AssertJSwingJUnitTestCase {

    private static final String SEPERATOR = System.getProperty("line.separator");
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
     * Startet VIPR. Das Beispielprogramm wird angezeigt.
     */

    @Ignore
    @Test

    public void startVIPR() {
        JTextComponentFixture codeEditor = window.textBox("codeEditor");
        codeEditor.requireText("mann(grampa)." + SEPERATOR + "mann(homer)." + SEPERATOR + "mann(bart)." + SEPERATOR
                + "frau(marge)." + SEPERATOR + "frau(lisa)." + SEPERATOR + "frau(maggie)." + SEPERATOR
                + "hund(knechtruprecht)." + SEPERATOR + "katze(snowball)." + SEPERATOR + SEPERATOR
                + "vater(grampa, homer)." + SEPERATOR + "vater(homer, bart)." + SEPERATOR + "vater(homer, lisa)."
                + SEPERATOR + "vater(homer, maggie)." + SEPERATOR + "mutter(marge, bart)." + SEPERATOR
                + "mutter(marge, lisa)." + SEPERATOR + "mutter(marge, maggie)." + SEPERATOR + SEPERATOR
                + "eltern(X,Y):-" + SEPERATOR + "     vater(X,Y)." + SEPERATOR + "eltern(X,Y):-" + SEPERATOR
                + "     mutter(X,Y)." + SEPERATOR + SEPERATOR + "großeltern(X,Y):-" + SEPERATOR + "     eltern(X,Z),"
                + SEPERATOR + "     eltern(Z,Y).");
    }

}