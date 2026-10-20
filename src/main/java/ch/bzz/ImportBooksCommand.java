package ch.bzz;

import java.util.List;

public class ImportBooksCommand implements Command {

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

        List<Book> books = bookReader.read(argument);
        bookRepository.saveAll(books);
        System.out.println("Imported " + books.size() + " books from " + argument);
    }
}
