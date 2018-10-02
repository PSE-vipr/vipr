package edu.kit.ipd.pp.prolog.vipr.controller;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import edu.kit.ipd.pp.prolog.vipr.controller.menulistener.MenuAboutListener;
import edu.kit.ipd.pp.prolog.vipr.controller.menulistener.MenuExportListener;
import edu.kit.ipd.pp.prolog.vipr.controller.menulistener.MenuHelpListener;
import edu.kit.ipd.pp.prolog.vipr.controller.menulistener.MenuLoadListener;
import edu.kit.ipd.pp.prolog.vipr.controller.menulistener.MenuNewListener;
import edu.kit.ipd.pp.prolog.vipr.controller.menulistener.MenuSaveAsListener;
import edu.kit.ipd.pp.prolog.vipr.controller.menulistener.MenuSaveListener;
import edu.kit.ipd.pp.prolog.vipr.controller.menulistener.MenuSettingsListener;
import edu.kit.ipd.pp.prolog.vipr.controller.menulistener.TextUpdateListener;
import edu.kit.ipd.pp.prolog.vipr.controller.toolbarlistener.ButtonAllSolutionsListener;
import edu.kit.ipd.pp.prolog.vipr.controller.toolbarlistener.ButtonNextSolutionListener;
import edu.kit.ipd.pp.prolog.vipr.controller.toolbarlistener.ButtonNextStepListener;
import edu.kit.ipd.pp.prolog.vipr.controller.toolbarlistener.ButtonParseListener;
import edu.kit.ipd.pp.prolog.vipr.controller.toolbarlistener.ButtonPauseListener;
import edu.kit.ipd.pp.prolog.vipr.controller.toolbarlistener.ButtonStepBackListener;
import edu.kit.ipd.pp.prolog.vipr.controller.toolbarlistener.InquiryListener;
import edu.kit.ipd.pp.prolog.vipr.controller.toolbarlistener.SubstitutionListener;
import edu.kit.ipd.pp.prolog.vipr.model.interpreter.Interpreter;
import edu.kit.ipd.pp.prolog.vipr.view.GUIMain;
import edu.kit.ipd.pp.prolog.vipr.view.Language;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.ToolBar;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.popups.HelpPopUp;
import edu.kit.ipd.pp.prolog.vipr.view.graphic.GraphicPane;
import edu.kit.ipd.pp.prolog.vipr.view.textarea.CodeEditor;
import edu.kit.ipd.pp.prolog.vipr.view.textarea.InOut;
import edu.kit.ipd.pp.prolog.vipr.view.textarea.QueryField;

/**
 * Diese Klasse beinhaltet alle Funktionalitäten, die das Starten von VIPR
 * regeln.
 */
public class Main {

    /**
     * Wird beim Start von VIPR aufgerufen und erstellt die grafische Oberfläche
     * sowie alle nötigen Objekte für die Laufzeit.
     * 
     * @param args
     *            Wird nicht verwendet.
     */
    public static void main(String[] args) {

        // starte die gui
        EventQueue.invokeLater(() -> { // Lambda Ausdruck

            // Einstellungen laden
            PreferenceSettings prefs = new PreferenceSettings();

            GUIMain guiMain = new GUIMain("VIPR");

            Interpreter ip = new Interpreter();
            Memento memento = new Memento();

            // im Folgenden häufig genutzte Felder:
            CodeEditor ce = guiMain.getEditor();
            GraphicPane gp = guiMain.getGraphicPane();
            ToolBar tb = guiMain.getToolBar();
            InOut io = guiMain.getInOut();
            QueryField qf = guiMain.getQueryField();

            // initialisiere die Listener und weise sie zu
            // füge gegebenenfalls Shortcuts hinzu

            TextUpdateListener textUpdateListener = new TextUpdateListener(ce, tb);
            ce.getDocument().addDocumentListener(textUpdateListener);

            // --

            MenuSaveAsListener menuSaveAsListener = new MenuSaveAsListener(ce);
            tb.getMenu().getSaveAs().addActionListener(menuSaveAsListener);

            MenuSaveListener menuSaveListener = new MenuSaveListener(ce);
            KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S,
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
            tb.getMenu().getSave().setAccelerator(ctrlS);
            tb.getMenu().getSave().addActionListener(menuSaveListener);

            MenuNewListener menuNewListener = new MenuNewListener(ip, ce, gp, tb);
            KeyStroke ctrlN = KeyStroke.getKeyStroke(KeyEvent.VK_N,
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
            tb.getMenu().getNew().setAccelerator(ctrlN);
            tb.getMenu().getNew().addActionListener(menuNewListener);

            MenuLoadListener menuLoadListener = new MenuLoadListener(ip, ce, gp, tb);
            KeyStroke ctrlO = KeyStroke.getKeyStroke(KeyEvent.VK_O,
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
            tb.getMenu().getOpen().setAccelerator(ctrlO);
            tb.getMenu().getOpen().addActionListener(menuLoadListener);

            MenuAboutListener menuAboutListener = new MenuAboutListener(guiMain);
            tb.getMenu().getAbout().addActionListener(menuAboutListener);

            MenuSettingsListener menuSettingsListener = new MenuSettingsListener(guiMain, prefs);
            tb.getMenu().getSettings().addActionListener(menuSettingsListener);

            MenuHelpListener menuHelpListener = new MenuHelpListener(guiMain);
            tb.getMenu().getHelp().addActionListener(menuHelpListener);

            MenuExportListener menuExportListener = new MenuExportListener(gp);
            tb.getMenu().getExport().addActionListener(menuExportListener);

            // --

            SubstitutionListener substitutionListener = new SubstitutionListener(ip, gp, memento, guiMain);
            tb.getBoxSubs().addActionListener(substitutionListener);

            ButtonPauseListener buttonPauseListener = new ButtonPauseListener();
            tb.getPauseButton().addActionListener(buttonPauseListener);

            ButtonParseListener buttonParseListener = new ButtonParseListener(ip, ce, gp, tb, io);
            KeyStroke ctrlP = KeyStroke.getKeyStroke(KeyEvent.VK_P,
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
            tb.getParseButton().addActionListener(buttonParseListener);
            tb.getParseButton().registerKeyboardAction(buttonParseListener, ctrlP, JComponent.WHEN_IN_FOCUSED_WINDOW);

            InquiryListener inquiryListener = new InquiryListener(ip, ce, gp, tb, io, qf, memento);
            qf.addKeyListener(inquiryListener);

            ButtonNextStepListener buttonNextStepListener = new ButtonNextStepListener(ip, ce, gp, tb, io, memento,
                    guiMain);
            KeyStroke ctrlR = KeyStroke.getKeyStroke(KeyEvent.VK_R,
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
            tb.getStepButton().addActionListener(buttonNextStepListener);
            tb.getStepButton().registerKeyboardAction(buttonNextStepListener, ctrlR, JComponent.WHEN_IN_FOCUSED_WINDOW);

            ButtonNextSolutionListener buttonNextSolutionListener = new ButtonNextSolutionListener(ip, ce, gp, tb, io,
                    memento, guiMain);
            tb.getNextButton().addActionListener(buttonNextSolutionListener);

            ButtonAllSolutionsListener buttonAllSolutionsListener = new ButtonAllSolutionsListener(ip, ce, gp, tb, io,
                    memento, guiMain);
            tb.getAllButton().addActionListener(buttonAllSolutionsListener);

            ButtonStepBackListener buttonStepBackListener = new ButtonStepBackListener(ip, ce, gp, tb, memento,
                    guiMain);
            tb.getBackButton().addActionListener(buttonStepBackListener);

            // aktiviere / deaktiviere Knöpfe
            tb.buttonsAfterLoad();

            if (prefs.getLargeFont()) {
                guiMain.changeFont();
                ce.setCaretPosition(0);
            }

            if (prefs.getShowHelp()) {
                JScrollPane sp = new JScrollPane(new HelpPopUp());
                sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                sp.setBorder(null);
                JOptionPane.showMessageDialog(guiMain, sp, Language.getInstance().getString("Help.title"),
                        JOptionPane.INFORMATION_MESSAGE);
            }
            guiMain.setVisible(true);
        });
    }

}
