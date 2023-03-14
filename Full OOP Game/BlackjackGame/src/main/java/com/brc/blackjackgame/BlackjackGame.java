/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.brc.blackjackgame;

import javax.swing.JOptionPane;

public class BlackjackGame {
    
    public static void main(String[] args) {
        
        //preparativos
        Deck deck = new Deck();
        Hand dealerHand = new Hand();
        Hand playerHand = new Hand();
        String dealerMessage;
        
        
        
        String name = JOptionPane.showInputDialog(null, "\n\n\n\n ╔═══════════════════════╗\n\n  C A S S I N O - U N I U P E\n\n ╚═══════════════════════╝\n\n\n\n\n Your name?");
        int deposit = Integer.parseInt(JOptionPane.showInputDialog("How many $ to deposit?"));
        Player player = new Player(name, deposit, playerHand);
        Dealer dealer = new Dealer(dealerHand);
        
        //pode assumir dois tipos por referencia na main, Card ou Hand.
        Object dealerArg;
        
        //rodada
        do {
            int round = 1;
            clearConsole();
            deck.shuffle();
            double betAmount = Double.parseDouble(JOptionPane.showInputDialog("What's your bet?"));
            dealCard(2, deck, dealerHand, playerHand);
            //System.out.println(player);
            //JOptionPane.showMessageDialog(null,String.format("Dealer: [%s, ?]%n%s: %s (%d)%n%n%n", dealerHand.getCard(0), name, playerHand, playerHand.getTotalSum()));
            //System.out.printf("Dealer: [%s, ?]%n", dealerHand.getCard(0));          
            //System.out.printf("%s: %s (%d)%n%n%n", name, playerHand, playerHand.getTotalSum());
            
            while (!playerHand.isBust() && !dealerHand.isBust() && !playerHand.isWin()) {
                
                //System.out.print("HIT(H) or STAND(S)>> ");
                if (round++ == 1) {
                    dealerMessage = "Dealer: [%s, ?]%n%s: %s (%d)%n%n%nHIT or STAND?";
                    dealerArg = dealerHand.getCard(0);
                }
                else {
                    dealerMessage = "Dealer: %s%n%s: %s (%d)%n%n%nHIT or STAND?";
                    dealerArg = dealerHand;
                }
                
                Object[] options = {"HIT", "STAND"};
                int result = JOptionPane.showOptionDialog(null,
            String.format(dealerMessage, dealerArg, name, playerHand, playerHand.getTotalSum()),
            "HIT or STAND",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
             null,
                 options,
                 options[0]);
                 if (result == JOptionPane.YES_OPTION) {
                    player.play(deck);
                } else {
                    dealer.play(deck);
                    displayDashboard(player, playerHand, dealerHand);
                    break;
                }
                displayDashboard(player, playerHand, dealerHand);
                round++;
            }
            
            if (playerHand.isBust()) {
                JOptionPane.showMessageDialog(null, "You lost!");
                player.bet(betAmount);
            } 
            else if (dealerHand.isBust() || playerHand.getTotalSum() > dealerHand.getTotalSum()) {
                JOptionPane.showMessageDialog(null, "You won!");
                player.receivePayment(betAmount);
            } 
            else if (playerHand.getTotalSum() == dealerHand.getTotalSum()) {
                JOptionPane.showMessageDialog(null, "Push!");
            } 
            else {
                JOptionPane.showMessageDialog(null, "You lost!");
                player.bet(betAmount);
            }
            
            playerHand.clear();
            dealerHand.clear();

        } while (player.playAgain() && player.getBalance() > 0);
        
    }
    
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public static void displayDashboard(Player player, Hand playerHand, Hand dealerHand) {
        JOptionPane.showMessageDialog(null, String.format("%s%nDealer: %s (%d)%n%s: %s (%d)%n%n%n", player, dealerHand, dealerHand.getTotalSum(), player.getName(), playerHand, playerHand.getTotalSum()));
        //System.out.println(player);
        //System.out.printf("Dealer: %s (%d)%n", dealerHand, dealerHand.getTotalSum());
        //System.out.printf("%s: %s (%d)%n%n%n", player.getName(), playerHand, playerHand.getTotalSum());
    }
    
    //adicionar uma carta pro player
    public static void dealCard(Player player, Card card) {
        player.hit(card);
    }
    
    //adicionar uma carta pro dealer
    public static void dealCard(Dealer dealer, Card card) {
        dealer.hit(card);
    }
    
    public static void dealCard(int qtdCards, Deck deck, Hand dealerHand, Hand playerHand) {
        for (int i = 0; i < qtdCards; i++) {
            dealerHand.addCard(deck.dealCard());
            playerHand.addCard(deck.dealCard());
        }
    }
    
}
