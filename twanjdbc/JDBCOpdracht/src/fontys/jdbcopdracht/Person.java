/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.jdbcopdracht;

/**
 *
 * @author DieltjesT
 */
public class Person {

    private int ID;
    private String Naam;

    public Person(String s, int id) {
        this.Naam = s;
        this.ID = id;
    }


    public int getID() {
        return ID;
    }

    public String getNaam() {
        return Naam;
    }
}
