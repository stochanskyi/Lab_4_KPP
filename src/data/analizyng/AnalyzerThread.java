package data.analizyng;

import java.io.File;

public class AnalyzerThread extends Thread {

    private AnalyzingManager manager;

    public AnalyzerThread(AnalyzingManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        System.out.println("running");
        File directory = manager.getNextDirectory();
        while (directory != null) {
            processDirectory(directory);
            directory = manager.getNextDirectory();
        }
    }

    private void processDirectory(File dir) {
        var nestedEntities = dir.listFiles();
        if (nestedEntities == null) return;
        for (File entity : nestedEntities) {
            processNestedEntity(entity);
        }
    }

    private void processNestedEntity(File entity) {
        if (entity.isDirectory()) processNestedDirectory(entity);
        else processFile(entity);
    }


    private void processNestedDirectory(File dir) {
        manager.onNewInnerDirectory();

        var isAdded = manager.addDirectory(dir);
        if (isAdded) return;

        processDirectory(dir);
    }

    private void processFile(File file) {
        manager.onNewFileSize(file.length());
        manager.onNewFileName(file.getName());
    }
}
