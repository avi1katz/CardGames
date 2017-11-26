package com.avi.cards;

public class Card {
	private Face face;
	private Suit suit;
	private boolean hidden;
	
	public Card(Face face, Suit suit){
		this.face = face;
		this.suit = suit;
		this.hidden = false;
	}

	public Face getFace() {
		return face;
	}

	public void setFace(Face face) {
		this.face = face;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}
	
	public int getFaceValue() {
		return this.face.getValue();
	}
	
	public boolean isHidden() {
		return hidden;
	}
	
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	public String toString() {
		return !hidden? ("Card[" + face + " of " + suit + "]") : "HIDDEN";
	}
}
