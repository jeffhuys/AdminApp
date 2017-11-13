/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.User;
import view.UserView;
import view.UsersView;

/**
 *
 * @author Mauricio
 */
public class UsersController {

    private UsersView usersView;
    private UserView userView;
    private int selectedRow;
    private int editUserByID = -1;

    /**
     * Constructor overload
     */
    public UsersController() {
        this(true);
    }
    /**
     * Constructor UsersController
     * @param exitOnClose boolean om dispose on close of exit on close te toggelen
     */
    public UsersController(boolean exitOnClose) {
        usersView = new UsersView(this);

        if (!exitOnClose) {
            usersView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } else {
            usersView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        usersView.setVisible(true);
    }
    /**
     * View formulier vewerken, opslaan of bewerken van gebruikers
     */
    public void verwerkFormulier() {
        boolean errorFound = false;

        if (userView.getVoornaam().equals("")) {
            userView.setFeedback("Voornaam opgeven", Color.red);
            errorFound = true;
        } else if (userView.getAchternaam().equals("")) {
            userView.setFeedback("Achternaam opgeven", Color.red);
            errorFound = true;
        } else if (userView.getWachtwoord1().equals("") || userView.getWachtwoord2().equals("")) {
            userView.setFeedback("Wachtwoord opgeven", Color.red);
            errorFound = true;
        } else if (!userView.getWachtwoord1().equals(userView.getWachtwoord2())) {
            userView.setFeedback("Wachtwoorden komen niet overeen", Color.red);
            errorFound = true;
        } else {
            // hier moet nog worden gekeken of de gebruiker niet al bestaat.
            // hoe moeten we dat gaan doen? :P
            System.out.println("whoehoo geen errors");

            userView.setFeedback("");
            
            boolean pasExists = false;
            boolean userExists = false;
            ArrayList<User> users = User.users;
            for (User user : users) {
                if (user.getVoornaam().equalsIgnoreCase(userView.getVoornaam()) && user.getAchternaam().equalsIgnoreCase(userView.getAchternaam())) {
                    userExists = true;
                    errorFound = true;
                }
                String tagIdFromPass = this.userView.getStatusPasTextField().getText();
               if(tagIdFromPass.equals(user.getTag())){
                   pasExists = true;
                   errorFound = true;
               }
                
            }
            if(editUserByID != -1) {
                // gebruiker bewerken
                User edit = User.getUserByID(editUserByID);
                if(userExists && edit.getVoornaam().equalsIgnoreCase(userView.getVoornaam()) && edit.getAchternaam().equalsIgnoreCase(userView.getAchternaam())) {
                    System.out.println("User voor en achternaam zijn hetzelfde");
                    if(edit.getTag().equals(userView.getStatusPasTextField().getText())) {
                        // pas is het zelfde, nu updaten!
                        System.out.println("Jeeej, alleen een password edit!");
                        String pass = User.convertToMD5(userView.getWachtwoord1()+User.salt);
                        edit.setPassword(editUserByID, pass);
                        
                        User.editUserIntoDatabase(edit, editUserByID);
                        
                    } else {
                        // mag geen andere pas invoeren.
                    }
                } else if(userExists) {
                    System.out.println("USer edit met bestaande naam, is niet cool!");
                } else {
                    // voor achternaam change, dat mag.
                    System.out.println("Voor achternaam change zonder dat deze al bestaat.");
                    if(edit.getTag().equals(userView.getStatusPasTextField().getText())) {
                        // hier opslaan.
                        edit.setAchternaam(editUserByID, userView.getAchternaam());
                        edit.setVoornaam(editUserByID, userView.getVoornaam());
                        User.editUserIntoDatabase(edit, editUserByID);
                    } else {
                        // andere pas en dat mag niet.
                    }
                    
                }
            } else {
                // gebruiker toevoegen!
                if (!userExists && !pasExists) {
                    if (userView.isAdminUser()) {
                        new LoginController(this);
                    } else {
                        createRegularUser();
                    }

                   String thePassword = this.userView.getWachtwoord2();
                   String theMD5 = User.convertToMD5((thePassword + User.salt));
                   ReaderRunnable.setStatusOnWritingUserDataToPass(theMD5);

                } else if (userExists){
                    userView.setFeedback("Gebruiker bestaat al!", Color.red);
                } else if(pasExists){
                    userView.setFeedback("Pas is al in gebruik!", Color.red);
                }
            }
        }

        if (!errorFound) {
            userView.setFeedback("", Color.BLACK);
        }
    }
    /**
     * Wanneer men op gebruiker toevegen klikt ( [+] ) dan wordt er een nieuwe JFrame gerendert
     */
    public void showUserView() {
        userView = new UserView(this);

        userView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        userView.getOpslaanButton().setEnabled(false);

        ReaderRunnable.setStatusOnInUserScreen(this);

        userView.setVisible(true);

        this.scanPass();

        //UserView uv = new UserView(this);
        //uv.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //uv.setVisible(true);

        /**
         * TUUURLIJK WERKT DIT NIET, HET ZOU NAMELIJK TE LOGISCH ZIJN!
         */
        // hier kan ik wellicht een event listener op toevoegen?
        userView.addWindowListener(new WindowListener() {

            @Override
            public void windowClosed(WindowEvent arg0) { /*System.out.println("Window close event occur");*/ }

            @Override
            public void windowActivated(WindowEvent arg0) { /*System.out.println("Window Activated");*/ }

            @Override
            public void windowClosing(WindowEvent arg0) {
                /**
                 * @TODO Hey, wat doet dit?
                 */
                ReaderRunnable.setStatusOnListeningAtDoor();
                System.out.println("Window userview Closing");
            }

            @Override
            public void windowDeactivated(WindowEvent arg0) { /*System.out.println("Window Deactivated");*/ }

            @Override
            public void windowDeiconified(WindowEvent arg0) { /*System.out.println("Window Deiconified");*/ }

            @Override
            public void windowIconified(WindowEvent arg0) { /* System.out.println("Window Iconified");*/ }

            @Override
            public void windowOpened(WindowEvent arg0) { /* System.out.println("Window Opened"); */ }
        });

    }
    /**
     * Wanneer men op gebruiker toevegen klikt ( [+] ) dan wordt er een nieuwe 
     * JFrame gerendert met de gebruikers gegevens
     * @param user User object
     */
    public void showUserView(User user) {
        userView = new UserView(this);

        userView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        userView.getOpslaanButton().setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user_edit.png")));
        userView.getOpslaanButton().setText("Wijzigen");
        //userView.getOpslaanButton().setEnabled(false);

        //ReaderRunnable.setStatusOnInUserScreen(this);
        
        userView.setVoornaam(user.getVoornaam());
        userView.setAchternaam(user.getAchternaam());
        userView.setStatusPasTextField(user.getTag());
        userView.setMakeAdmin(user.getIsAdmin());
        
        userView.setVisible(true);

        this.scanPass();

        userView.addWindowListener(new WindowListener() {
            @Override
            public void windowClosed(WindowEvent arg0) { /*System.out.println("Window close event occur");*/ }
            @Override
            public void windowActivated(WindowEvent arg0) { /*System.out.println("Window Activated");*/ }
            @Override
            public void windowClosing(WindowEvent arg0) {
                ReaderRunnable.setStatusOnListeningAtDoor();
                System.out.println("Window Closing");
                //usersView.kopLabel.setText("Wootwoo, updated");
            }
            @Override
            public void windowDeactivated(WindowEvent arg0) { /*System.out.println("Window Deactivated");*/ }
            @Override
            public void windowDeiconified(WindowEvent arg0) { /*System.out.println("Window Deiconified");*/ }
            @Override
            public void windowIconified(WindowEvent arg0) { /* System.out.println("Window Iconified");*/ }
            @Override
            public void windowOpened(WindowEvent arg0) { /* System.out.println("Window Opened"); */ }
        });

    }
    /**
     * UsersView opruimen
     */
    void disposeController() {
        usersView.dispose();
    }
    /**
     * Admin gebruiker aanmaken + succes-melding
     */
    public void createAdminUser() {
        new User(
                userView.getVoornaam(),
                userView.getAchternaam(),
                userView.getWachtwoord1(),
                userView.getStatusPasTextField().getText(),
                true);
        success();
    }
    /**
     * Normale gebruiker aanmaken + succes-melding
     */
    private void createRegularUser() {
        new User(
                userView.getVoornaam(),
                userView.getAchternaam(),
                userView.getWachtwoord1(),
                userView.getStatusPasTextField().getText(),
                false);
        success();
    }
    /**
     * Succes-melding rendert een Dialog
     */
    private void success() {
        JOptionPane.showMessageDialog(userView, "Gebruiker is succesvol toegevoegd!", "Gelukt!", JOptionPane.INFORMATION_MESSAGE);
        //userView.setVisible(false);
        if (userView.isGebruikerToevoegen()) {
            userView.resetForm();
            // .processUsers is niet nodig vanwege de eventlistener die nu wel z'n taak goed doet.
        } else {
            userView.dispose();
            usersView.processUsers();
        }
    }

    public void scanPass() {
    }
    /**
     * Haalt de UserView op
     * @return userView
     */
    public UserView getUserView() {
        return userView;
    }
    /**
     * Gebruiker bewerken knop rendert een UserView met inhoud
     * @param usersTable is nodig om de geselecteerde rij te vinden
     * @param tm is nodig om de userID te achterhalen
     */
    public void editUser(JTable usersTable, DefaultTableModel tm) {
        int userID = getUserIDFromSelectedRow(usersTable, tm);
        if(userID != -1){
            editUserByID = userID;
            showUserView(User.getUserByID(userID));
        }
    }
    /**
     * Haalt de ID op van een gebruiker aan de hand van de UsersView JTable
     * @param usersTable is nodig om de geselecteerde rij te vinden
     * @param tm is nodig om de userID te achterhalen
     * @return userID of -1 indien niet is gevonden/ niets geselecteerd
     */
    private int getUserIDFromSelectedRow(JTable usersTable, DefaultTableModel tm) {
        int row = usersTable.getSelectedRow();
        if (usersTable.getSelectedRow() != -1) {  
            selectedRow = row;
            return Integer.parseInt(usersTable.getModel().getValueAt(row, 3).toString());
        } else {
            JOptionPane.showMessageDialog(usersView, "Selecteer eerst een gebruiker voor deze actie.", "Niets geselecteerd", JOptionPane.INFORMATION_MESSAGE);
        }
        return -1;
    }
    /**
     * Gebruiker verwijderen
     * @param usersTable is nodig om de geselecteerde rij te vinden
     * @param tm is nodig om de userID te achterhalen
     */
    public void removeUser(JTable usersTable, DefaultTableModel tm) {
        int userID = getUserIDFromSelectedRow(usersTable, tm);
        if(userID != -1){
            tm.removeRow(selectedRow);
            User.removeUser(userID);
        }
    }

}
