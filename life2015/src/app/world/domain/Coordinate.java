/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.world.domain;

///import app.graphics.tilemapper.TileMap;
///import app.graphics.tilemapper.ViewPort;

/**
 *
 * @author Ron Olzheim
 */
public class Coordinate {
    ///private ViewPort vp;
    ///private TileMap tileMap;
    private int x;
    private int y;
    public Coordinate(Coordinate src) {
        ///this.tileMap = src.getTileMap();
        this.x = src.getX();
        this.y = src.getY();
    }
    public Coordinate(int x, int y) {
        ///vp = viewPort;
        ///this.tileMap = vp.getTileMap();
        this.x = x;
        this.y = y;
    }
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    ///public ViewPort getViewPort() { return vp; }
    ///public TileMap getTileMap() { return tileMap; }
    
    public int getTestX(Direction direction) { return x + direction.getXOffset(); }
    public int getTestY(Direction direction) { return y + direction.getYOffset(); }
    
    public Coordinate ApplyDirection(Direction direction)  {
        if (direction != null) {
            x += direction.getXOffset();
            y += direction.getYOffset();
        }
        return this;
    }
}
