package ch.bzz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UncheckedIOException;
import java.util.List;

public class ImportBooksCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(ImportBooksCommand.class);

    private final BookTsvReader bookReader;
    private final BookRepository bookRepository;

    public ImportBooksCommand(BookTsvReader bookReader, BookRepository bookRepository) {
        this.bookReader = bookReader;
        this.bookRepository = bookRepository;
    }

    @Override
    public String getDescription() {
        return "Imports books from a TSV file: importBooks <FILE_PATH>";
    }

    @Override
    public void execute(String argument) {
        if (argument == null || argument.isBlank()) {
            System.out.println("Please provide a file path: importBooks <FILE_PATH>");
            return;
        }

        List<Book> books;
        try {
            books = bookReader.read(argument);
        } catch (UncheckedIOException e) {
            log.error("Import file could not be read: {}", argument, e);
            System.out.println("File '" + argument + "' could not be read. Please check the path.");
            return;
        } catch (IllegalArgumentException e) {
            log.error("Import file has invalid content: {}", argument, e);
            System.out.println("File '" + argument + "' has invalid content: " + e.getMessage());
            return;
        }

        bookRepository.saveAll(books);
        log.info("Imported {} books from {}", books.size(), argument);
        System.out.println("Imported " + books.size() + " books from " + argument);
    }
}
