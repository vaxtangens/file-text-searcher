package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import entities.FileNode;
import entities.Filter;

public class ChildNodeCreator {

    private Filter filter;
    private DefaultMutableTreeNode root;
    private List<Path> suitablePaths;

    public ChildNodeCreator(DefaultMutableTreeNode root, Filter filter) {
        this.filter = filter;
        this.root = root;
    }

    public void createNodes() {
        findSuitablePaths();
        createChildren(filter.getSearchDirectory().toFile(), root);
    }

    private void findSuitablePaths() {
        PathSearcher searcher = new PathSearcher();
        try {
            suitablePaths = searcher.searchSuitablePathes(filter);
        } catch (IOException e) {
            System.out.println("Reading a file has failed " + e);
        }
    }

    private void createChildren(File fileRoot, DefaultMutableTreeNode node) {
        File[] files = fileRoot.listFiles();
        if (files == null)
            return;

        for (File file : files) {
            if (pathIsSuitable(file.toPath())) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(file));
                node.add(childNode);
                if (file.isDirectory()) {
                    createChildren(file, childNode);
                }
            }
        }
    }

    private boolean pathIsSuitable(Path path) {
        return Files.isDirectory(path) && containsSuitableFiles(path) || suitablePaths.contains(path);
    }

    private boolean containsSuitableFiles(Path path) {
        for (Path pathFromList : suitablePaths) {
            if (pathFromList.startsWith(path)) {
                return true;
            }
        }
        return false;
    }
}
