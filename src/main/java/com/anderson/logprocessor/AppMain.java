package com.anderson.logprocessor;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AppMain {

    public static void main(String[] args) throws IOException {
        List<LogRow> logRows = getLogRows();

        // counter to verify if all the lines have been printed
        int count = 0;

        // we will always use only first and second positions of the array.
        // and will print values from only the firstPosition, if while firstPosition.readLine.timestamp <= secondPosition.readLine.timestamp
        // when we'll have only one file left in the array, I skip the while and print it top/down
        while (logRows.size() > 1) {
            var first = logRows.get(0);
            var next = logRows.get(1);

            while (first.compareTo(next) <= 0) {
                System.out.println(first.getCurrentLine());
                count++;
                if (first.getLineIterator().hasNext()) {
                    first.setCurrentLine(first.getLineIterator().nextLine());
                } else {
                    // if the lineIterantor doesn't contain more lines, set currentLine == null, so it will be filtered out in the next sort event below
                    first.setCurrentLine(null);
                    first.getLineIterator().close();
                    break;
                }
            }
            logRows = logRows.stream()
                    .filter(e -> e.getCurrentLine()!=null)
                    .sorted()
                    .collect(Collectors.toList());
        }

        System.out.println(logRows.get(0).getCurrentLine());
        count++;
        while (logRows.get(0).getLineIterator().hasNext()) {
            System.out.println(logRows.get(0).getLineIterator().nextLine());
            count++;
        }
        System.out.println(count);
    }

    private static List<LogRow> getLogRows() {
        var pathname = "src/main/resources/generated/logs/";
        var fileNames = Arrays.asList(Objects.requireNonNull(new File(pathname).list()));

        List<LogRow> collect = fileNames.stream().map(file -> {
            try {
                var lineIterator = FileUtils.lineIterator(new File(pathname + file), "UTF-8");
                // don't add empty files, return null that would be filtered out in the filter below
                if (lineIterator.hasNext()) {
                    var logRow = new LogRow(lineIterator, file);
                    logRow.setCurrentLine(logRow.getLineIterator().nextLine());
                    return logRow;
                }
            } catch (IOException ignored) {}
            return null;
        })
        .filter(Objects::nonNull)
        // sort them using a comparator which uses the timestamp from first line of each of the files
        .sorted()
        .collect(Collectors.toList());

        return collect;
    }
}
