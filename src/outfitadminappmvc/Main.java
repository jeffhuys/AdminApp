/*
 *  _____       _    __ _ _   
 * |  _  |     | |  / _(_) |  
 * | | | |_   _| |_| |_ _| |_ 
 * | | | | | | | __|  _| | __|
 * \ \_/ / |_| | |_| | | | |_ 
 *  \___/ \__,_|\__|_| |_|\__|
 * Admin App
 * Mauricio Kruijer
 * Vincent Couzij
 * Bastiaan Niamut
 * Jeff Huijsmans
 * Rick Slot
 * Milco Majoor
 * 
 */
package outfitadminappmvc;

import controller.ApplicationRunnable;
import controller.DatabaseRunnable;
import controller.DistancesensorController;
import controller.ReaderRunnable;
import model.Debug;

/**
 *
 * @author Razzy
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Debug.openDebugView();
        
        
               
        Thread databaseThread = new Thread(new DatabaseRunnable(),"DatabaseThread");
        databaseThread.setPriority(Thread.MIN_PRIORITY);
        databaseThread.start();
        
        
        Thread appThread = new Thread(new ApplicationRunnable(),"AppThread");
        appThread.setPriority(Thread.NORM_PRIORITY);
        appThread.start();
        
        Thread readerThread = new Thread(new ReaderRunnable(),"ReaderThread");
        readerThread.setPriority(Thread.MAX_PRIORITY);
        readerThread.start();
        
        Thread distanceThread = new Thread(new DistancesensorController(),"DistanceThread");
        readerThread.setPriority(Thread.MAX_PRIORITY);
        Thread.sleep(5000);
        distanceThread.start();
       
    
                
    }
}
