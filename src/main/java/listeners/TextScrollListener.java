package listeners;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JScrollBar;
import javax.swing.JTextField;
import services.DocumentProcessor;

public class TextScrollListener implements AdjustmentListener {

    private DocumentProcessor documentProcessor;
    private JScrollBar scrollBar;
    private JTextField searchedText;

    public TextScrollListener(DocumentProcessor documentProcessor, JScrollBar scrollBar,JTextField searchedText) {
        this.documentProcessor = documentProcessor;
        this.scrollBar = scrollBar;
        this.searchedText = searchedText;
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        int currentPosition = scrollBar.getValue() + scrollBar.getModel().getExtent();
        int min = scrollBar.getMinimum() + scrollBar.getModel().getExtent();
        int max = scrollBar.getMaximum();

        if (currentPosition == max) {
            documentProcessor.displayDownScroll(searchedText.getText());
        }

        if (currentPosition == min) {
            documentProcessor.displayUpScroll(searchedText.getText());
        }
    }
}
