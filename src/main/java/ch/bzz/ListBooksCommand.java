package ch.bzz;

public class ListBooksCommand implements Command {

    private static final Book BOOK_1 = new Book(1, "978-3-8362-9544-4", "Java ist auch eine Insel", "Christian Ullenboom", 2023);
    private static final Book BOOK_2 = new Book(2, "978-3-658-43573-8", "Grundkurs Java", "Dietmar Abts", 2024);

    @Override
    public String getDescription() {
        return "Lists all available books";
    }

    @Override
    public void execute() {
        System.out.println(BOOK_1.getTitle());
        System.out.println(BOOK_2.getTitle());
    }
}
