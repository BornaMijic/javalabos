package main.java.sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import main.java.hr.java.covidportal.model.*;


import java.io.IOException;

import java.net.URL;
import java.sql.SQLException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static main.java.hr.java.covidportal.main.Glavna.logger;

/**
 * Navedena klasa DodavanjeOsobaController služi da bi dodali osobe u listu osoba
 * te ispisujemo obavijest ako se osoba uspješno dodala ili nije
 *
 * @author Borna
 */
public class DodavanjeOsobaController implements Initializable {
    public static final String FILE_OSOBE = "dat/osobe.txt";

    @FXML
    private TextField imeOsobeTextField;

    @FXML
    private TextField prezimeOsobeTextField;

    @FXML
    private DatePicker datumRodenjaOsobeDatePicker;

    @FXML
    private ListView<Zupanija> zupanijaOsobeListView;

    @FXML
    private ListView<Bolest> bolestOsobeListView;

    @FXML
    private ListView<Osoba> kontaktiraneOsobeOsobeListView;


    /**
     * Navedena metoda služi da bi napravili observablelistu zupanija,bolesti i osoba te ih postavili u listView, te
     * jos je stavljeno da kod list view kontaktiranih osoba da desnim klikom mozemo deselektirat ono šta smo selektirali
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("Ucitavamo podatke");
        List<Zupanija> zupanije = null;
        List<Bolest> bolesti = null;
        List<Osoba> osobe = null;
        BazaPodataka bazaPodataka = new BazaPodataka();
        try {
            zupanije = bazaPodataka.dohvatiSveZupanije();
            bolesti = bazaPodataka.dohvatiSveBolesti();
            osobe = bazaPodataka.dohvatiSveOsobe();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ObservableList<Zupanija> uneseneZupanije = FXCollections.observableArrayList(zupanije);
        zupanijaOsobeListView.setItems(uneseneZupanije);
        zupanijaOsobeListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ObservableList<Bolest> uneseneBolesti = FXCollections.observableArrayList(bolesti);
        bolestOsobeListView.setItems(uneseneBolesti);
        bolestOsobeListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ObservableList<Osoba> uneseneOsobe = FXCollections.observableArrayList(osobe);
        kontaktiraneOsobeOsobeListView.setItems(uneseneOsobe);
        kontaktiraneOsobeOsobeListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        kontaktiraneOsobeOsobeListView.setOnMousePressed(event -> {
            if (event.isSecondaryButtonDown()) {
                kontaktiraneOsobeOsobeListView.getSelectionModel().clearSelection(kontaktiraneOsobeOsobeListView.getSelectionModel().getSelectedIndex());
            }
        });
    }

    /**
     * Navedena metoda služi za dodavanje osoba
     */
    public void dodavanjeOsoba() {
        logger.info("Ucitavamo podatke");
        List<Osoba> osobe = null;
        BazaPodataka bazaPodataka = new BazaPodataka();
        try {
            osobe = bazaPodataka.dohvatiSveOsobe();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Osoba osoba = null;
            Long id = (long) (osobe.size() + 1);
            String ime = imeOsobeTextField.getText();
            String prezime = prezimeOsobeTextField.getText();
            Zupanija dodanaZupanija = zupanijaOsobeListView.getSelectionModel().getSelectedItem();
            Bolest dodanaBolest = bolestOsobeListView.getSelectionModel().getSelectedItem();
            ObservableList<Osoba> dodavanjeOsoba;
            dodavanjeOsoba = kontaktiraneOsobeOsobeListView.getSelectionModel().getSelectedItems();
            List<Osoba> dodaneOsobe = new ArrayList<>(dodavanjeOsoba);
            String stringDatumRodenja = null;
            LocalDate datumRodenja = null;
            boolean unesenDatum = false;
            LocalDate danas = LocalDate.now();
            if (datumRodenjaOsobeDatePicker.getValue() != null) {
                stringDatumRodenja = datumRodenjaOsobeDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String[] poljaDatumaRodenja = stringDatumRodenja.split("-");
                datumRodenja = LocalDate.of(Integer.parseInt(poljaDatumaRodenja[0]), Integer.parseInt(poljaDatumaRodenja[1]), Integer.parseInt(poljaDatumaRodenja[2]));
                unesenDatum = true;
                if (danas.getYear() == datumRodenja.getYear()) {
                    if (danas.getMonthValue() < datumRodenja.getMonthValue()) {
                        unesenDatum = false;
                    }
                    if (danas.getMonthValue() == datumRodenja.getMonthValue()) {
                        if (danas.getDayOfMonth() < datumRodenja.getDayOfMonth()) {
                            unesenDatum = false;
                        }
                    }
                }
            }
            if (ime instanceof String && !ime.isEmpty() && prezime instanceof String && !prezime.isEmpty() &&
                    dodanaZupanija != null && dodanaBolest != null && stringDatumRodenja != null && datumRodenja != null && unesenDatum == true &&
                    datumRodenja.getYear() <= danas.getYear()) {

                if (!dodaneOsobe.isEmpty()) {
                    osoba = new Osoba.Builder()
                            .postavljamId(id)
                            .postavljamImena(ime)
                            .postavljamPrezime(prezime)
                            .odredujemRodendan(datumRodenja)
                            .postavljanjeZupanije(dodanaZupanija)
                            .postavljanjeBolesti(dodanaBolest)
                            .odredivanjeKontaktiranihOsoba(dodaneOsobe)
                            .build();
                    osobe.add(osoba);
                } else {
                    osoba = new Osoba.Builder()
                            .postavljamId(id)
                            .postavljamImena(ime)
                            .postavljamPrezime(prezime)
                            .odredujemRodendan(datumRodenja)
                            .postavljanjeZupanije(dodanaZupanija)
                            .postavljanjeBolesti(dodanaBolest)
                            .odredivanjeKontaktiranihOsoba(null)
                            .build();
                    osobe.add(osoba);

                }
                bazaPodataka.spremiNovuOsobu(osoba);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Dodavanje osoba");
                alert.setHeaderText("Uspješno dodavanje");
                alert.setContentText("Osoba " + ime + " " + prezime + " uspješno dodana");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Dodavanje osobe");
                alert.setHeaderText("Neuspješno dodavanje");
                alert.setContentText("Osoba neuspješno dodana");
                alert.showAndWait();
            }
        } catch (IllegalArgumentException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dodavanje osobe");
            alert.setHeaderText("Neuspješno dodavanje");
            alert.setContentText("Osoba neuspješno dodana");
            alert.showAndWait();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ponovnoUcitavanjePodataka();
    }

    /**
     * Navedena metoda služi da bi deselektirali bilo koji selektirani item u list view kontaktiranih osoba
     */
    public void odznacaavanje() {
        kontaktiraneOsobeOsobeListView.getSelectionModel().clearSelection(kontaktiraneOsobeOsobeListView.getSelectionModel().getSelectedIndex());

    }

    /**
     * Navedena metoda služi da bi nakon unosa osobe da se ta osoba bez mijenjanja ekrana nalazi u list view
     * kontaktiranih osoba
     */
    public void ponovnoUcitavanjePodataka() {
        datumRodenjaOsobeDatePicker.setValue(null);
        logger.info("Ucitavamo podatke");
        logger.info("Ucitavamo podatke");
        List<Osoba> osobe = null;
        BazaPodataka bazaPodataka = new BazaPodataka();
        try {
            osobe = bazaPodataka.dohvatiSveOsobe();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            ObservableList<Osoba> uneseneOsobe = FXCollections.observableArrayList(osobe);
            kontaktiraneOsobeOsobeListView.setItems(uneseneOsobe);

        }
    }
}
