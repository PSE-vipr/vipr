package edu.kit.ipd.pp.prolog.vipr.view.textarea;

import java.awt.Color;
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
     * Konstruktor des Eingabefeldes.
     */
    public QueryField() {
        super();
        setName("query");
        setForeground(Color.GRAY);
        String currentMessage = Language.getInstance().getString("Query.text");
        setText(currentMessage);
        addFocusListener(new QueryFocusAdapter());
        setBounds(50, 725, 550, 25);
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
