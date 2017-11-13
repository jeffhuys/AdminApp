/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Razzy
 */
public class User {

    public static ArrayList<User> users = new ArrayList<User>();
    public static String salt = "KJHi&^*&6y&*GUy3";
    private int id;
    private String firstName;
    private String lastName;
    private String password;
    private String tagid;
    private boolean isAdmin;

    //private ArrayList<Group> groupsHasUser = new ArrayList<Group>(); 
    /**
     * Gebruiker aanmaken met de DatabaseRunnable
     * 
     * @param id
     * @param firstName
     * @param lastName
     * @param password
     * @param tagid
     * @param isAdmin 
     */
    public User(int id, String firstName, String lastName, String password, String tagid, boolean isAdmin) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.tagid = tagid;
        this.isAdmin = isAdmin;

        addUserToArrayList(this);
    }

    /**
     * Gebruiker aanmaken met de GUI (password krijgt een md5 checksum + salt)
     * 
     * @param firstName
     * @param lastName
     * @param password
     * @param tagid
     * @param isAdmin 
     */
    public User(String firstName, String lastName, String password, String tagid, boolean isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = convertToMD5((password + salt).getBytes());
        this.tagid = tagid;
        this.isAdmin = isAdmin;

        //Group group = new Group(this.firstName+this.lastName, true, this);
        //groupsHasUser.add(group);

        addUser(this);
    }

    /**
     * Gebruiker toevoegen, aan ArrayList en de database
     * @param user 
     */
    private void addUser(User user) {
        addUserToArrayList(user);
        addUserToDatabase(user);
    }

    /**
     * Voornaam ophalen
     * @return String Voornaam
     */
    public String getVoornaam() {
        return this.firstName;
    }

    /**
     * Controleer of het om een admin gaat
     * @return boolean true voor admin
     */
    public boolean isAdmin() {
        return this.isAdmin;
    }

    /**
     * Gebruiker aan de database toevoegen
     * @param user 
     */
    private void addUserToDatabase(User user) {
        try {
            Database.createStatement();
            int tinyint = 0;
            if (user.isAdmin) {
                tinyint = 1;
            }

            String query = "INSERT INTO `User` (`firstName`, `lastName`, `password`, `isAdmin`, `timeInterval`, `tagid`) VALUES ('" + user.firstName + "', '" + user.lastName + "', '" + user.password + "', '" + tinyint + "', '15', '" + user.tagid + "')";
            System.out.println(query);
            Database.executeUpdate(query);
            Database.closeStatement();
            Database.version++;


            Database.createStatement();
            ResultSet resultSet = Database.executeQuery("SELECT `id` FROM User WHERE `tagid` =\"" + user.tagid +"\"");
            int theId = resultSet.getInt("id");
            user.id = theId;
            Database.closeStatement();
            Database.version++;

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Voor het syncen van de bewerkte gebruiker naar de Database
     * @param user Userobject
     * @param ID userID
     */
    public static void editUserIntoDatabase(User user, int ID) {
        try {
            Database.createStatement();
            
            String query = "UPDATE `User` SET `firstName` = '" + user.getVoornaam() + "', `lastName` = '" + user.getAchternaam() + "', `password` = '" + user.password + "' WHERE `id` = " + ID;
            System.out.println("UPDATE USER IN DB " +query);
            Database.executeUpdate(query);
            Database.closeStatement();
            Database.version++;
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gebruiker toevoegen aan het geheugen (<ArrayList>)
     * @param user Object van gebruiker
     */
    private void addUserToArrayList(User user) {
        users.add(user);
    }

    /**
     * Achternaam ophalen
     * @return String Achternaam
     */
    public String getAchternaam() {
        return this.lastName;
    }

    /**
     * Gebruikerspas ophalen
     * @return String serienummer van de smartcard
     */
    public String getTag() {
        return this.tagid;
    }

    /**
     * Wachtwoord ophalen
     * @return String wachtwoord (MD5)
     */
    public String getPassword() {
        return this.password;
    }
    /**
     * Tovert een string om in een MD5 hash, + een whileloopje die er een 0 voor zet
     * als de BigInteger begint met een 0 (ondersteund namelijk geen leading zeroes)
     * @param hashword wachtwoord met zout
     * @return MD5-hash
     */
    public static String convertToMD5(String hashword) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(hashword.getBytes());

            BigInteger hash = new BigInteger(1, md5.digest());
            hashword = hash.toString(16);

            while (hashword.length() < 32) {
                hashword = "0" + hashword;
            }
        } catch (NoSuchAlgorithmException e) {
        }
        return hashword;
    }

    /**
     * Converteert een byteArray naar een MD5 hash (DEP)
     * @param bytesToConvert
     * @return dep
     */
    public static String convertToMD5(byte[] bytesToConvert) {
        MessageDigest m;
        String passHash = "";
        try {
            m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(bytesToConvert);
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            passHash = bigInt.toString(16);
            System.out.println("Dit is de Hash :" + passHash);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return passHash;
    }

    /**
     * Haalt ID op van gebruiker
     * @return userID
     */
    public int getId() {
        return this.id;
    }
    /**
     * Haalt een gebruiker weg uit de ArrayList en Database
     * @param ID userID
     */
    public static void removeUser(int ID) {
        int key = 0;
        for (User user : users) {
            if (user.getId() == ID) {
                users.remove(key);
                 String query = "DELETE FROM `User` WHERE id=" + ID;
                try {
                    Database.createStatement();
                    Database.executeUpdate(query);
                    Database.closeStatement();
                    Database.version++;
                } catch (SQLException ex) {
                    Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                }
                 
                
                System.out.println("WEGGEOOIDD!");
                // break als hij de gebruiker weggooid, anders barft hij een 
                // bigass error en doet hij meer dan dat je wilt
                break;

                /**
                 * @todo User remove from database. voor de break
                 */
            }
            key++;
        }
    }
    /**
     * Haalt een gebruiker op uit de ArrayList en matcht op userID
     * @param ID userID
     * @return User object van gebruiker
     */
    public static User getUserByID(int ID) {
        int i = 0;
        for (User user : users) {
            if (user.getId() == ID) {
                return users.get(i);
            }
            i++;
        }
        return null;
    }
    /**
     * Check of het een admin gebruiker is
     * @return boolean is admin of niet
     */
    public boolean getIsAdmin() {
        return this.isAdmin;
    }
    /**
     * Zet de voornaam van een gebruiker imv bewerken
     * @param ID userID
     * @param voornaam van gebruiker
     */
    public void setVoornaam(int ID, String voornaam) {
        for (User user : users) {
            if (user.getId() == ID) {
                user.firstName = voornaam;
                break;
            }
        }
    }
    /**
     * Zet de achternaam van een gebruiker ivm bewerken
     * @param ID userID
     * @param achternaam van gebruiker
     */
    public void setAchternaam(int ID, String achternaam) {
        for (User user : users) {
            if (user.getId() == ID) {
                user.lastName = achternaam;
                break;
            }
        }
    }
    /**
     * Zet het wachtwoord (String met MD5+salt) voor een gebruiker
     * @param ID userID
     * @param password md5hash
     */
    public void setPassword(int ID, String password) {
        for (User user : users) {
            if (user.getId() == ID) {
                user.password = password;
                break;
            }
        }
    }
}
