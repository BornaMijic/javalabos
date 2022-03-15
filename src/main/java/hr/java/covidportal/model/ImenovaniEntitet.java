package main.java.hr.java.covidportal.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Predstavlja apstraktu klasu ImenovaniEntitet koja je
 * nadklasa klasama bolest,simptom i zupanija, te ima atribut naziv
 * je
 *
 * @author Borna
 */

public abstract class ImenovaniEntitet implements Serializable {
    private String naziv;
    private Long id;

    /**
     * Inicijalizirnje podatka o nazivu nekog entiteta
     *
     * @param naziv podatak o nazivu nekog entiteta
     */
    public ImenovaniEntitet(Long id,String naziv) {
        this.naziv = naziv;
        this.id = id;
    }
    public ImenovaniEntitet(String naziv) {
        this.naziv = naziv;
    }

    /**
     * Navedena metoda služi za usporedivanje dviju instanci imenovaniEntiteta
     *
     * @param o podatak o drugoj bolesti
     * @return vrača boolean vrijednost usporedenih bolesti
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImenovaniEntitet that = (ImenovaniEntitet) o;
        return Objects.equals(naziv, that.naziv);
    }

    /**
     * Naveda metoda tocnije hashcode je integer vrijednost koja je povezana sa svakim objektom
     * @return vrača hash vrijednost ulazne vrijednosti
     */
    @Override
    public int hashCode() {
        return Objects.hash(naziv);
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
