/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package outfitadminappmvc;

import controller.LogController;
import controller.LoginController;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Database;
/**
 *
 * @author Razzy
 */
public class OutfitAdminAppMVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Database.initializeDatabase();
            Database.openConnection();
          
        } catch (SQLException ex) {
            Logger.getLogger(OutfitAdminAppMVC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        new LogController();
        
    }
}
