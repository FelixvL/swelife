/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runtime.control;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Numeric Helper Library Class
 *
 * @author Ron Olzheim
 * @version 1.0
 */
public class NumericHelper {
    private NumericHelper() {}
    
    
    public static XMLGregorianCalendar xmlDate(GregorianCalendar date) throws DatatypeConfigurationException {
        return  DatatypeFactory.newInstance().newXMLGregorianCalendar(
                date.get(Calendar.YEAR), 
                date.get(Calendar.MONTH)+1, 
                date.get(Calendar.DAY_OF_MONTH), 
                date.get(Calendar.HOUR),
                date.get(Calendar.MINUTE),
                date.get(Calendar.SECOND),
                0, 0);
    }
    
    /**
     * Function for calculating the current age based on a birthday date:
     * @param birthDate Date of birth
     * @return Int returning the current age in years
     */
    public static int getAge(XMLGregorianCalendar birthDate) {
        if (birthDate != null) {
            LocalDate birthdate = LocalDate.of(birthDate.getYear(), birthDate.getMonth(), birthDate.getDay());
            LocalDate now = LocalDate.now();
            return Period.between(birthdate, now).getYears();
        }
        return 0;
    }
    public static int getAge(GregorianCalendar birthDate) throws DatatypeConfigurationException {
        XMLGregorianCalendar xmlBirthDate = xmlDate(birthDate);
        return getAge(xmlBirthDate);
    }
    
       public static XMLGregorianCalendar parseDate(String date) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date parsed = format.parse(date);
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(parsed);
            System.out.println("Parsing date succeeded.");
            return xmlDate(cal);
        } catch (ParseException ex) {
            System.out.println("Parsing date failed. " + ex.getMessage()); 
            return null;
        } catch (DatatypeConfigurationException groovy){
            System.out.println("Parsing xml-date failed. " + groovy.getMessage());
            return null;
        }
    }

}
