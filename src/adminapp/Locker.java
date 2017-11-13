/*
 * Locker.java
 *
 * Created on 3 november 2007, 14:57
 *
 * 
 */
package adminapp;

import usb.ivi.USB_ivi_device;
import usb.ivi.Ivibot;

/**
 * Interface to the ivibot usb Device to lock and unlock a Locker
 * Uses ivibot library
 *
 * @author jand
 */
public class Locker {

    static public USB_ivi_device dev;
    private Ivibot bot; // voor motorsturing

    /** Creates a new instance of Locker */
    public Locker() {
        try {
            dev = new USB_ivi_device();
        } catch (Exception e) {
            System.err.println("USB_IVI DLL Exception catched - device disabled");
            dev = null;
            return;
        }
        dev.getPortA().setPortPinDirections((byte) 0);
        dev.getPortB().setPortPinDirections((byte) 0);
        dev.getPortEC().setPortPinDirections((byte) 0);
        // create ivibot object connected to device dev
        bot = new Ivibot(dev);
        motor_on();
    }

    /**
     * turn on motor of ivibot
     */
    private void motor_on() {
        bot.motor1_forward();
        bot.motor1_fast();
    }

    /** turn on all portB leds
     *
     */
    public void leds_on() {
        if (dev == null) { // no device
            System.err.println("no ivibot device");
            return;
        }
        //dev.getPortB().setPortPinDirections((byte)0);
        dev.getPortB().setPortPinValues((byte) 0xFF);
        motor_on();
    }

    /**
     * turn off all portB leds
     */
    public void leds_off() {
        if (dev == null) {
            System.err.println("no ivibot device");
            return;
        }
        dev.getPortB().setPortPinValues((byte) 0);
        bot.motor1_stop();
    }

    void sound() {
        dev.getPWM().setPWMFrequency(8000);
        //bot.motor1_fast();
        dev.getPortB().setPortPinValues((byte) 0xFF);
        //int i = 0;
                
        //while(true){
        //    dev.getPortA().setPortPinValues((byte)0xFF);
        //}
    }
    
    public void blink_leds() {
        if (dev == null) {
            System.err.println("no ivibot device");
            return;
        }
        
        for(int i = 0; i < 10; i++) {
            dev.getPortB().setPinValue(i, true);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        
        for(int i = 0; i < 3; i++) {
            dev.getPortA().setPortPinValues((byte) 0xFF);
            dev.getPortB().setPortPinValues((byte) 0xFF);
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            dev.getPortA().setPortPinValues((byte) 0);
            dev.getPortB().setPortPinValues((byte) 0);
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    void knightRider() {
        while(true) {

            
            for(int i = 7; i > 0; i--) {
                dev.getPortB().setPinValue(i, true);
                
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                
                dev.getPortB().setPortPinValues((byte) 0);
                
                try {
                    Thread.sleep(25);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                
            }
            
        }
    }
    void knightRideIn() {
             for(int i = 0; i < 7; i++) {
                dev.getPortB().setPinValue(i, true);
                
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                
                dev.getPortB().setPortPinValues((byte) 0);
                
                try {
                    Thread.sleep(25);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                
            }
    }
    
    void knightRideOut() {
            for(int i = 7; i > 0; i--) {
                dev.getPortB().setPinValue(i, true);
                
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                
                dev.getPortB().setPortPinValues((byte) 0);
                
                try {
                    Thread.sleep(25);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                
            }
    }
}
