/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.DataAccesLayer;

import fontys.jdbcopdracht.Person;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DieltjesT
 */
public class SQLRepository implements IDataLayer {

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    //static final String DB_URL = "jdbc:sqlserver://wsl-476\\sqlexpress;databaseName=FontysSWE;user=FONTYSSWE;password=FontysSWE2016#";
    static final String DB_URL = "jdbc:sqlserver://swesql.database.windows.net:1433;database=SWETest;user=swesa@swesql;password={your_password_here};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    static final String USER = "swesa";
    static final String PASS = "SweFontys16#";

    Connection conn = null;

    private Connection getConnection() throws ClassNotFoundException {
        try {
            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException ex) {
            Logger.getLogger(SQLRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Person getData(String name) {
        Person result = null;

        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM dbo.Sporter WHERE Name = ?");
            //pstmt.setString(1, Name);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String s = rs.getString("Name");

                    result = new Person(s, id);
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(SQLRepository.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SQLRepository.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return result;
    }

    @Override
    public boolean update(Person person) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Person FindByIDNumber(int idNumber) {
        Person result = null;

        return result;
    }

    @Override
    public boolean delete(Person person) {
       boolean result = false;
        try {
            Connection conn = getConnection();

            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM dbo.Sporter where Name = ?");
            pstmt.setString(1, person.getNaam());
            pstmt.execute();  //returns no resultset

            result = true;

        } catch (Exception ex) {
            Logger.getLogger(SQLRepository.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SQLRepository.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }

    @Override
    public boolean addPerson(Person persoon) {
        boolean result = false;
        try {
            Connection conn = getConnection();

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO dbo.Sporter (Name, ID) VALUES (?,?)");
            pstmt.setString(1, persoon.getNaam());
            pstmt.setInt(2, persoon.getID());
            pstmt.execute();  //returns no resultset

            result = true;

        } catch (Exception ex) {
            Logger.getLogger(SQLRepository.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SQLRepository.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }

}
