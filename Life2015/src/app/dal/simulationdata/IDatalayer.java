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
public interface IDatalayer {
 
    boolean SaveSimulationData(SimuationData data);
    
    SimuationData GetSimulationData();
}
