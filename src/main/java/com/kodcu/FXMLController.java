/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kodcu;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author usta
 */
public class FXMLController implements Initializable {

    @FXML
    private TableView bookTable;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn priceColumn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;

    private List<Book> kitaplar = null;
    private List<FxBook> fxKitaplar = null;
    private ObservableList<FxBook> kitapListesi = null;
    private final static String REST_ROOT_URL = "http://localhost:8080/rest/";
    private final Client client = ClientBuilder.newClient();

    @FXML
    private void remove(ActionEvent event) {

        FxBook fxBook = (FxBook) bookTable.getSelectionModel().getSelectedItem();
        kitapListesi.remove(fxBook);

        Response response = client.target(REST_ROOT_URL).
                path("publisher").
                path("book").
                path(String.valueOf(fxBook.getId())).
                request().delete();
    }

    @FXML
    private void save(ActionEvent event) {
        Book book = new Book();
        book.setName(nameField.getText());
        book.setPrice(Double.parseDouble(priceField.getText()));

        Entity<Book> kitapEntity = Entity.entity(book, MediaType.APPLICATION_XML);
        Response response = client.target(REST_ROOT_URL).
                path("publisher").
                path("book").
                request().
                post(kitapEntity);

        list(null);
        nameField.setText("");
        priceField.setText("");
    }

    @FXML
    private void list(ActionEvent event) {

        Response response = client.target("http://localhost:8080/rest/").
                path("publisher").
                path("books").
                request(MediaType.APPLICATION_XML).
                get();
        BookWrapper bookWrapper = response.readEntity(BookWrapper.class);



        kitaplar = (bookWrapper.getBookList()==null)?new ArrayList<Book>(): bookWrapper.getBookList();

        fxKitaplar = new ArrayList<>();

        for (Book k : kitaplar) {
            fxKitaplar.add(new FxBook(k));
        }

        kitapListesi = FXCollections.observableList(fxKitaplar);
        bookTable.setItems(kitapListesi);

    }

    public void kitapDuzenle(Book book) {

        Entity<Book> kitapEntity = Entity.entity(book, MediaType.APPLICATION_XML);
        Response response = client.target(REST_ROOT_URL).
                path("publisher").
                path("book").
                request().
                put(kitapEntity);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookTable.setEditable(true);

        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<FxBook, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<FxBook, String> t) {
                        ObservableList<FxBook> kitapListesi = t.getTableView().getItems();
                        FxBook kitap = kitapListesi.get(t.getTablePosition().getRow());
                        kitap.setName(t.getNewValue());
                        kitapDuzenle(kitap.getBook());
                    }
                }
                );
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleToStringConverter()));
        priceColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<FxBook, Double>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<FxBook, Double> t) {
                        ObservableList<FxBook> kitapListesi = t.getTableView().getItems();
                        FxBook kitap = kitapListesi.get(t.getTablePosition().getRow());
                        kitap.setPrice(t.getNewValue());
                        kitapDuzenle(kitap.getBook());
                    }
                }
                );

        idColumn.setCellValueFactory(
                new PropertyValueFactory<FxBook, Long>("id"));
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<FxBook, String>("name"));
        priceColumn.setCellValueFactory(
                new PropertyValueFactory<FxBook, Double>("price"));

        list(null);

    }
}
