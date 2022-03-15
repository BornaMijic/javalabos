package main.java.sample;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import main.java.hr.java.covidportal.model.Zupanija;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static main.java.hr.java.covidportal.main.Glavna.logger;

/**
 *Navedena klasa DodavanjeZupanijaController služi da bi dodali zupaniju u sortedset
 * te ispisujemo obavijest ako se zupanija uspješno dodala ili nije
 * @author Borna
 */
public class DodavanjeZupanijeController implements Initializable {
    public static final String FILE_ZUPANIJA = "dat/zupanije.txt";

    @FXML
    private TextField nazivZupanijeTextField;

    @FXML
    private TextField brojStanovnikaTextField;

    @FXML
    private TextField brojZarazenihStanovnikaTextField;

    /**
     * Navedena metoda služi za dodavanje zupanija
     */
    public void dodavanjeZupanija(){
        logger.info("Ucitavamo podatke");
        BazaPodataka bazaPodataka = new BazaPodataka();
        try{
            String naziv = nazivZupanijeTextField.getText();
            Integer brojStanovnika = Integer.parseInt(brojStanovnikaTextField.getText());
            Integer brojZarazenihStanovnika = Integer.parseInt(brojZarazenihStanovnikaTextField.getText());
            if(naziv instanceof String && brojStanovnika instanceof Integer && brojZarazenihStanovnika instanceof Integer &&
                    brojStanovnika >= brojZarazenihStanovnika && !naziv.isEmpty() ) {
                Zupanija zupanija = new Zupanija(naziv, brojStanovnika,brojZarazenihStanovnika);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Dodavanje Zupanije");
                alert.setHeaderText("Uspješno dodavanje");
                alert.setContentText("Zupanija " + naziv + " uspješno dodana");
                alert.showAndWait();
                bazaPodataka.spremiNovuZupaniju(zupanija);

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Dodavanje Zupanije");
                alert.setHeaderText("Neuspješno dodavanje");
                alert.setContentText("Zupanija neuspješno dodana");
                alert.showAndWait();
            }
        } catch (IllegalArgumentException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dodavanje Zupanije");
            alert.setHeaderText("Neuspješno dodavanje");
            alert.setContentText("Zupanija neuspješno dodana");
            alert.showAndWait();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
