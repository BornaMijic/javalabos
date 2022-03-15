package main.java.hr.java.covidportal.model;

import main.java.hr.java.covidportal.enums.VrijednostSimptoma;

import java.io.Serializable;
import java.util.Objects;

/**
 * Predstavlja klasu Simptomi koja  je podklasa klasi ImenovaniEntitet, te
 * je definirana vrijednosću simptoma te nazivom simptoma
 *
 * @author Borna
 */

public class Simptom extends ImenovaniEntitet implements Serializable{
    private VrijednostSimptoma vrijednost;

    /**
     * Inicijaliziranje podatke o nazivu simptoma koja se salje konstruktoru nadklase
     * i podatke o vrijednosti simptoma
     *
     * @param naziv ime simptoma
     * @param vrijednost varijabla vrijednosti
     */
    public Simptom(Long id,String naziv, VrijednostSimptoma vrijednost) {
        super(id,naziv);
        this.vrijednost = vrijednost;
    }

    public Simptom(String naziv, VrijednostSimptoma vrijednost) {
        super(naziv);
        this.vrijednost = vrijednost;
    }

    /**
     * Navedena metoda služi za usporedivanje dviju instanci simptoma
     *
     * @param o podatak o drugom simptomu
     * @return vrača boolean vrijednost usporedenih simptoma
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Simptom simptom = (Simptom) o;
        return vrijednost == simptom.vrijednost;
    }

    /**
     * Naveda metoda tocnije hashcode je integer vrijednost koja je povezana sa svakim objektom
     * @return vrača hash vrijednost ulazne vrijednosti
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), vrijednost);
    }

    public VrijednostSimptoma getVrijednost() {
        return vrijednost;
    }

    public void setVrijednost(VrijednostSimptoma vrijednost) {
        this.vrijednost = vrijednost;
    }

    @Override
    public String toString() {
        return super.getNaziv();
    }
}

