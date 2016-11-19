/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runtime.control;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * DataHelper Class
 * - Serialization / Deserialisation in XML Format
 * 
 * @author Ron Olzheim
 * @version 1.0
 */
public class DataHelper<T> {
    public DataHelper() {}


    public String serializeXML(T obj) {
        try {
            ByteArrayOutputStream bArr = new ByteArrayOutputStream();
            XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(bArr));
            encoder.writeObject(obj);
            encoder.close();
            return bArr.toString();
        }
        catch (Exception ex) {
            return null;
        }
    }

    public T deserializeXML(String data) {
        try{
            XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(
                    new ByteArrayInputStream(data.getBytes())));
            T obj = (T)decoder.readObject();
            decoder.close();
            return obj;
        }
        catch (Exception ex) {
            return null;
        }
    }
}
