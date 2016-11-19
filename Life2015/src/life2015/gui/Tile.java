/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package life2015.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Tile Class
 * -Features and Renders a single Tile, used by the TileMap Class.
 * 
 * @author Ron Olzheim
 * @version 1.0
 */
public class Tile {
    Color color = null;
    double paddingX = 0;
    double paddingY = 0;
    
    
    public Tile(Color color) { this(color, 0, 0); }
    public Tile(Color color, double paddingX, double paddingY) {
        this.color = color;
        this.paddingX = paddingX;
        this.paddingY = paddingY;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void render(GraphicsContext gc, double x, double y, double w, double h) {
        gc.setFill(color);
        gc.setStroke(color);
        gc.setLineWidth(5);
        gc.fillRoundRect(x + paddingX, y + paddingY, w - paddingX, h - paddingY, w / 2, h / 2);
    }
}
