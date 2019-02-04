package entities;

import java.nio.file.Path;

public class Filter {

    private String extension;
    private String searchedText;
    private Path searchDirectory;

    public Filter(String extension, String searchedText, Path searchDirectory) {
        super();
        this.extension = extension;
        this.searchedText = searchedText;
        this.searchDirectory = searchDirectory;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getSearchedText() {
        return searchedText;
    }

    public void setSearchedText(String searchedText) {
        this.searchedText = searchedText;
    }

    public Path getSearchDirectory() {
        return searchDirectory;
    }

    public void setSearchDirectory(Path searchDirectory) {
        this.searchDirectory = searchDirectory;
    }

}
