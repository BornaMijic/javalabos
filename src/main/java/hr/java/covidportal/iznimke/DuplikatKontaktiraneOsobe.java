package main.java.hr.java.covidportal.iznimke;

/**
 *  predstavlja klasu DuplikatKontaktiraneOsobe koja nasljeđuje Exception te je
 *  ova klasa predstavlja iznimku
 *
 * @author Borna
 */
public class DuplikatKontaktiraneOsobe extends Exception{
    /**
     *Konstruktor koji prima poruku o pogrešci i šalje je nadklasi
     *
     * @param message poruka o pogrešci
     */
    public DuplikatKontaktiraneOsobe(String message){
        super(message);
    }

    /**
     *Konstruktor koji prima podatak o uzročnoj iznimci i šalje je nadklasi
     *
     * @param cause uzročna iznimka
     */
    public DuplikatKontaktiraneOsobe(Throwable cause){
        super(cause);
    }

    /**
     *Konstruktor koji prima poruku o pogrešci i o uzročnoj iznimci i šalje ih nadklasi
     *
     * @param message poruka o pogrešci
     * @param cause uzročna iznimka
     */
    public DuplikatKontaktiraneOsobe(String message,Throwable cause){
        super(message, cause);
    }
}
