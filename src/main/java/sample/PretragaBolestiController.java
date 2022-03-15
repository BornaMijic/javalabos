package main.java.sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.hr.java.covidportal.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static main.java.hr.java.covidportal.main.Glavna.logger;

/**
 * Predstavlja klasu PretragaBolestiController u kojoj podstavljamo u table view listu
 * bolesti koju ćemo viditi u tom table view i imamo funkciju  koja služi za pretraživanje
 * bolesti u table view
 *
 * @author Borna
 */
public class PretragaBolestiController implements Initializable {

    private ObservableList<Bolest> bolesti;

    @FXML
    private TextField dioStringaKodNazivaBolesti;

    @FXML
    private TableView<Bolest> ispisBolesti;

    @FXML
    private TableColumn<Bolest,String> nazivBolestiUTablici;

    @FXML
    private TableColumn<Bolest, List<Simptom>> simptomiBolestiUTablici;

    /**
     * U navedenoj metodi podstavljamo koju vrijednost imamo u kojem stupcu u table view te podstavljamo observableArrayList
     * koju ćemo pokazivat u table view
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BazaPodataka bazaPodataka = new BazaPodataka();
        List<Bolest> bolestiBazePodataka = null ;
        logger.info("Ucitavamo podatke");
        try {
            bolestiBazePodataka = bazaPodataka.dohvatiSveBolesti();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        nazivBolestiUTablici.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        simptomiBolestiUTablici.setCellValueFactory(new PropertyValueFactory<>("simptomi"));
        List<Bolest> filtriranjeBolestiOdVirusa = bolestiBazePodataka.stream().filter(b -> b.isDaLiVirus() == false).collect(Collectors.toList());

        bolesti = FXCollections.observableArrayList(filtriranjeBolestiOdVirusa);

        ispisBolesti.setItems(bolesti);
    }

    /**
     * Navedena metoda služi da bi mogli pretraživat bolesti po odredenom stringu u table view
     */
    public void pretragaBolesti(){
        String moguciStringUNazivuBolesti = dioStringaKodNazivaBolesti.getText();
        ObservableList<Bolest> pretragaBolesti = bolesti;

        List<Bolest> nazivBolestiSOdredenimStringom  = pretragaBolesti.stream().filter(z -> z.getNaziv().toUpperCase().contains(moguciStringUNazivuBolesti.toUpperCase())).collect(Collectors.toList());

        ispisBolesti.setItems(FXCollections.observableArrayList(nazivBolestiSOdredenimStringom));

    }
}
