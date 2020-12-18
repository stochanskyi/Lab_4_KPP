package data.execution;

public class ThreadRunningData {

    private long startTime;
    private long endTime;

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long calculateRunningProcess() {
        return endTime - startTime;
    }
}
