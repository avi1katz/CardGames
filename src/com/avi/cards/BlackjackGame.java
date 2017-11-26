package com.avi.cards;

import java.util.Scanner;

public class BlackjackGame {
	private Hand dealer;
	private Hand player;
	private Deck deck;
	//private int roundNumber;
	
	
	public BlackjackGame() {
		initialize();
	}
	
	private void initialize() {
		deck = new Deck();
		deck.shuffle(5);
		dealer = new Hand();
		player = new Hand();
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
	
	private boolean playAgain(Scanner in) {
		System.out.print("\n\nWould you like to play again? (Y or N)\n");
		String response = in.next();
		return (response.toLowerCase().charAt(0) == 'y');
	}

	private boolean dealerTurn() {
		System.out.println("Dealer flips card:");
		dealer.unhideCards();
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
		dealTo(dealer, hidden);
	}
	
	private void dealToPlayer() {
		System.out.println("Player gets a card:");
		dealTo(player, false);
	}
	
	private void dealTo(Hand hand, boolean hidden) {
		Card newCard = deck.deal();
		newCard.setHidden(hidden);
		hand.add(newCard);
		System.out.println(hand);
	}
	
	public static void main(String[] args) {
		BlackjackGame game = new BlackjackGame();
		Scanner in = new Scanner(System.in);

        boolean keepPlaying = true;
		while (keepPlaying) {
			keepPlaying = game.playGame(in);
			game = new BlackjackGame();
		}
		System.out.println("Game ended!");
		in.close();

	}

}
