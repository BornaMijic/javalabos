package main.java.sample;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Osoba;
import main.java.hr.java.covidportal.model.Zupanija;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static main.java.hr.java.covidportal.main.Glavna.logger;

/**
 * Predstavlja klasu PretragaOsobaController u kojoj podstavljamo u table view set
 * osoba koju ćemo viditi u tom table view i imamo funkciju  koja služi za pretraživanje
 * osoba u table view
 *
 * @author Borna
 */
public class PretragaOsobaController implements Initializable {
    private ObservableList<Osoba> osobe;

    @FXML
    private TextField dioStringaKodImenaOsobe;

    @FXML
    private TextField dioStringaKodPrezimenaOsobe;

    @FXML
    private TableView<Osoba> ispisOsoba;

    @FXML
    private TableColumn<Osoba,String> imeOsobeUTablici;

    @FXML
    private TableColumn<Osoba,String> prezimeOsobeUTablici;

    @FXML
    private TableColumn<Osoba, Integer> datumRodenjaUTablici;

    @FXML
    private TableColumn<Osoba,Zupanija> zupanijaOsobeUTablici;

    @FXML
    private TableColumn<Osoba, Bolest> bolestOsobeUTablici;

    @FXML
    private TableColumn<Osoba, List<Osoba>> kontaktiraneOsobeOsobeUTablici;

    /**
     * U navedenoj metodi podstavljamo koju vrijednost imamo u kojem stupcu u table view te podstavljamo observableArrayList
     * koju ćemo pokazivat u table view
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("Ucitavamo podatke");

        List<Osoba> osobeIzBazePodataka = null;

        BazaPodataka bazaPodataka = new BazaPodataka();
        try {
            osobeIzBazePodataka = bazaPodataka.dohvatiSveOsobe();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        imeOsobeUTablici.setCellValueFactory(new PropertyValueFactory<>("ime"));
        prezimeOsobeUTablici.setCellValueFactory(new PropertyValueFactory<>("prezime"));
        datumRodenjaUTablici.setCellValueFactory(new PropertyValueFactory<>("datumRodenja"));
        zupanijaOsobeUTablici.setCellValueFactory(new PropertyValueFactory<>("zupanija"));
        bolestOsobeUTablici.setCellValueFactory(new PropertyValueFactory<>("zarazenBolescu"));
        kontaktiraneOsobeOsobeUTablici.setCellValueFactory(new PropertyValueFactory<>("kontaktiraneOsobe"));
        datumRodenjaUTablici.setCellValueFactory(s -> new SimpleObjectProperty<Integer>(s.getValue().godina(s.getValue().getStarost())));
        osobe = FXCollections.observableArrayList(osobeIzBazePodataka);
        ispisOsoba.setItems(osobe);
    }

    /**
     * Navedena metoda služi da bi mogli pretraživat osobe po odredenom stringu u table view
     */
    public void pretragaOsoba(){
        String moguciStringUImenuOsobe = dioStringaKodImenaOsobe.getText();
        String moguciStringUPrezimenuOsobe = dioStringaKodPrezimenaOsobe.getText();
        ObservableList<Osoba> pretragaOsoba = osobe;

        List<Osoba> imeOsobeSOdredenimStringom  = pretragaOsoba.
                stream().
                filter(z -> z.getIme().toUpperCase().contains(moguciStringUImenuOsobe.toUpperCase()) && z.getPrezime().toUpperCase().contains(moguciStringUPrezimenuOsobe.toUpperCase())).
                collect(Collectors.toList());

        ispisOsoba.setItems(FXCollections.observableArrayList(imeOsobeSOdredenimStringom));

    }
}
