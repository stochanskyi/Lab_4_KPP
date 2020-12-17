package data.filesManagement;

import app.AppConfigs;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Queue;

public class DirectoriesCache {

    public DirectoriesCache() {
        directories.add(new File(AppConfigs.ROOT_DIRECTORY));
    }

    private final Queue<File> directories = new ArrayDeque<>(AppConfigs.MAX_CACHE_SIZE);

    public boolean addDirectory(File dir) {
        if (!hasFreePlace()) return false;

        directories.add(dir);
        return true;
    }

    public File getNextDirectory() {
        return directories.poll();
    }

    public boolean isEmpty() {
        return directories.isEmpty();
    }

    private boolean hasFreePlace() {
        return directories.size() < AppConfigs.MAX_CACHE_SIZE;
    }
}
