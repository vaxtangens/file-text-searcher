package services;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

public class TextHighlighter implements TableCellRenderer {

    private String searchedText;

    public TextHighlighter(String searchedText) {
        this.searchedText = searchedText;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JTextField highlightedTextField = new JTextField();
        if (value != null) {
            highlightedTextField.setText(value.toString());
            String highlightedText = value.toString();
            if (highlightedText.contains(searchedText)) {
                int start = highlightedText.indexOf(searchedText);
                try {
                    highlightedTextField.getHighlighter().addHighlight(start, start + searchedText.length(),
                            new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN));
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        } else {
            highlightedTextField.setText("");
            highlightedTextField.getHighlighter().removeAllHighlights();
        }
        return highlightedTextField;
    }
}
