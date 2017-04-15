package pl.sda.hello.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Books implements Serializable {

    public Books(Map<String, Book> bookMap) {
        this.bookMap = bookMap;
    }

    private long serialVersionUID = 83877944648906l;

    private Map<String, Book> bookMap = new HashMap<>();

    @ApiModelProperty(hidden = true)
    @JsonProperty(value = "errorMessage", access = JsonProperty.Access.READ_ONLY)
    private String errorMessage;


    public String getErrorMessage() {
        return errorMessage;
    }


    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public Map<String, Book> getBookMap() {
        return bookMap;
    }


    public void setBookMap(Map<String, Book> bookMap) {
        this.bookMap = bookMap;
    }

    public String addBook(Book book) {
        book.setId(UUID.randomUUID().toString());
        bookMap.put(book.getId(), book);
        return book.getId();
    }

    public boolean deleteBook(String id) {
        try {
            bookMap.remove(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Book get(String id) {
        return bookMap.get(id);
    }

    public boolean update(String id, Book newBook) {
        try {
            bookMap.replace(id, newBook);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean contains(Book book) {
        System.out.println(bookMap.containsValue(book));
        return bookMap.containsValue(book);
    }

    public boolean contains(String id) {
        return bookMap.containsKey(id);
    }

    @Override
    public String toString() {
        return "Books{" +
                "serialVersionUID=" + serialVersionUID +
                ", bookMap=" + bookMap +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
