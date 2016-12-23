/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.dal.simulationdata;

/**
 *
 * @author DieltjesT
 */
public class DataAccessFactory {
    
    IDatalayer GetDataRepository(DataAccessTypes type)
    {
        IDatalayer x = null;
    
        switch (type)
        {
            case MSSql:
                x = new SQLRepository();
                break;
            case File:
                x = new FileRepository();
                break;
            default:
                throw new AssertionError(type.name());
        }
        
        return x;
    }
}   
