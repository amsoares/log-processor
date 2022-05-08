package com.anderson.logprocessor;

import org.apache.commons.io.LineIterator;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogRow implements Comparable {
    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private final LineIterator lineIterator;
    private final String fileName;
    private String currentLine;

    public LogRow(LineIterator lineIterator, String fileName) {
        this.lineIterator = lineIterator;
        this.fileName = fileName;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        LogRow thatLogRow = (LogRow) o;
        String[] thatSplit = thatLogRow.getCurrentLine().split(",");
        LocalDateTime thatLocalDateTime = LocalDateTime.parse(thatSplit[0], DATE_FORMATTER);

        String[] thisSplit = this.currentLine.split(",");
        LocalDateTime thisLocalDateTime = LocalDateTime.parse(thisSplit[0], DATE_FORMATTER);

        return thisLocalDateTime.compareTo(thatLocalDateTime);
    }

    @Override
    public String toString() {
        return fileName;
    }

    public void setCurrentLine(String currentLine) {
        this.currentLine = currentLine;
    }

    public String getCurrentLine() {
        return currentLine;
    }

    public LineIterator getLineIterator() {
        return lineIterator;
    }
}
