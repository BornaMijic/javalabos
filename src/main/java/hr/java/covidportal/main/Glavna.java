package main.java.hr.java.covidportal.main;

import javafx.scene.control.DatePicker;
import main.java.hr.java.covidportal.enums.VrijednostSimptoma;
import main.java.hr.java.covidportal.genericsi.KlinikaZaInfektivneBolesti;
import main.java.hr.java.covidportal.iznimke.DuplikatKontaktiraneOsobe;
import main.java.hr.java.covidportal.iznimke.OgranicenjeKontaktiranihOsoba;
import main.java.hr.java.covidportal.model.*;
import main.java.hr.java.covidportal.sort.CovidSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Predstavlja klasu Glavna u kojoj  se nalazi metoda main
 * koja je metoda koja se prva pokreće
 *
 * @author Borna
 */
public class Glavna {
    public static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    public static final String FILE_ZUPANIJA = "dat/zupanije.txt";
    public static final String FILE_SIMPTOMI = "dat/simptomi.txt";
    public static final String FILE_BOLESTI = "dat/bolesti.txt";
    public static final String FILE_VIRUSI = "dat/virusi.txt";
    public static final String FILE_OSOBE = "dat/osobe.txt";
    public static final String FILE_ZUPANIJE = "dat/serijaliziranjeZupanija.dat";


    /**
     * Predstavlja metodu main u koja se prva pokreće kada se run-a program te
     * ovdje podstavljamo zupanije, simptoma, bolesti i osobe
     * te ispisujemo popis osoba(ime,prezime,starost,zupanija,bolest i kontaktirane
     * osobe) te upisujemo bolest ili virus kao ključ u mapi te u value imamo listu osoba
     * koje imaju tu bolest ili virus te ispisujemo to i jos ispisujemo zupaniju
     * u kojoj je najveći postotak ljudi koji su zaraženi odredenom bolesti
     * ili virusom. Ispisujemo viruse, osobe koje su zaražene virusom te
     * imaju odredeni string u prezimenu te ispisivanje svake bolesti i virusa te
     * njihov broj simptoma, te upisujemo u binarnu datoteku serijaliziranjeZupanija.dat instancu
     * zupanije koje imaju vise 2 posto zarazenih ljudi u zupaniji
     *
     * @param args argumenti koje mozemo poslati u mainu
     */
    public static void main(String[] args) {
        logger.info("Pokrenuli smo program");

        Scanner unos = new Scanner(System.in);

        System.out.println("Ucitavanje podataka o zupanijama...");
        SortedSet<Zupanija> zupanije = postavljanjeZupanija();

        System.out.println("Ucitavanje podataka o bolestima...");
        Set<Simptom> simptomi = postavljanjeSimptoma();

        System.out.println("Ucitavanje podataka o bolestima...");
        System.out.println("Ucitavanje podataka o virusima...");
        Set<Bolest> bolesti = postavljanjeBolestiIliVirusa(simptomi);

        System.out.println("Ucitavanje podataka o bolestima...");
        List<Osoba> osobe = postavljanjeOsoba(zupanije, bolesti);


        popisOsoba(osobe);
        Map<Bolest, List<Osoba>> podatciOBolestimaOsoba = upisUMapu(osobe);
        ispisMape(podatciOBolestimaOsoba);

        System.out.println("Najviše zaraženih osoba ima u županiji " + zupanije.first().getNaziv() + " " + zupanije.first().postotakZarazenih() + "%");

        KlinikaZaInfektivneBolesti<Virus, Osoba> klinika = new KlinikaZaInfektivneBolesti<>();
        upisivanjeVirusaIOsoba(klinika, bolesti, osobe);

        KlinikaZaInfektivneBolesti<Virus, Osoba> klinika2 = new KlinikaZaInfektivneBolesti<>();
        upisivanjeVirusaIOsoba(klinika2, bolesti, osobe);

        SortiranjeVirusaObrnuto(klinika, klinika2);
        Optional<List<Osoba>> optionalOsobe = OdredenaSlovaUPrezimenu(unos, klinika);
        optionalOsobe.ifPresent((promatraneOsobe) -> promatraneOsobe.forEach(System.out::println));

        IspisBrojaSimptomeSvakeBolesti(bolesti);
        SeriliziranjeZupanija(zupanije);
        unos.close();
        logger.info("Završio je program");
    }

    /**
     * Odgovarajuća metoda služi za dodjeljivanje svakoj  županiji
     * njezinog id, imena, broja stanovnika i broja zaraženih iz datoteke
     * zupanije.txt
     * @return vrača sortedset koji se sastoji od županije
     */
    public static SortedSet<Zupanija> postavljanjeZupanija() {
        SortedSet<Zupanija> zupanije = new TreeSet<>(new CovidSorter());
        try (BufferedReader citacZupanija = new BufferedReader(new FileReader(FILE_ZUPANIJA))) {
            String linija;
            while ((linija = citacZupanija.readLine()) != null) {
                Long id = Long.parseLong(linija);
                String imeZupanije = citacZupanija.readLine();
                boolean istinitost;
                Integer stanovnika = Integer.parseInt(citacZupanija.readLine());
                logger.info("Uneseni broj stanovnika " + stanovnika);

                Integer brojZarazenih = Integer.parseInt(citacZupanija.readLine());
                logger.info("Uneseni broj zarazenih stanovnika " + stanovnika);
                zupanije.add(new Zupanija(id, imeZupanije, stanovnika, brojZarazenih));
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }

        return zupanije;
    }

    /**
     * Odgovarajuća metoda služi za postavljanje id,imena i vrijednosću svakom od
     * simptoma koje ćemo pročitati iz simptomi.txt i spremiti u set  u
     * navedenoj metodi
     *
     * @return vrača set koje se sastoji od tri simptoma
     */
    public static Set<Simptom> postavljanjeSimptoma() {
        System.out.println("ovdje");
        boolean nastavljanjePetlje;
        Set<Simptom> simptomi = new HashSet<>();
        try (BufferedReader citacSimptoma = new BufferedReader(new FileReader(FILE_SIMPTOMI))) {
            String linija;
            while ((linija = citacSimptoma.readLine()) != null) {
                Long id = Long.parseLong(linija);
                String nazivSimptoma = citacSimptoma.readLine();

                VrijednostSimptoma vrijednostSimptoma = VrijednostSimptoma.Jaka;
                do {
                    try {
                        nastavljanjePetlje = true;
                        vrijednostSimptoma = VrijednostSimptoma.valueOf(citacSimptoma.readLine());
                    } catch (IllegalArgumentException e) {
                        nastavljanjePetlje = false;
                    }

                } while (!nastavljanjePetlje);


                simptomi.add(new Simptom(id, nazivSimptoma, vrijednostSimptoma));
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }

        return simptomi;
    }

    /**
     * Navedena metoda služi za dodjeljivanje imena bolesti i virusa, odredivanje simptoma,
     * te id bolesti i virusa iz citanjem datoteka bolesti.txt i virusi.txt
     *
     * @param simptomi simptomi koji se nalaze u set-u
     * @return vraća set bolesti i virusa
     */
    public static Set<Bolest> postavljanjeBolestiIliVirusa(Set<Simptom> simptomi) {
        Set<Bolest> bolesti = new HashSet<>();
        ocitavanjeBolestiIliVirusa(FILE_BOLESTI,bolesti,simptomi);
        ocitavanjeBolestiIliVirusa(FILE_VIRUSI,bolesti,simptomi);
        return bolesti;
    }


    /**
     * Navedena metoda služi da bi u set-u simptoma mogla zahvatit simptom na točno odredenom mjestu
     *
     * @param simptomi set simptoma
     * @param i        traženi index
     * @return vrača simptom na indexu i
     */
    public static Simptom getIndexOfSimptom(Set<Simptom> simptomi, int i) {
        Iterator<Simptom> iter = simptomi.iterator();
        int j = 0;
        while (iter.hasNext()) {
            Simptom simptom = iter.next();
            if (j == i) {
                return simptom;

            }
            j++;
        }
        return new Simptom(null, null, null);
    }

    /**
     * Navedena metoda sluzi da bi procitala podatke o bolestima ili o virusima
     *
     * @param datoteka sadrži lokaciju od kod da cita podatke o bolesti ili virusu
     * @param bolesti set u koj se spremaju bolesti ili virusi
     * @param simptomi to su moguci simptomi koji mogu pripadati nekoj bolesti
     */
    public static void ocitavanjeBolestiIliVirusa(String datoteka, Set<Bolest> bolesti, Set<Simptom> simptomi) {
        try (BufferedReader citac = new BufferedReader(new FileReader(datoteka))) {
            String linija;
            while ((linija = citac.readLine()) != null) {
                Long id = Long.parseLong(linija);
                String ime = citac.readLine();
                String stringIdSimptoma = citac.readLine();
                String[] idSimptoma = stringIdSimptoma.split(",");
                Set<Simptom> simptomiVirusa = new HashSet<>();
                for (int j = 0; j < idSimptoma.length; j++) {
                    Long odabir;
                    odabir = Long.parseLong(idSimptoma[j]);
                    dodavanjeSimptomaBolestiIliVirusu(simptomiVirusa, simptomi, odabir);
                }
                if (datoteka.equals(FILE_VIRUSI)) {
                    bolesti.add(new Virus(id, ime, simptomiVirusa));
                }
                if (datoteka.equals(FILE_BOLESTI)){
                    bolesti.add(new Bolest(id, ime, simptomiVirusa));

                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    /**
     *Navedena metoda služi da nademo simptom s odredenim id iz seta simptomi
     * te dodamo taj simptom u set simptomiBolesti
     * @param simptomiBolesti ovo je set simptoma odredene bolesti
     * @param simptomi ovo je set simptoma
     * @param id ovo je id odredenog simptoma
     */
    public static void dodavanjeSimptomaBolestiIliVirusu(Set<Simptom> simptomiBolesti, Set<Simptom> simptomi, Long id) {
        for (Simptom simptom : simptomi) {
            if (simptom.getId() == id) {
                simptomiBolesti.add(simptom);
            }
        }
    }

    /**
     * Navedena metoda služi da bi u set-u bolesti mogla zahvatit bolest na točno odredenom mjestu
     *
     * @param bolesti set bolesti
     * @param i       traženi index
     * @return vrača bolest na indexu i
     */
    public static Bolest getIndexOfBolest(Set<Bolest> bolesti, int i) {
        Iterator<Bolest> iter = bolesti.iterator();
        int j = 0;
        while (iter.hasNext()) {
            Bolest bolest = iter.next();
            if (j == i) {
                return bolest;

            }
            j++;
        }
        return new Bolest(null, null, null);
    }

    /**
     * Navedena metoda služi da se unese id,ime, prezime, starost, zupanija, bolest
     * te kontaktirane osobe iz osobe.txt te se na kraju od tih podaca napravi instanca
     * osobe pomoću buildera. Ako je i jednako 0 onda pod kontaktiraneOsobe vraća
     * null,a ovako cita kontaktirane osobe iz datoteke osobe.txt
     *
     * @param zupanije sortedset zupanija
     * @param bolesti  set bolesti
     * @return vraća instancu osobe
     */
    public static List<Osoba> postavljanjeOsoba(SortedSet<Zupanija> zupanije,
                                             Set<Bolest> bolesti) {
        List<Osoba> osobe = new ArrayList<>();
        try (BufferedReader citacOsoba = new BufferedReader(new FileReader(FILE_OSOBE))) {
            String linija;
            int i = 0;
            while ((linija = citacOsoba.readLine()) != null) {
                Long id = Long.parseLong(linija);
                String imeOsobe = citacOsoba.readLine();
                String prezimeOsobe = citacOsoba.readLine();
                String stringDatumRodenja = citacOsoba.readLine();
                String[] poljaDatumaRodenja = stringDatumRodenja.split("-");
                LocalDate datumRodenja = LocalDate.of(Integer.parseInt(poljaDatumaRodenja[0]),Integer.parseInt(poljaDatumaRodenja[1]),Integer.parseInt(poljaDatumaRodenja[2]));
                Long idZupanija = Long.parseLong(citacOsoba.readLine());
                Zupanija zupanija = odabirZupanijeOsobe(zupanije,idZupanija);
                Long idBolesti = Long.parseLong(citacOsoba.readLine());
                Bolest bolest = odabirBolestiOsobe(bolesti,idBolesti);
                List<Osoba> kontaktiraneOsobe = new ArrayList<>();
                if (i == 0) {
                    String stringIdOsoba = citacOsoba.readLine();
                    osobe.add(new Osoba.Builder()
                            .postavljamId(id)
                            .postavljamImena(imeOsobe)
                            .postavljamPrezime(prezimeOsobe)
                            .odredujemRodendan(datumRodenja)
                            .postavljanjeZupanije(zupanija)
                            .postavljanjeBolesti(bolest)
                            .odredivanjeKontaktiranihOsoba(null)
                            .build());
                } else {
                    String stringIdOsoba = citacOsoba.readLine();
                    String[] idKontakata = stringIdOsoba.split(",");
                    Boolean istinitost = false;
                    for(int j = 0; j < idKontakata.length;++j){
                        if(Long.parseLong(idKontakata[j]) == 0 && idKontakata.length == 1){
                            istinitost = true;
                            break;
                        }
                        Long odabir;
                        odabir = Long.parseLong(idKontakata[j]);
                        dodavanjeKontaktiranihOsoba(kontaktiraneOsobe, osobe, odabir);
                    }
                    if(istinitost == true){
                        osobe.add(new Osoba.Builder()
                                .postavljamId(id)
                                .postavljamImena(imeOsobe)
                                .postavljamPrezime(prezimeOsobe)
                                .odredujemRodendan(datumRodenja)
                                .postavljanjeZupanije(zupanija)
                                .postavljanjeBolesti(bolest)
                                .odredivanjeKontaktiranihOsoba(null)
                                .build());
                    } else{
                        osobe.add(new Osoba.Builder()
                                .postavljamId(id)
                                .postavljamImena(imeOsobe)
                                .postavljamPrezime(prezimeOsobe)
                                .odredujemRodendan(datumRodenja)
                                .postavljanjeZupanije(zupanija)
                                .postavljanjeBolesti(bolest)
                                .odredivanjeKontaktiranihOsoba(kontaktiraneOsobe)
                                .build());
                    }

                }
                i++;
            }
        }catch (IOException ex) {
            System.err.println(ex);
        }
        return osobe;
    }


    /**
     * Navedena metoda služi da bi pronašla županiju s odredenim id
     *
     * @param zupanije sortedset koje sadrži unesene županije
     * @param idZupanije ovo je id odredene zupanije
     * @return vraća odabranu županiju

     */
    public static Zupanija odabirZupanijeOsobe(SortedSet<Zupanija> zupanije, Long idZupanije) {
        for(Zupanija zupanija: zupanije){
            if(zupanija.getId() == idZupanije){
                return zupanija;
            }
        }
        return null;
    }

    /**
     * Navedena metoda služi da bi iz seta došli do zupanije koja se nalazi na
     * odredenom indexu
     *
     * @param zupanije sortedset koji ima zapisane županije
     * @param i        podatak o traženom indexu
     * @return vrača županiju na traćenom indexu
     */
    public static Zupanija getIndexOfZupanija(SortedSet<Zupanija> zupanije, int i) {
        Iterator<Zupanija> iter = zupanije.iterator();
        int j = 0;
        while (iter.hasNext()) {
            Zupanija zupanija = iter.next();
            if (j == i) {
                return zupanija;

            }
            j++;
        }
        return new Zupanija(null, null, null, null);
    }

    /**
     * Navedena metoda služi da bi našli bolest iz seta bolesti putem id
     *
     * @param bolesti set koji sadrži bolesti
     * @param idBolesti ovo je id bolesti
     * @return vrača odabranu bolest koja će se unijet kao bolest trenutne osobe
                                  s
     */
    public static Bolest odabirBolestiOsobe(Set<Bolest> bolesti, Long idBolesti) {
        for(Bolest bolest: bolesti){
            if(bolest.getId() == idBolesti){
                return bolest;
            }
        }
        return null;
    }


    /**
     * Navedena metoda služi da bi ograničila maksimalan broj kontakata na broj kontakata trenutno unesenih osoba
     *
     * @param unos ovaj parametar služi za dodavanje vrijednosti putem tipkovnice
     * @param i    broj unesenih osoba
     * @return vrača broj kontaktiranih osoba
     * @throws OgranicenjeKontaktiranihOsoba baca se ako je broj kontaktiranih osoba veci od broja unesenih osoba
     */
    public static int Ogranicenja(Scanner unos, int i) throws OgranicenjeKontaktiranihOsoba {
        int brojKontakata;
        System.out.println("Unesite broj osoba koje su bile u kontaktu s tom osobom: ");
        System.out.print("Odabir: ");
        brojKontakata = unos.nextInt();
        logger.info("Uneseni broj kontakata: " + brojKontakata);
        if (brojKontakata < 0 || brojKontakata > i) {
            throw new OgranicenjeKontaktiranihOsoba("Neispravan unos, možete odabrani maksimalno " + i + "osobu.");
        }
        return brojKontakata;
    }

    /**
     * Navedena metoda služi da provjeri da li su u listi kontaktiraneOsobe upisane
     * dvije iste osobe te ako jesu baca se iznimka DuplikatKontaktiraneOsobe
     *
     * @param kontaktiraneOsobe lista kontaktiranih osoba
     * @param osobe             lista trenutno unesenih osoba
     * @param odabirKontakta    broj koji u listi osoba ako oduzmemo s -1 pokazuje odabrani kontakt
     * @throws DuplikatKontaktiraneOsobe baca iznimku ako se doda duplikat u listu kontaktiraneOsobe
     */
    public static void ProvjeraDuplikat(List<Osoba> kontaktiraneOsobe, List<Osoba> osobe, int odabirKontakta) throws DuplikatKontaktiraneOsobe {
        for (Osoba kontaktiranaOsoba : kontaktiraneOsobe) {
            if (osobe.get(odabirKontakta - 1).equals(kontaktiranaOsoba)) {
                throw new DuplikatKontaktiraneOsobe("Odabrana osoba se već nalazi među kontaktiranim osobama. Molimo Vas da\n" +
                        "odaberete neku drugu osobu.");
            }
        }
    }


    /**
     * Navedena metoda služi da dodamo u listu kontaktiranih osoba osobu koja
     * se nalazi u listi osoba te ima odgovarajući id
     * @param kontaktiraneOsobe
     * @param osobe
     * @param id
     */
    public static void dodavanjeKontaktiranihOsoba(List<Osoba> kontaktiraneOsobe, List<Osoba> osobe, Long id) {
        for (Osoba osoba : osobe) {
            if (osoba.getId() == id) {
                kontaktiraneOsobe.add(osoba);
            }
        }
    }



    /**
     * Navedena metoda ispisuje podatke osoba koje  se nalaze u listi.
     * Podatke koje ispisuje su ime, prezime, starost, županija, bolest te
     * kontaktirane osobe
     *
     * @param osobe ovo je lista koje sadrži unesene osobe
     */
    public static void popisOsoba(List<Osoba> osobe) {
        System.out.println("Popis Osoba");
        for (Osoba osoba : osobe) {
            System.out.println("Ime i prezime: " + osoba.getIme() + " " + osoba.getPrezime());
            System.out.println("Starost: " + osoba.getStarost());
            System.out.println("Županija prebivališta: " + osoba.getZupanija().getNaziv());
            System.out.println("Zaražen bolešću: " + osoba.getZarazenBolescu().getNaziv());
            System.out.println("Kontaktirane osobe: ");
            if (osoba.getKontaktiraneOsobe() == null) {
                System.out.println("Nema kontaktiranih osoba");
            } else {
                int j = 0;
                for (Osoba kontaktiranaOsoba : osoba.getKontaktiraneOsobe()) {
                    System.out.println((j + 1) + ". " + kontaktiranaOsoba.getIme() + " "
                            + kontaktiranaOsoba.getPrezime());
                    j++;
                }
            }
        }
    }


    /**
     * Navedena metoda upisMapu služi kako bi se u mapu stavila bolest osobe kao ključ
     * te List<Osobe> kao value.
     *
     * @param osobe lista osoba
     * @return vrača mapu koja kao ključ ima bolest, a kao value ima listu ljudi
     * s tom bolesti
     */
    private static Map<Bolest, List<Osoba>> upisUMapu(List<Osoba> osobe) {
        Map<Bolest, List<Osoba>> podatciOBolestimaOsoba = new HashMap<>();
        for (Osoba osoba : osobe) {
            List<Osoba> listaOsoba = new ArrayList<>();
            if (podatciOBolestimaOsoba.containsKey(osoba.getZarazenBolescu())) {
                listaOsoba = podatciOBolestimaOsoba.get(osoba.getZarazenBolescu());
                listaOsoba.add(osoba);
                podatciOBolestimaOsoba.put(osoba.getZarazenBolescu(), listaOsoba);
            } else {
                listaOsoba.add(osoba);
                podatciOBolestimaOsoba.put(osoba.getZarazenBolescu(), listaOsoba);

            }
        }
        return podatciOBolestimaOsoba;
    }


    /**
     * Navedena metoda ispisMape služi da bi ispisala sve ljude koji su zaraženi
     * tom bolesću ili virusom
     *
     * @param podatciOBolestimaOsoba mapa kojoj je ključ vrijednosti bolest te
     *                               value vrijednosti List<Osoba>
     */
    private static void ispisMape(Map<Bolest, List<Osoba>> podatciOBolestimaOsoba) {
        for (Bolest bolest : podatciOBolestimaOsoba.keySet()) {
            boolean isVirus = bolest instanceof Virus;
            if (isVirus) {
                System.out.print("Od virus" + " " + bolest.getNaziv() + " boluju: ");
            } else {
                System.out.print("Od bolest" + " " + bolest.getNaziv() + " boluju: ");
            }
            int i = 0;
            for (Osoba osoba : podatciOBolestimaOsoba.get(bolest)) {
                if (i == 0) {
                    System.out.print(osoba.getIme() + " " + osoba.getPrezime());
                } else {
                    System.out.print("," + osoba.getIme() + " " + osoba.getPrezime());
                }
                i++;
            }
            System.out.print("\n");
        }
    }

    /**
     * Navedena metoda služi da iz liste bolesti napišemo u listu instance klase
     * KlinikaZainfektivneBolesti,te iz liste osoba upišemo u drugu listu
     * instance klase KlinikaZainfektivneBolesti sve osobe zaražene virusom
     *
     * @param klinika instanca klase KlinikaZaInfektivneBolesti koja sadrži listu<T> i listu<S>
     * @param bolesti set koji sarži unesene viruse i bolesti
     * @param osobe   lista koja sadrži unesene osobe
     */
    private static void upisivanjeVirusaIOsoba(KlinikaZaInfektivneBolesti klinika, Set<Bolest> bolesti, List<Osoba> osobe) {
        for (Bolest bolest : bolesti) {
            if (bolest instanceof Virus virus) {
                klinika.dodavanjeVirusa(virus);
            }
        }
        for (Osoba osoba : osobe) {
            if (osoba.getZarazenBolescu() instanceof Virus virus) {
                klinika.dodavanjeOsobaZarazenihVirusom(osoba);
            }
        }
    }

    /**
     * Navedena metoda služi da bi sortirali listu virusa pomoću lambda funkije i
     * bez korištenja lambda funkije, te mjerenje koliko traje taj proces za svaki
     * način posebno
     *
     * @param klinika  instanca klase KlinikaZaInfektivneBolesti koja sadrži listu<T> i listu<S>
     * @param klinika2 instanca klase KlinikaZaInfektivneBolesti koja sadrži listu<T> i listu<S>
     */
    public static void SortiranjeVirusaObrnuto(KlinikaZaInfektivneBolesti<Virus, Osoba> klinika, KlinikaZaInfektivneBolesti<Virus, Osoba> klinika2) {
        System.out.println("Virusi sortirani po nazivu suprotno od poretka abecede: ");
        Instant start1 = Instant.now();
        List<Virus> virusi = klinika.getUneseniVirusi().stream().sorted(Comparator.comparing(Virus::getNaziv).reversed()).collect(Collectors.toList());
        Instant end1 = Instant.now();
        int i = 0;
        System.out.println("Ispis virusa s lambdom:");
        for (Virus virus : virusi) {
            i++;
            System.out.println(i + ". " + virus.getNaziv().toUpperCase());
        }
        Instant start2 = Instant.now();
        Collections.sort(klinika2.getUneseniVirusi(), new Comparator<Virus>() {
            @Override
            public int compare(Virus o1, Virus o2) {
                return o2.getNaziv().compareTo(o1.getNaziv());
            }
        });
        int j = 0;
        System.out.println("Ispis virusa bez lambde:");
        for (Virus uneseniVirusi : klinika2.getUneseniVirusi()) {
            j++;
            System.out.println(j + ". " + uneseniVirusi.getNaziv().toUpperCase());
        }
        Instant end2 = Instant.now();
        System.out.println("Sortiranje objekata korištenjem lambdi traje " + Duration.between(start1, end1).toMillis() + " milisekundi, a bez lambda traje\n" +
                Duration.between(start2, end2).toMillis() + " milisekundi.");
    }

    /**
     * Navedena metoda služi da kada upišemo neki string te ako prezimena u listi instannce
     * klinika sadrže neki dio tog stringa onda se ta prezimena upišu u novu listu
     * osobeSOdredenimPrezimenom
     *
     * @param unos    ovaj parametar služi za dodavanje vrijednosti putem tipkovnice
     * @param klinika instanca klase KlinikaZaInfektivneBolesti koja sadrži listu<T> i listu<S>
     * @return vrača optional od liste osobeSOdredenimPrezimenom
     */
    public static Optional<List<Osoba>> OdredenaSlovaUPrezimenu(Scanner unos, KlinikaZaInfektivneBolesti<Virus, Osoba> klinika) {
        Optional<List<Osoba>> optionalOsobe;
        System.out.print("Unesite string za pretragu po prezimenu: ");
        String dioPrezimena = unos.nextLine();
        List<Osoba> osobeSOdredenimPrezimenom = klinika.getOsobeZarazeneVirusom().stream().filter((Osoba o) -> {
            return o.getPrezime().toUpperCase().contains(dioPrezimena.toUpperCase());
        }).collect(Collectors.toList());
        if (osobeSOdredenimPrezimenom.isEmpty()) {
            optionalOsobe = Optional.empty();
        } else {
            optionalOsobe = Optional.of(osobeSOdredenimPrezimenom);
        }
        return optionalOsobe;
    }

    /**
     * Iz set bolesti uzimamo ime svake bolesti te njihovu veličinu seta simptomu
     * te spremamo u listu stringova, te je ispisujemo
     *
     * @param bolesti set koji sarži unesene viruse i bolesti
     */
    public static void IspisBrojaSimptomeSvakeBolesti(Set<Bolest> bolesti) {
        bolesti.stream().map(bolest -> bolest.getNaziv() + " ima " + bolest.getSimptomi().size() + " simptoma").forEach(System.out::println);
    }

    /**
     * Navedena metoda upisuje zupanije u datoteku serijaliziranjeZupanija.dat i to samo
     * zupanije koje imaju vise od 2 posto zarazenih
     * @param zupanije set koji sadrzi unesene zupanije
     */
    public static void SeriliziranjeZupanija(Set<Zupanija> zupanije) {
        List<Zupanija> listaZupanija = zupanije.stream().filter(z -> z.postotakZarazenih() > 2).collect(Collectors.toList());
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_ZUPANIJE))){
            out.writeObject(listaZupanija);
        } catch (IOException ex){
            System.err.println(ex);
        }

    }


}


