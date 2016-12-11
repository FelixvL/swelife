/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.helper;

import javafx.scene.SnapshotParameters;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.canvas.GraphicsContext;
import runtime.control.CallbackEvent;
//import javafx.scene.image.Image;
//import javafx.geometry.Point2D;



/**
 * A Canvas Graphics Backbuffer which can retain and restore and rescale canvas content.
 * @author Ron Olzheim
 * @version 1.0
 */
public class CanvasBackBuffer {
    public CallbackEvent<CanvasBackBuffer> event_BufferChanged = new CallbackEvent<>();
    
    private WritableImage srcImage;
    private Canvas srcCanvas;
    private GraphicsContext srcGC;
    private double srcW = 20;
    private double srcH = 20;
    private boolean containsImg = false;
    private boolean changed = false;
    
    
    
    public CanvasBackBuffer() {
        setBufferSize(srcW, srcH);
        changed = false;
        containsImg = false;
    }
    public CanvasBackBuffer(double srcW, double srcH) {
        setBufferSize(srcW, srcH);
        changed = false;
        containsImg = false;
    }
    public CanvasBackBuffer(Canvas srcCanvas) {
        setBufferFromCanvas(srcCanvas);
        changed = false;
    }
    
    
    public boolean isChanged() { return isChanged(false); }
    public boolean isChanged(boolean latchChangeState) { 
        boolean retState = changed; 
        if (!latchChangeState) changed = false; 
        return retState; 
    }
    
    public void setChanged() { setChanged(true); }
    public void setChanged(boolean notifyChangeListeners) {
        changed = true;
        if (notifyChangeListeners) event_BufferChanged.invokeCallback(this);
    }
    
    public void clearChanged() { changed = false; }
    
    public boolean isSet() { return containsImg; } 
    
    
    public double getWidth() { return srcW; }
    public double getHeight() { return srcH; }
    public Canvas getAsCanvas() { return srcCanvas; }
    public GraphicsContext getAsGraphicsContext() { return srcGC; }
    public void revertAsGraphicsContent() {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);         
        //WritableImage capture 
        srcImage =srcCanvas.snapshot(params, null);
        containsImg = false;
        setChanged();
     }
    
    public void setBufferSize(double width, double height) {
        srcW = width;
        srcH = height;
        srcCanvas = new Canvas(srcW, srcH);
        srcGC = srcCanvas.getGraphicsContext2D();
        changed = true;
    }
    
    public void setBufferFromCanvas(Canvas srcCanvas) {
        setBufferSize(srcCanvas.getWidth(), srcCanvas.getHeight());
        copyCanvas(srcCanvas, this.srcCanvas);
        containsImg = true;
    }
    
    
    public void renderBuffer(GraphicsContext graphicsContext) 
        { renderImage(graphicsContext); }
    public void renderBuffer(GraphicsContext graphicsContext, double x, double y, double w, double h) 
        { renderImage(graphicsContext, x, y, w, h); }
    public void renderBuffer(Canvas dstCanvas) 
        { renderImage(dstCanvas); }
    public void renderBuffer(Canvas dstCanvas, double x, double y, double w, double h) 
        { renderImage(dstCanvas, x, y, w, h); }

    public void eraseBuffer() { eraseBuffer(Color.TRANSPARENT); }
    public void eraseBuffer(Color color) { eraseCanvas(srcCanvas, color); changed = true; containsImg = false; }
    
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    
    private void copyCanvas(Canvas fromCanvas, Canvas toCanvas) 
        { copyCanvas(fromCanvas, toCanvas.getGraphicsContext2D(), 0, 0, toCanvas.getWidth(), toCanvas.getHeight()); }
    private void copyCanvas(Canvas fromCanvas, Canvas toCanvas, double x, double y, double w, double h) 
        { copyCanvas(fromCanvas, toCanvas.getGraphicsContext2D(), x, y, w, h); }
    private void copyCanvas(Canvas fromCanvas, GraphicsContext toGc) 
        { copyCanvas(fromCanvas, toGc, 0, 0, toGc.getCanvas().getWidth(), toGc.getCanvas().getHeight()); }
    private void copyCanvas(Canvas fromCanvas, GraphicsContext toGc, double x, double y, double w, double h) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);         
        //WritableImage capture 
        srcImage = fromCanvas.snapshot(params, null);
        toGc.drawImage(srcImage, x, y, w, h);
    }

    private void renderImage(Canvas toCanvas) 
        { renderImage(toCanvas.getGraphicsContext2D(), 0, 0, toCanvas.getWidth(), toCanvas.getHeight()); }
    private void renderImage(Canvas toCanvas, double x, double y, double w, double h) 
        { renderImage(toCanvas.getGraphicsContext2D(), x, y, w, h); }
    private void renderImage(GraphicsContext toGc) 
        { renderImage(toGc, 0, 0, toGc.getCanvas().getWidth(), toGc.getCanvas().getHeight()); }
    private void renderImage(GraphicsContext toGc, double x, double y, double w, double h) {
        /*
        double x = (x0 > x1) ? x1 : x0;
        double y = (y0 > y1) ? y1 : y0;
        double w = (x0 > x1) ? x0-x1 : x1-x0;
        double h = (y0 > y1) ? y0-y1 : y1-y0;
        */
        toGc.drawImage(srcImage, x, y, w, h);
    }
    
    
    
    private void eraseCanvas(Canvas dstCanvas, Color color) {
        GraphicsContext gc = dstCanvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillRect(0, 0, dstCanvas.getWidth(), dstCanvas.getHeight());
    }
    
}
