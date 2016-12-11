/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.world.interfaces;

import app.world.domain.Direction;

/**
 *
 * @author Ron
 */
public interface CollisionTest {
    public boolean testWaterLine(int posX, int posY, Direction direction);
}
