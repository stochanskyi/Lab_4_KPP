package data.analizyng;

import app.AppConfigs;
import data.execution.ThreadRunningData;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class AnalyzingData {

    private int filesSize = 0;
    private int filesMatchingPattern = 0;
    private int innerDirectories = 0;

    private final Map<String, ThreadRunningData> threadRunningDataMap = new HashMap<>();

    public void addThreadName(String name) {
        threadRunningDataMap.put(name, new ThreadRunningData());
    }

    public void setStartTimeForThread(String name, long startTime) {
        threadRunningDataMap.get(name).setStartTime(startTime);
    }

    public void setEndTimeForThread(String name, long endTime) {
        threadRunningDataMap.get(name).setEndTime(endTime);
    }

    public void onNewFileSize(long size) {
        if (validateNewFileSize(size)) filesSize++;
    }

    private boolean validateNewFileSize(long size) {
        return size > AppConfigs.MIN_SIZE_TO_CALCULATE;
    }

    public void onNewFileName(String name) {
        if (validateNewFileMatchesName(name)) filesMatchingPattern++;
    }

    private boolean validateNewFileMatchesName(String name) {
        return Pattern.compile(AppConfigs.FILE_NAME_PATTERN).matcher(name).matches();
    }

    public void onNewInnerDirectory() {
        innerDirectories++;
    }

    @Override
    public String toString() {
        return "Files matching size: " + filesSize + "\n" +
                "Files matching patter " + filesMatchingPattern + "\n" +
                "Inner directories: " + innerDirectories + "\n" +
                createThreadRunningStats();
    }

    private String createThreadRunningStats() {
        StringBuilder builder = new StringBuilder();
        threadRunningDataMap.forEach((key, value) -> {
            builder.append("Thread ");
            builder.append(key);
            builder.append(" : ");
            builder.append(value.calculateRunningProcess());
            builder.append("\n");
        });
        return builder.toString();
    }
}
