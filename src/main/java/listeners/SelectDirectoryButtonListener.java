package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import ui.DirectorySelector;

public class SelectDirectoryButtonListener implements ActionListener {

    private JTextField targetTextField;

    public SelectDirectoryButtonListener(JTextField targetTextField) {
        this.targetTextField = targetTextField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DirectorySelector selector = new DirectorySelector();
        targetTextField.setText(selector.selectDirectory().toString());
    }

}
