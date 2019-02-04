package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import entities.Filter;
import services.DocumentProcessor;
import ui.FilterSetupPanel;
import ui.SearchResultWindow;

public class SearchButtonListener implements ActionListener {

    private FilterSetupPanel sourcePanel;

    public SearchButtonListener(FilterSetupPanel sourcePanel) {
        this.sourcePanel = sourcePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Filter filter = new Filter(sourcePanel.getExtensionField().getText(),
                sourcePanel.getSearchedTextField().getText(),
                Paths.get(sourcePanel.getSearchDirectoryField().getText()));

        if (isValid(filter)) {
            SearchResultWindow searchResultWindow = new SearchResultWindow(filter, new DocumentProcessor());
            Thread filesLookUp = new Thread(searchResultWindow);
            filesLookUp.start();
        }
    }

    private boolean isValid(Filter filter) {
        if (filter.getExtension().isEmpty()) {
            JOptionPane.showMessageDialog(sourcePanel, "Extension is not selected!");
            return false;
        }
        if (filter.getSearchedText().isEmpty()) {
            JOptionPane.showMessageDialog(sourcePanel, "Searched text is not selected!");
            return false;
        }
        if (!filter.getSearchDirectory().toFile().exists()) {
            JOptionPane.showMessageDialog(sourcePanel, "Selected path does not exist!");
            return false;
        }
        if (!filter.getSearchDirectory().toFile().isDirectory()) {
            JOptionPane.showMessageDialog(sourcePanel, "Selected path is not a directory!");
            return false;
        }
        return true;
    }
}
