package services;

import java.io.File;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import entities.FileNode;

public class TreeNodeParser {

    public static File parseToFile(JTree tree) {
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree.getModel().getRoot();
        FileNode fileNode = (FileNode) rootNode.getUserObject();
        File file = fileNode.getFile();

        String prefix = file.getAbsolutePath().toString();
        String suffix = modifyTreePath(tree.getSelectionPath());
        return new File(prefix + suffix);
    }

    private static String modifyTreePath(TreePath treePath) {
        String[] separatedNodes = treePath.toString().split(", ");

        StringBuilder allNodesExceptRoot = new StringBuilder();
        for (int i = 1; i < separatedNodes.length; i++) {
            allNodesExceptRoot.append(separatedNodes[i]).append('\\');
        }

        StringBuilder lastBracketRemovedFirstBackSlashAdded = allNodesExceptRoot.reverse().replace(0, 2, "")
                .append('\\').reverse();

        String suffix = lastBracketRemovedFirstBackSlashAdded.toString();
        return suffix;
    }

}
