/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import acs.jni.ACR120U;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Debug;
import model.LogEntry;
import model.User;

/**
 *
 * @author Razzy
 */
public class ReaderRunnable extends ReaderHandeling implements Runnable {

    private static int status;
    public static final int LISTENING_AT_DOOR = 1;
    public static final int IN_USER_SCREEN = 2;
    public static final int WRITING_USER_DATA_TO_PASS = 3;
    private static UsersController usersController = null;
    private DoorController doorController;
    private static String passwordHashed = "";

    public ReaderRunnable() {
    }

    @Override
    public void run() {
        doorController = new DoorController();
        status = LISTENING_AT_DOOR;
        cardReader = new ACR120U();
        handle = cardReader.open(ACR120U.ACR120_USB1);
        System.out.println("handle: " + handle);





        while (true) {

            select();

            if (status == LISTENING_AT_DOOR) {
                if (tag >= 0) {
                    // checkUserData();
                    boolean access = false;



                    beep();
                    sectorLogin((byte) 2);
                    String theSerialNumber = "";
                    for (int i = 0; i < 10; i++) {
                        theSerialNumber += Integer.toHexString(serialNumber[i]);

                    }

                    User theUser = null;
                    String password = (readBlock((byte) 8) + readBlock((byte) 9));
                    for (User u : User.users) {
                        if (u.getTag().equals(theSerialNumber) && u.getPassword().equals(password)) {

                            theUser = u;
                            System.out.println("TheUser: " + theUser);


                            access = true;
                            new LogEntry(theUser, "Scanner", "De pas wordt gescand", LogEntry.LOGENTRY_PASGESCANT, true);
                            break;
                        } else {
                            access = false;

                        }
                    }
                    


                    
                    

                    for (User user : User.users) {
                        String hashedPassword = user.getPassword();
                        System.out.println(hashedPassword.getBytes());
                    }

                    beep();

                    if (access) {

                        // register in log that access is granted, and that door is open
                        doorController.openDoor();
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ReaderRunnable.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        doorController.closeDoor();
                    } else {
                        // Niet-bekende pas/fout wachtwoord
                        new LogEntry(null, "Scanner", "Onbekende pas gescand / fout wachtwoord op de pas", LogEntry.LOGENTRY_HACKPOGING, true);
                    }

                } else {
                    Debug.changeReaderStatus("Luistert bij deur -- Geen kaart");
                }

            }

            if (status == IN_USER_SCREEN) {
                Debug.changeReaderStatus("In de user screen");
                String theSerialNumber = "";
                if (tag >= 0) {

                    for (int i = 0; i < 10; i++) {
                        theSerialNumber += Integer.toHexString(serialNumber[i]);



                    }


                    usersController.getUserView().getStatusPasTextField().setText(theSerialNumber);
                    usersController.getUserView().getOpslaanButton().setEnabled(true);
                    Debug.changeReaderStatus("In de user screen -- Kaart op reader met serialnummer: " + theSerialNumber);


                } else {
                    Debug.changeReaderStatus("In de user screen -- Geen kaart op reader");
                    usersController.getUserView().getStatusPasTextField().setText("Zet pas op reader!!!");
                    usersController.getUserView().getOpslaanButton().setEnabled(false);
                }
            }

            if (status == WRITING_USER_DATA_TO_PASS) {
                Debug.changeReaderStatus("Schrijft user data naar pas");
                if (tag >= 0) {
                    sectorLogin((byte) 2);
                    String stringOne = passwordHashed.substring(0, 16);
                    String stringTwo = passwordHashed.substring(16, 32);

                    this.writeString(stringOne, (byte) 8);
                    this.writeString(stringTwo, (byte) 9);

                    status = LISTENING_AT_DOOR;
                    Debug.changeReaderStatus("Schrijft user data naar pas -- Heeft geschreven naar kaar: " + stringOne + stringTwo);

                }
            }


            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                Logger.getLogger(ReaderRunnable.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    static void setStatusOnInUserScreen(UsersController usersController) {
        status = IN_USER_SCREEN;
        ReaderRunnable.usersController = usersController;
    }

    static void setStatusOnListeningAtDoor() {
        status = LISTENING_AT_DOOR;


    }

    static void setStatusOnWritingUserDataToPass(String passwordHashed) {
        status = WRITING_USER_DATA_TO_PASS;

        ReaderRunnable.passwordHashed = passwordHashed;


    }
}
