package data.analizyng;

import data.monitor.ThreadState;

public interface AnalyzingConsumer {
    void onAnalyzingUpdate(ThreadState threadState);

    void onAnalyzingCompleted(AnalyzingData analyzingData);
}
