/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.world.domain;

import app.graphics.tilemapper.LifeEngine;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Ron Olzheim
 * @version 1.0
 */
public class CarnivorCreature extends Creature {
    Creature huntFor;
    
    public CarnivorCreature(LifeEngine LifeEngine, int initPosX, int initPosY, int speed) {
        super(LifeEngine, initPosX, initPosY, speed);
        color = Color.rgb(255, 150, 0);
    }
    
    
     // CreatureListener Interface -- Invoked by the LifeEngine: 
    @Override
    public boolean doTurn() {
        int chaseInterval = 0;
        Creature fnd = null;
        super.doTurn();
        boolean getNewHuntTarget = (Math.random() * 5) > 4;
        fnd = lEngine.findNearestObject(coord, this, (Creature have, Creature test) -> {
            boolean isCarnivor = (test instanceof CarnivorCreature);
            double dist = test.coord.getDistance(coord);
            return (int)(!isCarnivor && dist < 140 ? dist : 100000); 
        });
        if (fnd != null && getNewHuntTarget) huntFor = fnd;
        if (huntFor != null) {
            Direction newDir = coord.AimFor(huntFor.coord);
            if (newDir != Direction.STANDSTILL) dir = newDir;
        }
        return health > 0;
    }

  
    // Detect collision to avoid all Obstacles:
    public Direction.DirectionList collisionDetect(Coordinate curCoord) {
        return super.lEngine.CollisionDetect(curCoord, 1).addStandStill();
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
        return color;
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
}
