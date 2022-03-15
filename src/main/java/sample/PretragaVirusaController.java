package main.java.sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Simptom;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static main.java.hr.java.covidportal.main.Glavna.logger;

/**
 * Predstavlja klasu PretragaVirusaController u kojoj podstavljamo u table view set
 * virusa koju ćemo viditi u tom table view i imamo funkciju  koja služi za pretraživanje
 * virusa u table view
 *
 * @author Borna
 */
public class PretragaVirusaController implements Initializable {
    private ObservableList<Bolest> virusi;

    @FXML
    private TextField dioStringaKodNazivaVirusa;

    @FXML
    private TableView<Bolest> ispisVirusa;

    @FXML
    private TableColumn<Bolest,String> nazivVirusaUTablici;

    @FXML
    private TableColumn<Bolest, List<Simptom>> simptomiVirusaUTablici;

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
        List<Bolest> virusiBazePodataka = null ;
        try {
            virusiBazePodataka = bazaPodataka.dohvatiSveBolesti();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        nazivVirusaUTablici.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        simptomiVirusaUTablici.setCellValueFactory(new PropertyValueFactory<>("simptomi"));
        List<Bolest> filtriranjeVirusaOdBolesti= virusiBazePodataka.stream().filter(b -> b.isDaLiVirus() == true).collect(Collectors.toList());

        virusi = FXCollections.observableArrayList(filtriranjeVirusaOdBolesti);

        ispisVirusa.setItems(virusi);
    }

    /**
     * Navedena metoda služi da bi mogli pretraživat viruse po odredenom stringu u table view
     */
    public void pretragaVirusa(){
        String moguciStringUNazivuVirusa = dioStringaKodNazivaVirusa.getText();
        ObservableList<Bolest> pretragaVirusa = virusi;

        List<Bolest> nazivVirusaSOdredenimStringom  = pretragaVirusa.stream().filter(z -> z.getNaziv().toUpperCase().contains(moguciStringUNazivuVirusa.toUpperCase())).collect(Collectors.toList());

        ispisVirusa.setItems(FXCollections.observableArrayList(nazivVirusaSOdredenimStringom));

    }
}
