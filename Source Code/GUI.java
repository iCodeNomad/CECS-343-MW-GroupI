import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

public class GUI {

	private JFrame frame;
	private JTextField chatTextField;
	private JTextField textField;
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
		frame = new JFrame();
		frame.setSize(1300, 800);
		//frame.getBackground(red);
		//frame.setBounds(200, 200, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel cardsPanel = new JPanel();
		cardsPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		cardsPanel.setBackground(Color.LIGHT_GRAY);
		cardsPanel.setBounds(6, 601, 735, 111);
		panel.add(cardsPanel);
		cardsPanel.setLayout(null);
		
		FieldGUI gamePanel = new FieldGUI();
		gamePanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		gamePanel.setBackground(new Color(255, 255, 240));
		gamePanel.setBounds(6, 6, 1049, 583);
		panel.add(gamePanel);
		gamePanel.setLayout(null);
		Player player = new Player("Joe");
		Deck deck = new Deck();
		player.DrawCard(deck);
		player.DrawCard(deck);
		player.DrawCard(deck);
		player.DrawCard(deck);
		player.DrawCard(deck);
		
		Card card = (Card) deck.drawCard();
		CardGUI cardGUI = new CardGUI(card, new Rectangle(5, 10, 150, 90));
		gamePanel.addCard(cardGUI, new Point(29, 0));
		card = (Card) deck.drawCard();
		cardGUI = new CardGUI(card, new Rectangle(5, 10, 150, 90));
		gamePanel.addCard(cardGUI, new Point(6, 6));
		gamePanel.displayCards();
		GenerateHand(player, cardsPanel);
		
		
		
		textField_1 = new JTextField();
		textField_1.setText("$20");
		textField_1.setBackground(UIManager.getColor("Button.select"));
		textField_1.setBounds(470, 402, 35, 26);
		gamePanel.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setText("$30");
		textField_2.setColumns(10);
		textField_2.setBackground(UIManager.getColor("Button.select"));
		textField_2.setBounds(769, 388, 35, 26);
		gamePanel.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setText("$0");
		textField_3.setColumns(10);
		textField_3.setBackground(UIManager.getColor("Button.select"));
		textField_3.setBounds(757, 184, 35, 26);
		gamePanel.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setText("$10");
		textField_4.setColumns(10);
		textField_4.setBackground(UIManager.getColor("Button.select"));
		textField_4.setBounds(515, 140, 35, 26);
		gamePanel.add(textField_4);
		
		JPanel rightSidePanel = new JPanel();
		rightSidePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		rightSidePanel.setBounds(1061, 6, 233, 766);
		panel.add(rightSidePanel);
		rightSidePanel.setLayout(null);
		
		JEditorPane logsPane = new JEditorPane();
		logsPane.setText("player 1 loged in.\nplayer 2 loged in.\nplayer 3 loged in.\nGame Starts NOW...\nplayer 1 turn!\nplayer 1 added a card.\nplayer 1 end turn!\nplayer 2 turn!\nplayer 2 added a card.\nplayer 2 end turn!\nplayer 3 turn!\nplayer 3 added a card.");
		logsPane.setBounds(6, 39, 221, 193);
		rightSidePanel.add(logsPane);
		
		JLabel lblChat = new JLabel("Chat");
		lblChat.setBounds(6, 239, 222, 29);
		rightSidePanel.add(lblChat);
		lblChat.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		
		JLabel lblLogs = new JLabel("Logs");
		lblLogs.setBounds(6, 6, 220, 29);
		rightSidePanel.add(lblLogs);
		lblLogs.setFont(new Font("Arial", Font.PLAIN, 20));
		
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
		
		JButton clearButton = new JButton("Clear");
		clearButton.setBounds(133, 731, 93, 29);
		rightSidePanel.add(clearButton);
		
		JButton sendButton = new JButton("Send");
		sendButton.setBounds(6, 731, 130, 29);
		rightSidePanel.add(sendButton);
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
			}
		});
		
		JPanel playerActionsPanel = new JPanel();
		playerActionsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		playerActionsPanel.setBounds(760, 601, 295, 171);
		panel.add(playerActionsPanel);
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
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(6, 727, 735, 45);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Total balance:");
		lblNewLabel.setBounds(6, 6, 99, 16);
		panel_1.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setText("$200");
		textField.setBounds(99, 1, 130, 26);
		panel_1.add(textField);
		textField.setColumns(10);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
	}
	
	private void GenerateHand(Player player, JPanel panel){
		ArrayList<GroupCard> groups = player.GroupCards();
		ArrayList<SpecialCard> specials = player.SpecialCards();
		
		int xOffset = 4;
		
		for(Card card: groups){
			CardGUI cardGUI = new CardGUI(card, new Rectangle(xOffset, 10, 150, 90));
			panel.add(cardGUI);
			
			xOffset += 154;
		}
		for(Card card: specials){
			CardGUI cardGUI = new CardGUI(card, new Rectangle(xOffset, 10, 150, 90));
			panel.add(cardGUI);
			
			xOffset += 154;
		}
	}
}
