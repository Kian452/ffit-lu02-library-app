package ch.bzz;

public class ListBooksCommand implements Command {

    private final BookRepository bookRepository;

    public ListBooksCommand(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public String getDescription() {
        return "Lists all available books";
    }

    @Override
    public void execute(String argument) {
        for (Book book : bookRepository.findAll()) {
            System.out.println(book.getTitle());
        }
    }
}
