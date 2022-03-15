package main.java.hr.java.covidportal.iznimke;

/**
 *  predstavlja klasu BolestIstihSimptoma koja nasljeđuje RunTimeException te je
 *  ova klasa predstavlja iznimku
 *
 * @author Borna
 */
public class BolestIstihSimptoma extends RuntimeException {
    /**
     *Konstruktor koji prima poruku o pogrešci i šalje je nadklasi
     *
     * @param message poruka o pogrešci
     */
    public BolestIstihSimptoma(String message){
        super(message);
    }

    /**
     *Konstruktor koji prima podatak o uzročnoj iznimci i šalje je nadklasi
     *
     * @param cause uzročna iznimka
     */
    public BolestIstihSimptoma(Throwable cause){
        super(cause);
    }

    /**
     *Konstruktor koji prima poruku o pogrešci i o uzročnoj iznimci i šalje ih nadklasi
     *
     * @param message poruka o pogrešci
     * @param cause uzročna iznimka
     */
    public BolestIstihSimptoma(String message,Throwable cause){
        super(message, cause);
    }
}
