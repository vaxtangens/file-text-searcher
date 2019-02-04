package ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class DirectorySelector extends JFileChooser {

    public Path selectDirectory() {
        setDialogTitle("Select directory to search in.");
        setApproveButtonText("Select");
        setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return Paths.get(getSelectedFile().getAbsolutePath());
        } else {
            JOptionPane.showMessageDialog(this, "No directory selected");
            return Paths.get("");
        }
    }
}
