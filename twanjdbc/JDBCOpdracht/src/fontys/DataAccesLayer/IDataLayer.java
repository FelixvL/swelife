/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.DataAccesLayer;

import fontys.jdbcopdracht.Person;

/**
 *
 * @author DieltjesT
 */
public interface IDataLayer {

    public Person getData(String name);

    public boolean update(Person person);

    public Person FindByIDNumber(int idNumber);

    public boolean delete(Person person);
    
    public boolean addPerson(Person persoon);

}
