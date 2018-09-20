package edu.kit.ipd.pp.prolog.vipr.controller.menuListener;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JComboBoxFixture;
import org.assertj.swing.fixture.JMenuItemFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.controller.Main;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;

public class TestLanguageListener extends AssertJSwingJUnitTestCase {

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
    // --- LANGUAGE LISTENER ---

    @Ignore
    @Test
    public void selectEnglish() {
        JMenuItemFixture menu = window.menuItem("menu");
        // open menu
        menu.click();
        JMenuItemFixture settings = window.menuItem("settings");
        settings.click();
        // test for open SettingsPopUp
        DialogFixture settingsPopUp = window.dialog();
        JComboBoxFixture comboBox = settingsPopUp.comboBox();
        comboBox.click();
        comboBox.selectItem("ENGLISH");
        JButtonFixture ok = settingsPopUp.button("OK");
        ok.click();
        window.button("parse").requireText("Parse");

    }

    @Ignore
    @Test
    public void selectGerman() {
        JMenuItemFixture menu = window.menuItem("menu");
        // open menu
        menu.click();
        JMenuItemFixture settings = window.menuItem("settings");
        settings.click();
        // test for open SettingsPopUp
        DialogFixture settingsPopUp = window.dialog();
        JComboBoxFixture comboBox = settingsPopUp.comboBox();
        comboBox.click();
        comboBox.selectItem("DEUTSCH");
        JButtonFixture ok = settingsPopUp.button("OK");
        ok.click();
        window.button("parse").requireText("Parsen");

    }

    @Ignore
    @Test
    public void selectDifferentLanguage() {
        String language = null;
        switch (window.button("parse").text()) {
        case "Parse":
            language = "english";
            break;
        case "Parsen":
            language = "german";
            break;

        default:
            language = null;
            break;
        }
        JMenuItemFixture menu = window.menuItem("menu");
        // open menu
        menu.click();
        JMenuItemFixture settings = window.menuItem("settings");
        settings.click();
        // test for open SettingsPopUp
        DialogFixture settingsPopUp = window.dialog();
        JComboBoxFixture comboBox = settingsPopUp.comboBox();
        comboBox.click();
        switch (language) {
        case "english":
            comboBox.selectItem("DEUTSCH");
            break;
        case "german":
            comboBox.selectItem("ENGLISH");
            break;

        default:
            break;
        }
        settingsPopUp.button("OK").click();
        switch (language) {// now different than before
        case "english":
            window.button("parse").requireText("Parsen");
            break;
        case "german":
            window.button("parse").requireText("Parse");
            break;

        default:
            break;
        }
        /*
         * //and back again -> both directions tested menu.click();
         * 
         * settings = window.menuItem("settings"); settings.click();
         * 
         * settingsPopUp = window.dialog(); comboBox = settingsPopUp.comboBox(); switch
         * (language) { case "english": comboBox.selectItem("ENGLISH"); break; case
         * "german": comboBox.selectItem("DEUTSCH"); break;
         * 
         * default: break; } settingsPopUp.button("OK").click(); switch (language)
         * {//now different than before case "english":
         * window.button("parse").requireText("Parse"); break; case "german":
         * window.button("parse").requireText("Parsen"); break;
         * 
         * default: break; }
         */

    }
}
