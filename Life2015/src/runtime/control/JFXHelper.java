/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runtime.control;


import javafx.scene.paint.Color;
import java.util.ArrayList;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * JavaFX Helper Library Class
 * -Features Helper functions for JavaFX.
 * 
 * @author Ron Olzheim
 * @version 1.0
 */
public class JFXHelper {
        
     public static String colorToHexCode(Color color) {
        return String.format( "#%02X%02X%02X",
            (int)(color.getRed() * 255),
            (int)(color.getGreen() * 255),
            (int)(color.getBlue() * 255));
    }
     
    public static String getPackageID(Object obj) {
        if (obj != null) {
            return  obj.getClass().getPackage().getName().replace('.', '/');
        } else {
            return  "";
        }
    }
   
    
    
    
    
    public static class ImgHelper {
        public boolean setImg(ImageView iv, Image image) {
            try {
                iv.setImage(image);
                return true;
            }
            catch (Exception ex) {
                iv.setImage(null);
                return false;
            }
        }
        
        protected boolean setImgFromResFile(ImageView iv, String file) { return setImgFromResFile(iv, file, 80, 80, true); }
        protected boolean setImgFromResFile(ImageView iv, String file, int w, int h, boolean resize) {
            try {
                Image useImg = getImgFromResFile(file, w, h, resize); //new Image(resFileID, w, h, resize, resize);
                return setImg(iv, useImg);
            }
            catch (Exception ex) {    
                iv.setImage(null);
                //System.out.println("> SetImgFromResFile [" + iv.getId() + "] = " + file + " -> " + ex.toString());
                return false;
            }
        }
        protected boolean setImg(ToggleButton iv, Image image) {
            try {
                iv.setGraphic(new ImageView(image));
                return true;
            }
            catch (Exception ex) {
                iv.setGraphic(null);
                return false;
            }
        }
        protected boolean setImgFromResFile(ToggleButton iv, String file) { return setImgFromResFile(iv, file, 80, 80, true); }
        protected boolean setImgFromResFile(ToggleButton iv, String file, int w, int h, boolean resize) {
            try {
                Image useImg = getImgFromResFile(file, w, h, resize);
                return setImg(iv, useImg);
            }
            catch (Exception ex) {    
                iv.setGraphic(null);
                //System.out.println("> SetImgFromResFile [" + iv.getId() + "] = " + file + " -> " + ex.toString());
                return false;
            }
        }

        protected Image getImgFromResFile(String file) { return getImgFromResFile(file, 80, 80, true); }
        public Image getImgFromResFile(String file, int w, int h, boolean resize) {
            String resFileID = cleanupPathSegments(file);
            try {
                Image getImg = new Image(resFileID, w, h, resize, resize);
                //System.out.println(">>>> getImgFromResFile(" + resFileID + ")");
                return getImg;
            }
            catch (Exception ex) {    
                //System.out.println("> getImgFromResFile(" + resFileID + ") -> " + ex.toString());
                return null;
            }
        }


        // This function removes "." and processes level-up ".." from a path string:
        protected String cleanupPathSegments(String path) {
            String sSegm[];
            sSegm = path.split("/");
            ArrayList<String> nSegm = new ArrayList<>();
            for (int i=0; i<sSegm.length ; i++) {
                if (sSegm[i].equalsIgnoreCase("..")) {
                    if (nSegm.size()>0) nSegm.remove(nSegm.size()-1);
                }
                else if (!sSegm[i].equalsIgnoreCase(".") && !sSegm[i].isEmpty()) {
                    nSegm.add(sSegm[i]);
                }
            }
            return String.join("/", nSegm);
        }
    }


}
