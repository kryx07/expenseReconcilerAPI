package pl.sda.hello.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.sda.hello.model.Book;
import pl.sda.hello.model.Books;
import pl.sda.hello.services.BooksService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;

    @Autowired
    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Books> getAllBooks() {
        return booksService.getAllBooks() == null
                ? new ResponseEntity<Books>(booksService.createBooksWithError("There are no books in the library"),
                HttpStatus.NOT_FOUND)
                : new ResponseEntity<Books>(booksService.getAllBooks(), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        return booksService.getBookById(id) == null ?
                new ResponseEntity<Book>(booksService.createBookWithError
                        ("Book with id:" + id + " has not been found!"), HttpStatus.NOT_FOUND) :
                new ResponseEntity<Book>(booksService.getBookById(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Books> getBookByAuthor(@RequestParam String author) {
        Books booksByAuthor = booksService.getBookByAuthor(author);
        return booksByAuthor == null ?
                new ResponseEntity<Books>(booksService
                        .createBooksWithError("No books of " + author + " have been found!"), HttpStatus.NOT_FOUND) :
                new ResponseEntity<Books>(booksService.getBookByAuthor(author), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Book> addBook(@RequestBody Book book) {

        booksService.addBook(book);

        URI uri = null;
        try {
            uri = new URI(ServletUriComponentsBuilder.fromCurrentRequestUri().build() + "/" + book.getId());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return booksService.contains(book.getId()) ? ResponseEntity.created(uri).build()
                : ResponseEntity.badRequest().build();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Book> updateBook(@RequestParam String id, @RequestBody Book newBook) {
        URI uri = null;
        try {
            uri = new URI(ServletUriComponentsBuilder.fromCurrentRequestUri().build() + "/" + id);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return booksService.update(id, newBook) ?
                ResponseEntity.created(uri).build() :
                ResponseEntity.badRequest().build();

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteBook(@PathVariable String id){
        return booksService.delete(id) ?
                ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();


    }
}
