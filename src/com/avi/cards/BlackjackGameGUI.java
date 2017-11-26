package com.avi.cards;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Scanner;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class BlackjackGame extends JFrame{
	private Hand dealer;
	private Hand player;
	private Deck deck;
	private int roundNumber;
	
	private boolean playerBusted;
	private boolean dealerBusted;
	
	//Shared UI components
	private JLabel dealerLabel;
	private JLabel playerLabel;
	private JPanel buttonPanel;
	
	public BlackjackGame() {
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
		buttonPanel.remove(0);;
		JButton standButton = new JButton("Stand");

		standButton.addActionListener((ActionEvent event) -> {
            System.out.println("Stand");
            playerStand();
        });
		JButton hitButton = new JButton("Hit me");

		hitButton.addActionListener((ActionEvent event) -> {
            System.out.println("Hit me!");
            playerBusted = playerHit();
            if (playerBusted) playerBusted();
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
	
	private void playerBusted() {
		playerLabel.setText("Player Busted!");
		
	}

	private boolean playerHit() {
		dealToPlayer();
		return player.getCardSum() > 21;
	}
	
	private void playerStand() {
		if (!playerBusted) {
			dealerBusted = dealerTurn();
		}
	}
	
	private boolean playGame(Scanner in) {
		boolean playerBusted = false;
		boolean dealerBusted = false;
		System.out.println("Let's play!");
		
		//setup - deal 2 face-up to player, 1 face up and 1 face down to dealer
		
		dealToPlayer();
		dealToDealer(false);
		dealToPlayer();
		dealToDealer(true);
		
		//deal to player if wants
		playerBusted = playerLoop(in);
		
		//if player hasn't busted, go to dealer
		//first, flip dealer's card
		if (!playerBusted) {
			dealerBusted = dealerTurn();
		}
		
		if (playerBusted) {
			System.out.println("Player Busted! YOU LOSE!");
		} else if (dealerBusted) {
			System.out.println("Dealer Busted! YOU WIN!");
		} else if (dealer.getCardSum() == 21) {
			System.out.println("Dealer Blackjack!!! YOU LOSE!");
		} else if (dealer.getCardSum() >= player.getCardSum()) {
			System.out.println("Player not higher than Dealer. YOU LOSE!");
		} else {
			System.out.println("YOU WIN!");
		}
		
		return playAgain(in);
	}
	
	private boolean playerLoop() {
		boolean cont = true;
		while (cont && player.getCardSum() < 21) {
			System.out.println("Would you like to hit? (Y or N)");
			if (userInput.toLowerCase().charAt(0) == 'y' ) {
				System.out.println("HIT ME!");
				dealToPlayer();
			} else {
				cont = false;
			}
		}
		return player.getCardSum() > 21;
	}
	
	private boolean playAgain(Scanner in) {
		System.out.print("\n\nWould you like to play again? (Y or N)\n");
		String response = in.next();
		return (response.toLowerCase().charAt(0) == 'y');
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
	
	private boolean playerLoop(Scanner in) {
		boolean cont = true;
		while (cont && player.getCardSum() < 21) {
			System.out.println("Would you like to hit? (Y or N)");
			String userInput = in.next();
			if (userInput.toLowerCase().charAt(0) == 'y' ) {
				System.out.println("HIT ME!");
				dealToPlayer();
			} else {
				cont = false;
			}
		}
		return player.getCardSum() > 21;
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
			//Scanner in = new Scanner(System.in);
			BlackjackGame game = new BlackjackGame();
            game.setVisible(true);
//            boolean keepPlaying = true;
//    		while (keepPlaying) {
//    			keepPlaying = game.playGame(in);
//    			game = new BlackjackGame();
//    		}
    		//System.out.println("Game ended!");
    		//in.close();
        });
	}

}
