/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.world.interfaces;

import app.world.domain.InteractionResult;

/**
 *
 * @author Ron Olzheim
 * @version 1.0
 */
public interface CreatureListener {
    public boolean doTurn();
    public InteractionResult attack(int force);
    public InteractionResult damage(int health);
    public int getHealth();
}
