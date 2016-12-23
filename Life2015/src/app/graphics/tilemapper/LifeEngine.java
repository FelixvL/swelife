/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.graphics.tilemapper;

import app.world.domain.CarnivorCreature;
import app.world.domain.Coordinate;
import app.world.domain.Creature;
import app.world.domain.Direction;
import app.world.domain.TileCreature;
import app.world.domain.TileLand;
//import app.world.domain.TileSurface;
import app.world.interfaces.CreatureCallback;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import runtime.control.TimerEvent;

/**
 *
 * @author Ron Olzheim
 * @version 1.0
 */
public class LifeEngine implements CreatureCallback {
    private TimerEvent generationTimer = new TimerEvent();
    public ViewPort vp;
    private TileMap tileMap;
    private int fps = 40;
    private long lastFrameNs = 0;
    ArrayList<Creature> creatures = new ArrayList<>();
    AnimationTimer aniTmr;
    boolean blockRecursive = false;
    
    public LifeEngine(ViewPort viewPort) {
        this.vp = viewPort;
        this.tileMap = vp.getTileMap();
    }
    
    public void startSimulation() {
        aniTmr = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (vp == null || vp.getGraphicsContext() == null) {
                    aniTmr.stop();
                }else if (now - lastFrameNs > (1000000000/fps) || now < lastFrameNs) {
                    lastFrameNs = now;
                    runCreatures();
                }
            }     
        };
        aniTmr.start();
    }
    
    public void stopSimulation() {
        aniTmr.stop();
    }
    
    public void runCreatures() {
        if (blockRecursive) return;
        blockRecursive = true;
        Creature creature = null;
        //System.out.println(c.coord.getX() + " " + c.coord.getY() + "      " + c.coord.getTestX(c.dir) + " " + c.coord.getTestY(c.dir));
        for (Creature sc : creatures) {
            if (sc instanceof CarnivorCreature) {
                if(!((CarnivorCreature)sc).doTurn()) creature = sc;
            }else{
                if(!sc.doTurn()) creature = sc;
            }
        }
        if (creature != null) delCreature(creature); 
        blockRecursive = false;
    }
    
    public void addCreature() { 
        int x = (int)(Math.random() * this.tileMap.getTileCountX());
        int y = (int)(Math.random() * this.tileMap.getTileCountY());
        addCreature(new Creature(this, x, y, (int)(Math.random() * 3)));
    }
    public void addCarnivorCreature() { 
        int x = (int)(Math.random() * this.tileMap.getTileCountX());
        int y = (int)(Math.random() * this.tileMap.getTileCountY());
        addCreature(new CarnivorCreature(this, x, y, (int)(1 + Math.random() * 3)));
    }
    public void addCreature(Creature creature) {
        creatures.add(creature);
        tileMap.getTile(creature.coord.getX(), creature.coord.getY()).setCreature(creature);
    }
    
    public void delCreature(Creature creature) {
        tileMap.getTile(creature.coord.getX(), creature.coord.getY()).setCreature(null);
        creatures.remove(creature);
    }
    
    public void testCreature(int creatureCount) {
        for (int i=0; i<creatureCount; i++) {
            addCreature();
            addCarnivorCreature();
        }
    }

    public void setTimerInterval(long millis) {
        generationTimer.setTimeOut(millis).setRepeatMode(true).callbackEvent.addListener((TimerEvent.Listener) (TimerEvent detail) -> {
            System.out.println(">> Entering TimerEvent.callback... ");
            runCreatures();
        });
        generationTimer.startCallbackThread();
    }
    
    private Coordinate applyScreenWrapAround(Coordinate coordinate) {
        coordinate.setX((coordinate.getX() + vp.getTileMap().getTileCountX()) % vp.getTileMap().getTileCountX());
        coordinate.setY((coordinate.getY() + vp.getTileMap().getTileCountY()) % vp.getTileMap().getTileCountY());
        return coordinate;
    }

    
    // CreatureCallback Interface
    @Override
    public Direction.DirectionList CollisionDetect(Coordinate curCoord) {
        return CollisionDetect(curCoord, 0);
        /*
        // Determine Free Directions:
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
        */
    }

    
    @Override
    public Direction.DirectionList CollisionDetect(Coordinate curCoord, int ray) {
        //Direction.DirectionList collisionList = new Direction.DirectionList();
        /*
        for (int r=0; r<beam; r++) {
            for (int i=0; i<Direction.getCount(); i++) {
                Direction testDirection = Direction.getEnumFromVal(i);
                Tile testTile = vp.getTileMap().getTile(
                        vp.getTileMapWrapArroundX(curCoord.getTestX(testDirection, r + 1)), 
                        vp.getTileMapWrapArroundY(curCoord.getTestY(testDirection, r + 1)));
                if (((testTile.getSurface()) instanceof TileLand) && 
                        (testTile.getObstacle() == null) && 
                        (testTile.getCreature() == null)) 
                            collisionList.addDirection(testDirection);
            }
        }
        */
        Direction.DirectionList collisionList = new Direction.DirectionList().validateAllDirections();
        for (int r=0; r<ray; r++) {
            for (int i=0; i<Direction.getCount(); i++) {
                Direction testDirection = Direction.getEnumFromVal(i);
                Tile testTile = vp.getTileMap().getTile(
                        vp.getTileMapWrapArroundX(curCoord.getTestX(testDirection, r + 1)), 
                        vp.getTileMapWrapArroundY(curCoord.getTestY(testDirection, r + 1)));
                if (!((testTile.getSurface()) instanceof TileLand) || 
                        (testTile.getObject() != null) || 
                        (testTile.getCreature() != null)) 
                            collisionList.delDirection(testDirection);
            }
        }
        return collisionList;
    }

    
    @Override
    public boolean moveTo(TileCreature creature, Coordinate fromCoord, Coordinate toCoord) {
        toCoord = applyScreenWrapAround(toCoord);
        if (!fromCoord.Equals(toCoord)) {
            tileMap.getTile(toCoord.getX(), toCoord.getY()).setCreature(creature);
            tileMap.getTile(fromCoord.getX(), fromCoord.getY()).setCreature(null);
            return true;
        }
        return false;
    }
    
        @Override
    public Tile getTile(Coordinate coord) {
        return vp.getTileMap().getTile(
                    vp.getTileMapWrapArroundX(coord.getX()), 
                    vp.getTileMapWrapArroundY(coord.getY()));
    }

    @Override
    public Creature findNearestObject(Coordinate curCoord, Creature obj, CompareAimedCreature compareAimedCreature) {
        double dist = 100000;
        Creature foundObj = null;
        if (true) {//obj instanceof Creature) {
            for (Creature sc : creatures) {
                double newDist = dist;//sc.coord.getDistance(curCoord);
                if (foundObj == null || foundObj.equals(obj)) { 
                    foundObj = sc;
                }else{
                    newDist = compareAimedCreature.doCompare(foundObj, sc);
                    if (newDist < dist) {
                        foundObj = sc;
                        dist = newDist;
                    }
                }
            }
        } 
        return foundObj;
    }

}
