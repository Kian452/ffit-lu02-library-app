package ch.bzz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListBooksCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(ListBooksCommand.class);

    private final BookRepository bookRepository;

    public ListBooksCommand(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public String getDescription() {
        return "Lists all available books, optionally limited: listBooks [LIMIT]";
    }

    @Override
    public void execute(String argument) {
        int limit = Integer.MAX_VALUE;

        if (argument != null && !argument.isBlank()) {
            try {
                limit = Integer.parseInt(argument.trim());
            } catch (NumberFormatException e) {
                log.warn("Invalid limit for listBooks: '{}'", argument, e);
                System.out.println("Invalid limit '" + argument + "': please enter a whole number, e.g. listBooks 10");
                return;
            }

            if (limit < 1) {
                log.warn("Invalid limit for listBooks: {}", limit);
                System.out.println("Invalid limit '" + argument + "': the limit must be at least 1");
                return;
            }
        }

        for (Book book : bookRepository.findAll(limit)) {
            System.out.println(book.getTitle());
        }
    }
}
