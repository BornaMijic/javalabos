package main.java.hr.java.covidportal.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * Predstavlja klasu Bolest koja je podklasa klasi ImenovaniEntitet te
 * nadklasa klasi Virus te je definirama poljem simptoma te nazivom bolesti
 *
 * @author Borna
 */

public class Bolest extends ImenovaniEntitet implements Serializable {
    private Set<Simptom> simptomi;
    private boolean daLiVirus;

    /**
     * Inicijaliziranje podatke o nazivu bolesti koja se salje konstruktoru nadklase
     * i podatke o simptomima bolesti
     *
     * @param naziv podatak o nazivu bolesti
     * @param simptomi podatak o svim simptomima bolesti
     */

    public Bolest(String naziv, Set<Simptom> simptomi,boolean daLiVirus) {
        super(naziv);
        this.simptomi = simptomi;
        this.daLiVirus = daLiVirus;

    }

    public Bolest(Long id,String naziv, Set<Simptom> simptomi,boolean daLiVirus) {
        super(id,naziv);
        this.simptomi = simptomi;
        this.daLiVirus = daLiVirus;

    }

    public boolean isDaLiVirus() {
        return daLiVirus;
    }

    public void setDaLiVirus(boolean daLiVirus) {
        this.daLiVirus = daLiVirus;
    }

    public Bolest(Long id, String naziv, Set<Simptom> simptomi) {
        super(id,naziv);
        this.simptomi = simptomi;
        this.daLiVirus = daLiVirus;

    }



    /**
     * Navedena metoda služi za usporedivanje dviju instanci bolesti
     *
     * @param o podatak o drugoj bolesti
     * @return vrača boolean vrijednost usporedenih bolesti
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bolest bolest = (Bolest) o;
        return Objects.equals(simptomi, bolest.simptomi);
    }

    /**
     * Naveda metoda tocnije hashcode je integer vrijednost koja je povezana sa svakim objektom
     * @return vrača hash vrijednost ulazne vrijednosti
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), simptomi);
    }

    public Set<Simptom> getSimptomi() {
        return simptomi;
    }

    public void setSimptomi(Set<Simptom> simptomi) {
        this.simptomi = simptomi;
    }

    @Override
    public String toString() {
        return super.getNaziv();
    }
}

