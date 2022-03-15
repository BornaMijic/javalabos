package main.java.sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.hr.java.covidportal.enums.VrijednostSimptoma;
import main.java.hr.java.covidportal.model.Simptom;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static main.java.hr.java.covidportal.main.Glavna.logger;

/**
 * Predstavlja klasu PretragaSimptomaController u kojoj podstavljamo u table view set
 * simptoma koju ćemo viditi u tom table view i imamo funkciju  koja služi za pretraživanje
 * simptoma u table view
 *
 * @author Borna
 */
public class PretragaSimptomaController implements Initializable {
    private ObservableList<Simptom> simptomi;

    @FXML
    private TextField dioStringaKodSimptoma;

    @FXML
    private TableView<Simptom> ispisSimptoma;

    @FXML
    private TableColumn<Simptom,String> nazivSimptomaUTablici;

    @FXML
    private TableColumn<Simptom,VrijednostSimptoma> vrijednostSimptomaUTablici;


    /**
     * U navedenoj metodi podstavljamo koju vrijednost imamo u kojem stupcu u table view te podstavljamo observableArrayList
     * koju ćemo pokazivat u table view
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("Ucitavamo podatke");
        BazaPodataka bazaPodataka = new BazaPodataka();
        List<Simptom> bazaSimptoma = null;
        try {
            bazaSimptoma = bazaPodataka.dohvatiSveSimptome();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        nazivSimptomaUTablici.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        vrijednostSimptomaUTablici.setCellValueFactory(new PropertyValueFactory<>("vrijednost"));


        simptomi = FXCollections.observableArrayList(bazaSimptoma);

        ispisSimptoma.setItems(simptomi);
    }

    /**
     * Navedena metoda služi da bi mogli pretraživat simptome po odredenom stringu u table view
     */
    public void pretragaSimptoma(){
        String moguciStringUSimptomu = dioStringaKodSimptoma.getText();
        ObservableList<Simptom> pretragaSimptoma = simptomi;

        List<Simptom> simptomSOdredenimStringom  = pretragaSimptoma.stream().filter(z -> z.getNaziv().toUpperCase().contains(moguciStringUSimptomu.toUpperCase())).collect(Collectors.toList());

        ispisSimptoma.setItems(FXCollections.observableArrayList(simptomSOdredenimStringom));

    }
}
