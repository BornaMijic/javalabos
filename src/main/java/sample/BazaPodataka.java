package main.java.sample;

import main.java.hr.java.covidportal.enums.VrijednostSimptoma;
import main.java.hr.java.covidportal.model.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class BazaPodataka {

    private static final String DATABASE_CONFIGURATION_FILE = "src\\main\\resources\\database.properties";

    /**
     * Naveda metoda služi da bi se povezali na bazu podataka
     * @return vraća vezu da bazu podataka
     * @throws SQLException baca se ako se nismo povezali na bazu podataka
     * @throws IOException neke ulazne ili izlazne operacije nisu uspjele
     */
    public static Connection connectToDatabase() throws SQLException, IOException {
        Connection veza = null;
        Properties svojstva = new Properties();
        svojstva.load(new FileReader(DATABASE_CONFIGURATION_FILE));

        String urlBazePodataka = svojstva.getProperty("bazaPodatakaUrl");
        String korisnickoIme = svojstva.getProperty("korisnickoIme");
        String lozinka = svojstva.getProperty("lozinka");
        try {
            veza = DriverManager.getConnection(urlBazePodataka, korisnickoIme,
                    lozinka);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return veza;
    }

    /**
     * Navedena metoda služi da bi dohvatili sve zupanije iz baze podataka
     * @return vraća listu zupanija
     * @throws SQLException baca se ako se nismo povezali na bazu podataka
     * @throws IOException neke ulazne ili izlazne operacije nisu uspjele
     * @throws ClassNotFoundException navedeba klasa nije prodadena
     */
    public static List<Zupanija> dohvatiSveZupanije() throws SQLException, IOException, ClassNotFoundException {
        List<Zupanija> zupanije = new ArrayList<>();
        Connection veza = connectToDatabase();
        Statement statement = veza.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM ZUPANIJA");
        while(result.next()){
            Long id = result.getLong("ID");
            String naziv = result.getString("NAZIV");
            Integer brojStanovnika = result.getInt("BROJ_STANOVNIKA");
            Integer brojZarazenihStanovnika = result.getInt("BROJ_ZARAZENIH_STANOVNIKA");

            Zupanija zupanija = new Zupanija(id,naziv,brojStanovnika,brojZarazenihStanovnika);
            zupanija.setId(id);
            zupanije.add(zupanija);
        }
        closeConnectionToDatabase(veza,statement,result);
        return zupanije;
    }

    /**
     * Navedena metoda služi da bi spremili zupaniju u bazu podataka
     * @param zupanija instanca zupanije
     * @throws SQLException baca se ako se nismo povezali na bazu podataka
     * @throws IOException neke ulazne ili izlazne operacije nisu uspjele
     */
    public static void spremiNovuZupaniju(Zupanija zupanija) throws SQLException, IOException{
        Connection veza = connectToDatabase();
        PreparedStatement upis = veza.prepareStatement("INSERT INTO ZUPANIJA(NAZIV,BROJ_STANOVNIKA,BROJ_ZARAZENIH_STANOVNIKA) " +
                "VALUES(?, ?, ?)");

        upis.setString(1,zupanija.getNaziv());
        upis.setInt(2,zupanija.getBrojStanovnika());
        upis.setInt(3,zupanija.getBrojZarazenih());
        upis.executeUpdate();
        closeConnectionToDatabase(veza,upis);
    }

    /**
     * Navedena metoda služi da bi pomoću id izvadili iz baze podataka zupaniju s tim id-om
     * @param idZupanije id zupanije
     * @return vraća zupaniju
     * @throws SQLException baca se ako se nismo povezali na bazu podataka
     * @throws IOException neke ulazne ili izlazne operacije nisu uspjele
     */
    public static Zupanija dohvatiJednuZupaniju(Long idZupanije) throws SQLException, IOException{
        Connection veza = connectToDatabase();
        Statement statement = veza.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM ZUPANIJA WHERE ID = " + idZupanije);

        Long id = null;
        String naziv = null;
        Integer brojStanovnika = null;
        Integer brojZarazenihStanovnika = null;
        while(result.next()){
            id = result.getLong("ID");
            naziv = result.getString("NAZIV");
            brojStanovnika = result.getInt("BROJ_STANOVNIKA");
            brojZarazenihStanovnika = result.getInt("BROJ_ZARAZENIH_STANOVNIKA");
        }
        Zupanija zupanija = new Zupanija(naziv,brojStanovnika,brojZarazenihStanovnika);
        zupanija.setId(id);

        closeConnectionToDatabase(veza,statement,result);
        return zupanija;
    }

    /**
     * Navedena metoda služi da bi dohvatila sve simptome iz baze podataka
     * @return vraća listu simptoma
     * @throws SQLException baca se ako se nismo povezali na bazu podataka
     * @throws IOException neke ulazne ili izlazne operacije nisu uspjele
     */
    public static List<Simptom> dohvatiSveSimptome() throws SQLException, IOException{
        List<Simptom> simptomi = new ArrayList<>();
        Connection veza = connectToDatabase();
        Statement statement = veza.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM SIMPTOM");
        while(result.next()){
            Long id = result.getLong("ID");
            String naziv = result.getString("NAZIV");
            VrijednostSimptoma vrijednost = VrijednostSimptoma.valueOf((String)result.getObject(3));

            Simptom simptom = new Simptom(id,naziv,vrijednost);
            simptom.setId(id);
            simptomi.add(simptom);
        }
        closeConnectionToDatabase(veza,statement,result);
        return simptomi;
    }

    /**
     * Navedena metoda služi da bi iz baze podataka uzeli tocno simptom koji ima id koji odgovara parametru
     * @param idSimptoma id simptoma
     * @return vraća instancu simptoma
     * @throws SQLException baca se ako se nismo povezali na bazu podataka
     * @throws IOException neke ulazne ili izlazne operacije nisu uspjele
     */
    public static Simptom dohvatiJedanSimptom(Long idSimptoma) throws SQLException, IOException{
        Connection veza = connectToDatabase();
        Statement statement = veza.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM SIMPTOM WHERE ID = " + idSimptoma);
        Long id = null;
        String naziv = null;
        VrijednostSimptoma vrijednost = null;
        while(result.next()){
            id = result.getLong("ID");
            naziv = result.getString("NAZIV");
            vrijednost = VrijednostSimptoma.valueOf((String)result.getObject("VRIJEDNOST"));
        }

        Simptom simptom = new Simptom(naziv,vrijednost);
        simptom.setId(id);

        closeConnectionToDatabase(veza,statement,result);
        return simptom;
    }

    /**
     * Navedena metoda služi da bi u bazu podataka spremili simptom
     * @param simptom instanca simptoma
     * @throws SQLException baca se ako se nismo povezali na bazu podataka
     * @throws IOException neke ulazne ili izlazne operacije nisu uspjele
     */
    public static void spremiNoviSimptom(Simptom simptom) throws SQLException, IOException{
        Connection veza = connectToDatabase();
        PreparedStatement upis = veza.prepareStatement("INSERT INTO SIMPTOM(NAZIV,VRIJEDNOST) " +
                "VALUES(?, ?)");

        upis.setString(1,simptom.getNaziv());
        upis.setString(2,simptom.getVrijednost().toString());
        upis.executeUpdate();
        closeConnectionToDatabase(veza,upis);
    }

    /**
     * Navedena metoda služi da bi iz baze podataka dohvatili sve bolesti
     * @return vraća listu bolesti
     * @throws SQLException baca se ako se nismo povezali na bazu podataka
     * @throws IOException neke ulazne ili izlazne operacije nisu uspjele
     */
    public static List<Bolest> dohvatiSveBolesti() throws SQLException, IOException{
        Map<Long,List<Long>> idBolestiISimptoma = new HashMap<>();
        List<Bolest> bolesti = new ArrayList<>();
        Connection veza = connectToDatabase();
        Statement statement = veza.createStatement();
        ResultSet resultati = statement.executeQuery("SELECT * FROM BOLEST_SIMPTOM" );
        while(resultati.next()){
            Long idBolesti = resultati.getLong("BOLEST_ID");
            Long idSimptoma = resultati.getLong("SIMPTOM_ID");
            boolean istinitost = false;
            if(!idBolestiISimptoma.isEmpty()){
                for(Long key: idBolestiISimptoma.keySet()){
                    if (key.equals(idBolesti)){
                        idBolestiISimptoma.get(key).add(idSimptoma);
                        istinitost = true;
                    }
                }
            }
            if(istinitost == false){
                List<Long> idSimptomaLista = new ArrayList<>();
                idSimptomaLista.add(idSimptoma);
                idBolestiISimptoma.put(idBolesti,idSimptomaLista);

            }
        }

        ResultSet result = statement.executeQuery("SELECT * FROM BOLEST");
        while(result.next()){
            Bolest bolest = null;
            Long id = result.getLong("ID");
            String naziv = result.getString("NAZIV");
            boolean daLiJeVirus = result.getBoolean("VIRUS");
            Set<Simptom> simptomi = new HashSet<>();
            if(idBolestiISimptoma.containsKey(id)){
                List<Long> listaIdSimptoma = new ArrayList<>(idBolestiISimptoma.get(id));
                for(Long idSimptom: listaIdSimptoma){
                    Simptom dohvaceniSimptom = dohvatiJedanSimptom(idSimptom);
                    simptomi.add(dohvaceniSimptom);
                }
                if(daLiJeVirus){
                    bolest = new Virus(id,naziv,simptomi,daLiJeVirus);;
                } else{
                    bolest = new Bolest(id,naziv,simptomi,daLiJeVirus);
                }
                bolesti.add(bolest);
            }
        }
        closeConnectionToDatabase(veza,statement,result,resultati);
        return bolesti;
    }


    /**
     * Navedena metoda uzima iz baze podataka bolest koja odgovara id parametra
     * @param idBolest id bolesti
     * @return vraća instancu bolesti
     * @throws SQLException baca se ako se nismo povezali na bazu podataka
     * @throws IOException neke ulazne ili izlazne operacije nisu uspjele
     */
    public static Bolest dohvatiJednuBolest(Long idBolest) throws SQLException, IOException{
        Connection veza = connectToDatabase();
        Statement statement = veza.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM BOLEST WHERE ID = " + idBolest);
        Long id = null;
        String naziv = null;
        boolean daliJeVirus = true;
        while(result.next()){
            id = result.getLong("ID");
            naziv = result.getString("NAZIV");
            daliJeVirus = result.getBoolean("VIRUS");
        }
        Map<Long,List<Long>> idBolestiISimptoma = new HashMap<>();
        ResultSet resultati = statement.executeQuery("SELECT * FROM BOLEST_SIMPTOM" );
        while(resultati.next()){
            Long idBolesti = resultati.getLong("BOLEST_ID");
            Long idSimptoma = resultati.getLong("SIMPTOM_ID");
            boolean istinitost = false;
            if(!idBolestiISimptoma.isEmpty()){
                for(Long key: idBolestiISimptoma.keySet()){
                    if (key.equals(idBolesti)){
                        idBolestiISimptoma.get(key).add(idSimptoma);
                        istinitost = true;
                    }
                }
            }
            if(istinitost == false){
                List<Long> idSimptomaLista = new ArrayList<>();
                idSimptomaLista.add(idSimptoma);
                idBolestiISimptoma.put(idBolesti,idSimptomaLista);

            }
        }


        Set<Simptom> simptomi = new HashSet<>();
        if(idBolestiISimptoma.containsKey(id)){
            List<Long> listaIdSimptoma = new ArrayList<>(idBolestiISimptoma.get(id));
            for(Long idSimptom: listaIdSimptoma){
                Simptom dohvaceniSimptom = dohvatiJedanSimptom(idSimptom);
                simptomi.add(dohvaceniSimptom);
            }
        }

        Bolest bolest = null;
        if(daliJeVirus == true){
            bolest = new Virus(naziv,simptomi,daliJeVirus);
        } else{
            bolest = new Bolest(naziv,simptomi,daliJeVirus);
        }
        bolest.setId(id);

        closeConnectionToDatabase(veza,statement,result,resultati);
        return bolest;
    }

    /**
     *Navedena metoda služi da bi u bazu podataka spremili bolest
     * @param bolest instanca bolesti
     * @throws SQLException baca se ako se nismo povezali na bazu podataka
     * @throws IOException neke ulazne ili izlazne operacije nisu uspjele
     */
    public static void spremiNovuBolest(Bolest bolest) throws SQLException, IOException{
        Connection veza = connectToDatabase();
        PreparedStatement upis = veza.prepareStatement("INSERT INTO BOLEST(NAZIV,VIRUS) " +
                "VALUES(?,?)",Statement.RETURN_GENERATED_KEYS);

        upis.setString(1,bolest.getNaziv());
        upis.setBoolean(2,bolest.isDaLiVirus());
        upis.executeUpdate();
        Long idBolesti= -1l;
        ResultSet rs = upis.getGeneratedKeys();
        if (rs.next()){
            idBolesti=rs.getLong(1);
        }
        rs.close();
        for(Simptom simptom: bolest.getSimptomi()){
            spremiSimptomeBolesti(veza,idBolesti,simptom.getId());
        }
        closeConnectionToDatabase(veza,upis);
    }


    /**
     * U bazu podataka spremamo id bolesti te id simptoma koje te bolesti imaju
     * @param veza veza na bazu podataka
     * @param idBolesti id bolesti
     * @param idSimptoma id simptoma
     * @throws SQLException baca se ako se nismo povezali na bazu podataka
     * @throws IOException neke ulazne ili izlazne operacije nisu uspjele
     */
    public static void spremiSimptomeBolesti(Connection veza,Long idBolesti,Long idSimptoma) throws SQLException, IOException{
        PreparedStatement upisSimptoma = veza.prepareStatement("INSERT INTO BOLEST_SIMPTOM (BOLEST_ID,SIMPTOM_ID) " +
                "VALUES(?, ?)");
        upisSimptoma.setLong(1,idBolesti);
        upisSimptoma.setLong(2,idSimptoma);
        upisSimptoma.executeUpdate();
    }

    /**
     * Navedena metoda služi da bi dohvatila sve osobe iz baze podataka
     * @return vraća listu osoba
     * @throws SQLException baca se ako se nismo povezali na bazu podataka
     * @throws IOException neke ulazne ili izlazne operacije nisu uspjele
     * @throws ClassNotFoundException klasa nije pronadena
     */
    public static List<Osoba> dohvatiSveOsobe() throws SQLException, IOException, ClassNotFoundException {
        List<Osoba> osobe = new ArrayList<>();
        Connection veza = connectToDatabase();
        Statement statement = veza.createStatement();
        List<Bolest> bolestiIzBazePodataka = dohvatiSveBolesti();
        Statement statement1 = veza.createStatement();
        ResultSet result = statement1.executeQuery("SELECT * FROM OSOBA");
        while(result.next()){
            Long id = result.getLong("ID");
            String ime = result.getString("IME");
            String prezime = result.getString("PREZIME");
            LocalDate datumRodenja = LocalDate.parse(result.getString("DATUM_RODJENJA"));
            Long idZupanije = result.getLong("ZUPANIJA_ID");
            Long idBolesti = result.getLong("BOLEST_ID");
            Zupanija zupanija = dohvatiJednuZupaniju(idZupanije);
            Bolest bolest = dohvatiJednuBolest(idBolesti);
            osobe.add(new Osoba.Builder()
                    .postavljamId(id)
                    .postavljamImena(ime)
                    .postavljamPrezime(prezime)
                    .odredujemRodendan(datumRodenja)
                    .postavljanjeZupanije(zupanija)
                    .postavljanjeBolesti(bolest)
                    .odredivanjeKontaktiranihOsoba(null)
                    .build());
        }

        Map<Long,List<Long>> idKontaktiranihOsobaIOsobe = new HashMap<>();
        ResultSet resultati = statement.executeQuery("SELECT * FROM KONTAKTIRANE_OSOBE" );
        while(resultati.next()){
            Long idOsobe = resultati.getLong("OSOBA_ID");
            Long idKontaktiraneOsobe = resultati.getLong("KONTAKTIRANA_OSOBA_ID");
            boolean istinitost = false;
            if(!idKontaktiranihOsobaIOsobe.isEmpty()){
                for(Long key: idKontaktiranihOsobaIOsobe.keySet()){
                    if (key.equals(idOsobe)){
                        idKontaktiranihOsobaIOsobe.get(key).add(idKontaktiraneOsobe);
                        istinitost = true;
                    }
                }
            }
            if(istinitost == false){
                List<Long> idKontaktiranihLista = new ArrayList<>();
                idKontaktiranihLista.add(idKontaktiraneOsobe);
                idKontaktiranihOsobaIOsobe.put(idOsobe,idKontaktiranihLista);

            }
        }
        for(Osoba osoba: osobe){
            List<Osoba> kontaktiraneOsobe = new ArrayList<>();
            if(idKontaktiranihOsobaIOsobe.containsKey(osoba.getId())){
                List<Long> kontaktirane = new ArrayList<>(idKontaktiranihOsobaIOsobe.get(osoba.getId()));
                for(Long idKontaktirane: kontaktirane){
                    Osoba osoba1 = dohvatiJednuOsobu(idKontaktirane,osobe);
                    kontaktiraneOsobe.add(osoba1);

                }
            }
            osoba.setKontaktiraneOsobe(kontaktiraneOsobe);
        }
        closeConnectionToDatabase(veza,statement,result,resultati);
        return osobe;
    }

    /**
     * Navedena metoda služi kako bi dohvatili osobu s odredenim id iz baze podataka
     * @param idOsobeZaDohvatJedneOsobe id osobe
     * @param osobe lista osoba
     * @return
     * @throws SQLException baca se ako se nismo povezali na bazu podataka
     * @throws IOException neke ulazne ili izlazne operacije nisu uspjele
     * @throws ClassNotFoundException klasa nije pronadena
     */
    public static Osoba dohvatiJednuOsobu(Long idOsobeZaDohvatJedneOsobe,List<Osoba> osobe) throws SQLException, IOException, ClassNotFoundException {
        Connection veza = connectToDatabase();
        Statement statement = veza.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM OSOBA WHERE ID = " + idOsobeZaDohvatJedneOsobe);
        Long id = null;
        String ime = null;
        String prezime = null;
        LocalDate datumRodenja = null;
        Zupanija zupanija = null;
        Bolest bolest = null;
        while(result.next()){
            id = result.getLong("ID");
            ime = result.getString("IME");
            prezime = result.getString("PREZIME");
            datumRodenja = LocalDate.parse(result.getString("DATUM_RODJENJA"));
            Long idZupanije = result.getLong("ZUPANIJA_ID");
            Long idBolesti = result.getLong("BOLEST_ID");
            zupanija = dohvatiJednuZupaniju(idZupanije);
            bolest = dohvatiJednuBolest(idBolesti);
        }

        Map<Long,List<Long>> idKontaktiranihOsobaIOsobe = new HashMap<>();
        ResultSet resultati = statement.executeQuery("SELECT * FROM KONTAKTIRANE_OSOBE" );
        while(resultati.next()){
            Long idOsobe = resultati.getLong("OSOBA_ID");
            Long idKontaktiraneOsobe = resultati.getLong("KONTAKTIRANA_OSOBA_ID");
            boolean istinitost = false;
            if(!idKontaktiranihOsobaIOsobe.isEmpty()){
                for(Long key: idKontaktiranihOsobaIOsobe.keySet()){
                    if (key.equals(idOsobe)){
                        idKontaktiranihOsobaIOsobe.get(key).add(idKontaktiraneOsobe);
                        istinitost = true;
                    }
                }
            }
            if(istinitost == false){
                List<Long> idKontaktiranihLista = new ArrayList<>();
                idKontaktiranihLista.add(idKontaktiraneOsobe);
                idKontaktiranihOsobaIOsobe.put(idOsobe,idKontaktiranihLista);

            }
        }
        Osoba osoba = new Osoba.Builder()
                .postavljamId(id)
                .postavljamImena(ime)
                .postavljamPrezime(prezime)
                .odredujemRodendan(datumRodenja)
                .postavljanjeZupanije(zupanija)
                .postavljanjeBolesti(bolest)
                .odredivanjeKontaktiranihOsoba(null)
                .build();


        closeConnectionToDatabase(veza,statement,result,resultati);
        return osoba;
    }

    /**
     * Navedena metoda sprema osobu u bazu podataka
     * @param osoba instanca osobe
     * @throws SQLException baca se ako se nismo povezali na bazu podataka
     * @throws IOException neke ulazne ili izlazne operacije nisu uspjele
     */
    public static void spremiNovuOsobu(Osoba osoba) throws SQLException, IOException{
        Connection veza = connectToDatabase();
        PreparedStatement upis = veza.prepareStatement("INSERT INTO OSOBA(IME, PREZIME, DATUM_RODJENJA, ZUPANIJA_ID, BOLEST_ID)" +
                "VALUES (?,?,?,?,?); ",Statement.RETURN_GENERATED_KEYS);

        upis.setString(1,osoba.getIme());
        upis.setString(2,osoba.getPrezime());
        upis.setString(3,osoba.getStarost().toString());
        upis.setLong(4,osoba.getZupanija().getId());
        upis.setLong(5,osoba.getZarazenBolescu().getId());
        upis.executeUpdate();
        Long idOsoba= -1l;
        ResultSet rs = upis.getGeneratedKeys();
        if (rs.next()){
            idOsoba=rs.getLong(1);
        }
        rs.close();
        if(osoba.getKontaktiraneOsobe() != null){
            for(Osoba osoba1: osoba.getKontaktiraneOsobe()){
                spremiKontaktiraneOsobeOdOsobe(veza,idOsoba,osoba1.getId());
            }
        }
        closeConnectionToDatabase(veza,upis);
    }



    public static void spremiKontaktiraneOsobeOdOsobe(Connection veza,Long idOsoba,Long idKontaktiranaOsoba) throws SQLException, IOException{
        PreparedStatement upisSimptoma = veza.prepareStatement("INSERT INTO KONTAKTIRANE_OSOBE (OSOBA_ID,KONTAKTIRANA_OSOBA_ID) " +
                "VALUES(?, ?)");
        upisSimptoma.setLong(1,idOsoba);
        upisSimptoma.setLong(2,idKontaktiranaOsoba);
        upisSimptoma.executeUpdate();
    }


    /**
     * Navedena metoda zatvara vezu na bazu podataka
     * @param veza
     * @param statement
     * @throws SQLException baca se ako se nismo povezali na bazu podataka
     * @throws IOException neke ulazne ili izlazne operacije nisu uspjele
     */
    public static void closeConnectionToDatabase(Connection veza,Statement statement) throws SQLException, IOException{
        try{
            statement.close();
            veza.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Navedena metoda zatvara vezu na bazu podataka
     * @param veza veza na bazu podataka
     * @param statement
     * @param rs rezultati iz baze podataka
     * @throws SQLException baca se ako se nismo povezali na bazu podataka
     * @throws IOException neke ulazne ili izlazne operacije nisu uspjele
     */
    public static void closeConnectionToDatabase(Connection veza,Statement statement,ResultSet rs) throws SQLException, IOException{
        try{
            rs.close();
            statement.close();
            veza.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Navedena metoda zatvara vezu na bazu podataka
     * @param veza
     * @param statement
     * @param rs rezultati iz baze podataka
     * @param rs1 rezultati iz baze podataka
     * @throws SQLException baca se ako se nismo povezali na bazu podataka
     * @throws IOException neke ulazne ili izlazne operacije nisu uspjele
     */
    public static void closeConnectionToDatabase(Connection veza,Statement statement,ResultSet rs,ResultSet rs1) throws SQLException, IOException{
        try{
            rs1.close();
            rs.close();
            statement.close();
            veza.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
