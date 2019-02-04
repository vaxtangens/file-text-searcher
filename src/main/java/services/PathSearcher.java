package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import entities.Filter;

public class PathSearcher {

    public List<Path> searchSuitablePathes(Filter filter) throws IOException {
        List<Path> paths = new LinkedList<>();
        Files.walk(filter.getSearchDirectory()).filter(p -> p.toString().endsWith(filter.getExtension()))
                .filter(Files::isRegularFile).filter(Files::isReadable).forEach(paths::add);

        return findFilesWithSearchedContent(paths, filter.getSearchedText());
    }

    private List<Path> findFilesWithSearchedContent(List<Path> paths, String searchedText) {
        List<Path> result = new CopyOnWriteArrayList<Path>(paths);
        List<Future<?>> tasks = new CopyOnWriteArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (Path path : paths) {
            Future<?> task = executor.submit(new Runnable() {

                @Override
                public void run() {
                    File file = path.toFile();
                    if (!containsSearchedText(file, searchedText)) {
                        result.remove(path);
                    }
                }
            });
            tasks.add(task);
        }

        while (!executionIsFinished(tasks)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }

        executor.shutdown();
        return result;
    }

    private boolean containsSearchedText(File file, String searchedText) {
        try (FileInputStream inputStream = new FileInputStream(file);
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(streamReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(searchedText)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + e);
        } catch (IOException e) {
            System.out.println("Reading file has failed " + e);
        }
        return false;
    }

    private boolean executionIsFinished(List<Future<?>> tasks) {
        boolean isFinished = true;
        for (Future<?> task : tasks) {
            isFinished &= task.isDone();
        }
        return isFinished;
    }
}
