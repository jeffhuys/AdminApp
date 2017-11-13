/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import view.ExportView;

/**
 * Deze controller zorgt ervoor dat de LogItems kunnen worden geexporteerd
 * naar een extern programma
 * @author Razzy
 */
public class ExportController {
    ExportView exportView;
    DefaultTableModel tableModel;
    
    public ExportController(DefaultTableModel tableModel){
      
        this.tableModel = tableModel;
          this.exportView = new ExportView(this);
        exportView.setVisible(true);
        exportView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public void exportToCSV(){
        
        
        String theExport = "";
        Vector tableContent =  tableModel.getDataVector();
        theExport += "Type, Naam, Locatie, Bijzonderheden, Tijd \n";
        for(Object lineObject : tableContent){
           Vector lineVector = (Vector)lineObject;
        
           boolean firstTime = true;           
                   for(Object recordObject : lineVector){
               if(!firstTime){
                   theExport += ",";
               }
               
               String recordString = (String)recordObject;
               theExport += recordString;
               
               firstTime = false;
           }
           
           theExport += "\n";
        }
        
        exportView.getjTextArea1().setText(theExport);
        System.out.println(theExport);
    }
}
