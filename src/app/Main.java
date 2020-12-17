package app;

import data.analizyng.AnalyzingConsumer;
import data.analizyng.AnalyzingData;
import data.execution.Executor;
import data.monitor.ThreadState;

public class Main {
    public static void main(String[] args) {
        var consumer = new AnalyzingConsumer() {

            @Override
            public void onAnalyzingUpdate(ThreadState threadState) {
                System.out.println(threadState.toString());
            }

            @Override
            public void onAnalyzingCompleted(AnalyzingData analyzingData) {
                System.out.println(analyzingData.toString());
            }
        };
        new Executor().execute(consumer);
    }
}
