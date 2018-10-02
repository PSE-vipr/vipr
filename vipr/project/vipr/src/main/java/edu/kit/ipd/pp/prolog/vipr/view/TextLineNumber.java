package edu.kit.ipd.pp.prolog.vipr.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;
import javax.swing.text.Utilities;

/**
 * Diese Klasse fügt einer Textfeld-Komponente Zeilenangaben hinzu.
 */
@SuppressWarnings("serial")
public class TextLineNumber extends JPanel implements CaretListener, DocumentListener, PropertyChangeListener {

    /**
     * Abstand Rechts.
     */
    private static final float RIGHT = 1.0f;

    private static final Border OUTER = new MatteBorder(0, 0, 0, 2, Color.GRAY);

    private static final int HEIGHT = Integer.MAX_VALUE - 1000000;

    /**
     * Die Textkomponente, zu der die Zeilenangaben gehören.
     */
    private JTextComponent component;

    // Änderbare Einstellungen
    private boolean updateFont;
    private int borderGap;
    private Color currentLineForeground;
    private float digitAlignment;
    private int minimumDisplayDigits;

    // Informationen zu vergangenen Änderungen.
    private int lastDigits;
    private int lastHeight;
    private int lastLine;

    private HashMap<String, FontMetrics> fonts;

    /**
     * Konstruktor der Klasse mit drei Stellen. Ruft den anderen Konstruktor mit
     * Argument 3 auf.
     *
     * @param component
     *            Die zugehörige Textkomponente.
     */
    public TextLineNumber(JTextComponent component) {
        this(component, 3);
    }

    /**
     * Konstruktor der Klasse.
     *
     * @param component
     *            Die zugehörige Textkomponente.
     * @param minimumDisplayDigits
     *            Anzahl Stellen, die zur Berechnung der Minimalbreite benutzt
     *            werden.
     */
    public TextLineNumber(JTextComponent component, int minimumDisplayDigits) {
        this.component = component;

        setFont(component.getFont());

        setBorderGap(5);
        setDigitAlignment(RIGHT);
        setMinimumDisplayDigits(minimumDisplayDigits);

        component.getDocument().addDocumentListener(this);
        component.addCaretListener(this);
        component.addPropertyChangeListener("font", this);
    }

    /**
     * Gibt die neue Schriftart zurück.
     *
     * @return Die neue Schriftart.
     */
    public boolean getUpdateFont() {
        return updateFont;
    }

    /**
     * Setzt die Schriftart.
     *
     * @param updateFont
     *            Bei true wird repaint aufgerufen.
     */
    public void setUpdateFont(boolean updateFont) {
        this.updateFont = updateFont;
    }

    /**
     * Gibt die Lücke bis zum Rand in Pixeln zurück.
     *
     * @return die Lücke bis zum Rand
     */
    public int getBorderGap() {
        return borderGap;
    }

    /**
     * Lücke in Pixeln zur Berechnung der Abstände.
     *
     * @param borderGap
     *            Lücke in Pixeln
     */
    public void setBorderGap(int borderGap) {
        this.borderGap = borderGap;
        Border inner = new EmptyBorder(0, borderGap, 0, borderGap);
        setBorder(new CompoundBorder(OUTER, inner));
        lastDigits = 0;
        setPreferredWidth();
    }

    /**
     * Gibt die Farbe zurück, mit der die aktive Farbe gekennzeichnet wird.
     *
     * @return die Farbe
     */
    public Color getCurrentLineForeground() {
        return currentLineForeground == null ? getForeground() : currentLineForeground;
    }

    /**
     * Setzt die Farbe, mit der die aktive Farbe gekennzeichnet wird.
     *
     * @param currentLineForeground
     *            die Farbe
     */
    public void setCurrentLineForeground(Color currentLineForeground) {
        this.currentLineForeground = currentLineForeground;
    }

    /**
     * Gibt die Zentrierung zurück.
     *
     * @return die Zentrierung.
     */
    public float getDigitAlignment() {
        return digitAlignment;
    }

    /**
     * Setzt die Zentrierung.
     * <ul>
     * Typische Zentrierungen sind:
     * <li>TextLineNumber.LEFT
     * <li>TextLineNumber.CENTER
     * <li>TextLineNumber.RIGHT (default)
     * </ul>
     * 
     * @param digitAlignment
     *            Die Zentrierung.
     */
    public void setDigitAlignment(float digitAlignment) {
        if (digitAlignment > 1.0f) {
            this.digitAlignment = 1.0f;
        } else if (digitAlignment < 0.0f) {
            this.digitAlignment = -1.0f;
        } else {
            this.digitAlignment = digitAlignment;
        }
    }

    /**
     * Gibt die minimalen Stellen zurück.
     *
     * @return die minimalen Stellen.
     */
    public int getMinimumDisplayDigits() {
        return minimumDisplayDigits;
    }

    /**
     * Setzt die Anzahl STellen, die zur Berechnung des ABstandes benutzt werden.
     *
     * @param minimumDisplayDigits
     *            Anzahl Stellen
     */
    public void setMinimumDisplayDigits(int minimumDisplayDigits) {
        this.minimumDisplayDigits = minimumDisplayDigits;
        setPreferredWidth();
    }

    /**
     * Berechnet die Breite zum Anzeigen der Zeilennummern.
     */
    private void setPreferredWidth() {
        Element root = component.getDocument().getDefaultRootElement();
        int lines = root.getElementCount();
        int digits = Math.max(String.valueOf(lines).length(), minimumDisplayDigits);

        if (lastDigits != digits) {
            lastDigits = digits;
            FontMetrics fontMetrics = getFontMetrics(getFont());
            int width = fontMetrics.charWidth('0') * digits;
            Insets insets = getInsets();
            int preferredWidth = insets.left + insets.right + width;

            Dimension d = getPreferredSize();
            d.setSize(preferredWidth, HEIGHT);
            setPreferredSize(d);
            setSize(d);
        }
    }

    /**
     * Zeichnet die Zeilennummern.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Berechnung des Platzes.
        FontMetrics fontMetrics = component.getFontMetrics(component.getFont());
        Insets insets = getInsets();
        int availableWidth = getSize().width - insets.left - insets.right;

        Rectangle clip = g.getClipBounds();
        int rowStartOffset = component.viewToModel(new Point(0, clip.y));
        int endOffset = component.viewToModel(new Point(0, clip.y + clip.height));

        while (rowStartOffset <= endOffset) {
            try {
                if (isCurrentLine(rowStartOffset))
                    g.setColor(getCurrentLineForeground());
                else
                    g.setColor(getForeground());

                // Holt die Zeilennummer und berechnet die Offsets.
                String lineNumber = getTextLineNumber(rowStartOffset);
                int stringWidth = fontMetrics.stringWidth(lineNumber);
                int x = getOffsetX(availableWidth, stringWidth) + insets.left;
                int y = getOffsetY(rowStartOffset, fontMetrics);
                g.drawString(lineNumber, x, y);

                // Nächste Zeile
                rowStartOffset = Utilities.getRowEnd(component, rowStartOffset) + 1;
            } catch (BadLocationException e) {
                // nichts zu tun
                return;
            }

        }
    }

    /**
     * Gibt zurück, ob die betrachtete Zeile die aktuelle Zeile ist.
     */
    private boolean isCurrentLine(int rowStartOffset) {
        int caretPosition = component.getCaretPosition();
        Element root = component.getDocument().getDefaultRootElement();
        return root.getElementIndex(rowStartOffset) == root.getElementIndex(caretPosition);
    }

    /**
     * Gibt die Zeile zurück, die angezeigt werden soll.
     * 
     * @param rowStartOffset
     *            Offset.
     * @return Die Zeilennummer, die angezeigt werden soll.
     */
    protected String getTextLineNumber(int rowStartOffset) {
        Element root = component.getDocument().getDefaultRootElement();
        int index = root.getElementIndex(rowStartOffset);
        Element line = root.getElement(index);

        if (line.getStartOffset() == rowStartOffset)
            return String.valueOf(index + 1);
        else
            return "";
    }

    /**
     * Berechnet X Offset.
     */
    private int getOffsetX(int availableWidth, int stringWidth) {
        return (int) ((availableWidth - stringWidth) * digitAlignment);
    }

    /**
     * Berechnet Y Offset.
     */
    private int getOffsetY(int rowStartOffset, FontMetrics fontMetrics) throws BadLocationException {

        Rectangle r = component.modelToView(rowStartOffset);
        int lineHeight = fontMetrics.getHeight();
        int y = r.y + r.height;
        int descent = 0;

        if (r.height == lineHeight) {
            descent = fontMetrics.getDescent();
        } else // Test, ob Schriftart geändert wurde.
        {
            if (fonts == null)
                fonts = new HashMap<>();

            Element root = component.getDocument().getDefaultRootElement();
            int index = root.getElementIndex(rowStartOffset);
            Element line = root.getElement(index);

            for (int i = 0; i < line.getElementCount(); i++) {
                Element child = line.getElement(i);
                AttributeSet as = child.getAttributes();
                String fontFamily = (String) as.getAttribute(StyleConstants.FontFamily);
                Integer fontSize = (Integer) as.getAttribute(StyleConstants.FontSize);
                String key = fontFamily + fontSize;

                FontMetrics fm = fonts.get(key);

                if (fm == null) {
                    Font font = new Font(fontFamily, Font.PLAIN, fontSize);
                    fm = component.getFontMetrics(font);
                    fonts.put(key, fm);
                }

                descent = Math.max(descent, fm.getDescent());
            }
        }

        return y - descent;
    }

    // Implementiert CaretListener Schnittstelle.
    @Override
    public void caretUpdate(CaretEvent e) {
        // Zeile, in der der Mauszeiger ist.
        int caretPosition = component.getCaretPosition();
        Element root = component.getDocument().getDefaultRootElement();
        int currentLine = root.getElementIndex(caretPosition);

        if (lastLine != currentLine) {
            repaint();
            lastLine = currentLine;
        }
    }

    // Implementiert DocumentListener Schnittstelle
    @Override
    public void changedUpdate(DocumentEvent e) {
        documentChanged();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        documentChanged();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        documentChanged();
    }

    /**
     * Wenn ein Document geändert wurde, müssen auch die Zeilen geändert werden.
     */
    private void documentChanged() {
        SwingUtilities.invokeLater(() -> {
            try {
                int endPos = component.getDocument().getLength();
                Rectangle rect = component.modelToView(endPos);

                if (rect != null && rect.y != lastHeight) {
                    setPreferredWidth();
                    repaint();
                    lastHeight = rect.y;
                }
            } catch (BadLocationException ex) {
                // nichts zu tun
                return;
            }
        });
    }

    // Implementiert PropertyChangeListener Schnittstelle.
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof Font) {
            if (updateFont) {
                Font newFont = (Font) evt.getNewValue();
                setFont(newFont);
                lastDigits = 0;
                setPreferredWidth();
            } else {
                repaint();
            }
        }
    }
}
