/*
 * ACRReader.java
 *
 * Created on 31 oktober 2007, 20:36
 *
 * 
 */

package adminapp;

import acs.jni.ACR120U; //usb rfid reader version 
//import adminapp.AdminApp;


/**
 * Interface to the ACR120 rfid reader. See also ACR120U-API-doc-html docs.
 * @author  jand
 *
 */
public class ACRReader 
{
    /** the the cardReader reference */
    private ACR120U cardReader;    
    /** cardReader connection handle (result from opening the device) */
    private short handle = -1;         
    /** COM port that will be used COM1=0 (default) COM2=1 etc. */
    //private short comport = ACR120S.ACR120_COM1;
    /** last result message */
    private String message;
    /** default sector+block to write/read card */
    private byte blocknr = 8, sector = 2;
    
    public static void main(String []args){
        System.out.println("This is ACRReader without the GUI");
        ACRReader test = new ACRReader();
        test.openReader();
        long tag = test.readTag();
        System.out.printf("\nTag read = %X\n", tag);
    }
    

    /**
     * Open connection to the cardreader and keep the handle and stationId
     * in this object's fields. Use COMport in this.comport
     */
    public void openReader(){
        handle = -1;
        try {
        cardReader = new ACR120U();
        } catch (java.lang.UnsatisfiedLinkError e) {
            e.printStackTrace();
            System.err.println("Are you sure the DLL is installed???");
            message = "DLL Installation problem.";
            return;
        }
        handle = cardReader.open(ACR120U.ACR120_USB1);  
        if (handle < 0){
            message = "Error opening reader [port may be in use or reader not in binary mode]";
            System.err.println(message);
            return;
        }
        message = "Device opened. handle="+handle;
    }
    
    public boolean checkOpen(){
        if (handle < 0){
            message = "Device not open";
            System.err.println(message);
            return false;
        }
        return true;
    }

    public long readTag(){
        // check precondition: device must be opened
        if (!checkOpen()){
            return 0;
        }
        // arrays will hold return values from various calls to reader
        byte[] pLen = new byte[1];
        byte[] pTAG = new byte[1];
        byte[] pSN =  new byte[10];
        byte[] pNumTagFound = new byte[1];
        short tag = cardReader.select(handle,pTAG, pLen, pSN);
        if (tag < 0 ){
            //message = " NO TAG FOUND";
            //System.err.println(message);
            return 0;
        }
        //System.out.println("Tagtype:"+pTAG[0] + " SN[0]:" + Integer.toHexString(pSN[0]) + " return= " + tag);
        long serial = 0;
        for(int i=0;i<pLen[0];i++) {
            // shift and bitwise-OR the bytes into one 64-bits integer
            serial = (serial << 8 );  // shift left 8 bits
            serial = serial | (pSN[i] & 0xFF); // bitwise OR byte into int
        }
        System.out.printf(" serial = hex: %X\n", serial);
        message = "Tag read";
        
        String serialString = Long.toString(serial);
        
        System.out.println(serialString);

        boolean openDeur = false;
        
        if("2485935971".equals(serialString)) {
            System.out.println("Standaardkaart");
            openDeur = true;
        }
        
        if(openDeur) {
            System.out.println("ACCESS GRANTED");
            blink();
            openDeur();
        } else {
            System.out.println("ACCESS DENIED");
            blinkError();
        }
        
        
        
        try {   Thread.sleep(1000); } catch (InterruptedException ex) {      ex.printStackTrace();        }
        // test card read/write+login functions; only with new cards
        //writeString("Hello RFID card");
        //System.err.println(readString());
        return (int)serial;
        
        // @TODO SQL QUERY CODE MOST LIKELY HERE -Jeff
    }
    
    /**
     *  Reset the cardreader  
     */
    public void reset(){
        if (!checkOpen()){
            return;
        }
        cardReader.power(handle, (byte)1);
        cardReader.reset(handle);   
        message = "device reset done";
    }    
    
    /** buzzer does not work on serial ACR120S */
    public void buzzer(boolean status){
        byte newSetting = 0;
        if (status){
            newSetting = 0x44;// bit 6=led, bit 2=buzzer
        }
        else { 
            newSetting = 0; // all Off
        }
        cardReader.writeUserPort(handle,newSetting);     
    }
    
    /** power+led on/off */
    public void power(boolean power){
        // check precondition: device must be opened
        if (!checkOpen()){
            return;
        }
        System.out.println("POWER "+power);      
        if (power){
            
            cardReader.power(handle, (byte)1);  
            cardReader.writeUserPort(handle,(byte)0x05); //buzzer on
        } else {
            cardReader.writeUserPort(handle,(byte)0x00);
            cardReader.power(handle, (byte)0);              
        }
    }
    
    /** get the last error or status message */
    public String getMessage(){
        return message;
    }
    
    /** close the reader device and COM port */
    void close(){
        // check precondition: device must be opened
        if (!checkOpen()){
            return;
        }
        // turn off antenna and LED
        cardReader.power(handle, (byte)0);
        cardReader.close(handle);
        handle = -1;
        message = "Device closed";
    }
    
    /** write some data in a block  */
    public void writeString(String s){  
        if (!login())
            return;
        s = s + "                "; // padding in case len < 16
        byte[] b = s.getBytes();
        byte[] b16 = new byte[16];
        for (int i=0;i<16;i++) 
            b16[i] = b[i];
        int result = cardReader.write(handle,blocknr,b16);
        if (result < 0) {
            message = "write block failed";
        }
        System.err.println(message);        
    }
    
    /** read som block data */
    public String readString(){   
        if (!login()) return "Login Error";
        String s;
        byte[] bdata = new byte[16];
        cardReader.read(handle,blocknr,bdata);
        s = new String(bdata);
        System.err.println("Read block: " + s);        
        return s;
    }
    
    /** read som block data */
    public byte[] readBlock(int blocknr){   
        byte[] bdata = new byte[16];
        for (int i=0;i<16;i++) bdata[i]=0;
        int result = cardReader.read(handle,(byte)blocknr,bdata);
        if (result < 0){
            System.err.println("error reading block "+ blocknr);
            return bdata;
        }
        System.err.printf("Page %02d: ", blocknr);
        for (int i=0; i<4;i++){
            int b = bdata[i] & 0xFF;
            System.err.printf("%02X ",b);            
        }
        System.err.println();
        return bdata;
    }
    
    public void test() {
        byte[] bwrite = new byte[16];
        bwrite[0] = 0;
        bwrite[1] = 2;
        bwrite[2] = 4;
        bwrite[3] = 6;
        byte[] bdata = new byte[16];
        cardReader.write(handle, (byte)1, bwrite);
        System.out.println(cardReader.read(handle, (byte)1, bdata));
        
        
        
    }

    /** login into a sector on a card */
    public boolean login() {        
        byte keytype = (byte)ACR120U.AC_MIFARE_LOGIN_KEYTYPE_A;
        byte[] key = new byte[6];
        for(int i=0;i<6;i++) 
            key[i] = (byte)0xff;
        int result;
        result = cardReader.login(handle,sector,keytype,(byte)00,key);
        if (result < 0){ 
            message = "login failed";
            return false;
        }
        return true;
    }
    
    /** Blink/sound powerled/buzzer 2 times */
    public void blink(){
        boolean p = false;
        for (int i=1; i<=5; i++){
            buzzer(p); 
            p = !p;
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void blinkError(){
        boolean p = false;
        for (int i=1; i<=5; i++){
            buzzer(p); 
            p = !p;
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void openDeur() {
        // Locker blink
        //OutfitGuiApp.locker.blink_leds();
    }
}
