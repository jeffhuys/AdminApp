package model;

public class Settings {
    
    private static int logoutTimeout = -1;
    private static boolean logoutEnabled = false;
    

    /**
     * @return the logoutTimeout
     */
    public static int getLogoutTimeout() {
        return logoutTimeout;
    }
    
    /**
     * @return if logout after certain amount of time is enabled
     */
    public static boolean getLogoutEnabled() {
        return logoutEnabled;
    }

    /**
     * @param logoutTimeoutToSet the logoutTimeout to set
     */
    public static void setDefaultSettings(int logoutTimeoutToSet, boolean logoutEnabledToSet) {
        logoutTimeout = logoutTimeoutToSet;
        logoutEnabled = logoutEnabledToSet;
    }
}
