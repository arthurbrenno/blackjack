/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.brc.blackjackgame;

public class Card {

    private String value;
    private String suit;
    
    public Card(String value, String suit) {
        this.value = value;
        this.suit = suit;
    }
    
    //pegar valor da carta
    public int getIntValue() {
        int cardValue;
        switch (value) {
            case "Queen", "Jack", "King" -> cardValue = 10;
            case "A" -> cardValue = 11;
            default -> cardValue = Integer.parseInt(value);
        }
        return cardValue;
    }
    
    public String getStringValue() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return String.format("%s of %s", this.value, this.suit);
    }

}
