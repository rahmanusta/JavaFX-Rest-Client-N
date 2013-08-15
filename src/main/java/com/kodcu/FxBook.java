/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kodcu;

import java.util.Objects;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author usta
 */
public class FxBook {

   protected SimpleLongProperty id;
   protected SimpleStringProperty name;
   protected SimpleDoubleProperty price;
   protected Book book;
   
   public FxBook(Book book){
       this.book = book;
       this.id=new SimpleLongProperty(this.book.getId());
       this.name =new SimpleStringProperty(this.book.getName());
       this.price =new SimpleDoubleProperty(this.book.getPrice());
   }

    public Long getId() {
        return id.get();
    }

    public void setId(Long id) {
        this.id.set(id);
        this.book.setId(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
        this.book.setName(name);
    }

    public Double getPrice() {
        return price.get();
    }

    public void setPrice(Double price) {
        this.price.set(price);
        this.book.setPrice(price);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FxBook other = (FxBook) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        return true;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
  
}
