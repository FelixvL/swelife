/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.graphics.tilemapper;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import runtime.control.CallbackEvent;

/**
 * ViewPort Class
 * -Provides the Graphical ViewPort for a TileMap Class. 
 * 
 * @author Ron Olzheim
 * @version 1.1
 */
public class ViewPort {
    TileMap tm = null;
    Node parentNode = null;
    GraphicsContext gc = null;
    double width = 800;
    double height = 600;
    double offsetX = 0;
    double offsetY = 0;
    double zoomX = 1;
    double zoomY = 1;
    private double autoZoomX = 1;
    private double autoZoomY = 1;
    private boolean autoZoomEnabled = false;
    public boolean isVisible = true;
    
    public CallbackEvent<ViewPort> modifyEvent = null;
    public CallbackEvent<ViewPort> resizeEvent = null;
    public CallbackEvent<ViewPort> redrawEvent = null;
    public CallbackEvent<ViewPort> updateScrollbarX = null;
    public CallbackEvent<ViewPort> updateScrollbarY = null;
    
    
   // public ViewPort(TileMap tm) { this(tm, null); }
    public ViewPort(Node parentNode, TileMap tm) {
        assert(tm == null) : "Fatal Error: Passed TileMap is null!";
        gc = ((Canvas)parentNode).getGraphicsContext2D();
        this.tm = tm;
        modifyEvent = new CallbackEvent<>();
        resizeEvent = new CallbackEvent<>();
        redrawEvent = new CallbackEvent<>();
        updateScrollbarX = new CallbackEvent<>();
        updateScrollbarY = new CallbackEvent<>();
        this.parentNode = parentNode;
    }
    
    public GraphicsContext getGraphicsContext() { return gc; }
    
    public TileMap getTileMap() { return tm; }
    public void setTileMap(TileMap tileMap) { tm = tileMap; }
    
    public void modify(double offsetX, double offsetY, double zoomX, double zoomY) {
        boolean updated = false;
        if (offsetX > 0) {
            updated = true;
            this.offsetX = offsetX;
        }
        if (offsetY > 0) {
            updated = true;
            this.offsetY = offsetY;
        }
        if (zoomX > 0 && zoomX != this.zoomX) {
            updated = true;
            double zoomFactX = this.zoomX / zoomX;
            this.offsetX = (this.offsetX * zoomFactX);
            this.zoomX = zoomX;
            refreshAutoZoom();
            updateScrollbarX.invokeCallback(this);
        }
        if (zoomY > 0 && zoomY != this.zoomY) {
            updated = true;
            double zoomFactY = this.zoomY / zoomY;
            this.offsetY = (this.offsetY * zoomFactY);
            this.zoomY = zoomY;
            refreshAutoZoom();
            updateScrollbarY.invokeCallback(this);
        }
        if (updated) {
            modifyEvent.invokeCallback(this);
            refreshAutoZoom();
            redrawEvent.invokeCallback(this);
        }
    }
/*
    private double zoomLog(double zoom) {
        return 0.1 + (zoom / Math.pow(2, (10-zoom)));
    }
 */ 
    public void resize(double width, double height) {
        boolean updated = false;
        if (width > 0 && width != this.width) {
            updated = true;
            this.width = width;
            updateScrollbarX.invokeCallback(this);
        }
        if (height > 0 && height != this.height) {
            updated = true;
            this.height = height;
            updateScrollbarY.invokeCallback(this);
        }
        if (updated) {
            resizeEvent.invokeCallback(this);
            refreshAutoZoom();
            redrawEvent.invokeCallback(this);
        }
    }
    
    public double getOffsetX() {
        return offsetX * zoomX;
    }
    
    public double getOffsetY() {
        return offsetY * zoomY;
    }
    
    public double getWidth() {
        //if (parentNode != null) return parentNode.getLayoutBounds().getWidth();
        return width;
    }
    
    public double getHeight() {
        //if (parentNode != null) return parentNode.getLayoutBounds().getHeight();
        return height;
    }
    
    public int getTileMapWrapArroundX(int x) { return (x + getTileMap().getTileCountX()) % getTileMap().getTileCountX(); }
    public int getTileMapWrapArroundY(int y) { return (y + getTileMap().getTileCountY()) % getTileMap().getTileCountY(); }
    
    public double getscrollbarXMax() {
        double max = (zoomX * (tm.getTileSizeX()) * tm.getTileCountX()) - width;
        return max < 0 ? 0 : max;
    }
    
    public double getscrollbarYMax() {
        double max = (zoomY * (tm.getTileSizeY()) * tm.getTileCountY()) - height;
        return max < 0 ? 0 : max;
    }
        
    public double getScrollbarXVal() {
        double scrollbarXMax = getscrollbarXMax();
        if (offsetX > scrollbarXMax) offsetX = scrollbarXMax;
        return offsetX;
    }
    
    public double getScrollbarYVal() {
        double scrollbarYMax = getscrollbarYMax();
        if (offsetY > scrollbarYMax) offsetY = scrollbarYMax;
        return offsetY;
    }
    
    public double getscrollbarXPixelShift() {
        return 1 - (offsetX % (tm.getTileSizeX() * zoomX));
    }
    
    public double getscrollbarYPixelShift() {
        return 1 - (offsetY % (tm.getTileSizeY() * zoomY));
    }
    
    public void setAutoZoomEnabled(boolean enable) { 
        autoZoomEnabled = enable; 
        refreshAutoZoom();
        redrawEvent.invokeCallback(this);
    }
    public boolean getAutoZoomEnabled() { return autoZoomEnabled; }
    
    public double getZoomX() { return autoZoomEnabled ? zoomX * autoZoomX : zoomX; }
    public double getZoomY() { return autoZoomEnabled ? zoomY * autoZoomY : zoomY; }
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    private void refreshAutoZoom() {
        if (!autoZoomEnabled || tm == null) return;
        double fitScale = 1;
        if (width < height) {
            double totMapW = getTotalMapWidth();
            fitScale = width / totMapW;
            if (fitScale < 1) fitScale = 1;  
        }else{
            double totMapH = getTotalMapHeight();
            fitScale = height / totMapH;
            if (fitScale < 1) fitScale = 1;              
        }
        autoZoomX = fitScale;
        autoZoomY = fitScale;
    }
    
    
    
    public int getStartTileX() { return (int)(offsetX / (tm.getTileSizeX() * getZoomX())); }
    public int getStartTileY() { return (int)(offsetY / (tm.getTileSizeY() * getZoomY())); }
    public int getEndTileX() { return (int)(2 + (getWidth() / (tm.getTileSizeX() * getZoomX()))); }
    public int getEndTileY() { return (int)(2 + (getHeight() / (tm.getTileSizeY() * getZoomY()))); }  
    
    double getTilePixelX(int tileColumn) { return (getZoomX() * tm.getTileSizeX()) * tileColumn; }
    double getTilePixelY(int tileRow) { return (getZoomY() * tm.getTileSizeY()) * tileRow; }
    double getTilePixelW() { return getZoomX() * tm.getTileSizeX(); }
    double getTilePixelH() { return getZoomY() * tm.getTileSizeY(); }
    
    
    public double getTotalMapWidth() { 
        return (constrain(getEndTileX(), 0, tm.getTileCountX() - (constrain(getStartTileX(), 0, 
                tm.getTileCountX())))) * tm.getTileSizeX() * zoomX; 
    }
    public double getTotalMapHeight() { 
        return (constrain(getEndTileY(), 0, tm.getTileCountY() - (constrain(getStartTileY(), 0, 
                tm.getTileCountY())))) * tm.getTileSizeY() * zoomY; 
    }
    
    
    
    
    private int constrain(int val, int min, int max) { return val < min ? min : val > max ? max : val; }
}
