/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.dal.applicationsettings;

import java.io.Serializable;

//TODO: TWDI:  Review/aanvulling etc gegevens die van de simulatie bewaard moeten blijven!!
/**
 * Application settings to be saved
 * @author DieltjesT
 */
public class ApplicationSettings implements Serializable{
    private int SimulationSpeed;
    private int AantalOmnivoren;
    private int TotaalEnergieOmnivoren;
    private int GemiddeldEnergieLevelOmnivoren;
    
    private int AantalCarnivoren;
    private int TotaalEnergieCarnivoren;
    private int GemiddeldEnergieLevelCarnivoren;
    
    private int AantalHerbivoren;
    private int TotaalEnergieHerbivoren;
    private int GemiddeldEnergieLevelHerbivoren;
    
    private int TotaalPlanten;
    private int TotaalEnergiePlanten;
    
    /**
     * Get Simulation speed
     * @return
     */
    public int getSimulationSpeed() {
        return SimulationSpeed;
    }

    /**
     * Set Simulation speed
     * @param SimulationSpeed
     */
    public void setSimulationSpeed(int SimulationSpeed) {
        this.SimulationSpeed = SimulationSpeed;
    }

    /**
     *
     * @return
     */
    public int getAantalOmnivoren() {
        return AantalOmnivoren;
    }

    /**
     *
     * @param AantalOmnivoren
     */
    public void setAantalOmnivoren(int AantalOmnivoren) {
        this.AantalOmnivoren = AantalOmnivoren;
    }

    /**
     *
     * @return
     */
    public int getTotaalEnergieOmnivoren() {
        return TotaalEnergieOmnivoren;
    }

    /**
     *
     * @param TotaalEnergieOmnivoren
     */
    public void setTotaalEnergieOmnivoren(int TotaalEnergieOmnivoren) {
        this.TotaalEnergieOmnivoren = TotaalEnergieOmnivoren;
    }

    /**
     *
     * @return
     */
    public int getGemiddeldEnergieLevelOmnivoren() {
        return GemiddeldEnergieLevelOmnivoren;
    }

    /**
     *
     * @param GemiddeldEnergieLevelOmnivoren
     */
    public void setGemiddeldEnergieLevelOmnivoren(int GemiddeldEnergieLevelOmnivoren) {
        this.GemiddeldEnergieLevelOmnivoren = GemiddeldEnergieLevelOmnivoren;
    }

    /**
     *
     * @return
     */
    public int getAantalCarnivoren() {
        return AantalCarnivoren;
    }

    /**
     *
     * @param AantalCarnivoren
     */
    public void setAantalCarnivoren(int AantalCarnivoren) {
        this.AantalCarnivoren = AantalCarnivoren;
    }

    /**
     *
     * @return
     */
    public int getTotaalEnergieCarnivoren() {
        return TotaalEnergieCarnivoren;
    }

    /**
     *
     * @param TotaalEnergieCarnivoren
     */
    public void setTotaalEnergieCarnivoren(int TotaalEnergieCarnivoren) {
        this.TotaalEnergieCarnivoren = TotaalEnergieCarnivoren;
    }

    /**
     *
     * @return
     */
    public int getGemiddeldEnergieLevelCarnivoren() {
        return GemiddeldEnergieLevelCarnivoren;
    }

    /**
     *
     * @param GemiddeldEnergieLevelCarnivoren
     */
    public void setGemiddeldEnergieLevelCarnivoren(int GemiddeldEnergieLevelCarnivoren) {
        this.GemiddeldEnergieLevelCarnivoren = GemiddeldEnergieLevelCarnivoren;
    }

    /**
     *
     * @return
     */
    public int getAantalHerbivoren() {
        return AantalHerbivoren;
    }

    /**
     *
     * @param AantalHerbivoren
     */
    public void setAantalHerbivoren(int AantalHerbivoren) {
        this.AantalHerbivoren = AantalHerbivoren;
    }

    /**
     *
     * @return
     */
    public int getTotaalEnergieHerbivoren() {
        return TotaalEnergieHerbivoren;
    }

    /**
     *
     * @param TotaalEnergieHerbivoren
     */
    public void setTotaalEnergieHerbivoren(int TotaalEnergieHerbivoren) {
        this.TotaalEnergieHerbivoren = TotaalEnergieHerbivoren;
    }

    /**
     *
     * @return
     */
    public int getGemiddeldEnergieLevelHerbivoren() {
        return GemiddeldEnergieLevelHerbivoren;
    }

    /**
     *
     * @param GemiddeldEnergieLevelHerbivoren
     */
    public void setGemiddeldEnergieLevelHerbivoren(int GemiddeldEnergieLevelHerbivoren) {
        this.GemiddeldEnergieLevelHerbivoren = GemiddeldEnergieLevelHerbivoren;
    }

    /**
     *
     * @return
     */
    public int getTotaalPlanten() {
        return TotaalPlanten;
    }

    /**
     *
     * @param TotaalPlanten
     */
    public void setTotaalPlanten(int TotaalPlanten) {
        this.TotaalPlanten = TotaalPlanten;
    }

    /**
     *
     * @return
     */
    public int getTotaalEnergiePlanten() {
        return TotaalEnergiePlanten;
    }

    /**
     *
     * @param TotaalEnergiePlanten
     */
    public void setTotaalEnergiePlanten(int TotaalEnergiePlanten) {
        this.TotaalEnergiePlanten = TotaalEnergiePlanten;
    }
    
    
    
}
