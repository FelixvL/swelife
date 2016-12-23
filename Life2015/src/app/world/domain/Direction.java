/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.world.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Ron Olzheim
 * @version 1.2
 */
public enum Direction {
    UP         (0),
    UP_RIGHT   (1),
    RIGHT      (2),
    RIGHT_DOWN (3),
    DOWN       (4),
    DOWN_LEFT  (5),
    LEFT       (6),
    LEFT_UP    (7),
    STANDSTILL (8);
    
    private static final Direction[] typeLookup = Direction.values();
    private static ArrayList<Direction> allDirections;
    private final int move; 
    Direction(int move) { this.move = move; }
    int getVal() { return move; }
    public static int getCount() { return typeLookup.length; }
    int getXOffset() { return move > 0 && move < 4 ? 1 : move > 4 && move < 8 ? -1 : 0; }
    int getYOffset() { return move < 2 || (move > 6 && move < 8) ? -1 : move > 2 && move < 6 ? 1 : 0; }
    public static Direction getEnumFromVal(int val) { return typeLookup[val]; }
    public static Direction target(int deltaX, int deltaY) {
        int dir = deltaX>0 ? 2 : deltaX<0 ? 6 : 0;
        dir +=  deltaX>0 ? (deltaY<0 ? -1 : deltaY>0 ? 1 : 0) : 
                deltaX<0 ? (deltaY<0 ? 1 : deltaY>0 ? -1 : 0) : 
                           (deltaY<0 ? 0 : deltaY>0 ? 4 : 8);
        return typeLookup[dir];
    }
    public static List<Direction> getAllDirections() {
        if (allDirections == null) {
            allDirections = new ArrayList<>();
            allDirections.addAll(Arrays.asList(typeLookup));
            allDirections.remove(Direction.STANDSTILL);
        }
        return (List<Direction>) Collections.unmodifiableList(allDirections);
    }
    
    public static class DirectionList{
        private ArrayList<Direction> directionsList = new ArrayList<>();
        public DirectionList() {}
        public DirectionList(ArrayList<Direction> directionsList) {
            this();
            this.directionsList = directionsList;
        }
        public DirectionList addStandStill() { 
            directionsList.add(Direction.STANDSTILL); 
            return this; 
        }
        public DirectionList validateAllDirections() {
            directionsList.addAll(Direction.getAllDirections());
            return this;
        }
        public DirectionList addDirection(Direction direction) {
            if (direction != null) directionsList.add(direction);
            return this;
        }
        public DirectionList delDirection(Direction direction) {
            if (direction != null) directionsList.remove(direction);
            return this;
        }
        public int getCount() { return directionsList.size(); }
        
        public boolean isDirectionSet(Direction testDirection) {
            return directionsList.indexOf(testDirection) >= 0;
        }
       
        // Sould be used as object-prefix for the directional change request-methods below:
        public DirectionList allign(Direction currentDirection) {
            int dirCount = getCount();
            int curDirIndex = directionsList.indexOf(currentDirection);
            while (curDirIndex > 0) {
                directionsList.add(dirCount, directionsList.get(0));
                directionsList.remove(0);
                curDirIndex--;
            }
            return this;
        }
       
        public Direction getOpposite() {
            return changeDirection(getCount() / 2);
        }
       
        public Direction getOther() {
            return changeDirection(1 + (int)(Math.random() * (getCount())));
        }
       
        public Direction getSlightChange() {
            if ((int)(Math.random() * 2) > 0) {
                return changeDirection(-1);
            }else{
                return changeDirection(1);
            }
        }
        
        private Direction changeDirection(int dirOffset) {
            if (getCount() == 0) {
                return null;
            }else{
                int dirIndex = (getCount() + dirOffset) % (getCount()==0 ? 1 : getCount());
                return directionsList.get(dirIndex);
            }
        }
    }
    
}
