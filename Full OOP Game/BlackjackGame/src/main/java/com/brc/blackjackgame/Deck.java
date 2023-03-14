/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.brc.blackjackgame;

import java.util.ArrayList;
import java.security.SecureRandom;

public class Deck {
    
    private ArrayList<Card> deck = new ArrayList<>();
    private int index;
    SecureRandom sRandom = new SecureRandom();
    
    public Deck() {
        index = 0;
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        
        for (String value : values) {
            for (String suit : suits) {
                deck.add(new Card(value, suit));
            }
        }
        
    }
    
    public void shuffle() {
        //SecureRandom sRandom = new SecureRandom();
        int lastIndex = deck.size() - 1;
        while (lastIndex > 0) {
            int randomIndex = sRandom.nextInt(lastIndex);
            Card temp = deck.get(lastIndex);
            deck.set(lastIndex, deck.get(randomIndex));
            deck.set(randomIndex, temp);
            lastIndex--;
        }
    }
    
    public Card dealCard() {
        if (index > 51) {
            throw new RuntimeException("Deck is empty!");
        }
        return deck.get(index++);
    }
    
    @Override
    public String toString() {
        return deck.toString();
    }
    
}
