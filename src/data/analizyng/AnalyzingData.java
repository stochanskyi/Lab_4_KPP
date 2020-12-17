package data.analizyng;

import app.AppConfigs;

import java.util.regex.Pattern;

public class AnalyzingData {

    private int filesSize = 0;
    private int filesMatchingPattern = 0;
    private int innerDirectories = 0;

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
                "Inner directories: " + innerDirectories + "\n";
    }
}
