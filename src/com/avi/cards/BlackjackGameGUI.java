package com.avi.cards;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class BlackjackGameGUI extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2050102704836321932L;
	private Hand dealer;
	private Hand player;
	private Deck deck;
	//private int roundNumber;
	
	private boolean playerBusted;
	private boolean dealerBusted;
	
	//Shared UI components
	private JLabel dealerLabel;
	private JLabel playerLabel;
	private JPanel buttonPanel;
	
	public BlackjackGameGUI() {
		initialize();
	}
	
	private void initialize() {
		deck = new Deck();
		deck.shuffle(5);
		dealer = new Hand();
		player = new Hand();
		initUI();
	}
	
	private void initUI() {
		JButton playButton = new JButton("Play Game");

        playButton.addActionListener((ActionEvent event) -> {
        	startGame();
        });
        
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(playButton);
        playerLabel = new JLabel("player", JLabel.CENTER);
        dealerLabel = new JLabel("dealer", JLabel.CENTER);
        
        setLayout(new GridLayout(3, 1));
     
        playerLabel.setSize(350,100);
        dealerLabel.setSize(350,100);
        
        //createLayout(playButton);
        add(dealerLabel);
        add(playerLabel);
        add(buttonPanel);
        
        setTitle("Blackjack Game");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

	private boolean startGame() {
		buttonPanel.removeAll();
		JButton standButton = new JButton("Stand");

		standButton.addActionListener((ActionEvent event) -> {
            System.out.println("Stand");
            playerEndTurn();
        });
		JButton hitButton = new JButton("Hit me");

		hitButton.addActionListener((ActionEvent event) -> {
            System.out.println("Hit me!");
            playerBusted = playerHit();
            makeChanges();
        });
		
		buttonPanel.add(standButton);
		buttonPanel.add(hitButton);
		//playerLabel.setText("something happened");
		buttonPanel.revalidate();
		playerBusted = false;
		dealerBusted = false;
		System.out.println("Let's play!");
		
		//setup - deal 2 face-up to player, 1 face up and 1 face down to dealer
		
		dealToPlayer();
		dealToDealer(false);
		dealToPlayer();
		dealToDealer(true);
		
		return false;
	}
	
	private void makeChanges() {
		if (playerBusted) {
			disableButtons();
			playerBusted();
		}
		
	}
	
	private void disableButtons() {
		for (Component c : buttonPanel.getComponents()){
			JButton button = (JButton) c;
			button.setEnabled(false);
		}
	}
	
	private void enableButtons() {
		for (Component c : buttonPanel.getComponents()){
			JButton button = (JButton) c;
			button.setEnabled(true);
		}
	}

	private void playerBusted() {
		playerEndTurn();
	}

	private boolean playerHit() {
		dealToPlayer();
		return player.getCardSum() > 21;
	}
	
	private void playerEndTurn() {
		disableButtons();
		String result = "Game over.";
		if (!playerBusted) {
			dealerBusted = dealerTurn();
		}
		if (playerBusted) {
			result = "Player Busted! YOU LOSE!";
		} else if (dealerBusted) {
			result = "Dealer Busted! YOU WIN!";
		} else if (dealer.getCardSum() == 21) {
			result = "Dealer Blackjack!!! YOU LOSE!";
		} else if (dealer.getCardSum() >= player.getCardSum()) {
			result = "Player not higher than Dealer. YOU LOSE!";
		} else {
			result = "YOU WIN!";
		}	
		System.out.println(result);
		int playAgain = JOptionPane.showConfirmDialog(this, result +" Play again?", "Play Again?", JOptionPane.YES_NO_OPTION);
		System.out.println(playAgain);
		if (playAgain == JOptionPane.OK_OPTION) {
			resetGame();
		} else {
			System.exit(0);
		}
		
	}
	
	private void resetGame() {
		deck = new Deck();
		deck.shuffle(5);
		dealer = new Hand();
		player = new Hand();
		playerBusted = false;
		dealerBusted = false;
		
		dealerLabel.setText("Dealer");
		playerLabel.setText("Player");
		
		enableButtons();
		//this.revalidate();
		startGame();
		
	}

	private boolean dealerTurn() {
		System.out.println("Dealer flips card:");
		dealer.unhideCards();
		dealerLabel.setText(dealer.toString());
		System.out.println(dealer);
		while (dealer.getCardSum() < 17) {
			dealToDealer(false);
		}
		return dealer.getCardSum() > 21;
	}
	
	private void dealToDealer(boolean hidden) {
		System.out.println("Dealer gets a card:");
		dealTo(dealer, hidden, dealerLabel);
	}
	
	private void dealToPlayer() {
		System.out.println("Player gets a card:");
		dealTo(player, false, playerLabel);
	}
	
	private void dealTo(Hand hand, boolean hidden, JLabel label) {
		Card newCard = deck.deal();
		newCard.setHidden(hidden);
		hand.add(newCard);
		System.out.println(hand);
		label.setText(hand.toString());
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			BlackjackGameGUI game = new BlackjackGameGUI();
            game.setVisible(true);

        });
	}

}
