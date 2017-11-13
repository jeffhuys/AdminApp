/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Database;

/**
 * Deze klasse zorgt ervoor dat de app- en database-versie gelijk lopen en
 * geupdate worden
 * @author Razzy
 */
public class IncrementVersionRunnable implements Runnable {
    /**
     * Deze methode update het veld version als er een update word uitgevoerd in de database.
     */
    @Override
    public void run() {
        try {
            /*
            try {
                Thread.sleep(2500);
            } catch (InterruptedException ex) {
                Logger.getLogger(IncrementVersionRunnable.class.getName()).log(Level.SEVERE, null, ex);
            }
             */
            String incrementQuery = "UPDATE `Database` SET version = (version + 1) WHERE id = 1;";

            while(Database.statement != null) { }
            Database.createStatement();
            Database.executeCleanUpdate(incrementQuery);
            Database.closeStatement();
            
            System.out.println("lol");

        } catch (SQLException ex) {
            Logger.getLogger(IncrementVersionRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
