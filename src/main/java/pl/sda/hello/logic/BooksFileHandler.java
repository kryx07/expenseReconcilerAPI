package pl.sda.hello.logic;

import org.apache.log4j.Logger;
import pl.sda.hello.model.Books;

import java.io.*;

/**
 * Created by RENT on 2017-04-04.
 */

public class BooksFileHandler {

    public BooksFileHandler() {
        this.booksFile = "books.o";
    }

    final static Logger logger = Logger.getLogger(BooksFileHandler.class);

    private String booksFile = "books.o";

    public void save(Books books) {

        try (FileOutputStream fileOutputStream = new FileOutputStream(booksFile);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);) {
            objectOutputStream.writeObject(books);
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }

        System.out.printf("Serialized data is saved in " + booksFile + System.lineSeparator());
    }

    public Books readAll() {

        Books books = null;
        try (FileInputStream fileIn = new FileInputStream(booksFile);
             ObjectInputStream in = new ObjectInputStream(fileIn);) {
            books = (Books) in.readObject();
        } catch (FileNotFoundException | NullPointerException e) {
            logger.debug(e.getMessage() + "The file doesn't exist yet - no source to readAll from.");
        } catch (IOException i) {
            logger.debug(i.getMessage());
        } catch (ClassNotFoundException c) {
            logger.debug(c.getMessage() + "Books class not found");
        }
        return books;
    }

}