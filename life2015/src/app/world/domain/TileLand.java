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
public class TileLand implements TileSurface {
    private Color color = Color.GREEN;
    
    public TileLand() { super(); }
    public TileLand(Color color) {
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
        gc.setFill(color);
        gc.setStroke(color);
        gc.setLineWidth(5);
        //gc.fillRoundRect(x + paddingX, y + paddingY, w - paddingX, h - paddingY, w / 2, h / 2);
        gc.fillRect(x + paddingX, y + paddingY, w - paddingX, h - paddingY);
    }
    
}
