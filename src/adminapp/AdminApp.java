package adminapp;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class AdminApp {
	private JFrame frame;
	
	public AdminApp() {
		frame = new AddUser();
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setVisible(true); 
	}
	
	public static void main(String[] args) {
		AdminApp aa = new AdminApp();
	}
}
