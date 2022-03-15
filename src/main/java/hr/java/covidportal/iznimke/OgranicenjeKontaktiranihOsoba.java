package main.java.hr.java.covidportal.iznimke;

/**
 *  predstavlja klasu OgranicenjeKontaktiranihOsoba  koja nasljeđuje RunTimeException te je
 *  ova klasa predstavlja iznimku
 *
 * @author Borna
 */

public class OgranicenjeKontaktiranihOsoba extends RuntimeException {
    /**
     *Konstruktor koji prima poruku o pogrešci i šalje je nadklasi
     *
     * @param message poruka o pogrešci
     */

    public OgranicenjeKontaktiranihOsoba(String message){
        super(message);
    }

    /**
     *Konstruktor koji prima podatak o uzročnoj iznimci i šalje je nadklasi
     *
     * @param cause uzročna iznimka
     */
    public OgranicenjeKontaktiranihOsoba(Throwable cause){
        super(cause);
    }

    /**
     *Konstruktor koji prima poruku o pogrešci i o uzročnoj iznimci i šalje ih nadklasi
     *
     * @param message poruka o pogrešci
     * @param cause uzročna iznimka
     */
    public OgranicenjeKontaktiranihOsoba(String message, Throwable cause){
        super(message, cause);
    }

}
