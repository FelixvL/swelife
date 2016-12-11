/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.graphics.tilemapper;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import static runtime.control.JFXHelper.colorToHexCode;

/**
 *
 * @author Ron Olzheim
 * @version 1.3
 */
public class RenderEngine {
    //public TileMap tileMap = null;
    private ViewPort vp = null;
    private AnimationTimer aniTmr;
    private int fps = 50;
    private long lastFrameNs = 0;
    
    public RenderEngine() {}
    public RenderEngine(ViewPort viewPort) {
        this.vp = viewPort;
        
    }
    
    public void setFps(int fps) { this.fps = constrain(fps, 1, 200); }
    public int getFps() { return fps; }
    
    
    public void startRendering() {
        aniTmr = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (vp == null || vp.getGraphicsContext() == null) {
                    aniTmr.stop();
                }else if (now - lastFrameNs > (1000000000/fps) || now < lastFrameNs) {
                    lastFrameNs = now;
                    if (vp.isVisible) renderTiles();
                }
            }
            
        };
        aniTmr.start();
    }
    
    public void stopRendering() {
        aniTmr.stop();
    }
    
    
    //public void setTileMap(TileMap tileMap) { this.tileMap = tileMap; }
    //public TileMap getTileMap() { return tileMap; }
    public void setViewPort(ViewPort viewPort) { this.vp = viewPort; } 
    public ViewPort getViewPort() { return vp; }
    
        
    public void renderTiles() {
        assert(vp == null) : "Fatal Error: The ViewPort is not Set (null)!";
        TileMap tileMap = vp.getTileMap();
        assert(tileMap == null) : "Fatal Error: The TileMap property of the ViewPort is not Set (null)!";
        GraphicsContext gc = vp.getGraphicsContext();
        assert(gc == null) : "Fatal Error: The GraphicsContext of the ViewPort is null!";
        // Determine viewport scope:
        int startTileX = constrain(vp.getStartTileX(), 0, tileMap.getTileCountX());
        int startTileY = constrain(vp.getStartTileY(), 0, tileMap.getTileCountY());
        int endTileX = constrain(vp.getEndTileX(), 0, tileMap.getTileCountX() - startTileX);
        int endTileY = constrain(vp.getEndTileY(), 0, tileMap.getTileCountY() - startTileY);
        double offsCenterX = ((vp.getWidth() > (endTileX * tileMap.getTileSizeX() * vp.getZoomX()))) 
                ? ((vp.getWidth() - (endTileX * tileMap.getTileSizeX() * vp.getZoomX())) / 2) : 0;
        double offsCenterY = ((vp.getHeight() > (endTileY * tileMap.getTileSizeY() * vp.getZoomY()))) 
                ? ((vp.getHeight() - (endTileY * tileMap.getTileSizeY()  * vp.getZoomY())) / 2) : 0;
        // Erase Screen:
        gc.getCanvas().getParent().getParent().setStyle("-fx-background-color: " + colorToHexCode(tileMap.getBackColor()) + ";");
        gc.clearRect(0, 0, vp.getWidth()+100, vp.getHeight()+100);
        // Render Tiles:
        for (int x=0; x<endTileX; x++) {
            for (int y=0; y<endTileY; y++) {
                Tile curTile = tileMap.tiles.get(x+startTileX).get(y+startTileY);
                curTile.render(gc, 
                        offsCenterX + vp.getscrollbarXPixelShift() + vp.getTilePixelX(x), 
                        offsCenterY + vp.getscrollbarYPixelShift() + vp.getTilePixelY(y), 
                        vp.getTilePixelW(), vp.getTilePixelH());
            }
        }
    }
    
    
    
    private int constrain(int val, int min, int max) { return val < min ? min : val > max ? max : val; }
}
