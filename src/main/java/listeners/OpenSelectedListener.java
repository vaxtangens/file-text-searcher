package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JTree;

import services.DocumentProcessor;
import services.TreeNodeParser;

public class OpenSelectedListener implements ActionListener {

    private JTree fileTree;
    private DocumentProcessor documentProcessor;
    private String searchedText;

    public OpenSelectedListener(JTree fileTree, DocumentProcessor documentProcessor, String searchedText) {
        this.fileTree = fileTree;
        this.documentProcessor = documentProcessor;
        this.searchedText = searchedText;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File selectedFile = TreeNodeParser.parseToFile(fileTree);
        documentProcessor.setSelectedFile(selectedFile);
        documentProcessor.updateForNewSearch(searchedText);
        documentProcessor.initialDisplay();
    }
}
