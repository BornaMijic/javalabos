package main.java.sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Simptom;



import java.io.IOException;

import java.net.URL;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import static main.java.hr.java.covidportal.main.Glavna.logger;

/**
 * Navedena klasa DodavanjeBolestiController služi da bi dodali bolest u set bolesti
 * te ispisujemo obavijest ako se bolest uspješno dodala ili nije
 *
 * @author Borna
 */
public class DodavanjeBolestiController implements Initializable {
    @FXML
    private TextField nazivBolestiTextField;

    @FXML
    private ListView<Simptom> listaSimptoma;

    /**
     * Navedena metoda služi da bi postavili simptome u obervable listu te onda to u listView
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        listaSimptoma.setItems(uneseniSimptomi);
        listaSimptoma.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    /**
     * Navedena metoda služi za dodavanje bolesti
     */
    public void dodavanjeBolesti() throws IOException, SQLException {
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
        String naziv = nazivBolestiTextField.getText();
        ObservableList<Simptom> dodavanjeSimptoma;
        dodavanjeSimptoma = listaSimptoma.getSelectionModel().getSelectedItems();
        Set<Simptom> dodaniSimptomi = new HashSet<>(dodavanjeSimptoma);
        Long broj  = bolesti.stream().count();
        Long id = broj + 1;
        System.out.println(id);

        if (naziv instanceof String && !naziv.isEmpty() && !dodaniSimptomi.isEmpty()) {
            Bolest bolest = new Bolest(id,naziv, dodaniSimptomi, false);
            bazaPodataka.spremiNovuBolest(bolest);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Dodavanje bolesti");
            alert.setHeaderText("Uspješno dodavanje");
            alert.setContentText("Bolest " + naziv + " uspješno dodana");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dodavanje bolesti");
            alert.setHeaderText("Neuspješno dodavanje");
            alert.setContentText("Bolest neuspješno dodana");
            alert.showAndWait();
        }

    }
}
