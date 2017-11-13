/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import model.LogEntry;
import outfitadminappmvc.CustomRenderer;
import view.LogView;

/**
 * Bestuurt alle log-gerelateerde kwesties
 * Tevens bestuurt deze klasse het hoofdscherm
 * @author Mauricio
 */
public class LogController {
    public static LogController logController;
    
    private LogView logView;
    private StatisticsController statisticsController;
    private UsersController usersController;
    private SettingsController settingsController;
    private LoginController loginController;
    private ArrayList<LogEntry> tableList = new ArrayList<LogEntry>();
    private DefaultTableModel defaultTableModel;
    private String logFilter = "";
    private final String todayDate;

    public LogController() {
        logView = new LogView(this);
        logView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logView.setVisible(true);
        
        
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date today = Calendar.getInstance().getTime();
        todayDate = df.format(today);

        createLogTableModel();
        fillLogTable("");

        logController = this;
    }

    /**
     * Laat usersView zien
     */
    public void showUsers() {
        if (usersController == null) {
            usersController = new UsersController(false);
        } else {
            usersController.disposeController();
            usersController = null;
        }

    }

    /**
     * Laat statisticsView zien
     */
    public void showStatistics() {
        if (statisticsController == null) {
            //statisticsController = new StatisticsController(750, 0);
            statisticsController = new StatisticsController(true, logView);
        } else {
            statisticsController.disposeController();
            statisticsController = null;
        }
    }
    
    /**
     * Laat settingsView zien
     */
    public void showSettings() {
        if (settingsController == null) {
            //statisticsController = new StatisticsController(750, 0);
            settingsController = new SettingsController(true, logView);
        } else {
            settingsController.disposeController();
            settingsController = null;
        }
    }

    private void fillLogTable(String type) {


        for (LogEntry logEntry : LogEntry.logEntries) {

            String logEntryData = null;
            
            switch(logEntry.getLog_types_id()) {
                case 1:
                    logEntryData = "Pas gescand";
                    break;
                case 2:
                    logEntryData = "Deur open";
                    break;
                case 3:
                    logEntryData = "Deur dicht";
                    break;
                case 4:
                    logEntryData = "Sensor gepasseerd";
                    break;
                case 5:
                    logEntryData = "Sensor gepasseerd uit";
                    break;
                case 6:
                    logEntryData = "HACKPOGING";
                    break;
            }
            String voornaam = "-";
            if(logEntry.getUser() != null){
                voornaam = logEntry.getUser().getVoornaam();
            }
            if(type.isEmpty()) {
                // all
                this.defaultTableModel.addRow(new Object[]{logEntryData, voornaam, logEntry.getLocatie(), logEntry.getBijzonderheden(), logEntry.getTimestamp()});
            } else if(type.equals("today")) {
                if(logEntry.getTimestamp().substring(0, 10).equals(todayDate)) {
                    this.defaultTableModel.addRow(new Object[]{logEntryData, voornaam, logEntry.getLocatie(), logEntry.getBijzonderheden(), logEntry.getTimestamp()});
                }
            } else if(type.equals("hack")) {
                if(logEntry.getLog_types_id() == 6) {
                    this.defaultTableModel.addRow(new Object[]{logEntryData, voornaam, logEntry.getLocatie(), logEntry.getBijzonderheden(), logEntry.getTimestamp()});
                }
            }
            this.logFilter = type;
        }
    }

    private void createLogTableModel() {
        this.defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        this.defaultTableModel.addColumn("Type");
        this.defaultTableModel.addColumn("Naam");
        this.defaultTableModel.addColumn("Locatie");
        this.defaultTableModel.addColumn("Bijzonderheden");
        this.defaultTableModel.addColumn("Tijd");

        LogView.logTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        LogView.logTable.setModel(defaultTableModel);
        //LogView.logTable.setAutoCreateRowSorter(true); // Uitgecomment vanwege 
        // bekende bug: http://www.dreamincode.net/forums/topic/71364-jtables-setautocreaterowsorter-makes-me-hate-javaor-maybe-just/
        LogView.logTable.setDefaultRenderer(Object.class, new CustomRenderer());
    }

    /**
     * Exporteert de log naar een .txt bestand
     */
    public void exportLog() {
        new ExportController(this.defaultTableModel);
    }

    public void logOff() {
        logView.dispose();
        new LoginController();
    }
    
    public void refreshTable(){
        while(defaultTableModel.getRowCount() != 0){
            defaultTableModel.removeRow(0);
        }
        
   
        this.fillLogTable(this.logFilter);
    }
    public void refreshTable(String type) {
        this.logFilter = type;
        System.out.println(type + "filteren1");
        refreshTable();
    }
  
   
}
