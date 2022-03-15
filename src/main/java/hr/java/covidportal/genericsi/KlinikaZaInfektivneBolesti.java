package main.java.hr.java.covidportal.genericsi;

import main.java.hr.java.covidportal.model.Osoba;
import main.java.hr.java.covidportal.model.Virus;

import java.util.ArrayList;
import java.util.List;

/**
 * Predstavlja parametriziranu klasu KlinikaZaInfektivneBolesti
 * koja ima parametar T koja nasljeduje Virus i parametar S koja
 * nasljeduje Osobu. Ima dvije liste jedna s parametrom T, a drugu s
 * parametor S
 *
 * @author Borna
 */
public class KlinikaZaInfektivneBolesti<T extends Virus, S extends Osoba> {
    private List<T> uneseniVirusi;
    private List<S> osobeZarazeneVirusom;

    /**
     * Inicijaliziranje dviju listi u konstruktoru parametriziranu klasu KlinikaZaInfektivneBolesti
     */
    public KlinikaZaInfektivneBolesti() {
        this.uneseniVirusi = new ArrayList<>();
        this.osobeZarazeneVirusom = new ArrayList<>();
    }

    public List<T> getUneseniVirusi() {
        return uneseniVirusi;
    }

    public List<S> getOsobeZarazeneVirusom() {
        return osobeZarazeneVirusom;
    }

    /**
     * Navedena metoda dodaje atribut virus u listu uneseniVirusi
     * @param virus atribut parametra T
     */
    public void dodavanjeVirusa(T virus){
            uneseniVirusi.add(virus);
    }

    /**
     * Navedena metoda dodaje osoba virus u listu osobeZarazeneVirusom
     * @param osoba atribut parametra S
     */
    public void dodavanjeOsobaZarazenihVirusom(S osoba){
        osobeZarazeneVirusom.add(osoba);
    }
}
