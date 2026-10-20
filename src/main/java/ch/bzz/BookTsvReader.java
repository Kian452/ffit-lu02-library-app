package ch.bzz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BookTsvReader {

    private static final String DELIMITER = "\t";

    public List<Book> read(String filePath) {
        List<Book> books = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line = reader.readLine(); // skip header
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.isBlank()) {
                    continue;
                }

                books.add(parseBook(line, lineNumber));
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read file: " + filePath, e);
        }

        return books;
    }

    private Book parseBook(String line, int lineNumber) {
        String[] values = line.split(DELIMITER, -1);

        try {
            return new Book(
                    Integer.parseInt(values[0].trim()),
                    values[1].trim(),
                    values[2].trim(),
                    values[3].trim(),
                    Integer.parseInt(values[4].trim()));
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid book data in line " + lineNumber + ": " + line, e);
        }
    }
}
