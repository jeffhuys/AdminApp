/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import acs.jni.ACR120U;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Razzy
 */
public class ReaderHandeling {

    protected ACR120U cardReader;
    protected short handle;
    protected short tag;
    
    protected byte[] serialNumber = new byte[10];

    protected void select() {


        byte[] pLength = new byte[1];
        byte[] pTagType = new byte[1];
        byte[] pSerialNumber = new byte[10];

        tag = cardReader.select(handle, pTagType, pLength, pSerialNumber);
       
        serialNumber = pSerialNumber;

    }

    protected String readBlock(byte blocknumber) {
        byte[] data = new byte[16];
        cardReader.read(handle, blocknumber, data);
        String s = new String(data);
       
        return s;
    }

    protected void writeBlock(byte blocknumber, byte data[]) {
        cardReader.write(handle, blocknumber, data);

    }
    
      public void writeString(String s, byte blocknr) {
       
        s = s + "                "; // padding in case len < 16
        byte[] b = s.getBytes();
        byte[] b16 = new byte[16];
        for (int i = 0; i < 16; i++) {
            b16[i] = b[i];
        }
        int result = cardReader.write(handle, blocknr, b16);
        if (result < 0) {
            System.out.println("FAILLL!!!");
        }
      
    }

    protected void sectorLogin(byte sector) {
        byte keytype = (byte) ACR120U.AC_MIFARE_LOGIN_KEYTYPE_A;


        // moet later met hand ingevuld worden
        byte[] key = new byte[6];
        for (int i = 0; i < 6; i++) {
            key[i] = (byte) 0xff;

        }



        int result = cardReader.login(handle, sector, keytype, (byte) 00, key);
        System.out.println("Result of login: " + result);

    }

    protected void buzzer(boolean status) {
        byte newSetting = 0;
        if (status) {
            newSetting = 0x44;// bit 6=led, bit 2=buzzer
        } else {
            newSetting = 0; // all Off
        }
        cardReader.writeUserPort(handle, newSetting);
    }

    protected void beep() {
        this.buzzer(true);
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(ReaderRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.buzzer(false);
    }

    protected void longBeep() {
        
        
        this.buzzer(true);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(ReaderRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.buzzer(false);
    }
}
