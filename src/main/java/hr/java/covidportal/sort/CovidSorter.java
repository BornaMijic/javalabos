package main.java.hr.java.covidportal.sort;

import main.java.hr.java.covidportal.model.Zupanija;

import java.util.Comparator;

/**
 * Predstavlja klasu CovidSorter koja implementira Comparator<Zupanija>,te
 * služo za usporedivanje županija
 *
 * @author Borna
 */

public class CovidSorter implements Comparator<Zupanija> {

    /**
     * Navedena metoda compare usporeduje dvije zupanije po postotku zarazenih osoba na broj stanovnika
     *
     * @param o1 prva instanca
     * @param o2 druga instanca
     * @return vrača int koji odgova usporedbi dvaju postotaka zarazenih osoba na broju stanovnika dviju zupanija
     */
    @Override
    public int compare(Zupanija o1, Zupanija o2) {
        double postotakZarazenihStanovnikaPrveZupanije = (((double)o1.getBrojStanovnika()) / ((double)o1.getBrojZarazenih()))*100;
        double postotakZarazenihStanovnikaDrugeZupanije = ((double)o2.getBrojStanovnika()) / ((double)o2.getBrojZarazenih())*100;
        if (postotakZarazenihStanovnikaPrveZupanije  > postotakZarazenihStanovnikaDrugeZupanije) {
            return 1;
        } else{
            return -1;
        }
    }

}
