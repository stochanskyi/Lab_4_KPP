package data.analizyng;

import app.AppConfigs;
import data.filesManagement.DirectoriesCache;

import java.io.File;
import java.util.ArrayList;

public class AnalyzingManager {

    private final DirectoriesCache cache;

    private int waitingThreadsCount = 0;

    private boolean isAnalyzingCompeted = false;

    public AnalyzingManager(DirectoriesCache cache) {
        this.cache = cache;
    }

    public boolean addDirectory(File dir) {
        synchronized (cache) {
            boolean isAdded = cache.addDirectory(dir);
            if (isAdded && hasSleepingThreads()) {
                cache.notify();
            }
            return isAdded;
        }
    }

    public File getNextDirectory() {
        synchronized (cache) {
            waitingThreadsCount++;

            if (cache.isEmpty() && waitingThreadsCount == AppConfigs.THREADS_COUNT) {
                isAnalyzingCompeted = true;
                cache.notifyAll();
                return null;
            }

            while (cache.isEmpty() && !isAnalyzingCompeted) {
                try {
                    cache.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            waitingThreadsCount--;

            if (isAnalyzingCompeted) return null;
            return cache.getNextDirectory();
        }
    }

    private boolean hasSleepingThreads() {
        return waitingThreadsCount > 0;
    }
}
