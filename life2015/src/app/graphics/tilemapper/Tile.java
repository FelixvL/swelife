/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.graphics.tilemapper;

import app.helper.CanvasBackBuffer;
import app.world.interfaces.CollisionDetect;
import app.world.domain.Coordinate;
import app.world.domain.Direction;
import app.world.domain.TileCreature;
import app.world.domain.TileObstacle;
import app.world.domain.TileSurface;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Tile Class
 * -Features and Renders a single Tile, used by the TileMap Class.
 * 
 * @author Ron Olzheim
 * @version 1.0
 */
public class Tile implements CollisionDetect {
    private Coordinate coord;
    private Color color = null;
    private double paddingX = 0;
    private double paddingY = 0;
    private CanvasBackBuffer backBuffer;
    private TileSurface surface;
    private TileObstacle obstacle;
    private TileCreature creature;
    
    public Tile(Coordinate coordinate, TileSurface surface) { this(coordinate, surface, 0, 0); }
    public Tile(Coordinate coordinate, TileSurface surface, double paddingX, double paddingY) {
        this.coord = coordinate;
        this.paddingX = paddingX;
        this.paddingY = paddingY;
        this.surface = surface;
        //redrawBackBuffer();
    }
    
    public Color getColor() {
        return color;
    }
    
   
    
    public void render(GraphicsContext gc, double x, double y, double w, double h) {
        //backBuffer.renderBuffer(gc, x, y, w, h);
        if (surface != null) surface.renderTile(gc, x, y, w, h);
        if (obstacle != null) obstacle.renderTile(gc, x, y, w, h);
        if (creature != null) creature.renderTile(gc, x, y, w, h);
    }
    
    
    public TileSurface getSurface() {
        return surface;
    }    

    public TileSurface setSurface(TileSurface surface) {
        this.surface = surface;
        //redrawBackBuffer();
        return surface;
    }
    
    public TileObstacle getObstacle() {
        return obstacle;
    }
    
    public TileObstacle setObstacle(TileObstacle obstacle) {
        this.obstacle = obstacle;
        //redrawBackBuffer();
        return obstacle;
    }

    public TileCreature getCreature() {
        return creature;
    }

    public TileCreature setCreature(TileCreature creature) {
        this.creature = creature;
        //redrawBackBuffer();
        return creature;
    }
    
    private void redrawBackBuffer() {
        backBuffer = new CanvasBackBuffer();
        //this.color = surface.getColor();
        backBuffer.eraseBuffer();
        surface.renderTile(backBuffer.getAsGraphicsContext(), 0, 0, backBuffer.getWidth(), backBuffer.getHeight());
        backBuffer.revertAsGraphicsContent();
        if (creature != null) {
            creature.renderTile(backBuffer.getAsGraphicsContext(), 0, 0, backBuffer.getWidth(), backBuffer.getHeight());
            backBuffer.revertAsGraphicsContent();
        }
    }
    

    @Override
    public Direction.DirectionList CollisionDetect(Coordinate curCoord) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
