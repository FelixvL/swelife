/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runtime.control;

//import java.util.Timer;

import javafx.application.Platform;

//import java.util.TimerTask;
//import static runtime.control.Test.counter;

/**
 *
 * @author Ron Olzheim
 * @version 1.1
 */
public final class TimerEvent {
    private boolean callbackThreadRunning = false;
    private boolean repeatMode = false;
    private long curMillis = 0;
    private long lastMillis = 0;
    private long endMillis = 1000;
    private boolean running = false;
    private boolean trig = false;
    private boolean trigCallback = false;
    
    public CallbackEvent<TimerEvent> callbackEvent = new CallbackEvent<>();
    public interface Listener extends CallbackEvent.Listener<TimerEvent> {
        public void callback_Event(TimerEvent detail);
    } 
    
    public TimerEvent() {
        reset();
    }
    public TimerEvent(long millis) { 
        this(); 
        setTimeOut(millis);
    }
    public TimerEvent(long millis, boolean repeatMode) { 
        this(); 
        setTimeOut(millis);
        setRepeatMode(repeatMode);
    }
    public TimerEvent(long millis, boolean repeatMode, boolean runAsTriggerCallbackThread) { 
        this(); 
        setTimeOut(millis);
        setRepeatMode(repeatMode);
        if (runAsTriggerCallbackThread)  setTriggerCallbackThread(); 
    }
    
    public void dispose() {
        running = false;
        callbackEvent.removeAllListeners();
    }
    
    
    public TimerEvent setRepeatMode(boolean newValue) { repeatMode = newValue; return this; }
    public boolean getRepeatMode() { return repeatMode; }
    
    public TimerEvent setTimeOut(long millis) { endMillis = Math.abs(millis); return this; }
    public long getTimeOut() { return endMillis; }
    
    public long getRuntime() { return curMillis; }
    public long getRemaining() { return endMillis - curMillis; }
    
    public TimerEvent startTriggerCallbackThread() { setTriggerCallbackThread(); return this; }
    public boolean istTriggerCallbackThreadRunning() { return callbackThreadRunning; }

    
    
    public TimerEvent reset() {
        lastMillis = 0;
        curMillis = 0;
        trig = false;
        trigCallback = false;
        running = false;
        return this;
    }
    
    public void start() {
        if (!running) {
            if (lastMillis > 0) {
                lastMillis = getMillis() - curMillis;
            }else{
                lastMillis = getMillis();
            }
        }
        running = true;
        //setlTriggerCallbackThread();
        //eval();
    }
    
    public void startCallbackThread() {
        running = true;
        setTriggerCallbackThread();
    }
    
    public void restart() {
        lastMillis = getMillis();
        running = true;
        eval();
    }
    
    public void stop() {
        running = false;
    }
    
    public TimerEvent triggerReset() {
        trig = false;
        trigCallback = false;
         return this;
    }
    
    public boolean isRunning() { 
        eval();
        return running; 
    }
    
    public boolean isTriggered() {
        eval();
        return trig;
    }
    
    
    public long evaltriggerCallbacks() {
        long retVal = eval();
        if (trigCallback) {
            callbackEvent.invokeCallback(this);
            trigCallback = false;
        }
        return retVal;
    }
    
    
    private void setTriggerCallbackThread() {
        if (callbackThreadRunning) return;
        if (endMillis > 0 && endMillis < 5000) {
            System.out.println(">> Entering TimerEvent.setTriggerCallbackThread: " + evaltriggerCallbacks());
            //create thread to fire callback events periodically:
            callbackThreadRunning = true;
            Thread t = new Thread(new Runnable() {
                
                @Override public void run() {
                    Platform.runLater(new Runnable(){
                        @Override public void run() {
                            while (running) {
                            try {
                                Thread.sleep(endMillis);
                                eval();
                                //evaltriggerCallbacks();
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                        
                        }    
                    
                    });
                    
                }
                 
            });
            callbackThreadRunning = false;
            //t.run();
        }else{
            eval();
        }
    }
  
    
    private long eval() {
        if (lastMillis > 0) {
            if (running) {
                curMillis = getMillis() - lastMillis;
                allignTimer();
                if (curMillis > endMillis) {
                    trig = true;
                    trigCallback = true;
                    if (repeatMode) {
                        lastMillis += endMillis;
                        curMillis -= endMillis;
                    }else{
                        running = false;
                        curMillis = endMillis;
                    }
                }
            }
            return curMillis;
        }else{
            return 0;
        }
    }
    
    private long getMillis() {
        return System.currentTimeMillis();
    }
    
    private void allignTimer() {
        if (curMillis < 0) { 
            // Timer reoccurence allignment
        }
    }
}

