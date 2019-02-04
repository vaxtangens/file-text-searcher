package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import services.DocumentProcessor;

public class PreviousMatchListener implements ActionListener {

    private JTextField searchedTextField;
    private DocumentProcessor documentProcessor;

    public PreviousMatchListener(JTextField searchedTextField, DocumentProcessor documentProcessor) {
        this.searchedTextField = searchedTextField;
        this.documentProcessor = documentProcessor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        documentProcessor.displayPrevious(searchedTextField.getText());
    }
}
