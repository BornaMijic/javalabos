package main.java.hr.java.covidportal.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Predstavlja klasu zupanija koja je podklasa klasi ImenovaniEntitet, te je
 * definirana nazivom Županije te brojem stanovnika županije
 *
 * @author Borna
 */

public class Zupanija extends ImenovaniEntitet implements Serializable {
    private Integer brojStanovnika;
    private Integer brojZarazenih;

    /**
     * Inicijaliziranje podatke o nazivu županije koja se
     * šalje konstruktoru nadklase i podatke o broju stanovnika
     *
     * @param naziv podatak o nazivu županije
     * @param brojStanovnika podatak o broju stanovnika
     */
    public Zupanija(Long id, String naziv, Integer brojStanovnika, Integer brojZarazenih) {
        super(id,naziv);
        this.brojStanovnika = brojStanovnika;
        this.brojZarazenih = brojZarazenih;
    }

    public Zupanija(String naziv, Integer brojStanovnika, Integer brojZarazenih) {
        super(naziv);
        this.brojStanovnika = brojStanovnika;
        this.brojZarazenih = brojZarazenih;
    }

    /**
     * Navedena metoda služi za usporedivanje dviju instanci zupanija
     *
     * @param o podatak o drugoj zupaniji
     * @return vrača boolean vrijednost usporedenih zupanija
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Zupanija zupanija = (Zupanija) o;
        return Objects.equals(brojStanovnika, zupanija.brojStanovnika) &&
                Objects.equals(brojZarazenih, zupanija.brojZarazenih);
    }

    /**
     * Naveda metoda tocnije hashcode je integer vrijednost koja je povezana sa svakim objektom
     * @return vrača hash vrijednost ulazne vrijednosti
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), brojStanovnika, brojZarazenih);
    }

    public Double postotakZarazenih(){
        return 100*(((double)this.getBrojZarazenih())/((double)this.getBrojStanovnika()));

    }

    public Integer getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(Integer brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public Integer getBrojZarazenih() {
        return brojZarazenih;
    }

    public void setBrojZarazenih(Integer brojZarazenih) {
        this.brojZarazenih = brojZarazenih;
    }

    @Override
    public String toString() {
        return super.getNaziv();
    }
}
