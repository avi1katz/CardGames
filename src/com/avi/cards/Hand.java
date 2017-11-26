package com.avi.cards;

import java.util.ArrayList;

import com.avi.cards.Card;

public class Hand {
	private ArrayList<Card> cards;

	public Hand() {
		cards = new ArrayList<Card>();
	}
	
	public void add(Card card) {
//		int cardVal = card.getFaceValue();
//		if (cardSum == -1 || card.isHidden()) {
//			cardSum = -1;
//		} else {
//			cardSum += cardVal;
//		}
		cards.add(card);
	}
	
	public int getCardSum() {
		int cardSum = 0, maxCardSum = 0;
		for (Card card : cards) {
			if (card.isHidden()) {
				cardSum = -1;
				maxCardSum = -1;
				break;
			}
			cardSum += card.getFaceValue();
			maxCardSum += card.getFaceValue();
			if (card.getFace().equals(Face.ACE)){
				maxCardSum += 10;
			}
		}
		return (maxCardSum > 21? cardSum : maxCardSum);
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(cards.toString());
		int cardSum = getCardSum();
		return str.append(cardSum >= 0? cardSum : "???").toString();
	}

	public void unhideCards() {
		for (Card card : cards) {
			card.setHidden(false);
		}
	}
}
