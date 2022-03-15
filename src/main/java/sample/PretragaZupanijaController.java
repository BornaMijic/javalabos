package main.java.sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.hr.java.covidportal.main.Glavna;
import main.java.hr.java.covidportal.model.Zupanija;


import java.io.IOException;
import java.net.URL;

import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import java.util.stream.Collectors;

import static main.java.hr.java.covidportal.main.Glavna.logger;

/**
 * Predstavlja klasu PretragaZupanijaController u kojoj podstavljamo u table view sortedset
 * zupanija koju ćemo viditi u tom table view i imamo funkciju  koja služi za pretraživanje
 * zupanija u table view
 *
 * @author Borna
 */
public class PretragaZupanijaController implements Initializable {
    private ObservableList<Zupanija> zupanije;

    @FXML
    private TextField nazivZupanije;

    @FXML
    private TableView<Zupanija> ispisZupanija;

    @FXML
    private TableColumn<Zupanija,String> nazivZupanijeUTablici;

    @FXML
    private TableColumn<Zupanija,Integer> brojStanovnikaUTablici;

    @FXML
    private TableColumn<Zupanija,Integer> brojZarazenihUTablici;


    /**
     * U navedenoj metodi podstavljamo koju vrijednost imamo u kojem stupcu u table view te podstavljamo observableArrayList
     * koju ćemo pokazivat u table view
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BazaPodataka bazaPodataka = new BazaPodataka();
        List<Zupanija> bazaZupanija = null;
        logger.info("Ucitavamo podatke");
        try {
            bazaZupanija = bazaPodataka.dohvatiSveZupanije();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Ovdje");

        nazivZupanijeUTablici.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        brojStanovnikaUTablici.setCellValueFactory(new PropertyValueFactory<>("brojStanovnika"));
        brojZarazenihUTablici.setCellValueFactory(new PropertyValueFactory<>("brojZarazenih"));

        zupanije = FXCollections.observableArrayList(bazaZupanija);

        ispisZupanija.setItems(zupanije);
    }

    /**
     * Navedena metoda služi da bi mogli pretraživat zupanije po odredenom stringu u table view
     */
    public void pretragaZupanije(){
        String moguciStringUZupaniji = nazivZupanije.getText();
        ObservableList<Zupanija> pretragaZupanija = zupanije;

        List<Zupanija> zupanijeSOdredenimStringom  = pretragaZupanija.stream().filter(z -> z.getNaziv().toUpperCase().contains(moguciStringUZupaniji.toUpperCase())).collect(Collectors.toList());

        ispisZupanija.setItems(FXCollections.observableArrayList(zupanijeSOdredenimStringom));

    }
}
