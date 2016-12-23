/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.dal.applicationsettings;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Save application settings
 *
 * @author DieltjesT
 */
public final class ApplicationSettingsManager {

    public static void SaveApplicationSettings(ApplicationSettings settings, String fileName) {
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(fileName); //TODO Welke location??? Geen harde paden
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(settings);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException ex) {
                    Logger.getLogger(ApplicationSettingsManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Get saved application settings
     *
     * @author DieltjesT
     */
    public static ApplicationSettings GetApplicationSettings(String fileName) {
        FileInputStream streamIn = null;
        try {
            streamIn = new FileInputStream(fileName); //TODO Welke location??? Geen harde paden
            ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
            ApplicationSettings settings = (ApplicationSettings) objectinputstream.readObject();
            return settings;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (streamIn != null) {
                try {
                    streamIn.close();
                } catch (IOException ex) {
                    Logger.getLogger(ApplicationSettingsManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
}
