package main.java.hr.java.covidportal.model;

/**
 * Predstavlja sučelje Zarazno koje sadrži metodu prelazakZarazeNaOsobu
 *
 * @author Borna
 */

public interface Zarazno {
    /**
     * radi se o metodi podstavlja određen virus
     * na atribut zarazenBolescu od klase Osoba
     *
     * @param osoba instanca klase Osoba
     */
    public void prelazakZarazeNaOsobu(Osoba osoba);
}
