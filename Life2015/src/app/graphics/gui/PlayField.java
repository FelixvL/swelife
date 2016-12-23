package app.graphics.gui;

import app.graphics.tilemapper.LifeEngine;
import app.graphics.tilemapper.RenderEngine;
import app.graphics.tilemapper.TileMap;
import app.graphics.tilemapper.ViewPort;
import app.world.domain.World;
import javafx.scene.canvas.Canvas;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import runtime.control.CallbackEvent;
import runtime.control.JFXHelper;
import static runtime.control.JFXHelper.getPackageID;



/**
 * LIFE 2015 - FHICT - SWE Casus
 * -JavaFX PlayField GUI Component Class
 * -This is the destination Output window for rendering a TileMap Class.
 * 
 * @author Ron Olzheim
 * @version 1.0
 */
public class PlayField extends Application {
    private static final String windowTitle = "LIFE_2015 - GUI Callback-demo";
    private static final double windowMinW = 250;
    private static final double windowMinH = 200;
    private TileMap map = null;
    private ViewPort view = null;
    private RenderEngine engine = null;
    private LifeEngine lEngine = null;
    private World world = null;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    
    public PlayField() {}
    
    
    // Initialize GUI JavaFX Scene and Stage and handle Window Events:
    @Override
    public void start(Stage primaryStage) {
        
        // Construct Root Window Container Pane:
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 400, 400);
        scene.getStylesheets().add(getPackageID(this) + "/PlayField.css");
        
        
        //#########################################################################################
        //   GUI CONTROLS LAY-OUT:
        //#########################################################################################
        // Construct center canvas playfield pane:
        Pane outputPane = new Pane();
        Canvas output = new Canvas(40, 40);
        output.setEffect(new GaussianBlur(2));
        
        map = new TileMap(81, 81, Color.DARKBLUE);
        view = new ViewPort(output, map);
        engine = new RenderEngine(view);
        lEngine = new LifeEngine(view);
        world = new World(map);
        world.generateMap();
        
        outputPane.getChildren().add(output);
        root.setCenter(outputPane);   
        
        // Construct Top-Bar pane:
        HBox topBarPane = new HBox();
        topBarPane.setMaxWidth(10000);
        topBarPane.setPadding(new Insets(15, 12, 15, 12));
        topBarPane.setSpacing(10);
        topBarPane.setStyle("-fx-background-color: #336699;");
        Label scrollbarZLbl = new Label("ZOOM:");
        scrollbarZLbl.setTextFill(Color.CYAN);
        ScrollBar scrollbarZ = new ScrollBar();
        scrollbarZ.setMin(0.01);
        scrollbarZ.setMax(10);
        scrollbarZ.setPrefWidth(150); //scrollbarZ.setPrefWidth(scene.getWidth() - scrollbarZ.getWidth());
        CheckBox chkBoxAutoZoom = new CheckBox("AUTO ZOOM");
        chkBoxAutoZoom.setStyle("cmn-toggle-round: 10px;");
        chkBoxAutoZoom.setTextFill(Color.CYAN);
        topBarPane.getChildren().add(scrollbarZLbl);
        topBarPane.getChildren().add(scrollbarZ);
        topBarPane.getChildren().add(chkBoxAutoZoom);
        root.setTop(topBarPane);    
        
        // Construct Side-Bar pane:
        VBox sideBarPane = new VBox();
        sideBarPane.setMaxHeight(10000);
        sideBarPane.setPadding(new Insets(0, 15, 0, 15));
        sideBarPane.setSpacing(10);
        sideBarPane.setStyle("-fx-background-color: #336699;");
        ScrollBar scrollbarY = new ScrollBar();
        scrollbarY.setMin(0);
        scrollbarY.setMax(1000);
        scrollbarY.setOrientation(Orientation.VERTICAL);
        scrollbarY.setPrefHeight(scene.getHeight() - scrollbarY.getHeight());       
        sideBarPane.getChildren().add(scrollbarY);
        root.setRight(sideBarPane);      
        
        // Construct Bottom-Bar pane:
        VBox bottomBarPane = new VBox();
        bottomBarPane.setMaxHeight(10000);
        bottomBarPane.setPadding(new Insets(15, 12, 15, 12));
        bottomBarPane.setSpacing(10);
        bottomBarPane.setStyle("-fx-background-color: #336699;");
        ScrollBar scrollbarX = new ScrollBar();
        scrollbarX.setMin(0);
        scrollbarX.setMax(1000);
        scrollbarX.setPrefWidth(scene.getWidth() - scrollbarX.getWidth());       
        bottomBarPane.getChildren().add(scrollbarX);
        root.setBottom(bottomBarPane);
        
        
        //#########################################################################################
        //   CONTROL EVENT HANDLERS:
        //#########################################################################################
        // Handle scrollbarX (cursor X offset) Value Change Event: 
        scrollbarX.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    view.modify(new_val.doubleValue(), 0, 0, 0);
            }
        });
        
        // Handle scrollbarY (cursor Y offset) Value Change Event: 
        scrollbarY.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    view.modify(0, new_val.doubleValue(), 0, 0);
            }
        });
        
        // Handle scrollbarZ (viewport zoom factor) Value Change Event: 
        scrollbarZ.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    view.modify(0, 0, new_val.doubleValue(), new_val.doubleValue());
            }
        });
        
        // Handle Auto-Zoom check-box Value change event:
        chkBoxAutoZoom.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                view.setAutoZoomEnabled(newValue);
            } 
        });
        
        // Handle Window Width Resize Event: 
        ChangeListener<Number> SceneWidthResizeListener = new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (primaryStage.getWidth() < windowMinW) primaryStage.setWidth(windowMinW);
                scrollbarX.setPrefWidth(newValue.doubleValue() - (2 * scrollbarX.getLayoutX()));
                view.resize(scrollbarX.getPrefWidth() - (2 * scrollbarX.getLayoutX()), 0);
            }
        };
        scene.widthProperty().addListener(SceneWidthResizeListener);
        
        // Handle Window Height Resize Event:    (Lambda syntax.)
        ChangeListener<Number> SceneHeightResizeListener = 
            (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                if (primaryStage.getHeight() < windowMinH) primaryStage.setHeight(windowMinH);
                scrollbarY.setPrefHeight(newValue.doubleValue() - (2 * scrollbarY.getLayoutY()));
                view.resize(0, scrollbarY.getPrefHeight() - (2 * scrollbarY.getParent().getLayoutY()));
        };
        scene.heightProperty().addListener(SceneHeightResizeListener);
             
        // Handle ViewPort Properties Modify Event:
        view.modifyEvent.addListener(new CallbackEvent.Listener<ViewPort>() {
            public void callback_Event(ViewPort vp) {
                /// System.out.println("modify");
            }
        });
        
        // Handle ViewPort Resize Event:
        view.resizeEvent.addListener(new CallbackEvent.Listener<ViewPort>() {
            public void callback_Event(ViewPort vp) {
                output.setWidth(vp.getWidth());
                output.setHeight(vp.getHeight());
                //System.out.println("resize: " + root.getWidth() + ", " + root.getHeight());
            }   
        });
        
        // Handle ViewPort Redraw Event:
        view.redrawEvent.addListener(new CallbackEvent.Listener<ViewPort>() {
            public void callback_Event(ViewPort vp) {
                //engine.renderTiles();
               // System.out.println("redraw");
            }   
        });
        
        // Handle ViewPort updateScrollbarX Event:
        view.updateScrollbarX.addListener(new CallbackEvent.Listener<ViewPort>() {
            public void callback_Event(ViewPort vp) {
                double maxValX = vp.getscrollbarXMax();
                double valX = vp.getScrollbarXVal();
                scrollbarX.setMax(maxValX);
                scrollbarX.setVisible(maxValX > 0);
                scrollbarX.setValue(vp.getScrollbarXVal());
                //System.out.println("updateScrollbarX: "+vp.getScrollbarXVal()+" - "+maxValX);
            }   
        });
        
        // Handle ViewPort updateScrollbarY Event:    (Lambda syntax.)
        view.updateScrollbarY.addListener((ViewPort vp) -> {
            double maxValY = vp.getscrollbarYMax();
            scrollbarY.setMax(maxValY);
            scrollbarY.setVisible(maxValY > 0);
            scrollbarY.setValue(vp.getScrollbarYVal());
            //System.out.println("updateScrollbarY");
        });
        
        root.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getText().equalsIgnoreCase("r")) {
                    world.generateMap();
                }else if (event.getText().equalsIgnoreCase("l")) {
                    lEngine.testCreature(10);
                }
            }
            
        });


        //#########################################################################################
        //   LOAD GUI STAGE WINDOW:
        //#########################################################################################
        // Initialize Control Values:
        scrollbarZ.setValue(0.5);
        
        // Initialize Stage and load Window:
        JFXHelper.ImgHelper imgHelper = new JFXHelper.ImgHelper(); 
        Image windowIcon = imgHelper.getImgFromResFile(
                getPackageID(this) + "/../../../app/resource/life.png", 80, 80, true);
        if (windowIcon != null) {
            primaryStage.getIcons().add(windowIcon);
        }else{
            System.out.println("#### ICON RESOURCE NOT FOUND! <<<<");
        }
        primaryStage.setTitle(windowTitle);
        primaryStage.setScene(scene);
        primaryStage.setWidth(400);
        primaryStage.setHeight(400);
        primaryStage.setMinWidth(windowMinW);
        primaryStage.setMinHeight(windowMinH);
        primaryStage.show();
        
        
        lEngine.testCreature(40);
       // lEngine.setTimerInterval(500);
        lEngine.startSimulation();
        engine.startRendering();
    }
    
    
/*
    private void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);
        gc.fillOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                       new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90},
                         new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140},
                          new double[]{210, 210, 240, 240}, 4);
    }
  */  
}