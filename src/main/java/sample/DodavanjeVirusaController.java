package main.java.sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.model.Virus;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import static main.java.hr.java.covidportal.main.Glavna.logger;

/**
 * Navedena klasa DodavanjeVirusaController služi da bi dodali viruse u set virusa
 * te ispisujemo obavijest ako se virus uspješno dodao ili nije
 *
 * @author Borna
 */
public class DodavanjeVirusaController implements Initializable {

    public static final String FILE_VIRUSI = "dat/virusi.txt";

    @FXML
    private TextField nazivVirusaTextField;

    @FXML
    private ListView<Simptom> listaVirusa;

    /**
     * Navedena metoda služi da bi postavili simptome u observable listu te onda to u listView
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("Ucitavamo podatke");
        logger.info("Ucitavamo podatke");
        List<Simptom> simptomi = null;
        BazaPodataka bazaPodataka = new BazaPodataka();
        try {
            simptomi = bazaPodataka.dohvatiSveSimptome();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObservableList<Simptom> uneseniSimptomi = FXCollections.observableArrayList(simptomi);
        listaVirusa.setItems(uneseniSimptomi);
        listaVirusa.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


    }

    /**
     * Navedena metoda služi za dodavanje simptoma
     */
    public void dodavanjeVirusa() throws IOException, SQLException {
        logger.info("Ucitavamo podatke");
        logger.info("Ucitavamo podatke");
        List<Bolest> bolesti = null;
        BazaPodataka bazaPodataka = new BazaPodataka();
        try {
            bolesti = bazaPodataka.dohvatiSveBolesti();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String naziv = nazivVirusaTextField.getText();
        ObservableList<Simptom> dodavanjeSimptoma;
        dodavanjeSimptoma = listaVirusa.getSelectionModel().getSelectedItems();
        Set<Simptom> dodaniSimptomi = new HashSet<>(dodavanjeSimptoma);
        Long broj = bolesti.stream().count();
        Long id = broj+1;

        if (naziv instanceof String && !naziv.isEmpty() && !dodaniSimptomi.isEmpty()) {
            Bolest virus = new Virus(id, naziv, dodaniSimptomi,true);
            bazaPodataka.spremiNovuBolest(virus);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Dodavanje virusa");
            alert.setHeaderText("Uspješno dodavanje");
            alert.setContentText("Virus " + naziv + " uspješno dodana");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dodavanje virusa");
            alert.setHeaderText("Neuspješno dodavanje");
            alert.setContentText("Virus neuspješno dodana");
            alert.showAndWait();
        }
    }

}
