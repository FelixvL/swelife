/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.dal.applicationsettings;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author DieltjesT
 */
public class ApplicationSettingsManagerTest {

    private static final String fileName = "c:\\temp\\applicationsettings.ser";

    public ApplicationSettingsManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        File f = new File(fileName);
        if (f.exists()) {
            f.delete();
        }
    }

    @AfterClass
    public static void tearDownClass() {

    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    /**
     * Test of SaveApplicationSettings method, of class
     * ApplicationSettingsManager.
     */
    @Test
    public void testSaveAndGetApplicatoinSettings() throws IOException {
        testSaveApplicationSettings();
        testGetApplicationSettings();
        File f = new File(fileName);
        if (f.exists()) {
            f.delete();
        }
    }

    /**
     * Test of SaveApplicationSettings method, of class
     * ApplicationSettingsManager.
     */
    public void testSaveApplicationSettings() {
        System.out.println("SaveApplicationSettings");
        ApplicationSettings settings = new ApplicationSettings();
        settings.setSimulationSpeed(5);
        settings.setAantalCarnivoren(10);
        settings.setAantalHerbivoren(30);
        settings.setAantalOmnivoren(20);

        ApplicationSettingsManager.SaveApplicationSettings(settings, fileName);
        // TODO review the generated test code and remove the default call to fail.
        File f = new File(fileName);
        assertTrue("File does not exist", f.exists());
        f = null;
    }

    /**
     * Test of GetApplicationSettings method, of class
     * ApplicationSettingsManager.
     */
    public void testGetApplicationSettings() throws IOException {
        System.out.println("GetApplicationSettings");
        File f = new File(fileName);
        assertTrue("File does not exist", f.exists());
        ApplicationSettings expResult = null;
        ApplicationSettings result = ApplicationSettingsManager.GetApplicationSettings(fileName);
        assertEquals("Simulation speed differs", 5, result.getSimulationSpeed());
        assertEquals("Aantal carnivoren differs", 10, result.getAantalCarnivoren());
        assertEquals("Aantal carnivoren differs", 30, result.getAantalHerbivoren());
        assertEquals("Aantal carnivoren differs", 20, result.getAantalOmnivoren());
        f = null;
    }
}
