import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.Label;
import java.awt.Canvas;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import javax.swing.JSeparator;
import java.awt.Panel;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.AbstractAction;
import javax.swing.Action;

public class GUI {

	public JFrame frame;
	//private JFrame connectFrame; // this frame is for the connect frame
	private JTextField money;
	
	// here is the chat stuff
	private static int port = 4444;
	private static BufferedReader streamIn;
    private static PrintStream streamOut;
    private JTextField chatTextField;
    private JTextArea textArea;
    private JButton sendButton;
    private JButton clearButton;
    
    private JPanel gamePanel;
    private JPanel cardsPanel;
    private JPanel gameFiled;
    private JPanel rightSidePanel;
    
    

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
					window.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Here I am just creating the gui
		frame = new JFrame();
		frame.setSize(1300, 800);
		
		// This will close the game when you click the close button
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		// This panel is the whole game panel
		gamePanel = new JPanel();
		frame.getContentPane().add(gamePanel);
		gamePanel.setLayout(null);
		
		// This panel is the hand panel, the card here should be the players hand cards
		cardsPanel = new JPanel();
		cardsPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		cardsPanel.setBackground(Color.LIGHT_GRAY);
		cardsPanel.setBounds(6, 601, 735, 111);
		gamePanel.add(cardsPanel);
		cardsPanel.setLayout(null);
		
		
		// This is the game panel, "board", the played cards should be here in this panel. 
		gameFiled = new JPanel();
		gameFiled.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		gameFiled.setBackground(new Color(255, 255, 240));
		gameFiled.setBounds(6, 6, 1049, 583);
		gamePanel.add(gameFiled);
		gameFiled.setLayout(null);
		
		
		
		// This panel is the right side panel,, where the chat and logs are... 
		rightSidePanel = new JPanel();
		rightSidePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		rightSidePanel.setBounds(1061, 6, 233, 766);
		gamePanel.add(rightSidePanel);
		rightSidePanel.setLayout(null);
		
		JLabel lblLogs = new JLabel("Logs");
		lblLogs.setBounds(7, 127, 220, 29);
		rightSidePanel.add(lblLogs);
		lblLogs.setFont(new Font("Arial", Font.PLAIN, 20));
		
		// Here is the chat info
		JLabel lblChat = new JLabel("Chat");
		lblChat.setBounds(5, 364, 222, 29);
		rightSidePanel.add(lblChat);
		lblChat.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		

		JButton btnPlayer = new JButton("player 3");
		btnPlayer.setBounds(3, 86, 106, 29);
		rightSidePanel.add(btnPlayer);
		
		JButton btnPlayer_3 = new JButton("player 4");
		btnPlayer_3.setBounds(121, 86, 106, 29);
		rightSidePanel.add(btnPlayer_3);
		
		JButton btnPlayer_1 = new JButton("player 2");
		btnPlayer_1.setBounds(121, 45, 106, 29);
		rightSidePanel.add(btnPlayer_1);
		
		JButton btnPlayer_2 = new JButton("player 1");
		btnPlayer_2.setBounds(7, 45, 106, 29);
		rightSidePanel.add(btnPlayer_2);
		
		JLabel screen = new JLabel("screen");
		screen.setFont(new Font("Arial", Font.PLAIN, 20));
		screen.setBounds(8, 4, 220, 29);
		rightSidePanel.add(screen);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(6, 161, 221, 191);
		rightSidePanel.add(panel_2);
		panel_2.setLayout(null);
		
		JTextArea logsArea = new JTextArea();
		logsArea.setEditable(false);
		logsArea.setBounds(0, 0, 221, 191);
		panel_2.add(logsArea);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(7, 405, 221, 287);
		rightSidePanel.add(panel_1);
		panel_1.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		textArea.setBounds(0, 0, 221, 286);
		panel_1.add(textArea);
		
		JPanel panel = new JPanel();
		panel.setBounds(7, 704, 220, 25);
		rightSidePanel.add(panel);
		panel.setLayout(null);
		
		chatTextField = new JTextField();
		chatTextField.setBounds(0, 0, 220, 26);
		panel.add(chatTextField);
		chatTextField.setColumns(10);

		
		// Here are the buttons to use in chat.
		clearButton = new JButton("Clear");
		clearButton.setBounds(133, 731, 93, 29);
		rightSidePanel.add(clearButton);
		
		sendButton = new JButton("Send");
		sendButton.setBounds(7, 731, 130, 29);
		rightSidePanel.add(sendButton);
		
		sendButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                streamOut.println(chatTextField.getText());
                chatTextField.setText("");
            }
        });
       
		chatTextField.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                streamOut.println(chatTextField.getText());
                chatTextField.setText("");
            }
        });           

		
		
		
		// This panel is for the Actions of the game, the player can use these actions during his turn. These actions should be linked with the other classes to make it work..
		JPanel playerActionsPanel = new JPanel();
		playerActionsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		playerActionsPanel.setBounds(760, 601, 295, 171);
		gamePanel.add(playerActionsPanel);
		playerActionsPanel.setLayout(null);
		
		JButton btnNext = new JButton("Transfer Money");
		btnNext.setBounds(153, 38, 130, 29);
		playerActionsPanel.add(btnNext);
		
		JLabel lblCardActionsJ = new JLabel("Player Actions ");
		lblCardActionsJ.setBounds(6, 6, 121, 20);
		playerActionsPanel.add(lblCardActionsJ);
		lblCardActionsJ.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		
		JButton btnAttack = new JButton("Move Money");
		btnAttack.setBounds(10, 67, 130, 29);
		playerActionsPanel.add(btnAttack);
		
		JButton btnGiveAGroup = new JButton("Give a Group");
		btnGiveAGroup.setBounds(153, 67, 130, 29);
		playerActionsPanel.add(btnGiveAGroup);
		
		JButton btnDropAGroup = new JButton("Give a Card");
		btnDropAGroup.setBounds(153, 96, 130, 29);
		playerActionsPanel.add(btnDropAGroup);
		
		JButton btnAttack_1 = new JButton("Attack");
		btnAttack_1.setBounds(11, 38, 130, 29);
		playerActionsPanel.add(btnAttack_1);
		
		JButton btnUseASpecial = new JButton("Use a Specialty ");
		btnUseASpecial.setBounds(10, 96, 130, 29);
		playerActionsPanel.add(btnUseASpecial);
		
		JButton button = new JButton("Drop a Group");
		button.setBounds(10, 136, 130, 29);
		playerActionsPanel.add(button);
		
		JButton btnNextTurn = new JButton("Done");
		btnNextTurn.setBounds(153, 136, 130, 29);
		playerActionsPanel.add(btnNextTurn);
		
		
		// This panel is player info panel,, it shows the info of the player 
		JPanel playerInfo = new JPanel();
		playerInfo.setBorder(new LineBorder(new Color(0, 0, 0)));
		playerInfo.setBounds(6, 727, 735, 45);
		gamePanel.add(playerInfo);
		playerInfo.setLayout(null);
		
		// This is the player money, should be linked with the player attributes
		JLabel lblTotalBalance = new JLabel("Total balance:");
		lblTotalBalance.setBounds(6, 6, 99, 16);
		playerInfo.add(lblTotalBalance);
		
		money = new JTextField();
		money.setText("$200");
		money.setBounds(99, 1, 130, 26);
		playerInfo.add(money);
		money.setColumns(10);
		
		
		// here is the connect button
		JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(599, 10, 130, 29);
		playerInfo.add(btnConnect);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//connectFrame connect = new connectFrame();
				
				
			} //close the action 
			
		}); // close initialize()
		
		
	}

    private String getUsername(){
        return JOptionPane.showInputDialog(frame, "Server IP Address:", "Welcome to Chat", JOptionPane.QUESTION_MESSAGE);
    }
	

    private void run() throws IOException{
        Socket clientSocket = new Socket("localhost", port);
        streamIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        streamOut = new PrintStream(clientSocket.getOutputStream(), true);
    
        while(true){
            String line = streamIn.readLine();
            if(line.startsWith("Username")){
                streamOut.println(getUsername());
            }else if(line.startsWith("Welcome")){
            	chatTextField.setEditable(true);
            }else if(line.startsWith("From")){
            	textArea.append(line.substring(10)+ "\n"); // output
            }
        }   
    }
}
