/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Razzy
*/
public class Smartcard {
    public static ArrayList<Smartcard> smartcards = new ArrayList<Smartcard>();
    
    private int id;
    private String serialNumber;
    private String sectorPassword;
    
    
    
    public Smartcard(String serialNumber, String sectorPassword){
        this.serialNumber = serialNumber;
        this.sectorPassword = sectorPassword;
        
        smartcards.add(this);
    }
}
