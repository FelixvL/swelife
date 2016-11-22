/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.jdbcopdracht;

import fontys.DataAccesLayer.IDataLayer;
import fontys.DataAccesLayer.SQLRepository;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DieltjesT
 */
public class JdbcOpdrachtMain {

    static List<Jojo> jojoLijst;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        String naam = "Jan";
        IDataLayer rep = new SQLRepository();
        Person p = rep.getData(naam);
        if (p != null) {
            System.out.println("id: " + p.getID() + " naam: " + p.getNaam());
        } else {
            System.out.println("Geen persoon met de naam '" + naam + "' gevonden!. Persoon wordt toegevoegd");
            Person newp = new Person(naam, 10);
            rep.addPerson(newp);
        }
        
        CheckDelete();
    }

    private static void CheckDelete() {
        IDataLayer rep = new SQLRepository();
        String naam = "Jan2";
        int ID = 20;

        Person p = rep.getData(naam);
        if (p == null) {
            p = new Person(naam, 20);
            rep.addPerson(p);
            System.out.println(naam + " toegevoegd");
        } else {
            System.out.println(naam + " bestaat");
        }

        rep.delete(p);

    }

    /**
     *
     */
    public void gogo() {
        try {
            System.out.println("in gogo");
            File naamFile = new File("D:\\_FONTYSSWE\\gogo.txt");
            System.out.println("in writen4");
            FileOutputStream stream = new FileOutputStream(naamFile);
            System.out.println("in writen3");
            ObjectOutputStream out = new ObjectOutputStream(stream);
            System.out.println("in writen2");
            out.writeObject(new Jojo());
//            out.writeObject(someOtherObject);
            System.out.println("in writen1");
        } catch (Exception e) {
            System.out.println("in catch writen");
        }

    }
}

class Jojo implements Serializable {

    String naam;
    int id;

    Jojo(int id, String naam) {
        this.id = id;
        this.naam = naam;
    }

    Jojo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
