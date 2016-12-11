/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runtime.control;

import java.util.ArrayList;
import java.util.List;


/**
 * Callback Event - Event Linking and Invokation Class
 * Class for creating, Registering and invoking multiple Callback Events for a Classes.
 *
 * @author Ron Olzheim
 * @version 1.1
 * 
 * @param <T> Generic type for passing detail information when invoking the Callback Event.
 */
public class CallbackEvent<T> {

    // Set DEBUG_MODE = true to enable debug information console output:
    private static final boolean DEBUG_MODE = false;
    
    /**
    * Callback Event - Event Listener Interface
    *
    * @author Ron Olzheim
    * @version 1.0
    * @param <T> Generic type for passing detail information when invoking the Callback Event.
    */
    public static interface Listener<T> {
        public void callback_Event(T detail);
    }
    
    
    private List<Listener<T>> listeners = new ArrayList<>();        // CallbackListeners stack.
    
    public CallbackEvent() { }
        
    /**
     * Adds a Callback Listener to the Event Caller stack.
     * @param listener pointer to the CallbackListener Interface.
     */
    public void addListener(Listener<T> listener) {
        dbgPrint(">>>> " + getInvokingClassName());
        for(Listener<T> iListener : listeners){
            if (iListener.equals(listener)) {
                dbgPrintln(" >> Listener already registered: " + listener.getClass().getName());
                return;
            }
        }
        listeners.add(listener);
        dbgPrintln(" >> Listener Added: " + listener.getClass().getName());
        dbgPrintln(">>>> Listeners Count: " + listeners.size());
    }
    
    /**
     * Removes a Callback Listener from the Event Caller stack.
     * @param listener pointer to the CallbackListener Interface.
     */
    public void removeListener(Listener listener) {
        dbgPrintln(">>>> " + getInvokingClassName() + " >> Listener Removed: " + listener.toString());
        listeners.remove(listener);
    }

    /**
     * Removes all Callback Listeners from the Event Caller stack
     */
    public void removeAllListeners() {
        dbgPrintln(">>>> " + getInvokingClassName() + " >> All Listeners Removed!");
        listeners.clear();
    }

    
    /**
     * Invokes a Callback Event which will fire the callback_Event for all registered Listener. 
     */
    public void invokeCallback() { invokeCallback(null); }
    /**
     * Invokes a Callback Event and pass detail information when firing the callback_Event for all registered Listeners. 
     * @param detail Generic type Event detail information, passed to the CallbackListener Event.
     */
    public void invokeCallback(T detail){
        dbgPrintln(">>>> " + getInvokingClassName() + " >>>> Listener(s) called: " + listeners.size());
        for(Listener<T> listener : listeners){
            dbgPrintln("   -> Listener: " + listener.getClass().getName());
            listener.callback_Event(detail);
        }
    }
    
    /**
     * Returns a string representation of the invoking class' name.
     * @return 
     */
    private String getInvokingClassName() {
        return this.getClass().getCanonicalName();
    }
    
    /**
     * Debug text-line output function, will write a text-line to the output console when DEBUG_MODE = true.  
     * @param txt Text to output to the console.
     */
    private void dbgPrintln(String txt) {
        if (DEBUG_MODE) System.out.println(txt);
    }
    
        /**
     * Debug text output function, will append text to the output console when DEBUG_MODE = true.  
     * @param txt Text to output to the console.
     */
    private void dbgPrint(String txt) {
        if (DEBUG_MODE) System.out.print(txt);
    }
}