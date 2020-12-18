package data.analizyng;

import app.AppConfigs;
import data.filesManagement.DirectoriesCache;

import java.io.File;

public class AnalyzingManager {

    private final DirectoriesCache cache;

    private int waitingThreadsCount = 0;

    private int killedThreads = 0;

    private boolean isAnalyzingCompeted = false;

    private Runnable completedCallback;

    private final AnalyzingData data;

    public AnalyzingManager(DirectoriesCache cache, AnalyzingData data, Runnable completedCallback) {
        this.cache = cache;
        this.completedCallback = completedCallback;
        this.data = data;
    }

    public void onThreadRunningStarted() {
        synchronized (data) {
            data.setStartTimeForThread(Thread.currentThread().getName(), System.currentTimeMillis());
        }
    }

    public synchronized void onThreadRunningEnded() {
        data.setEndTimeForThread(Thread.currentThread().getName(), System.currentTimeMillis());
        killedThreads++;
        validateAnalyzingFinished();
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

    public void onNewFileSize(long size) {
        synchronized (data) {
            data.onNewFileSize(size);
        }
    }

    public void onNewFileName(String name) {
        synchronized (data) {
            data.onNewFileName(name);
        }
    }

    public void onNewInnerDirectory() {
        synchronized (data) {
            data.onNewInnerDirectory();
        }
    }

    private void processAnalyzingFinish() {
        completedCallback.run();
    }

    private boolean hasSleepingThreads() {
        return waitingThreadsCount > 0;
    }

    private void validateAnalyzingFinished() {
        if (killedThreads == AppConfigs.THREADS_COUNT) {
            processAnalyzingFinish();
        }
    }
}
