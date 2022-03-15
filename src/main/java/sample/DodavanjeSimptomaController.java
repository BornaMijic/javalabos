package main.java.sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import main.java.hr.java.covidportal.enums.VrijednostSimptoma;
import main.java.hr.java.covidportal.model.Simptom;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


import static main.java.hr.java.covidportal.main.Glavna.logger;

/**
 *Navedena klasa DodavanjeSimptomaController služi da bi dodali simptom u set simptoma
 * te ispisujemo obavijest ako se simptom uspješno dodao ili nije
 * @author Borna
 */
public class DodavanjeSimptomaController implements Initializable {
    public static final String FILE_SIMPTOMI = "dat/simptomi.txt";
    @FXML
    private TextField nazivSimptomaTextField;

    @FXML
    private ListView<VrijednostSimptoma> vrijednostSimptomaListView;


    /**
     * Navedena metoda služi da bi postavili vrijednosti Simptoma u observable listu te onda to u listView
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<VrijednostSimptoma> uneseneVrijednosti = FXCollections.
                observableArrayList(VrijednostSimptoma.Jaka,VrijednostSimptoma.Produktivni,VrijednostSimptoma.Intenzivno,VrijednostSimptoma.Visoka);
        vrijednostSimptomaListView.setItems(uneseneVrijednosti);
        vrijednostSimptomaListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    /**
     * Navedena metoda služi za dodavanje simptoma
     */
    public void dodavanjeSimptoma() throws IOException, SQLException {
            logger.info("Ucitavamo podatke");
            BazaPodataka bazaPodataka = new BazaPodataka();
            String naziv = nazivSimptomaTextField.getText();
            VrijednostSimptoma dodavanjeVrijednosti = vrijednostSimptomaListView.getSelectionModel().getSelectedItem();

            if(naziv instanceof String && !naziv.isEmpty() && dodavanjeVrijednosti != null) {
                Simptom simptom = new Simptom(naziv, dodavanjeVrijednosti);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Dodavanje simptoma");
                alert.setHeaderText("Uspješno dodavanje");
                alert.setContentText("Simptom " + naziv + " uspješno dodana");
                alert.showAndWait();
                bazaPodataka.spremiNoviSimptom(simptom);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Dodavanje simptoma");
                alert.setHeaderText("Neuspješno dodavanje");
                alert.setContentText("Simptom neuspješno dodana");
                alert.showAndWait();
            }

    }
}
