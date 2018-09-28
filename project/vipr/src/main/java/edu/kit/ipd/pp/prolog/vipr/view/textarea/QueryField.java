package edu.kit.ipd.pp.prolog.vipr.view.textarea;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

import edu.kit.ipd.pp.prolog.vipr.view.Language;

/**
 * Diese Klasse ist das Eingabefeld. Hier werden Anfragen eingegeben.
 *
 */
@SuppressWarnings("serial")
public class QueryField extends JTextField {

    /**
     * Schrift für den Normale Benutzung
     */
    private Font standardFont;

    /**
     * Schrift für den Beamer Modus
     */
    private Font beamerFont;
    /**
     * Konstruktor des Code-Editors.
     */
    
    /**
     * Konstruktor des Eingabefeldes.
     */
    public QueryField(int beamerFontSize) {
        super();
        setName("query");
        setForeground(Color.GRAY);
        String currentMessage = Language.getInstance().getString("Query.text");
        setText(currentMessage);
        addFocusListener(new QueryFocusAdapter());
        setBounds(50, 725, 550, 40);
        standardFont = this.getFont();
        beamerFont = new Font(standardFont.getFontName(), standardFont.getStyle(), standardFont.getSize() + beamerFontSize);
    }

    /**
     * Setzt die Nachricht im Eingabefeld auf die gewählte Sprache.
     */
    public void updateLabels() {
        if (getForeground().equals(Color.GRAY)) {
            String currentMessage = Language.getInstance().getString("Query.text");
            setText(currentMessage);
        }
    }
    
    public void switchMode(boolean beamerMode) {
        if(beamerMode) {
        this.setFont(beamerFont);
        }
        else {
            this.setFont(standardFont);
        }
    }

    private static class QueryFocusAdapter extends FocusAdapter {

        @Override
        public void focusGained(FocusEvent e) {
            QueryField qf = (QueryField) e.getComponent();
            if (qf.getForeground().equals(Color.GRAY)) {
                qf.setForeground(Color.BLACK);
                qf.setText("");
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            QueryField qf = (QueryField) e.getComponent();
            if (qf.getText().equals("")) {
                qf.setForeground(Color.GRAY);
                String currentMessage = Language.getInstance().getString("Query.text");
                qf.setText(currentMessage);
            }
        }
    }

}
