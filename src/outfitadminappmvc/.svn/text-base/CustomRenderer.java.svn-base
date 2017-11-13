package outfitadminappmvc;

import java.awt.Color;
import java.awt.Component;
import java.util.Arrays;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomRenderer extends DefaultTableCellRenderer {
    List<Color> rowColours = Arrays.asList(
        Color.RED,
        Color.GREEN,
        Color.CYAN
    );
    
    private Color nextColor = new Color(0, 0, 255);

    public void setRowColour(int row, Color c) {
        rowColours.set(row, c);
    //    fireTableRowsUpdated(row, row);
        
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        //System.out.println(column);
        return c;
        
    }
    
    

    
}
