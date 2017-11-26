package com.avi.cards;

import java.util.Random;

public class Deck {
	private final int NUM_OF_CARDS = 52;
	private Card[] cards;
	private int cardCounter;
	
	public Deck() {
		initialize();
	}
	
	private void initialize() {
		cardCounter = 0;
		cards = new Card[NUM_OF_CARDS];
		int count = 0;
		while(count < NUM_OF_CARDS) {
			for (Face face : Face.values()){
				for (Suit suit : Suit.values()){
					cards[count++] = new Card(face, suit);
				}
			}
		}
	}
	
	public Card[] getCards() {
		return cards;
	}
	
	public Card getCard(int index) {
		return cards[index];
	}
	
	public void shuffle() {
		Random rand = new Random();
		for (int cardNum = 0; cardNum < NUM_OF_CARDS; cardNum++) {
			int randomCard = rand.nextInt(NUM_OF_CARDS);
			swapCards(cardNum, randomCard);
		}
	}
	
	public void shuffle(int timesToShuffle) {
		while (timesToShuffle-- > 0) {
//			System.out.println("SHUFFLING!");
			shuffle();
		}
	}
	private void swapCards(int cardNum, int randomCard) {
		Card temp = cards[randomCard];
		cards[randomCard] = cards[cardNum];
		cards[cardNum] = temp;
	}

	public int getCardCounter() {
		return cardCounter;
	}
	
	public Card deal() {
		return cards[cardCounter++];
	}
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (Card card: getCards()) {
			str.append(card.toString());
		}
		return str.toString();
	}
	
	public static void main(String[] args) {
		Deck deck = new Deck();
		deck.shuffle(3);
		//System.out.println(deck);
		for (int cardNum = 0; cardNum < deck.getCards().length; cardNum++) {
			
			if ( cardNum > 0 && cardNum % 4 == 0) {
				System.out.println();
			}
			System.out.printf("%30s %4s", deck.getCard(cardNum), deck.getCard(cardNum).getFaceValue());
		}
	}
}
