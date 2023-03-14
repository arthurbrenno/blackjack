/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.brc.blackjackgame;
import java.util.List;
import java.util.ArrayList;
public class Hand {
    
    private List<Card> cards;
    
    public Hand() {
        cards = new ArrayList<>();
    }
    
    //exemplo de uso addCard(deck.dealCard());
    public void addCard(Card card) {
        cards.add(card);
    }
    
    //soma das cartas da mao
    public int getTotalSum() {
        int total = 0;
        int aces = 0;
        for (Card card : cards) {
            total += card.getIntValue();
            if (card.getStringValue().equals("A")) {
                aces++;
            }
        }
        //tratar o valor total caso seja > 21 e caso haja "as". Nesse caso o "as" vale 1 apenas.
        while (total > 21 && aces>0) {
            total-=10;
            aces--;
        }
        return total;
    }
    
    public Card getCard(int index) {
        return cards.get(index);
    }
    
    //estouro
    public boolean isBust() {
        return this.getTotalSum() > 21;
    }
    
    //vitoria
    public boolean isWin() {
        return this.getTotalSum() == 21;
    }
    
    public void clear() {
        cards.clear();
    }
    
    @Override
    public String toString() {
        return cards.toString();
    }
    
}
