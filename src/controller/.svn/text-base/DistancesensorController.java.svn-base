package controller;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Debug;
import model.LogEntry;
import model.User;
import usb.ivi.Distance;

/**
 * Deze controller stuurt de sensor aan
 * @author Jeff
 */
public class DistancesensorController extends java.lang.Object implements Runnable {

	public static Distance sensor = new Distance();

	public DistancesensorController() {
	}

	/**
	 * Deze methode zorgt ervoor dat de sensor gelezen word.
	 * @return De waarde van de sensor
	 * @throws SQLException 
	 */
	public static int readSensor() throws SQLException {

		int value = sensor.back_distance();
		return value;

	}

	/**
	 * Deze methode kan je gebruiken om te kijken of de sensor gepasseerd wordt. Er wordt ook een log bijgeschreven als
	 * de sensor gepasseerd wordt.
	 * @throws SQLException 
	 */
	public void triggerSensor() throws SQLException {
		while (true) {
			int sensorValue = readSensor();
                
                        Debug.changeSensorValue(sensorValue);
		
			if (sensorValue > 590) {
				//zet hier de log value neer dat de deur gepasseerd is.
				// eventueel functie dat de deur meteen dicht gaat. 
				Debug.changeSensorValue(sensorValue + "-- Sensor geactiveerd");
                                new LogEntry(null, "Sensor", "Sensor gepasseerd", LogEntry.LOGENTRY_SENSOR, true); ;
				try {
					Thread.sleep(2000);
				} catch (InterruptedException ex) {
					Logger.getLogger(DistancesensorController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
				Logger.getLogger(DistancesensorController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	@Override
	/**
	 * Deze run zorgt ervoor dat je de sensor in een Thread kan gebruiken.
	 */
	public void run() {
		try {
			triggerSensor();
		} catch (SQLException ex) {
			Logger.getLogger(DistancesensorController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
