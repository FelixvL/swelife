/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.world.domain;

import java.util.ArrayList;

/**
 *
 * @author Ron Olzheim
 * @version 1.1
 */
public enum Direction {
    UP         (0),
    UP_RIGHT   (1),
    RIGHT      (2),
    RIGHT_DOWN (3),
    DOWN       (4),
    DOWN_LEFT  (5),
    LEFT       (6),
    LEFT_UP    (7);
    
    private static final Direction[] typeLookup = Direction.values();
    private final int move; 
    Direction(int move) { this.move = move; }
    int getVal() { return move; }
    public static int getCount() { return typeLookup.length; }
    int getXOffset() { return move > 0 && move < 4 ? 1 : move > 4 && move < 9 ? -1 : 0; }
    int getYOffset() { return move < 2 || move > 6 ? -1 : move > 2 && move < 6 ? 1 : 0; }
    public static Direction getEnumFromVal(int val) { return typeLookup[val]; }
    
    
    public static class DirectionList{
        private ArrayList<Direction> possibleDirections = new ArrayList<>();
        public DirectionList() {}
        public DirectionList(ArrayList<Direction> possibleDirections) {
            this.possibleDirections = possibleDirections;
        }
        public DirectionList addDirection(Direction addDirection) {
            if (addDirection != null) possibleDirections.add(addDirection);
            return this;
        }
        public int getCount() { return possibleDirections.size(); }
        
        public boolean isDirectionSet(Direction testDirection) {
            return possibleDirections.indexOf(testDirection) >= 0;
        }
       
        // Sould be used as object-prefix for the directional change request-methods below:
        public DirectionList allign(Direction currentDirection) {
            int dirCount = getCount();
            int curDirIndex = possibleDirections.indexOf(currentDirection);
            while (curDirIndex > 0) {
                possibleDirections.add(dirCount, possibleDirections.get(0));
                possibleDirections.remove(0);
                curDirIndex--;
            }
            return this;
        }
       
        public Direction getOpposite() {
            return changeDirection(getCount() / 2);
        }
       
        public Direction getOther() {
            return changeDirection(1 + (int)(Math.random() * (getCount()-1)));
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
                return possibleDirections.get(dirIndex);
            }
        }
    }
    
}
