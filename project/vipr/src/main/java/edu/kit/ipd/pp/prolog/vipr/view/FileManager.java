package edu.kit.ipd.pp.prolog.vipr.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Diese Klasse verwaltet das Laden und Speichern von Prolog-Programmen, sowie
 * das Exportieren der Grafik als PNG. Sie ist als Singleton entworfen, es gibt
 * also nur eine Instanz davon.
 */
@SuppressWarnings("serial")
public final class FileManager extends JFileChooser {

    /**
     * Filter, der beim Laden von Prolog-Dateien nur .pl Dateien anzeigt und
     * zulässt. Beim Speichern wird, wenn nicht vom Nutzer gescheheen, .pl als
     * Dateiendung angehängt.
     */
    private static FileNameExtensionFilter prologFilter;

    /**
     * Filter, der beim Exportieren des Graphen verwendet wird. Beim Speichern wird,
     * wenn nicht vom Nutzer gescheheen, .png als Dateiendung angehängt.
     */
    private static FileNameExtensionFilter imageFilter;

    /**
     * Die einzige Instanz der Klasse.
     */
    private static FileManager fileManager;

    /**
     * Speichert den Pfad der aktuell gewählten Datei.
     */
    private String selectedFilePath;

    /**
     * Erzeugt die einzige Instanz dieser Klasse.
     */
    private FileManager() {
    }

    /**
     * Methode zum Laden einer Prolog-Datei. Öffnet den Datei-Manager und liest die
     * gewählte Datei ein.
     * 
     * @return Der eingelesene Code.
     */
    public String loadFile() {
        fileManager.resetChoosableFileFilters();
        fileManager.setFileFilter(prologFilter);
        int returnVal = fileManager.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            selectedFilePath = fileManager.getSelectedFile().getAbsolutePath();
            if (selectedFilePath != null) {
                try {
                    return readStringFromFile(selectedFilePath, StandardCharsets.UTF_8);
                } catch (IOException e) {
                    selectedFilePath = null;
                }
            }
        }
        return null;
    }

    /**
     * Methode zum Speichern einer Prolog-Datei. Überschreibt die Datei am
     * derzeitigen Speicherort.
     * 
     * @param data
     *            Der Inhalt der Datei die gespeichert werden soll.
     * @throws IOException
     *             wenn beim Schreiben in eine Datei ein Fehler auftritt.
     */
    public void saveFile(String data) throws IOException {
        if (selectedFilePath != null) {
            File file = new File(selectedFilePath);

            try (Writer fstream = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);) {
                fstream.write(data);
            }

        } else {
            saveAsFile(data);
        }

    }

    /**
     * Methode zum Speichern einer Datei an einem neuen Speicherort. Öffnet den
     * Dateimanager.
     * 
     * @param data
     *            Der Inhalt der Datei die gespeichert werden soll.
     * @throws IOException
     *             wenn beim Schreiben in eine Datei ein Fehler auftritt.
     */
    public void saveAsFile(String data) throws IOException {
        fileManager.resetChoosableFileFilters();
        fileManager.setFileFilter(prologFilter);
        int returnVal = fileManager.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            selectedFilePath = fileManager.getSelectedFile().getAbsolutePath();
            if (selectedFilePath != null) {
                if (!selectedFilePath.endsWith(".pl")) {
                    selectedFilePath += ".pl";
                }
                File file = new File(selectedFilePath);
                try (Writer fstream = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);) {
                    fstream.write(data);
                }
            }
        }
    }

    /**
     * Methode zum Exportieren der Grafik als .png Datei.
     * 
     * @param image
     *            Die Grafik im Anzeigefeld.
     * @throws IOException
     *             wenn beim Schreiben in eine Datei ein Fehler auftritt.
     */
    public void export(BufferedImage image) throws IOException {
        fileManager.resetChoosableFileFilters();
        fileManager.setFileFilter(imageFilter);
        int returnVal = fileManager.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String exportLocation = fileManager.getSelectedFile().getAbsolutePath();
            if (exportLocation != null && image != null) {
                if (!exportLocation.toLowerCase().endsWith(".png")) {
                    exportLocation += ".png";
                }
                File file = new File(exportLocation);
                ImageIO.write(image, "PNG", file);
            }
        }
    }

    /**
     * Hilfsmethode zum Einlesen der gewählten Prolog-Datei als String.
     * 
     * @param path
     *            Pfad der Prolog-Datei.
     * @param encoding
     *            Das gewählte Charset.
     * @return den Inhalt der Datei als String
     * @throws IOException
     */
    private String readStringFromFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    /**
     * Gibt die Instanz von FileManager zurück, wenn sie schon existiert. Ansonsten
     * wird sie erstellt und dann zurückgegeben.
     * 
     * @return Die Instanz von FileManager.
     */
    public static synchronized FileManager getInstance() {
        if (fileManager == null) {
            fileManager = new FileManager();
            prologFilter = new FileNameExtensionFilter("Prolog File", "pl");
            imageFilter = new FileNameExtensionFilter("PNG", "png");
            fileManager.setAcceptAllFileFilterUsed(false);
            return fileManager;
        } else {
            return fileManager;
        }

    }

    /**
     * Setzt den Pfad auf null.
     */
    public void resetPath() {
        this.selectedFilePath = null;
    }
}
