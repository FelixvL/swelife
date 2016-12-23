/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.graphics.tilemapper;

import app.helper.CanvasBackBuffer;
import app.world.domain.Coordinate;
import app.world.domain.TileChanged;
import app.world.domain.TileCreature;
import app.world.domain.TileObject;
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
public class Tile implements TileChanged {
    private Coordinate coord;
    private Color color = null;
    private double paddingX = 0;
    private double paddingY = 0;
    private CanvasBackBuffer backBuffer;
    private TileSurface surface;
    private TileObject object;
    private TileCreature creature;
    private boolean tileChanged = false;
    
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
        if (object != null) object.renderTile(gc, x, y, w, h);
        if (creature != null) creature.renderTile(gc, x, y, w, h);
        tileChanged = false;
    }
    
    
    public TileSurface getSurface() {
        return surface;
    }    

    public TileSurface setSurface(TileSurface surface) {
        this.surface = surface;
        //redrawBackBuffer();
        tileChanged = true;
        return surface;
    }
    
    public TileObject getObject() {
        return object;
    }
    
    public TileObject setObject(TileObject object) {
        this.object = object;
        //redrawBackBuffer();
        tileChanged = true;
        return object;
    }

    public TileCreature getCreature() {
        return creature;
    }

    public TileCreature setCreature(TileCreature creature) {
        this.creature = creature;
        //redrawBackBuffer();
        tileChanged = true;
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
    public boolean isChanged() {
        boolean retVal = tileChanged;
        tileChanged = false;
        return retVal;
    }

    @Override
    public void setChanged(boolean changed) {
        tileChanged = changed;
    }

}
