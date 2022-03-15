package main.java.hr.java.covidportal.enums;

/**
 * Predstavlja enum Vrijednosti koja služi da kada upisujemo vrijednost simptoma da možemo
 * ograničit na tri vrijednosti
 *
 * @author Borna
 */

public enum VrijednostSimptoma {
    Produktivni("Produktivni"), Intenzivno("Intenzivno"),Visoka("Visoka"),Jaka("Jaka");

    private String vrijednost;

    /**
     * inicijalizacija enumeracija
     *
     * @param vrijednost vrijednost simptoma
     */
    private VrijednostSimptoma(String vrijednost){
        this.vrijednost = vrijednost;
    }

    public String getVrijednost() {
        return vrijednost;
    }
}


