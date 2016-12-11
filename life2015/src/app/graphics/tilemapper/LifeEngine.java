/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.graphics.tilemapper;

import app.world.domain.Coordinate;
import app.world.domain.Creature;
import app.world.domain.Direction;
import app.world.domain.TileCreature;
import app.world.domain.TileSurface;
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
    private int fps = 10;
    private long lastFrameNs = 0;
    ArrayList<TileCreature> creatures = new ArrayList<>();
    AnimationTimer aniTmr;
    
    public LifeEngine(ViewPort viewPort) {
        this.vp = viewPort;
        this.tileMap = vp.getTileMap();
    }
    
        public void startRendering() {
            aniTmr = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //if (vp == null || vp.getGraphicsContext() == null) {
                   // aniTmr.stop();
                 if (now - lastFrameNs > (1000000000/fps) || now < lastFrameNs) {
                    lastFrameNs = now;
                    //if (vp.isVisible) renderTiles();
                    runCreatures();
                }
            }
            
        };
        aniTmr.start();
    }
    
    public void stopRendering() {
        aniTmr.stop();
    }
    
    public void runCreatures() {
        Creature c = ((Creature)creatures.get(0));
        //System.out.println(c.coord.getX() + " " + c.coord.getY() + "      " + c.coord.getTestX(c.dir) + " " + c.coord.getTestY(c.dir));
        for (TileCreature sc : creatures) {
            sc.doTurn();
        }
    }
    
    public void addCreature() {
        int x = (int)(Math.random() * this.tileMap.getTileCountX());
        int y = (int)(Math.random() * this.tileMap.getTileCountY());
        TileCreature c = new Creature(this, vp, x, y, (int)(Math.random() * 5));
        creatures.add(c);
        tileMap.getTile(x, y).setCreature(c);
    }
    
    public void testCreature(int creatureCount) {
        for (int i=0; i<creatureCount; i++) {
            addCreature();
        }
    }

    public void setTimerInterval(long millis) {
        generationTimer.setTimeOut(millis).setRepeatMode(true).callbackEvent.addListener((TimerEvent.Listener) (TimerEvent detail) -> {
            System.out.println(">> Entering TimerEvent.callback... ");
            runCreatures();
        });
        generationTimer.startCallbackThread();
    }


    @Override
    public Direction.DirectionList CollisionDetect(Coordinate curCoord) {
        Direction.DirectionList collisionList = new Direction.DirectionList();
        for (int i=0; i<Direction.values().length; i++) {
            Direction testDirection = Direction.getEnumFromVal(i);
            Tile testTile = vp.getTileMap().getTile(curCoord.getTestX(testDirection), curCoord.getTestY(testDirection));
            if (testTile.getSurface()instanceof TileSurface) collisionList.addDirection(testDirection);
        }
        return collisionList;
    }

    @Override
    public boolean moveTo(TileCreature creature, Coordinate fromCoord, Coordinate toCoord) {
        tileMap.getTile(toCoord.getX(), toCoord.getY()).setCreature(creature);
        tileMap.getTile(fromCoord.getX(), fromCoord.getY()).setCreature(null);
        return true;
    }

}
