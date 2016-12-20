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
 * @version 0.1
 */
public class InteractionResult {
    public class LevelKind {
        private int level;
        public LevelKind(int level) {
            this.level = level;
        }
        public int get() { return level; }
    }
    
        
    public class AttackDamage extends LevelKind {
        private TileCreature attackingCreature;
        public AttackDamage(int damage, TileCreature attackingCreature) {
            super(damage);
            this.attackingCreature = attackingCreature;
        }
        @Override
        public int get() { return -super.get(); }     
    }
    
    public class FoodEnergy extends LevelKind {
        private EnergySource source;
        public FoodEnergy(int energy, EnergySource source) {
            super(energy);
            this.source = source;
        }
    }
    
    public class EatResult {
        public AttackDamage damage;
        public FoodEnergy energy;
        public EatResult(AttackDamage damage, FoodEnergy energy) {
            this.damage = damage;
            this.energy  = energy;
        }
    }

    public interface EnergySource {
        EatResult Eat(int hunger, int attackPower);
        boolean isPlant();
        boolean isCreature();
        boolean isSibling();
    }
    
}
