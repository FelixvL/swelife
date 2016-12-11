/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.world.interfaces;

import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author Ron Olzheim
 * @version 1.0
 */
public interface CreatureListener {
    public void doTurn();
    public void render(GraphicsContext gc, double x, double y, double w, double h);
}
