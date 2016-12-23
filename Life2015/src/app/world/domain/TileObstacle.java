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
 */
public class TileObstacle implements TileObject {
    private Color color = Color.RED;
    
    public TileObstacle() { super(); }
    public TileObstacle(Color color) {
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
        gc.fillRoundRect(x + paddingX, y + paddingY, w - paddingX, h - paddingY, 5, 5);
    }
    
}
