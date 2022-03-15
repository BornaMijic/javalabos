package main.java.hr.java.covidportal.model;

import java.io.Serializable;
import java.util.Set;

/**
 * Predstavlja klasu Virus koja je podklasa klase Bolest te implementira
 * sučelje Zarazno. Definirana je nazivom virusa te poljem simptoma
 *
 * @author Borna
 */

public class Virus extends Bolest implements Zarazno, Serializable {

    /**
     * Inicijaliziranje podatke o nazivu virusa koja se salje konstruktoru nadklase
     * i podatke o svim simptonmima virusa
     *
     * @param naziv podatak o nazivu virusa koji se salju
     *              konstruktoru nadklase
     *
     * @param simptomi podatak o simptomima virusa koji se salju
     *                 konstruktoru nadklase
     */
    public Virus(Long id,String naziv, Set<Simptom> simptomi) {

        super(id,naziv, simptomi);
    }

    public Virus(String naziv, Set<Simptom> simptomi,boolean daLiJeVirus) {
        super(naziv, simptomi,daLiJeVirus);
    }

    public Virus(Long id, String naziv, Set<Simptom> simptomi, boolean daLiJeVirus) {
        super(id,naziv,simptomi,daLiJeVirus);
    }


    /**
     * radi se o metodi koja osobi pomoću settera podstavlja određen virus
     * na atribut zarazenBolescu
     *
     * @param osoba instanca klase Osoba
     */
    @Override
    public void prelazakZarazeNaOsobu(Osoba osoba) {
        osoba.setZarazenBolescu(this);
    }

    /**
     * Navedena metoda služi za usporedivanje dviju instanci virusa
     *
     * @param o podatak o drugom virusu
     * @return vrača boolean vrijednost usporedenih virusa
     */
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    /**
     * Naveda metoda tocnije hashcode je integer vrijednost koja je povezana sa svakim objektom
     * @return vrača hash vrijednost ulazne vrijednosti
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.getNaziv();
    }
}
