
import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class test extends JLabel {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test window = new test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
		public static String path;
		
		
	/**
	 * Create the application.
	 */
	public test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JLabel lblMAIN = new JLabel();
		// here we are not changing the size we are just creating it...
		changeSize(0, 0, 150, 90,lblMAIN);
		lblMAIN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// tried to use the method here but i don't know why its not working... if you can fix it that would be cleaner. 
				//changeSize(0, 0, 200, 200,lblMAIN);

				lblMAIN.setBounds(0, 0, 300, 180);
				path = "/Users/nasser/Desktop/CECS 343/card pics/1.jpg"; 
				frame.getContentPane().add(lblMAIN);
				ImageIcon imageIcon = new ImageIcon(path);
				Image img = imageIcon.getImage();
				Image scaledImage = img.getScaledInstance(lblMAIN.getWidth(), lblMAIN.getHeight(), Image.SCALE_SMOOTH);
				
				ImageIcon image = new ImageIcon(scaledImage);
				lblMAIN.setIcon(image);
				
				
			}
			public void mouseExited(MouseEvent e) {
				// tried to use the method here but i don't know why its not working... if you can fix it that would be cleaner. 
				//changeSize(0, 0, 100, 100,lblMAIN);
				lblMAIN.setBounds(0, 0, 150, 90);
				path ="/Users/nasser/Desktop/CECS 343/card pics/1.jpg";
				frame.getContentPane().add(lblMAIN);
				ImageIcon imageIcon = new ImageIcon(path);
				Image img = imageIcon.getImage();
				Image scaledImage = img.getScaledInstance(lblMAIN.getWidth(), lblMAIN.getHeight(), Image.SCALE_SMOOTH);
				
				ImageIcon image = new ImageIcon(scaledImage);
				lblMAIN.setIcon(image);
			    }
		});
		
	}
	/*
	 * This method is to change the size for the card 
	 */
	public void changeSize(int x, int y, int h, int w, JLabel z){
		// x and y are the locations
		// h and w are the size 
		z.setBounds(0, 0, 100, 100);
		
		// path can also be an attribute 
		path ="/Users/nasser/Desktop/CECS 343/card pics/1.jpg"; 
		frame.getContentPane().add(z);
		ImageIcon imageIcon = new ImageIcon(path);
		Image img = imageIcon.getImage();
		Image scaledImage = img.getScaledInstance(z.getWidth(), z.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(scaledImage);
		z.setIcon(image);
		
		
	}
}
