/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import view.DebugView;

/**
 *
 * @author Razzy
 */
public class Debug {
   
    private static DebugView debugView;
    
   public static void openDebugView(){
       debugView = new DebugView();
       debugView.setVisible(false);
       
   }
   
   public static void changeReaderStatus(String status){
       debugView.getReaderStatusTextField().setText("");
        debugView.getReaderStatusTextField().setText(status + "");
   }
   
   public static void changeSensorValue(int value){
       debugView.getSensorWaardeTextField().setText("");
       debugView.getSensorWaardeTextField().setText(value + "");
   }
   
    public static void changeSensorValue(String status){
       debugView.getSensorWaardeTextField().setText("");
       debugView.getSensorWaardeTextField().setText(status + "");
   }
   
   public static void changeDatabaseVersionValue(int value){
       debugView.getDatabaseVersionTextField().setText("");
       debugView.getDatabaseVersionTextField().setText(value + "");
   }

   public static void changeAppVersionValue(int value){
       debugView.getAppVersionTextField().setText("");
       debugView.getAppVersionTextField().setText(value + "");
   }

}


