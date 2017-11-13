package model;

import controller.LogController;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import view.LogView;

/**
 * LogEntry class
 * Met deze klasse kan men een logEntry aanmaken.
 * Deze wordt meteen in de database gezet, en eventueel ook direct aan de
 * UI toegevoegd.
 * Reden om LogEntry NIET direct aan de UI toe te voegen:
 *  - De LogView is op dit moment niet beschikbaar (dit kan fouten voorkomen als
 *    er nog niet is ingelogd)
 * @author Jeff
 */
public class LogEntry {

    private String timestamp;
    private int id;
    private User user;
    private String locatie;
    private String bijzonderheden;
    private int log_types_id;
    public static ArrayList<LogEntry> logEntries = new ArrayList<LogEntry>();
    public static ArrayList<User> usersInside = new ArrayList<User>();
    public static ArrayList<User> usersBeenInside = new ArrayList<User>();
    final public static int LOGENTRY_PASGESCANT = 1;
    final public static int LOGENTRY_DEUROPEN = 2;
    final public static int LOGENTRY_DEURDICHT = 3;
    final public static int LOGENTRY_SENSOR = 4;
    final public static int LOGENTRY_SENSORUIT = 5; // not used atm
    final public static int LOGENTRY_HACKPOGING = 6;

    // Enkel voor in het geheugen zetten uit database
    public LogEntry(int id, String timestamp, User user, String locatie, String bijzonderheden, int log_types_id) throws SQLException {
        this.id = id;
        this.timestamp = timestamp;
        this.user = user;
        this.locatie = locatie;
        this.bijzonderheden = bijzonderheden;
        this.log_types_id = log_types_id;
        addLogEntryToArrayList(this);
    }

    /**
     * Voeg een LogEntry toe, en voer deze gelijk in in de database
     * @param user Geef een user op, deze word gebruikt om achter de voornaam te komen en deze in de database te zetten
     * @param locatie Geef een locatie op waar deze gebeurtenis heeft plaatsgevonden (e.g.: Deur 1)
     * @param bijzonderheden Geef bijzonderheden op, waar nodig
     * @param log_types_id Geef het logtype op (voor logtypes, raadpleeg de database)
     * @throws SQLException 
     */
    public LogEntry(User user, String locatie, String bijzonderheden, int log_types_id, boolean UI) {
        this.user = user;
        this.locatie = locatie;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        this.timestamp = dateFormat.format(cal.getTime());

        this.bijzonderheden = bijzonderheden;
        this.log_types_id = log_types_id;
        try {
            addLogEntry(this, UI);
        } catch (SQLException ex) {
            Logger.getLogger(LogEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Deze functie word gebruikt om enkele andere methodes aan te roepen
     * @param logEntry logEntry om toe te voegen, compleet gevuld
     * @param UI boolean om aan te geven of de UI aanspreekbaar is
     * @throws SQLException 
     */
    private void addLogEntry(LogEntry logEntry, boolean UI) throws SQLException {
        
        addLogEntryToArrayList(logEntry);
        addLogEntryToDatabase(logEntry);

        // Als er een UI beschikbaar is, voeg ook toe aan de logView
        // Deze check bestaat om nullpointer-exceptions te voorkomen
        if (UI) {
            addLogEntryToUI(logEntry);
        }
        if (LogController.logController != null) {
            LogController.logController.refreshTable();
        }
    }

    /**
     * Voert de logEntry en alle benodigde info in in de database
     * @param logEntry logEntry om toe te voegen, compleet gevuld
     * @throws SQLException 
     */
    private void addLogEntryToDatabase(LogEntry logEntry) throws SQLException {
        int logEntryId = logEntry.id;
        String logEntryTimestamp = logEntry.timestamp;

        int logEntryGebruikersId = 0;
        if (logEntry.user != null) {
            System.out.println("Error?");
            logEntryGebruikersId = logEntry.user.getId();
            System.out.println("NoError.");
        }
        String logEntryLocatie = logEntry.locatie;
        String logEntryBijzonderheden = logEntry.bijzonderheden;
        int logEntryTypeId = logEntry.log_types_id;

        String query = "INSERT INTO `Log` (`gebruikers_id`, `locatie`, `bijzonderheden`, `log_types_id`) VALUES ('" + logEntryGebruikersId + "', '" + logEntryLocatie + "', '" + logEntryBijzonderheden + "', '" + logEntryTypeId + "')";
        System.out.println("Query (logEntry): " + query);
        
        while(Database.statement != null) {} // Wacht tot statement vrij is
        
        Database.createStatement();
        Database.executeUpdate(query);
        Database.closeStatement();
        System.out.println("Query uitgevoerd");


        Database.version++;
    }

    /**
     * Voert de logEntry en alle benodigde info in in de ArrayList
     * @param logEntry logEntry om toe te voegen, compleet gevuld
     */
    private void addLogEntryToArrayList(LogEntry logEntry) {
        logEntries.add(logEntry);
    }

    /**
     * Voert de logEntry en alle benodigde info in in de UI
     * @param logEntry logEntry om toe te voegen, compleet gevuld
     */
    private void addLogEntryToUI(LogEntry logEntry) {
        /**
        DefaultTableModel tm = new DefaultTableModel();
        
        tm.addColumn("Type");
        tm.addColumn("Naam");
        tm.addColumn("Locatie");
        tm.addColumn("Bijzonderheden");
        tm.addColumn("Tijd");
        
        for (int i = 0; i < logEntries.size(); i++) {
        //Integer.toString(logEntries.get(i).getLog_types_id())
        tm.addRow(new String[]{"LOLZ", logEntries.get(i).user.getVoornaam(), logEntries.get(i).locatie, logEntries.get(i).bijzonderheden, logEntries.get(i).timestamp});
        }
        
        LogView.logTable.setModel(tm);
         */
    }

    /**
     * @return current timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @return instantie van User
     */
    public User getUser() {
        return user;
    }

    /**
     * @return the locatie
     */
    public String getLocatie() {
        return locatie;
    }

    /**
     * @return the bijzonderheden
     */
    public String getBijzonderheden() {
        return bijzonderheden;
    }

    /**
     * @return the log_types_id
     */
    public int getLog_types_id() {
        return log_types_id;
    }

    public static void checkIfInside() {
        int insideCount = 0;
        User user = null;
        int size = logEntries.size();
        int pointOfChecking = size - 4;
        for (int i = pointOfChecking; i < size; i++) {
            int logTypeId = logEntries.get(i).getLog_types_id();
            switch (logTypeId) {
                case LOGENTRY_PASGESCANT:
                    insideCount++;
                    user = logEntries.get(i).getUser();
                case LOGENTRY_DEUROPEN:
                    insideCount++;
                case LOGENTRY_SENSOR:
                    insideCount++;
                case LOGENTRY_DEURDICHT:
                    insideCount++;


            }


        }
        if (insideCount == 10) {
          
            usersInside.add(user);
            usersBeenInside.add(user);
           
        }
    }
}
