package ch.bzz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private static final Logger log = LoggerFactory.getLogger(BookRepository.class);

    public List<Book> findAll(int limit) {
        String sql = "SELECT id, isbn, title, author, publication_year FROM books ORDER BY id LIMIT ?";
        log.debug("Executing: {} (limit={})", sql, limit);
        List<Book> books = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, limit);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    books.add(new Book(
                            resultSet.getInt("id"),
                            resultSet.getString("isbn"),
                            resultSet.getString("title"),
                            resultSet.getString("author"),
                            resultSet.getInt("publication_year")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not read books from the database", e);
        }

        return books;
    }

    public void saveAll(List<Book> books) {
        String sql = "INSERT INTO books (id, isbn, title, author, publication_year) VALUES (?, ?, ?, ?, ?) "
                + "ON CONFLICT (id) DO UPDATE SET isbn = EXCLUDED.isbn, title = EXCLUDED.title, "
                + "author = EXCLUDED.author, publication_year = EXCLUDED.publication_year";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (Book book : books) {
                statement.setInt(1, book.getId());
                statement.setString(2, book.getIsbn());
                statement.setString(3, book.getTitle());
                statement.setString(4, book.getAuthor());
                statement.setInt(5, book.getYear());
                statement.addBatch();
            }

            statement.executeBatch();
            log.debug("Saved {} books", books.size());
        } catch (SQLException e) {
            throw new RuntimeException("Could not save books to the database", e);
        }
    }
}
