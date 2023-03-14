/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.brc.blackjackgame;

public class Dealer {
   
    //toda alteracao la ou aqui vai afetar dealerHand()
    private Hand hand;
    
    public Dealer(Hand hand) {
        this.hand = hand;
    }
    
    public void hit(Card card) {
        hand.addCard(card);
    }
    
    //“dealer stands on 17”
    public void play(Deck deck) {
        while (hand.getTotalSum() < 17) {
            hand.addCard(deck.dealCard());
        }
    }
    

}
