package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Database;
import model.LogEntry;
import model.User;
import controller.SettingsController;
import model.Debug;
import model.Settings;

/**
 * Deze thread doet alle Database-gerelateerde zaken
 * @author Razzy
 */
public class DatabaseRunnable implements Runnable {

    @Override
    public void run() {
        try {
            Database.initializeDatabase();
            Database.openConnection();
            loadDataFromDatabase();


            while (true) {
                String versionQuery = "SELECT * FROM `Database`";
                Database.createStatement();
                ResultSet resultSet = Database.executeQuery(versionQuery);
                resultSet.first();
                long databaseVersion = resultSet.getLong("version");
                long appVersion = Database.version;
                
                Debug.changeDatabaseVersionValue((int) databaseVersion);
                Debug.changeAppVersionValue((int) appVersion);
               
                if (databaseVersion != appVersion) {
                    System.out.println("Versies niet gelijk! (" + databaseVersion + " <-> " + appVersion + ")");
                    System.out.println("Bezig met opnieuw ophalen...");
                    
                    

                }
                Database.closeStatement();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DatabaseRunnable.class.getName()).log(Level.SEVERE, null, ex);
                }

            }



        } catch (SQLException ex) {
            Logger.getLogger(DatabaseRunnable.class.getName()).log(Level.SEVERE, null, ex);

        }




    }
    /** 
     * Deze methode zorgt er voor dat het versie nummer van de database opgehaald wordt.
     * @throws SQLException 
     */
    private void loadDataFromDatabase() throws SQLException {
        //maak alle arraylists leeg
        User.users.clear();

        //laad data van de database versie (belangrijk)
        String versionQuery = "SELECT * FROM `Database`";
        Database.createStatement();
        ResultSet resultSetVersion = Database.executeQuery(versionQuery);
        resultSetVersion.first();
        long databaseVersion = resultSetVersion.getLong("version");
        Database.version = databaseVersion;

        Database.closeStatement();


        //load all data from user

        String userQuery = "SELECT * FROM User";
        Database.createStatement();
        ResultSet resultSetUser = Database.executeQuery(userQuery);
        resultSetUser.beforeFirst();

        while (resultSetUser.next()) {
            int id = resultSetUser.getInt("id");
            String firstName = resultSetUser.getString("firstName");
            String lastName = resultSetUser.getString("lastName");
            String password = resultSetUser.getString("password");
            String tag = resultSetUser.getString("tagid");
            boolean isAdmin = resultSetUser.getBoolean("isAdmin");
            int timeInterval = resultSetUser.getInt("timeInterval");

            new User(id, firstName, lastName, password, tag, isAdmin);

        }

        Database.closeStatement();


        //load all data from LogEntry
        String logQuery = "SELECT * FROM Log";
        Database.createStatement();
        ResultSet resultSetLog = Database.executeQuery(logQuery);
        resultSetLog.beforeFirst();

        while (resultSetLog.next()) {
            int id = resultSetLog.getInt("id");
            String timestamp = resultSetLog.getString("timestamp");
            // je zou denken je doet poep, maar anders krijg je er een vervelende
            // .0 achter. #niemandweetwaarom
            timestamp = timestamp.substring(0, 19);

            int gebruikers_id = resultSetLog.getInt("gebruikers_id");

            String locatie = resultSetLog.getString("locatie");
            String bijzonderheden = resultSetLog.getString("bijzonderheden");

            int log_types_id = resultSetLog.getInt("log_types_id");

            User logTypeUser = null;
            for (User theUser : User.users) {
                if (theUser.getId() == gebruikers_id) {
                    logTypeUser = theUser;
                    break;
                }
            }

            if (logTypeUser != null) {
                new LogEntry(id, timestamp, logTypeUser, locatie, bijzonderheden, log_types_id);

            } else {
                new LogEntry(id, timestamp, null, locatie, bijzonderheden, log_types_id);
            }
        }
        
        //load all data from Settings
        String settingsQuery = "SELECT * FROM Settings";
        Database.createStatement();
        ResultSet resultSetSettings = Database.executeQuery(settingsQuery);
        resultSetLog.beforeFirst();
        SettingsController settingsController = null;

        while (resultSetLog.next()) {
            int id = resultSetSettings.getInt("id");
            int logoutTimeout = resultSetSettings.getInt("autoLogoutMinutes");
            boolean logoutEnabled = resultSetSettings.getBoolean("autoLogoutEnabled");
            
            // Gooi alles in de settings
            Settings.setDefaultSettings(logoutTimeout, logoutEnabled);
            
        }
    }
}