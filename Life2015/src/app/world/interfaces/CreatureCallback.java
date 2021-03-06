/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.world.interfaces;

import app.graphics.tilemapper.Tile;
import app.world.domain.Coordinate;
import app.world.domain.Creature;
import app.world.domain.Direction;
import app.world.domain.TileCreature;

/**
 *
 * @author Ron Olzheim
 * @version 1.0
 */
public interface CreatureCallback {
    boolean moveTo(TileCreature creature, Coordinate fromCoord, Coordinate toCoord);
    public Direction.DirectionList CollisionDetect(Coordinate curCoord);
    public Direction.DirectionList CollisionDetect(Coordinate curCoord, int ray);
    public Tile getTile(Coordinate coord);
    public static interface CompareAimedCreature {
        public int doCompare(Creature have, Creature test);
    }
    public Creature findNearestObject(Coordinate curCoord, Creature obj, CompareAimedCreature compareAimedCreature);
}
