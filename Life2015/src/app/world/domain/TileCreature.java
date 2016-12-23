/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.world.domain;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Ron
 */
public interface TileCreature {
    Color getColor();
    void renderTile(GraphicsContext gc, double x, double y, double w, double h); 
}
