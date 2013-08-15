package com.kodcu;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: usta
 * Date: 1/11/13
 * Time: 5:43 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement
public class BookWrapper implements Serializable {

    private List<Book> bookList;

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public BookWrapper(List<Book> bookList) {
        this.bookList = bookList;
    }

    public BookWrapper() {
    }
}
