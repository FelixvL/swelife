/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runtime.control;

import java.util.ArrayList;


/**
 * String Helper Library Class
 *
 * @author Ron Olzheim
 * @version 1.0
 */
public class StringHelper {
    private StringHelper() {}
    
    // Returns true if containsTxt is found within checkTxt:
    public static boolean contains(String checkTxt, String containsTxt) {
        
        return checkTxt.matches("^.*" + containsTxt + ".*$");
    }
    
    
    // This function removes "." and processes level-up ".." from a path string:
    public static String cleanupPathSegments(String path) {
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
        return String.join("/", nSegm) + "/";
    }

}
