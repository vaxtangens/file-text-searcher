package ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import entities.FileNode;
import entities.Filter;
import listeners.NextMatchListener;
import listeners.OpenSelectedListener;
import listeners.PreviousMatchListener;
import listeners.TextScrollListener;
import services.ChildNodeCreator;
import services.DocumentProcessor;

public class SearchResultWindow extends JFrame implements Runnable {

    private Filter filter;
    private DocumentProcessor documentProcessor;

    public SearchResultWindow(Filter filter, DocumentProcessor documentProcessor) {
        this.filter = filter;
        this.documentProcessor = documentProcessor;
    }

    @Override
    public void run() {
        draw();
    }

    private void draw() {
        setTitle("Search result");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(0, 0, dimension.width, dimension.height);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(3, 3, 3, 3);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new FileNode(filter.getSearchDirectory().toFile()));
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        JTree fileTree = new JTree(treeModel);
        fileTree.setShowsRootHandles(true);

        JTable fileContent = new JTable();
        fileContent.setShowGrid(false);
        fileContent.setIntercellSpacing(new Dimension(0, 0));
        documentProcessor.setTargetTable(fileContent);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridheight = 4;
        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 0;
        JScrollPane treePane = new JScrollPane(fileTree);
        treePane.setPreferredSize(new Dimension(dimension.width * 25 / 100, dimension.height * 8 / 10));
        add(treePane, constraints);
        ChildNodeCreator creator = new ChildNodeCreator(root, filter);
        creator.createNodes();

        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        constraints.gridx = GridBagConstraints.RELATIVE;
        add(new JLabel("Searched text:"), constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = GridBagConstraints.RELATIVE;
        JTextField searchedText = new JTextField(filter.getSearchedText());
        searchedText.setPreferredSize(new Dimension(dimension.width * 6 / 10, dimension.height * 3 / 100));
        add(searchedText, constraints);

        constraints.gridx = 2;
        constraints.gridy = GridBagConstraints.RELATIVE;
        JButton previousMatch = new JButton("previous match");
        previousMatch.addActionListener(new PreviousMatchListener(searchedText, documentProcessor));
        add(previousMatch, constraints);

        constraints.gridy = GridBagConstraints.RELATIVE;
        JButton nextMatch = new JButton("next match");
        nextMatch.addActionListener(new NextMatchListener(searchedText, documentProcessor));
        add(nextMatch, constraints);

        constraints.gridy = GridBagConstraints.RELATIVE;
        JButton openSelected = new JButton("open selected file");
        openSelected.addActionListener(new OpenSelectedListener(fileTree, documentProcessor, searchedText.getText()));
        add(openSelected, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridheight = 3;
        constraints.gridwidth = 5;
        constraints.gridx = 3;
        constraints.gridy = 1;
        JScrollPane scrollPane = new JScrollPane(fileContent);
        
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new TextScrollListener(documentProcessor, scrollPane.getVerticalScrollBar(), searchedText));
        add(scrollPane, constraints);

        setVisible(true);
    }
}
