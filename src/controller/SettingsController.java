package controller;

import java.sql.SQLException;
import javax.swing.JFrame;
import model.Database;
import model.Settings;
import view.LogView;
import view.SettingsView;

/**
 *
 * @author Jeff
 */
public class SettingsController {

    private SettingsView settingsView;

    public SettingsController() {
        settingsView = new SettingsView(this);
        settingsView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsView.setVisible(true);
        
        setValuesFromDatabase();

    }

    public SettingsController(boolean exitOnClose) {
        settingsView = new SettingsView(this);

        if (!exitOnClose) {
            settingsView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } else {
            settingsView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        settingsView.setVisible(true);
        
        setValuesFromDatabase();
    }

    public SettingsController(int startX, int startY) {
        settingsView = new SettingsView(this);
        settingsView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsView.setVisible(true);

        settingsView.setLocation(startX, startY);
        
        setValuesFromDatabase();
    }

    public SettingsController(boolean snapToRight, LogView logView) {
        settingsView = new SettingsView(this);
        settingsView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsView.setVisible(true);

        if (snapToRight) {
            //settingsView.setLocation(startX, startY);
            settingsView.setLocationRelativeTo(logView);
            settingsView.setLocation(settingsView.getX() + 490, settingsView.getY() - 125);

            // Hacky self-check for position
            if (settingsView.getX() > 1366) {
                settingsView.setLocation(1366 - 250, 250);
            }
            if (settingsView.getY() < 0) {
                settingsView.setLocation(settingsView.getX(), 0);
            }
        }
        
        setValuesFromDatabase();
    }

    public void disposeController() {
        settingsView.dispose();
    }

    private void setValuesFromDatabase() {
        // Initialize the values read from the database
        SettingsView.minutesSpinner.setValue(Settings.getLogoutTimeout());
        SettingsView.autoLogoutEnabledCheckbox.setSelected(Settings.getLogoutEnabled());
    }
    

    public void saveSettings() throws SQLException {
        // Save settings to database
        String settingsQuery = null;
        
        int autoLogoutMinutes = Integer.decode(SettingsView.minutesSpinner.getValue().toString());
        boolean autoLogoutEnabled = SettingsView.autoLogoutEnabledCheckbox.isSelected();
        
        // Convert boolean to integer
        int autoLogoutEnabledInt = 0;
        if(autoLogoutEnabled)
            autoLogoutEnabledInt = 1;
        
        System.out.println("Minutes: " + autoLogoutMinutes);
        System.out.println("Enabled: " + autoLogoutEnabled);
        
        settingsQuery = "UPDATE `Settings` SET `autoLogoutMinutes` = '" + autoLogoutMinutes + "', `autoLogoutEnabled` = '" + autoLogoutEnabledInt + "' WHERE `id` = 0;";
        Database.executeUpdate(settingsQuery);
        Database.version++;
        
        System.out.println("Executed query!");
        
        // Set the default settings to the new settings
        Settings.setDefaultSettings(autoLogoutMinutes, autoLogoutEnabled);
    }
}