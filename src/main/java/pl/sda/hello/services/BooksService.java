package pl.sda.hello.services;

import org.springframework.stereotype.Service;
import pl.sda.hello.logic.BooksFileHandler;
import pl.sda.hello.model.Book;
import pl.sda.hello.model.Books;

import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class BooksService {

    private BooksFileHandler booksFileHandler = new BooksFileHandler();

    public Book getBookById(String id) {
        Books books = booksFileHandler.readAll();
        return books == null ? null : booksFileHandler.readAll().get(id);
    }

    public Books getAllBooks() {
        return booksFileHandler.readAll();
    }


    public boolean addBook(Book book) {

        Books books = booksFileHandler.readAll();
        if (books == null) {
            books = new Books(new HashMap<String, Book>());
        }

        books.addBook(book);
        booksFileHandler.save(books);

        return booksFileHandler.readAll().contains(book.getId());
    }

    public boolean contains(Book book) {
        return booksFileHandler.readAll().contains(book);
    }

    public boolean contains(String id) {
        return booksFileHandler.readAll().contains(id);
    }

    public Book createBookWithError(String errorMessage) {
        Book book = new Book();
        book.setErrorMessage(errorMessage);
        return book;
    }

    public Books createBooksWithError(String errorMessage) {
        Books books = new Books(null);
        books.setErrorMessage(errorMessage);
        return books;
    }

    public Books getBookByAuthor(String author) {
        Books books = booksFileHandler.readAll();
        return books == null ?
                null :
                new Books(books
                        .getBookMap()
                        .values()
                        .stream()
                        .filter(b -> b.getAuthor()
                                .equals(author))
                        .collect(Collectors
                                .toMap(book -> book.getId(), x -> x)));
    }

    public boolean update(String id, Book newBook) {
        Books books = booksFileHandler.readAll();
        if (books == null || !books.contains(id)) {
            return false;
        }
        boolean isUpdated = books.update(id, newBook);
        booksFileHandler.save(books);
        return isUpdated;
    }

    public boolean delete(String id) {
        Books books = booksFileHandler.readAll();
        if (books == null || !books.contains(id)) {
            return false;
        }
        boolean isDeleted = books.deleteBook(id);
        System.out.println(booksFileHandler.readAll());
        booksFileHandler.save(books);
        return isDeleted;
    }
}
