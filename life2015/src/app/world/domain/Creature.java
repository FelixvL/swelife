/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.world.domain;

import app.graphics.tilemapper.LifeEngine;
import app.graphics.tilemapper.Tile;
import app.graphics.tilemapper.ViewPort;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Ron Olzheim
 * @version 1.1
 */
public class Creature implements TileCreature {
    private LifeEngine lEngine;
    private ViewPort vp;
    public Coordinate coord;
    public Direction dir;
    public int speedProperty = 1;
    private int speedCounter = 0;
    private Color color;
    
    public Creature(LifeEngine LifeEngine, ViewPort viewPort, int initPosX, int initPosY, int speed) {
        lEngine = LifeEngine;
        vp = viewPort;
        coord = new Coordinate(initPosX, initPosY);
        dir = Direction.UP;
        speedProperty = speed;
    }
    
    // Invoked by the 
    @Override
    public void doTurn() {
        speedCounter++;
        if (speedCounter >= speedProperty) {
            speedCounter = 0;

            boolean doRandomDirectionChange = Math.random() * 5 > 4;
            Direction.DirectionList possibleDirections = collisionDetect(coord);
            possibleDirections.allign(dir);
            if (!possibleDirections.isDirectionSet(dir)) {
                dir = possibleDirections.getOther();
            }else if (doRandomDirectionChange) {
                dir = possibleDirections.getSlightChange();
            }
            if (lEngine.moveTo(this, new Coordinate(coord), applyScreenWrapAround(coord.ApplyDirection(dir)))) {

            }
        }
    }
    
    private Coordinate applyScreenWrapAround(Coordinate coordinate) {
        coordinate.setX((coordinate.getX() + vp.getTileMap().getTileCountX()) % vp.getTileMap().getTileCountX());
        coordinate.setY((coordinate.getY() + vp.getTileMap().getTileCountY()) % vp.getTileMap().getTileCountY());
        return coordinate;
    }


    @Override
    public Color getColor() {
        return Color.WHITE;
    }

    @Override
    public void renderTile(GraphicsContext gc, double x, double y, double w, double h) {
        int paddingX = -5;
        int paddingY = -5;
        gc.setFill(getColor());
        gc.setStroke(getColor());
        gc.setLineWidth(5);
        gc.fillOval(x + paddingX, y + paddingY, w - paddingX, h - paddingY);
    }


  
    // Detect collision to avoid all Obstacles:
    public Direction.DirectionList collisionDetect(Coordinate curCoord) {
        Direction.DirectionList collisionList = new Direction.DirectionList();
        for (int i=0; i<Direction.getCount(); i++) {
            Direction testDirection = Direction.getEnumFromVal(i);
            Tile testTile = vp.getTileMap().getTile(
                    vp.getTileMapWrapArroundX(curCoord.getTestX(testDirection)), 
                    vp.getTileMapWrapArroundY(curCoord.getTestY(testDirection)));
            if (((testTile.getSurface()) instanceof TileLand) && 
                    (testTile.getObstacle() == null) && 
                    (testTile.getCreature() == null)) 
                        collisionList.addDirection(testDirection);
        }
        return collisionList;
    }

    
}
