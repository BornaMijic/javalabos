package main.java.sample;


import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Osoba;
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.model.Zupanija;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static main.java.hr.java.covidportal.main.Glavna.logger;

/**
 * Navedena klasa Controller sadrži metode koje služe da bi prešli da sljedecu scenu na istom ekranu
 *
 * @author Borna
 */
public class Controller implements Initializable {


    /**
     *Navedena metoda služi za očitavanje scene gdje pretražujemo gradove
     *
     * @throws IOException baca se kada se ne očita scena
     */
    public void prikaziEkranPretrageZupanija() throws IOException {
        logger.info("Otvaramo screen s pretragom o zupanijama");
        Parent pretragaZupanijaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "pretragaZupanija.fxml"));
        Scene pretragaZupanijaScene = new Scene(pretragaZupanijaFrame, 600, 400);
        Main.getMainStage().setScene(pretragaZupanijaScene);

    }

    /**
     *Navedena metoda služi za očitavanje scene gdje pretražujemo simptome
     *
     * @throws IOException baca se kada se ne očita scena
     */
    public void prikaziEkranPretrageSimptoma() throws IOException {
        logger.info("Otvaramo screen s pretragom o simptomima");
        Parent pretragaSimptomaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "pretragaSimptoma.fxml"));
        Scene pretragaSimptomaScene = new Scene(pretragaSimptomaFrame, 600, 400);
        Main.getMainStage().setScene(pretragaSimptomaScene);
    }

    /**
     *Navedena metoda služi za očitavanje scene gdje pretražujemo bolesti
     *
     * @throws IOException baca se kada se ne očita scena
     */
    public void prikaziEkranPretrageBolesti() throws IOException {
        logger.info("Otvaramo screen s pretragom o bolestima");
        Parent pretragaBolestiFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "pretragaBolesti.fxml"));
        Scene pretragaBolestiScene = new Scene(pretragaBolestiFrame, 600, 400);
        Main.getMainStage().setScene(pretragaBolestiScene);
    }

    /**
     *Navedena metoda služi za očitavanje scene gdje pretražujemo viruse
     *
     * @throws IOException baca se kada se ne očita scena
     */
    public void prikaziEkranPretrageVirusa() throws IOException {
        logger.info("Otvaramo screen s pretragom o virusima");
        Parent pretragaVirusaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "pretragaVirusa.fxml"));
        Scene pretragaVirusaScene = new Scene(pretragaVirusaFrame, 600, 400);
        Main.getMainStage().setScene(pretragaVirusaScene);
    }

    /**
     *Navedena metoda služi za očitavanje scene gdje pretražujemo osobe
     *
     * @throws IOException baca se kada se ne očita scena
     */
    public void prikaziEkranPretrageOsoba() throws IOException {
        logger.info("Otvaramo screen s pretragom o osobama");
        Parent pretragaOsobaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "pretragaOsoba.fxml"));
        Scene pretragaOsobaScene = new Scene(pretragaOsobaFrame, 600, 400);
        Main.getMainStage().setScene(pretragaOsobaScene);
    }

    /**
     *Navedena metoda služi za očitavanje scene gdje dodajemo zupanije
     *
     * @throws IOException baca se kada se ne očita scena
     */
    public void dodavanjeZupanija() throws IOException {
        logger.info("Otvaramo screen s dodavanjem zupanijama");
        Parent dodavanjeZupanijaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "dodavanjeZupanija.fxml"));
        Scene dodavanjeZupanijaScene = new Scene(dodavanjeZupanijaFrame, 600, 400);
        Main.getMainStage().setScene(dodavanjeZupanijaScene);
    }

    /**
     *Navedena metoda služi za očitavanje scene gdje dodajemo simptome
     *
     * @throws IOException baca se kada se ne očita scena
     */
    public void dodavanjeSimptom() throws IOException {
        logger.info("Otvaramo screen s dodavanjem simptoma");
        Parent dodavanjeSimptomaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "dodavanjeSimptoma.fxml"));
        Scene dodavanjeSimptomaScene = new Scene(dodavanjeSimptomaFrame, 600, 400);
        Main.getMainStage().setScene(dodavanjeSimptomaScene);
    }

    /**
     *Navedena metoda služi za očitavanje scene gdje dodajemo bolesti
     *
     * @throws IOException baca se kada se ne očita scena
     */
    public void dodavanjeBolesti() throws IOException {
        logger.info("Otvaramo screen s dodavanjem bolesti");
        Parent dodavanjeBolestiFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "dodavanjeBolesti.fxml"));
        Scene dodavanjeBolestiScene = new Scene(dodavanjeBolestiFrame, 600, 400);
        Main.getMainStage().setScene(dodavanjeBolestiScene);
    }

    /**
     *Navedena metoda služi za očitavanje scene gdje dodajemo viruse
     *
     * @throws IOException baca se kada se ne očita scena
     */
    public void dodavanjeVirusa() throws IOException {
        logger.info("Otvaramo screen s dodavanjem virusa");
        Parent dodavanjeVirusaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "dodavanjeVirusa.fxml"));
        Scene dodavanjeVirusaScene = new Scene(dodavanjeVirusaFrame, 600, 400);
        Main.getMainStage().setScene(dodavanjeVirusaScene);
    }

    /**
     *Navedena metoda služi za očitavanje scene gdje dodajemo osobe
     *
     * @throws IOException baca se kada se ne očita scena
     */
    public void dodavanjeOsoba() throws IOException {
        logger.info("Otvaramo screen s dodavanjem Osoba");
        Parent dodavanjeOsobaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "dodavanjeOsoba.fxml"));
        Scene dodavanjeOsobaScene = new Scene(dodavanjeOsobaFrame, 600, 400);
        Main.getMainStage().setScene(dodavanjeOsobaScene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BazaPodataka bazaPodatak = new BazaPodataka();

    }
}