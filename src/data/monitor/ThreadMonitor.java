package data.monitor;

import javax.security.auth.callback.Callback;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class ThreadMonitor {

    private final Thread thread;
    private final Consumer<ThreadState> consumer;
    private final int timeout;

    private boolean isKilled = false;

    public ThreadMonitor(Thread thread, int timeout, Consumer<ThreadState> consumer) {
        this.thread = thread;
        this.consumer = consumer;
        this.timeout = timeout;
    }

    public void startMonitoring() {
        var runnable = new Timer(timeout, () -> {
            consumer.accept(createThreadState());
            return null;
        });

        new Thread(runnable).start();
    }

    public void stopMonitoring() {
        isKilled = true;
    }

    private ThreadState createThreadState() {
        return new ThreadState(
                thread.getName(),
                String.valueOf(thread.getState()),
                String.valueOf(thread.getPriority()),
                String.valueOf(thread.isAlive())
        );
    }

    private class Timer implements Runnable {

        private Callable<Void> callable;
        private int timeoutMillis;

        public Timer(int timeoutMillis, Callable<Void> callable) {
            this.callable = callable;
            this.timeoutMillis = timeoutMillis;
        }

        @Override
        public void run() {
            while (true) {
                if (isKilled) return;
                try {
                    callable.call();
                    Thread.sleep(timeoutMillis);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
