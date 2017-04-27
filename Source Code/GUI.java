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
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.Label;
import java.awt.Canvas;
import javax.swing.JLayeredPane;
import javax.swing.border.EtchedBorder;
import javax.swing.JSeparator;
import java.awt.Panel;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;

public class GUI {

	private JFrame frame;
	private JTextField chatTextField;
	private JTextField money;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
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
		JPanel gamePanel = new JPanel();
		frame.getContentPane().add(gamePanel);
		gamePanel.setLayout(null);
		
		// This panel is the hand panel, the card here should be the players hand cards
		JPanel cardsPanel = new JPanel();
		cardsPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		cardsPanel.setBackground(Color.LIGHT_GRAY);
		cardsPanel.setBounds(6, 601, 735, 111);
		gamePanel.add(cardsPanel);
		cardsPanel.setLayout(null);
		
		
		// This is the game panel, "board", the played cards should be here in this panel. 
		JPanel gameFiled = new JPanel();
		gameFiled.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		gameFiled.setBackground(new Color(255, 255, 240));
		gameFiled.setBounds(6, 6, 1049, 583);
		gamePanel.add(gameFiled);
		gameFiled.setLayout(null);
		
		
		// Here are the card pictures, they should be card class. We have to link the card class with the GUI and make it display in here... 
		// Delete this 
		JLabel label_7 = new JLabel("");
		label_7.setIcon(new ImageIcon("/Users/nasser/Desktop/CECS 343/card pics/7.jpg"));
		label_7.setBounds(6, 6, 150, 95);
		cardsPanel.add(label_7);
		
		JLabel label_8 = new JLabel("");
		label_8.setIcon(new ImageIcon("/Users/nasser/Desktop/CECS 343/card pics/6.jpg"));
		label_8.setBounds(168, 6, 150, 95);
		cardsPanel.add(label_8);
		
		JLabel label_9 = new JLabel("");
		label_9.setIcon(new ImageIcon("/Users/nasser/Desktop/CECS 343/card pics/5.jpg"));
		label_9.setBounds(330, 6, 150, 95);
		cardsPanel.add(label_9);
		
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("/Users/nasser/Desktop/CECS 343/card pics/1.jpg"));
		label.setBounds(308, 422, 150, 103);
		gameFiled.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("/Users/nasser/Desktop/CECS 343/card pics/2.jpg"));
		label_1.setBounds(804, 228, 101, 150);
		gameFiled.add(label_1);
		
		JLabel label_3 = new JLabel("");
		label_3.setBounds(470, 422, 150, 103);
		gameFiled.add(label_3);
		
		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon("/Users/nasser/Desktop/CECS 343/card pics/8.jpg"));
		label_4.setBounds(804, 390, 101, 150);
		gameFiled.add(label_4);
		
		JLabel label_5 = new JLabel("");
		label_5.setIcon(new ImageIcon("/Users/nasser/Desktop/CECS 343/card pics/10.jpg"));
		label_5.setBounds(804, 66, 96, 150);
		gameFiled.add(label_5);
		
		JLabel label_6 = new JLabel("");
		label_6.setIcon(new ImageIcon("/Users/nasser/Desktop/CECS 343/card pics/4.jpg"));
		label_6.setBounds(321, 41, 150, 103);
		gameFiled.add(label_6);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon("/Users/nasser/Desktop/CECS 343/card pics/9.jpg"));
		label_2.setBounds(483, 41, 150, 103);
		gameFiled.add(label_2);
		
		// Here is the amount of the money for each card,, this should be an attribute of the card class. should be linked with the card class too
		textField_1 = new JTextField();
		textField_1.setText("$20");
		textField_1.setBackground(UIManager.getColor("Button.select"));
		textField_1.setBounds(470, 402, 35, 26);
		gameFiled.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setText("$30");
		textField_2.setColumns(10);
		textField_2.setBackground(UIManager.getColor("Button.select"));
		textField_2.setBounds(769, 388, 35, 26);
		gameFiled.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setText("$0");
		textField_3.setColumns(10);
		textField_3.setBackground(UIManager.getColor("Button.select"));
		textField_3.setBounds(757, 184, 35, 26);
		gameFiled.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setText("$10");
		textField_4.setColumns(10);
		textField_4.setBackground(UIManager.getColor("Button.select"));
		textField_4.setBounds(515, 140, 35, 26);
		gameFiled.add(textField_4);
		
		
		// This panel is the right side panel,, where the chat and logs are... 
		JPanel rightSidePanel = new JPanel();
		rightSidePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		rightSidePanel.setBounds(1061, 6, 233, 766);
		gamePanel.add(rightSidePanel);
		rightSidePanel.setLayout(null);
		
		// Here is the logs info:
	
		JEditorPane logsPane = new JEditorPane();
		logsPane.setText("player 1 loged in.\nplayer 2 loged in.\nplayer 3 loged in.\nGame Starts NOW...\nplayer 1 turn!\nplayer 1 added a card.\nplayer 1 end turn!\nplayer 2 turn!\nplayer 2 added a card.\nplayer 2 end turn!\nplayer 3 turn!\nplayer 3 added a card.");
		logsPane.setBounds(6, 39, 221, 193);
		rightSidePanel.add(logsPane);
		
		JLabel lblLogs = new JLabel("Logs");
		lblLogs.setBounds(6, 6, 220, 29);
		rightSidePanel.add(lblLogs);
		lblLogs.setFont(new Font("Arial", Font.PLAIN, 20));
		
		// Here is the chat info
		JLabel lblChat = new JLabel("Chat");
		lblChat.setBounds(6, 239, 222, 29);
		rightSidePanel.add(lblChat);
		lblChat.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		
		
		JTextPane chatPane = new JTextPane();
		chatPane.setText("Player 1: Hi there.\nPlayer 1: I wish you the best, but i will win.\nPlayer 2: I dont think so.\nPlayer 1: haha will see.\nPlayer 2: kk\nPlayer 3: can you please hurry up and play");
		chatPane.setBounds(6, 280, 220, 423);
		rightSidePanel.add(chatPane);
		chatPane.setForeground(Color.BLACK);
		chatPane.setBackground(Color.WHITE);
		
		chatTextField = new JTextField();
		chatTextField.setBounds(6, 710, 220, 26);
		rightSidePanel.add(chatTextField);
		chatTextField.setColumns(10);
		
		// Here are the buttons to use in chat.
		JButton clearButton = new JButton("Clear");
		clearButton.setBounds(133, 731, 93, 29);
		rightSidePanel.add(clearButton);
		
		JButton sendButton = new JButton("Send");
		sendButton.setBounds(6, 731, 130, 29);
		rightSidePanel.add(sendButton);
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// link with the server
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
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
	}
}




/*CardGUI for later
 CardGUI myCard = new CardGUI((Card) deck.drawCard());
		 myCard.setIcon(new ImageIcon(myCard.Card().ImagePath()));
		 myCard.setBounds(168, 6, 150, 95);
		cardsPanel.add(myCard);
		myCard.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	        	myCard.setIcon(new ImageIcon("C:/Users/Ryan/eclipse/workspace/Illuminati2/src/img/TheMafia.png"));
	        }
	    });
		*/
