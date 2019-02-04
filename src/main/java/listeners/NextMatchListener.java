package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import services.DocumentProcessor;

public class NextMatchListener implements ActionListener {

    private JTextField searchedTextField;
    private DocumentProcessor documentProcessor;

    public NextMatchListener(JTextField searchedTextField, DocumentProcessor navigationDispatcher) {
        this.searchedTextField = searchedTextField;
        this.documentProcessor = navigationDispatcher;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        documentProcessor.displayNext(searchedTextField.getText());
    }
}
