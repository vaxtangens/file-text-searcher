package services;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DocumentProcessor {

    private JTable targetTable;
    private File selectedFile;
    private String currentSearchedText;
    private int cursorPosition;
    private List<Integer> suitableRowIndexes;
    private List<String> loadedText;
    private int fileLoadedFrom;
    private int fileLoadedTo;
    private int linesInDocument;

    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    public void setTargetTable(JTable targetTable) {
        this.targetTable = targetTable;
    }

    public void displayNext(String searchedText) {
        if (searchedText.equals(currentSearchedText)) {
            cursorPosition++;
            if (suitableRowIndexes.size() > cursorPosition) {
                setUpActualFilePart();
                loadFilePart();
                display();
                highlightSearchedText();
                scrollToSearchedText();
            } else {
                cursorPosition--;
                JOptionPane.showMessageDialog(targetTable, "No matches found!");
            }
        } else {
            updateForNewSearch(searchedText);
            initialDisplay();
        }
    }

    public void displayPrevious(String searchedText) {
        if (searchedText.equals(currentSearchedText)) {
            cursorPosition--;
            if (cursorPosition >= 0) {
                setUpActualFilePart();
                loadFilePart();
                display();
                highlightSearchedText();
                scrollToSearchedText();
            } else {
                cursorPosition++;
                JOptionPane.showMessageDialog(targetTable, "No matches found!");
            }
        } else {
            updateForNewSearch(searchedText);
            initialDisplay();
        }
    }

    public void displayDownScroll(String searchedText) {
        if (fileLoadedTo < linesInDocument) {
            fileLoadedFrom += 50;
            fileLoadedTo += 50;
            loadFilePart();
            display();
            currentSearchedText = searchedText;
            highlightSearchedText();
            targetTable.scrollRectToVisible(targetTable.getCellRect(99, 0, true));
        }
    }

    public void displayUpScroll(String searchedText) {
        if (fileLoadedFrom > 0) {
            fileLoadedFrom -= 50;
            fileLoadedTo -= 50;
            loadFilePart();
            display();
            currentSearchedText = searchedText;
            highlightSearchedText();
            targetTable.scrollRectToVisible(targetTable.getCellRect(99, 0, true));
        }
    }

    public void updateForNewSearch(String searchedText) {
        currentSearchedText = searchedText;
        cursorPosition = 0;
        suitableRowIndexes = new ArrayList<>(50);
        loadedText = new ArrayList<>(200);
    }

    public void initialDisplay() {
        findSearchedLineIndexes();
        setUpActualFilePart();
        loadFilePart();
        display();
        highlightSearchedText();
        scrollToSearchedText();
    }

    private void findSearchedLineIndexes() {
        try (FileInputStream inputStream = new FileInputStream(selectedFile);
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(streamReader)) {
            String line;
            int counter = 0;
            while ((line = bufferedReader.readLine()) != null) {
                counter++;
                if (line.contains(currentSearchedText)) {
                    suitableRowIndexes.add(counter);
                }
                linesInDocument = counter;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUpActualFilePart() {
        int lineNumberOfCurrentMatch = suitableRowIndexes.get(cursorPosition);
        fileLoadedFrom = lineNumberOfCurrentMatch - 100;
        fileLoadedTo = lineNumberOfCurrentMatch + 100;
    }

    private void loadFilePart() {
        loadedText.clear();
        try (FileInputStream inputStream = new FileInputStream(selectedFile);
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(streamReader)) {
            String line;
            int counter = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if (counter >= fileLoadedFrom && counter <= fileLoadedTo) {
                    loadedText.add(line);
                }
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void display() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("");
        for (String line : loadedText) {
            model.addRow(new Object[] { line });
        }
        targetTable.setModel(model);
    }
    
    private void highlightSearchedText() {
        targetTable.getColumnModel().getColumn(0).setCellRenderer(new TextHighlighter(currentSearchedText));
        targetTable.repaint();
        targetTable.setShowGrid(false);
        targetTable.setIntercellSpacing(new Dimension(0, 0));
    }

    private void scrollToSearchedText() {
        int searchedTextRowIndex = 0;
        for (String line : loadedText) {
            if (line.contains(currentSearchedText)) {
                break;
            }
            searchedTextRowIndex++;
        }
        targetTable.scrollRectToVisible(targetTable.getCellRect(searchedTextRowIndex, 0, true));
    }
}
