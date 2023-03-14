/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.brc.blackjackgame;

import java.math.BigDecimal;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Player {
    //Maior seguranca
    private String name;
    private BigDecimal balance;
    private Hand hand;
    private Scanner scanner = new Scanner(System.in);
    
    public Player(String name, double money, Hand hand) {
        this.name = name;
        this.balance = BigDecimal.valueOf(money);
        this.hand = hand;
    }
    
    public void setBalance(double balance) {
        this.balance = BigDecimal.valueOf(balance);
    }
    
    public double getBalance() {
        return this.balance.doubleValue();
    }

    //apostar
    public void bet(double betAmount) {
        if (betAmount > balance.doubleValue()) {
            System.out.println("You don't have that money!");
            return;
        }
        BigDecimal bet = BigDecimal.valueOf(betAmount);
        this.balance = balance.subtract(bet);
    }
    
    public void receivePayment(double betAmount) {
        BigDecimal betAmountBD = BigDecimal.valueOf(betAmount);
        balance = balance.add(betAmountBD);
    }
    
    public void hit(Card card) {
        while (hand.getTotalSum() < 21) {
            hand.addCard(card);
        }
    }
    
    public void play(Deck deck) {
        hand.addCard(deck.dealCard());
    }
    
    public String showHand() {
        return this.hand.toString();
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean playAgain() {
    Object[] options = {"Yes", "No"};
    int result = JOptionPane.showOptionDialog(null,
        String.format("%s, want to play again?", this.getName()),
        "Play again?",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[0]);

    return result == JOptionPane.YES_OPTION;
}
    
    @Override
    public String toString() {
        return String.format("Bank: %.2f", this.getBalance());
    }
}
