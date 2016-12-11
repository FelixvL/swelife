/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.graphics.tilemapper;

import app.world.domain.Coordinate;
import app.world.domain.Direction;
import app.world.domain.TileLand;
import app.world.domain.TileWater;
import app.world.interfaces.CollisionTest;
import java.util.ArrayList;
import javafx.scene.paint.Color;



/**
 * TileMap Class
 * -Organizes and Renders a 2 dimensional array of Tiles.
 * 
 * @author Ron Olzheim
 * @version 1.1
 */
public class TileMap  {
    private int tilesX = 20;
    private int tilesY = 20;
    private int tileSizeX = 20;
    private int tileSizeY = 20;
    private Color backColor;
    
    ArrayList<ArrayList<Tile>> tiles = null;
    
    
    public TileMap() { this(20, 20, Color.BLACK); }
    public TileMap(Color backColor) { this(20, 20, backColor); }
    public TileMap(int tilesX, int tilesY, Color backColor) {
        this.tilesX = tilesX;
        this.tilesY = tilesY;
        this.backColor = backColor;
        refactorTiles(tilesX, tilesY);
    }
         
    
    public Color getBackColor() { return backColor; }
    public void setBackColor(Color backColor) { this.backColor = backColor; }
    public int getTileSizeX()  { return tileSizeX; }
    public int getTileSizeY()  { return tileSizeY; }
    public int getTileCountX() { return tilesX; }
    public int getTileCountY() { return tilesY; }

    
    
    public Tile getTile(int x, int y) {
        if (tileExists(x,y)) {
            return tiles.get(x).get(y);
        }else{
            return null;
        }
    }
    
    public Tile setTile(int x, int y, Tile newTile) {
        if (tileExists(x,y)) {
            return tiles.get(x).set(y, newTile);
        }else{
            return null;
        }
    }
    
    
        
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    
        
    private void refactorTiles(int tilesX, int tilesY) {
        ArrayList<ArrayList<Tile>> newTiles = new ArrayList<>();
        for (int x=0; x<tilesX; x++) {
            ArrayList<Tile> tilesColumn = new ArrayList<>();
            for (int y=0; y<tilesY; y++) {
                //Tile newTile = new Tile(new Coordinate(x, y), (Math.random() * 2 > 1.5) ? new TileWater() : new TileLand(), 1, 1);
                Tile newTile = new Tile(new Coordinate(x, y), new TileLand(), 1, 1);
                tilesColumn.add(newTile);
            }
            newTiles.add(tilesColumn);
        }
        tiles = newTiles;
    }
    
    private boolean tileExists(int x, int y) {
        if (x>=0 && x<=tiles.size()) {
            if (y>=0 && y<tiles.get(x).size()) {
                return true;
            }
        }
        return false;
    }
  
    
}
