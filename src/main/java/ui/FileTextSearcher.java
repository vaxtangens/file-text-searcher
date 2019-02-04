package ui;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class FileTextSearcher {
    public static void main(String args[]) throws IOException {

        JFrame jFrame = new JFrame();
        jFrame.setTitle("File Text Searcher");

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLocationByPlatform(true);

        FilterSetupPanel filterSetupPanel = new FilterSetupPanel();
        filterSetupPanel.draw();
        jFrame.add(filterSetupPanel);
        jFrame.pack();
        jFrame.setVisible(true);
        
    }
}
