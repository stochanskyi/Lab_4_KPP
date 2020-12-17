package data.execution;

import app.AppConfigs;
import data.analizyng.AnalyzerThread;
import data.analizyng.AnalyzingConsumer;
import data.analizyng.AnalyzingData;
import data.analizyng.AnalyzingManager;
import data.filesManagement.DirectoriesCache;
import data.monitor.ThreadMonitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Executor {

    public void execute(AnalyzingConsumer consumer) {
        var analyzingData = new AnalyzingData();

        ArrayList<ThreadMonitor> monitors = new ArrayList<>();

        var manager = new AnalyzingManager(new DirectoriesCache(), analyzingData, () -> {
            monitors.forEach(ThreadMonitor::stopMonitoring);
            consumer.onAnalyzingCompleted(analyzingData);
        });


        AtomicInteger ai = new AtomicInteger(0);

        for (int i = 0; i < AppConfigs.THREADS_COUNT; ++i) {
            var thread = new AnalyzerThread(manager);
            thread.setName(String.valueOf(ai.incrementAndGet()));
            thread.start();

            var monitor = new ThreadMonitor(thread, 500, consumer::onAnalyzingUpdate);
            monitors.add(monitor);
            monitor.startMonitoring();
        }
    }
}
