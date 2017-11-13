/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 * Deze thread roept LoginController aan.
 * @author Razzy
 */
public class ApplicationRunnable implements Runnable{
    @Override
    public void run() {
         new LoginController();
    }

    
    
}
