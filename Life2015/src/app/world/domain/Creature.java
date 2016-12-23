/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.world.domain;

import app.graphics.tilemapper.LifeEngine;
import app.world.interfaces.CreatureCallback;
import app.world.interfaces.CreatureListener;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Ron Olzheim
 * @version 1.1
 */
public class Creature implements TileCreature, CreatureListener {
    protected final LifeEngine lEngine;
    //public InteractionResult.LevelKind life;
    public Coordinate coord;
    public Direction dir;
    public int speedProperty = 1;
    protected int speedCounter = 0;
    protected Color color;
    protected int health = 1000;
    
    
    public Creature(LifeEngine LifeEngine, int initPosX, int initPosY, int speed) {
        color = Color.WHITE;
        lEngine = LifeEngine;
        //life = new InteractionResult.AttackDamage(0, this);  
        coord = new Coordinate(initPosX, initPosY);
        dir = Direction.UP;
        speedProperty = speed;
    }
    
    
    // CreatureListener Interface -- Invoked by the LifeEngine: 
    @Override
    public boolean doTurn() {
        speedCounter++;
        if (speedCounter >= speedProperty) {
            speedCounter = 0;
            boolean doRandomDirectionChange = Math.random() * 5 > 4;
            /*
            if (speedProperty >= 2) {
                Creature fnd = lEngine.findNearestObject(coord, this, new CreatureCallback.CompareAimedCreature() {
                    @Override
                    public int doCompare(Creature have, Creature test) {
                        return (int)(test.speedProperty < speedProperty - 1 ? test.coord.getDistance(coord) : 100000); 
                    }
                    
                });
                color = Color.rgb(255, 0, 255);
                if (fnd != null) {
                    dir = coord.AimFor(fnd.coord);
                }
            }
            */
            Direction.DirectionList possibleDirections = collisionDetect(coord);
            possibleDirections.allign(dir);
            if (!possibleDirections.isDirectionSet(dir)) {
                dir = possibleDirections.getOther();
            }else if (doRandomDirectionChange) {
                dir = possibleDirections.getSlightChange();
            }
            if (lEngine.moveTo(this, new Coordinate(coord), coord.ApplyDirection(dir))) {
                // no movement done
            }
            if (lEngine.getTile(coord).getSurface() instanceof TileWater) {
                health = 0;
            }
        }
        return health > 0;
    }

  
    // Detect collision to avoid all Obstacles:
    public Direction.DirectionList collisionDetect(Coordinate curCoord) {
        return lEngine.CollisionDetect(curCoord, 2).addStandStill();
    }

    @Override
    public InteractionResult attack(int force) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InteractionResult damage(int health) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    // TileCreature Interface:
    @Override
    public Color getColor() {
        return color;//Color.WHITE;
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

    @Override
    public int getHealth() {
        return health;
    }
    
}
