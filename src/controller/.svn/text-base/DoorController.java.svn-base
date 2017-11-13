package controller;

import model.LogEntry;
import model.User;
import usb.ivi.Ivibot;
import usb.ivi.USB_ivi_device;


/**
 * Deze controller stuurt de deur aan
 * @author Rick
 */
public class DoorController{
        public static DoorController doorController;
	private USB_ivi_device dev;
	private Ivibot bot;
	
	/**
	 * zorgt voor de verbinding met de ivibot
	 */
	public DoorController() {
		try {
			dev = new USB_ivi_device();
		} catch (Exception e) {
                        System.out.println(e);
			System.err.println("USB_IVI DLL Exception catched - device disabled");
			dev = null;
			System.out.println("USB IVI verbinding niet gelukt");
			//return
		}

		dev.getPortA().setPortPinDirections((byte) 0);
		dev.getPortB().setPortPinDirections((byte) 0);
		dev.getPortEC().setPortPinDirections((byte) 0);

		bot = new Ivibot(dev);
              
               doorController = this;
	}

	/**
	 * Zorgt ervoor dat de deur open gaat.
         * motor2_stop moet 4 keer worden uitgevoerd door een bug in de ivitbot
	 */
	public void openDoor() {
               this.allLedsOff();
                this.ledOn(7);
                this.sound(60000);
            
		bot.motor2_forward();
		wait(2);
		bot.motor2_stop();
		bot.motor2_stop();
		bot.motor2_stop();
		bot.motor2_stop();
		System.out.println("deur gaat open log");
                new LogEntry(null, "Deur", "De deur word geopend", LogEntry.LOGENTRY_DEUROPEN, true);
	}
        /**
         * Zet een van de leds aan
         * @param ledNumber 
         */
        private void ledOn(int ledNumber){
            dev.getPortB().setPinValue(ledNumber, true);
        }
	/**
         * Zet alle leds uit
         */
        private void allLedsOff(){
            dev.getPortB().setPortPinValues((byte) 0);
        }
        /**
         * Start een geluid op een bepaalde frequentie
         * @param frequenty 
         */
        private void sound(double frequenty){
            dev.getPWM().setPWMFrequency(frequenty);
            dev.getPortA().setPortPinValues((byte)0xFF);
        }
        
	/**
	 * Zorgt ervoor dat de deur weer sluit.
         * motor2_stop moet 4 keer worden uitgevoerd door een bug in de ivitbot
	 */
	public void closeDoor(){
		System.out.println("deur gaat dicht log");		
		this.allLedsOff();
                this.ledOn(6);
                sound(2000); 
                bot.motor2_reverse();
		wait(2);
		bot.motor2_stop();
		bot.motor2_stop();
		bot.motor2_stop();
		bot.motor2_stop();
		  
                new LogEntry(null, "Deur", "De deur word gesloten", LogEntry.LOGENTRY_DEURDICHT, true);
                
               LogEntry.checkIfInside();
	}
	
	/**
	 * deze methode zorgt ervoor dat je een aantal seconden kan wachten.
	 * @param seconds seconds is het aantal seconden dat je wil wachten.
	 */
	private void wait(int seconds) {
		
		long time0, time1;

		time0 = System.currentTimeMillis();

		do {
			time1 = System.currentTimeMillis();
		} while ((time1 - time0) < (seconds * 1000));
	}
	
	public static void main(String args[]){
		DoorController deur = new DoorController();
		
		//deur.openDoor();
                
		
	
	}
}
