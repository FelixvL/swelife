/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.world.domain;

import app.graphics.tilemapper.TileMap;
import app.helper.SimplexNoise;
import javafx.scene.paint.Color;


/**
 *
 * @author Ron Olzheim
 * @version 1.0
 */
public class World {
    private TileMap tileMap;
    private SimplexNoise gen1;
    private SimplexNoise gen2;
    
    
    public World(TileMap tileMap) {
        this.tileMap = tileMap;
    }
    
    public void generateMap() {
        int width = tileMap.getTileCountX();
        int height = tileMap.getTileCountY();
        
        //float rng1 = PM_PRNG.create(seed1);
        //float rng2 = PM_PRNG.create(seed2);
        //gen1 = new SimplexNoise(rng1.nextDouble.bind(rng1));
        //gen2 = new SimplexNoise(rng2.nextDouble.bind(rng2));

        for (double y = 0; y < height; y++) {
            for (double x = 0; x < width; x++) {      
                double nx = x/width - 0.5, ny = y/height - 0.5;
                double e = (1.00 * noise1( 1 * nx,  1 * ny)
                       + 0.50 * noise1( 2 * nx,  2 * ny)
                       + 0.25 * noise1( 4 * nx,  4 * ny)
                       + 0.13 * noise1( 8 * nx,  8 * ny)
                       + 0.06 * noise1(16 * nx, 16 * ny)
                       + 0.03 * noise1(32 * nx, 32 * ny));
                e /= (1.00+0.50+0.25+0.13+0.06+0.03);
                e = Math.pow(e, 5.00);
                double m = (1.00 * noise2( 1 * nx,  1 * ny)
                       + 0.75 * noise2( 2 * nx,  2 * ny)
                       + 0.33 * noise2( 4 * nx,  4 * ny)
                       + 0.33 * noise2( 8 * nx,  8 * ny)
                       + 0.33 * noise2(16 * nx, 16 * ny)
                       + 0.50 * noise2(32 * nx, 32 * ny));
                m /= (1.00+0.75+0.33+0.33+0.33+0.50);
                /* draw biome(e, m) at x,y */
                //System.out.print(e + " " + m + "   ");
                if (m > 0.4) {
                    // Surface:
                    Color col = Color.rgb((int)(e * 0xFF), (int)(m * 0xFF), 0);
                    tileMap.getTile((int)x, (int)y).setSurface(new TileLand(col));
                }else{
                // Water:
                    Color col = Color.rgb((int)(e * 0xFF), (int)(m/1.5 * 0xFF), (int)(m * 0xFF));
                    tileMap.getTile((int)x, (int)y).setSurface(new TileWater(col));
                }
                // Obstacle:
                if (m > 0.7) tileMap.getTile((int)x, (int)y).setObstacle(new TileObstacle());
            }
        }
    }
    private double noise1(double nx, double ny) { return SimplexNoise.noise(nx, ny)/2 + 0.5; }
    private double noise2(double nx, double ny) { return SimplexNoise.noise(nx, ny)/2 + 0.5; }
}

