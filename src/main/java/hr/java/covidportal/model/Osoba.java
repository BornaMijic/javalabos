package main.java.hr.java.covidportal.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;

import java.io.Serializable;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;

/**
 * Predstavlja klasu Osoba koja je definirana imenom, prezimenom,
 * starost osobe, zupanija u kojoj osoba se nalazi, bolest osobe te
 * podatci o osobama s  kojima je osoba bila u kontaktu
 *
 * @author Borna
 */

public class Osoba implements Serializable{


    /**
     * Predstavlja klasu Builder
     */
    public static class Builder{
        private Long id;
        private String ime;
        private String prezime;
        private LocalDate datumRodenja;
        private Zupanija zupanija;
        private Bolest zarazenBolescu;
        private List<Osoba> kontaktiraneOsobe;

        /**
         * Navedena metoza služi da bi usporedila dvije instance osobe
         * @param o putem ovoga se šalje druga instanca osobe
         * @return vrača podatak da li su osobe jednake
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Builder builder = (Builder) o;
            return Objects.equals(ime, builder.ime) &&
                    Objects.equals(prezime, builder.prezime) &&
                    Objects.equals(datumRodenja, builder.datumRodenja) &&
                    Objects.equals(zupanija, builder.zupanija) &&
                    Objects.equals(zarazenBolescu, builder.zarazenBolescu) &&
                    Objects.equals(kontaktiraneOsobe, builder.kontaktiraneOsobe);
        }

        /**
         * Naveda metoda tocnije hashcode je integer vrijednost koja je povezana sa svakim objektom
         * @return vrača hash vrijednost ulazne vrijednosti
         */
        @Override
        public int hashCode() {
            return Objects.hash(ime, prezime, datumRodenja, zupanija, zarazenBolescu, kontaktiraneOsobe);
        }

        public Builder(){ }

        public Builder postavljamId(Long id){
            this.id = id;
            return this;
        }

        /**
         *Postavljanje parametra ime na ime trenutne instance Osoba
         * @param ime podatak o imenu osobe
         * @return vraća trenutnu instancu osoba
         */
        public Builder postavljamImena(String ime){
            this.ime = ime;
            return this;
        }

        /**
         *Postavljanje parametra prezime na prezime trenutne instance Osoba
         * @param prezime podatak o prezimenu osobe
         * @return vraća trenutnu instancu osoba
         */
        public Builder postavljamPrezime(String prezime){
            this.prezime = prezime;
            return this;
        }

        /**
         *Postavljanje parametra starost na starost trenutne instance Osoba
         * @param datumRodenja podatak o starosti osobe
         * @return vraća trenutnu instancu osoba
         */
        public Builder odredujemRodendan(LocalDate datumRodenja){
            this.datumRodenja = datumRodenja;
            return this;
        }

        /**
         *Postavljanje parametra zupanija na zupaniju trenutne instance Osoba
         * @param zupanija podatak o zupaniji osobe
         * @return vraća trenutnu instancu osoba
         */
        public Builder postavljanjeZupanije(Zupanija zupanija){
            this.zupanija = zupanija;
            return this;
        }

        /**
         *Postavljanje parametra zarazenBolescu na zarazenBolescu trenutne instance Osoba
         * @param zarazenBolescu podatak o bolesti osobe
         * @return vraća trenutnu instancu osoba
         */
        public Builder postavljanjeBolesti(Bolest zarazenBolescu){
            this.zarazenBolescu = zarazenBolescu;
            return this;
        }

        /**
         *Postavljanje polja kontaktiraneOsobe na polje kontaktiraneOsobe trenutne instance Osoba
         * @param kontaktiraneOsobe podatak o kontaktiranim osobama
         * @return vraća trenutnu instancu osoba
         */
        public Builder odredivanjeKontaktiranihOsoba(List<Osoba> kontaktiraneOsobe){
            this.kontaktiraneOsobe = kontaktiraneOsobe;
            return this;
        }

        /**
         * Stvaranje instance osoba te ako je osoba zaražena
         * virusom podstavljanje tog virusa na atribut zarazenBolescu svih
         * osoba s kojima je trenutna osoba bila u kontaktu
         * @return
         */
        public Osoba build(){
            Osoba osoba = new Osoba();
            osoba.id = this.id;
            osoba.ime = this.ime;
            osoba.prezime = this.prezime;
            osoba.datumRodenja = this.datumRodenja;
            osoba.zupanija = this.zupanija;
            osoba.zarazenBolescu = this.zarazenBolescu;
            osoba.kontaktiraneOsobe = this.kontaktiraneOsobe;

            if (this.zarazenBolescu instanceof Virus virus && this.kontaktiraneOsobe != null) {
                for (int i = 0; i < this.kontaktiraneOsobe.size(); ++i) {
                    virus.prelazakZarazeNaOsobu(this.kontaktiraneOsobe.get(i));
                }
            }

            return osoba;
        }
    }

    private Long id;
    private String ime;
    private String prezime;
    private LocalDate datumRodenja;
    private Zupanija zupanija;
    private Bolest zarazenBolescu;
    private List<Osoba> kontaktiraneOsobe;


    /**
     * predstavlja privatan i prazan konstruktor osobe
     */
    private Osoba() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public LocalDate getStarost() {
        return datumRodenja;
    }

    public void setStarost(LocalDate datumRodenja) {
        this.datumRodenja= datumRodenja;
    }

    public Zupanija getZupanija() {
        return zupanija;
    }

    public void setZupanija(Zupanija zupanija) {
        this.zupanija = zupanija;
    }

    public Bolest getZarazenBolescu() {
        return zarazenBolescu;
    }

    public void setZarazenBolescu(Bolest zarazenBolescu) {
        this.zarazenBolescu = zarazenBolescu;
    }

    public List<Osoba> getKontaktiraneOsobe() {
        return kontaktiraneOsobe;
    }

    public void setKontaktiraneOsobe(List<Osoba> kontaktiraneOsobe) {
        this.kontaktiraneOsobe = kontaktiraneOsobe;
    }

    @Override
    public String toString() {
        return this.getIme() + " " + this.getPrezime();
    }

    /**
     * Navedena metoda vraća koliko neka osoba ima godina
     * @param rodenje datum kada se osoba rodila
     * @ vraća koliko je osoba stara
     */
    public int godina(LocalDate rodenje){
        LocalDate danas = LocalDate.now();
        int starostInt = Period.between(rodenje,danas).getYears();
        return starostInt;
    }
}
