package main.java.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.hr.java.covidportal.main.Glavna;
import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Osoba;
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.model.Zupanija;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import static main.java.hr.java.covidportal.main.Glavna.logger;

/**
 * Predstavlja Main klasu u kojoj postavljamo scenu, naslov aplikacije te
 * loadom ekran, te imamo static setove zupanija,simptomi, bolesti i static listu osobe
 * u koju upisujemo vrijednosti iz datoteka
 *
 * @author Borna
 */
public class Main extends Application {

    private static Stage mainStage;
    /**
     * Predstavlja metodu u kojoj očitavamo scenu
     *
     * @param primaryStage stage kreiran od strane JavaFX
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("pocetniEkran.fxml"));
        primaryStage.setTitle("Aplikacija");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage newStage) {
        mainStage = newStage;
    }

    public static void setNewScene(Scene scene) {
        mainStage.setScene(scene);
    }


    /**
     * Navedena metoda služi da bi očitali statične varijable setova i lista iz datoteka
     * @param args argumenti koje mozemo poslati u mainu
     */
    public static void main(String[] args) throws IOException, SQLException {
        launch(args);
    }
}
