/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package life2015.gui;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import static runtime.control.JFXHelper.colorToHexCode;



/**
 * TileMap Class
 * -Organizes and Renders a 2 dimensional array of Tiles.
 * 
 * @author Ron Olzheim
 * @version 1.0
 */
public class TileMap {
    private int tilesX = 20;
    private int tilesY = 20;
    private int tileSizeX = 20;
    private int tileSizeY = 20;
    private double zoomX = 1;
    private double zoomY = 1;
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
    public double getTileSizeX()  { return tileSizeX; }
    public double getTileSizeY()  { return tileSizeY; }
    public double getTileCountX() { return tilesX; }
    public double getTileCountY() { return tilesY; }

    public double getTotalMapWidth(ViewPort viewPort) { 
        return (constrain(getEndTileX(viewPort), 0, tilesX - (constrain(getStartTileX(viewPort), 0, tilesX)))) * tileSizeX * viewPort.zoomX; 
    }
    public double getTotalMapHeight(ViewPort viewPort) { 
        return (constrain(getEndTileY(viewPort), 0, tilesY - (constrain(getStartTileY(viewPort), 0, tilesY)))) * tileSizeY * viewPort.zoomY; 
    }
    
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
    
    
    public void renderTiles(GraphicsContext gc, ViewPort viewPort) {
        int startTileX = constrain(getStartTileX(viewPort), 0, tilesX); // x<tiles.size()
        int startTileY = constrain(getStartTileY(viewPort), 0, tilesY);// tiles.get(x).size()
        int endTileX = constrain(getEndTileX(viewPort), 0, tilesX - startTileX);
        int endTileY = constrain(getEndTileY(viewPort), 0, tilesY - startTileY);
        double offsCenterX = ((viewPort.getWidth() > (endTileX * tileSizeX * viewPort.getZoomX()))) 
                ? ((viewPort.getWidth() - (endTileX * tileSizeX * viewPort.getZoomX())) / 2) : 0;
        double offsCenterY = ((viewPort.getHeight() > (endTileY * tileSizeY * viewPort.getZoomY()))) 
                ? ((viewPort.getHeight() - (endTileY * tileSizeY  * viewPort.getZoomY())) / 2) : 0;
        //System.out.println("("+startTileX+","+startTileY+")-("+endTileX+","+endTileY+")  "+tilesX+","+tilesY+" - "+viewPort.getWidth()+","+viewPort.getHeight());
        gc.getCanvas().getParent().getParent().setStyle("-fx-background-color: " + colorToHexCode(backColor) + ";");
        gc.clearRect(0, 0, viewPort.getWidth()+100, viewPort.getHeight()+100);
        for (int x=0; x<endTileX; x++) {
            for (int y=0; y<endTileY; y++) {
                Tile curTile = tiles.get(x+startTileX).get(y+startTileY);
                curTile.render(gc, 
                        offsCenterX + viewPort.getscrollbarXPixelShift() + getTilePixelX(x, viewPort), 
                        offsCenterY + viewPort.getscrollbarYPixelShift() + getTilePixelY(y, viewPort), 
                        getTilePixelW(viewPort), getTilePixelH(viewPort));
            }
        }
    }
    
        
        
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    
        
    private void refactorTiles(int tilesX, int tilesY) {
        ArrayList<ArrayList<Tile>> newTiles = new ArrayList<>();
        for (int x=0; x<tilesX; x++) {
            ArrayList<Tile> tilesColumn = new ArrayList<>();
            for (int y=0; y<tilesY; y++) {
                Tile newTile = new Tile(Color.color(Math.random(), Math.random(), Math.random()), 1, 1);
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
    
    private int constrain(int val, int min, int max) { return val < min ? min : val > max ? max : val; }
  
    private int getStartTileX(ViewPort viewPort) { return (int)(viewPort.offsetX / (tileSizeX * viewPort.getZoomX())); }
    private int getStartTileY(ViewPort viewPort) { return (int)(viewPort.offsetY / (tileSizeY * viewPort.getZoomY())); }
    private int getEndTileX(ViewPort viewPort) { return (int)(2 + (viewPort.getWidth() / (tileSizeX * viewPort.getZoomX()))); }
    private int getEndTileY(ViewPort viewPort) { return (int)(2 + (viewPort.getHeight() / (tileSizeY * viewPort.getZoomY()))); }  
    
    private double getTilePixelX(int tileColumn, ViewPort viewPort) { return (viewPort.getZoomX() * tileSizeX) * tileColumn; }
    private double getTilePixelY(int tileRow, ViewPort viewPort) { return (viewPort.getZoomY() * tileSizeY) * tileRow; }
    private double getTilePixelW(ViewPort viewPort) { return viewPort.getZoomX() * tileSizeX; }
    private double getTilePixelH(ViewPort viewPort) { return viewPort.getZoomY() * tileSizeY; }

}
