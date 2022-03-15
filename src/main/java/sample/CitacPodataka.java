package main.java.sample;

import main.java.hr.java.covidportal.main.Glavna;
import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Osoba;
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.model.Zupanija;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

public class CitacPodataka {
    private SortedSet<Zupanija> zupanije;
    private Set<Simptom> simptomi;
    private Set<Bolest> bolesti;
    private List<Osoba> osobe;

    public CitacPodataka() {
        this.zupanije = Glavna.postavljanjeZupanija();
        this.simptomi = Glavna.postavljanjeSimptoma();
        this.bolesti = Glavna.postavljanjeBolestiIliVirusa(simptomi);
        this.osobe = Glavna.postavljanjeOsoba(zupanije,bolesti);
    }

    public SortedSet<Zupanija> getZupanije() {
        return zupanije;
    }

    public void setZupanije(SortedSet<Zupanija> zupanije) {
        this.zupanije = zupanije;
    }

    public Set<Simptom> getSimptomi() {
        return simptomi;
    }

    public void setSimptomi(Set<Simptom> simptomi) {
        this.simptomi = simptomi;
    }

    public Set<Bolest> getBolesti() {
        return bolesti;
    }

    public void setBolesti(Set<Bolest> bolesti) {
        this.bolesti = bolesti;
    }

    public List<Osoba> getOsobe() {
        return osobe;
    }

    public void setOsobe(List<Osoba> osobe) {
        this.osobe = osobe;
    }
}
