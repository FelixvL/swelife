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
 * @author Ron Olzheim
 * @version 1.0
 */
public class TileWater implements TileSurface {
    private Color color = Color.BLUE;
    
    public TileWater() { super(); }
    public TileWater(Color color) {
        super();
        this.color = color;
    }
    
    @Override
    public Color getColor() {
        return color;
    }
    
    @Override
    public void renderTile(GraphicsContext gc, double x, double y, double w, double h) {
        int paddingX = 0;
        int paddingY = 0;
        gc.setFill(getColor());
        gc.setStroke(getColor());
        gc.setLineWidth(5);
        gc.fillRect(x + paddingX, y + paddingY, w - paddingX, h - paddingY);
    }
}
