package edu.kit.ipd.pp.prolog.vipr.controller.menulistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import edu.kit.ipd.pp.prolog.vipr.view.FileManager;
import edu.kit.ipd.pp.prolog.vipr.view.functionality.popups.ErrorPopUp;
import edu.kit.ipd.pp.prolog.vipr.view.textarea.CodeEditor;

/**
 * Dieser Listener ermöglicht das Abspreichern des Prolog-Programms. Erstellt
 * eine Prolog-Datei mit dem Text des Codefeldes.
 */
public class MenuSaveAsListener implements ActionListener {

    /**
     * Das Codefeld der grafischen Benutzeroberfläche. Wird zum Eingeben eines
     * Prolog-Programms verwendet.
     */
    private CodeEditor codeEditor;

    /**
     * Initialisiert den Listener mit allen nötigen Parametern.
     * 
     * @param ce
     *            Das Codefeld der grafischen Benutzeroberfläche. Wird zum Eingeben
     *            eines Prolog-Programms verwendet.
     */
    public MenuSaveAsListener(CodeEditor ce) {
        this.codeEditor = ce;
    }

    /**
     * Erstellt eine neue Prolog-Datei mit dem Text des Codefeldes.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        FileManager fm = FileManager.getInstance();
        try {
            // Zeilenumbrüche für windows ändern
            fm.saveAsFile(codeEditor.getText().replaceAll("\n", System.getProperty("line.separator")));
        } catch (IOException e1) {
            new ErrorPopUp("Error", "An error occured while saving");
        }
        // keine Köpfe ändern
    }

}
