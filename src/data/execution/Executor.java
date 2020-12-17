package data.execution;

import app.AppConfigs;
import data.analizyng.AnalyzerThread;
import data.analizyng.AnalyzingManager;
import data.filesManagement.DirectoriesCache;

import java.util.concurrent.atomic.AtomicInteger;

public class Executor {

    public void execute() {
        var manager = new AnalyzingManager(new DirectoriesCache());

        AtomicInteger ai = new AtomicInteger(0);
        for (int i = 0; i < AppConfigs.THREADS_COUNT; ++i) {
            var thread = new AnalyzerThread(manager);
            thread.setName(String.valueOf(ai.incrementAndGet()));
            thread.start();
        }
    }
}
