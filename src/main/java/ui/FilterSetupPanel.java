package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import listeners.SearchButtonListener;
import listeners.SelectDirectoryButtonListener;

public class FilterSetupPanel extends JPanel {

    private JTextField extensionField;
    private JTextField searchedTextField;
    private JTextField searchDirectoryField;

    public JTextField getExtensionField() {
        return extensionField;
    }

    public JTextField getSearchedTextField() {
        return searchedTextField;
    }

    public JTextField getSearchDirectoryField() {
        return searchDirectoryField;
    }

    public void draw() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("File extension:"), constraints);

        constraints.gridx = 1;
        add(new JLabel("Searched text:"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        extensionField = new JTextField("log");
        add(extensionField, constraints);

        constraints.gridx = 1;
        searchedTextField = new JTextField("some", 40);
        add(searchedTextField, constraints);

        constraints.gridy = 2;
        searchDirectoryField = new JTextField("D:\\Projects\\logs repository\\A", 40);
        add(searchDirectoryField, constraints);

        constraints.gridx = 0;
        JButton selectDirectoryButton = new JButton("Select search directory");
        selectDirectoryButton.addActionListener(new SelectDirectoryButtonListener(searchDirectoryField));
        add(selectDirectoryButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridwidth = 1;
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchButtonListener(this));
        add(searchButton, constraints);
    }
}
