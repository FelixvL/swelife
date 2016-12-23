/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.world.domain;

/**
 *
 * @author Ron Olzheim
 * @version 1.1
 */
public class Coordinate {
    private int x;
    private int y;
    public Coordinate(Coordinate src) {
        this.x = src.getX();
        this.y = src.getY();
    }
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getTestX(Direction direction) { return x + direction.getXOffset(); }
    public int getTestY(Direction direction) { return y + direction.getYOffset(); }
    public int getTestX(Direction direction, int beam) { return x + (beam * direction.getXOffset()); }
    public int getTestY(Direction direction, int beam) { return y + (beam * direction.getYOffset()); }
    public double getDistance(Coordinate fromCoord) { return Math.sqrt(Math.pow(x - fromCoord.x, 2) + Math.pow(y - fromCoord.y, 2)); }
    public Direction AimFor(Coordinate coord) {
        Direction dir = Direction.target(coord.x - x, coord.y - y);
        return dir;
    }
    public Coordinate ApplyDirection(Direction direction)  {
        if (direction != null) {
            //if (direction == Direction.STANDSTILL) System.out.println("standstill");
            x += direction.getXOffset();
            y += direction.getYOffset();
        }
        return this;
    }
    
    public boolean Equals(Coordinate other) {
        return x == other.getX() && y == other.getY();
    }
}
