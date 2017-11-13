package controller;
import javax.swing.JFrame;
import view.LogView;
import view.StatisticsView;
/**
 *
 * @author Vincent
 */
public class StatisticsController {
    private StatisticsView statisticsView;
    
    public StatisticsController() {
		statisticsView = new StatisticsView(this);
                statisticsView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                statisticsView.setVisible(true);

	}
        /**
        * Afhandelen van de sluitknop
        *  
        * @param exitOnClose 
        */
	public StatisticsController(boolean exitOnClose) {
		statisticsView = new StatisticsView(this);
		
		if(!exitOnClose)
			statisticsView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		else
			statisticsView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		statisticsView.setVisible(true);
	}
        /**
         * Begin positie van StatisticsView
         * 
         * @param startX
         * @param startY 
         */
        public StatisticsController(int startX, int startY) {
		statisticsView = new StatisticsView(this);
                statisticsView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                statisticsView.setVisible(true);
                
                statisticsView.setLocation(startX, startY);
        }
        
        /**
         * Zorgt er voor dat StatisticsView naast LogView komt te staan.
         * 
         * @param snapToRight
         * @param logView 
         */
        public StatisticsController(boolean snapToRight, LogView logView) {
		statisticsView = new StatisticsView(this);
                statisticsView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                statisticsView.setVisible(true);
                
                if(snapToRight) {
                    //statisticsView.setLocation(startX, startY);
                    statisticsView.setLocationRelativeTo(logView);
                    statisticsView.setLocation(statisticsView.getX() + 490, statisticsView.getY() - 125);
                                        
                    if(statisticsView.getX() > 1366)
                        statisticsView.setLocation(1366-250, 250);
                    if(statisticsView.getY() < 0)
                        statisticsView.setLocation(statisticsView.getX(), 0);
                }
        }
        
        public void disposeController() {
            statisticsView.dispose();
        }
    
}
