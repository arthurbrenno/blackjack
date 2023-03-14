/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.uniube.game;

import java.util.Scanner;
import java.security.SecureRandom;
/**
 *
 * @author User
 */
public class Game {
    
    private static final Scanner scanner = new Scanner(System.in);
    private static final SecureRandom random = new SecureRandom();
    private static final int NUMERO_DE_CARTAS = 52;
    private static int pilha = NUMERO_DE_CARTAS - 1;
    private static int index = 0;
    private static boolean jogo = true;
    private static int vitorias = 0;
    
    
    
    
    public static void main(String[] args) {
        
        boolean jogoAcabou = false;
        String[] maoJogador = new String[10];
        String[] maoDealer = new String[6]; //dealer so pede no maximo 5;
        String[] valores = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Valete", "Dama", "Rei"};
        String[] naipes = {"Paus", "Ouros", "Copas", "Espadas"};
        String[] baralho = new String[NUMERO_DE_CARTAS];
        
        
        //Gerar baralho
        for (String valor : valores) {
            for (String naipe : naipes) {
                baralho[index++] = String.format("%s de %s", valor, naipe);
            }
        }
        
        System.out.printf("C A S S I N O%nU N I U B E%n%n");
        System.out.print("Bem vindo, jogador! Qual o seu nome? >> ");
        String nome = scanner.nextLine();
        System.out.printf("Certo, %s. Pressione Enter quando estiver pronto. >> ", nome);
        scanner.nextLine();
        System.out.print("\n\n");
        
        while (jogo) {
            jogoAcabou = false;
            int valorMaoJogador = 0;
            int valorMaoDealer = 0;
            String pedirOuParar;
            System.out.printf("[Vitorias: %d]%n%n",vitorias);
            embaralhar(baralho);
            distribuirDuasCartas(maoJogador, maoDealer, baralho);
            
            valorMaoJogador = getValorMao(maoJogador);
            valorMaoDealer = getValorMao(maoDealer);
            
            mostrarCartas(maoDealer, "Dealer", true);
            mostrarCartas(maoJogador, nome, false);
            if (valorMaoJogador == 21 || valorMaoDealer == 21) {
                jogoAcabou = true;
            }
            
            while (!jogoAcabou) {
                System.out.printf("%s, PEDIR(1) ou PARAR(0): ",nome);
                pedirOuParar = scanner.nextLine();
                if(pedirOuParar.equals("1")) {
                    distribuirCarta(maoJogador, baralho);
                }
                else {
                    jogarDealer(maoDealer, baralho);
                    mostrarCartas(maoDealer, "Dealer", false);
                    mostrarCartas(maoJogador, nome, false);
                    break;
                }
                System.out.println();
                mostrarCartas(maoDealer, "Dealer", false);
                mostrarCartas(maoJogador, nome, false);
                jogoAcabou = checarFimDeJogo(maoJogador, maoDealer);
            }
            
            mostrarStatus(maoJogador, maoDealer);
            limparMao(maoJogador);
            limparMao(maoDealer);
            System.out.printf("%n%s, vamos jogar de novo? (1/0)", nome);
            jogo = scanner.nextLine().equals("1");
        }
        
        
        
    }
    
    
    
    public static void distribuirCarta(String[] mao, String[] baralho) {
        //se tiver vazia, add a ultima carta da pilha e decrementa ela
        for (int i = 0; i < mao.length; i++) {
            if (mao[i] == null) {
                mao[i] = baralho[pilha--];
                return;
            }
        }
    }
    
    public static void distribuirDuasCartas(String[] maoJogador, String[] maoDealer, String[] baralho) {
        for (int i = 0; i < 2; i++) {
            distribuirCarta(maoJogador, baralho);
            distribuirCarta(maoDealer, baralho);
        }
    }
    
    
    public static void mostrarCartas(String[] mao, String nome, boolean flag) {
        if (!flag) {
            System.out.printf("%s: [ ", nome);
            for (String carta : mao) {
                if (carta != null) {
                    System.out.printf("%s ", carta);
                }
            }
            System.out.printf("] (%d) %n", getValorMao(mao));
            return;
        }
        System.out.printf("%s: [ %s [?] ] (%d)%n", nome, mao[0], getValorCarta(mao[0]));
    }
    
    
    public static void embaralhar(String[] baralho) {
        int ultimoIndex = baralho.length - 1;
        String temp;
        int randomIndex;
        
        while (ultimoIndex > 0) {
            randomIndex = random.nextInt(ultimoIndex);
            temp = baralho[ultimoIndex];
            baralho[ultimoIndex] = baralho[randomIndex];
            baralho[randomIndex] = temp;
            ultimoIndex--;
        }
    }
    
    public static void jogarDealer(String[] maoDealer, String[] baralho) {
        while(getValorMao(maoDealer) < 17) {
            distribuirCarta(maoDealer, baralho);
        }
    }
    
    
    public static int getValorCarta(String carta) {
        //charAt(0) vai dar problema se o numero for 10, vai retornar apenas 1.
        int valorCarta;
        char primeiroValor = carta.charAt(0);
        
        if (carta.startsWith("10")) {
            return 10;
        }
        
        switch (primeiroValor) {
            case 'D', 'V', 'R' -> valorCarta = 10;
            case 'A' -> valorCarta = 11;
            default -> valorCarta = Integer.parseInt(String.valueOf(primeiroValor));
        }
        return valorCarta;
    }
    
    
    public static int getValorMao(String[] mao) {
        int somaCartas = 0;
        int ases = 0;
        for (String carta : mao) {
            if (carta != null) {
                somaCartas += getValorCarta(carta);
                if (carta.charAt(0) == 'A') { ases++;}
            }
        }
        while (somaCartas > 21 && ases > 0) {
            somaCartas -= 10;
            ases--;
        }
        return somaCartas;
    }
    
    
    public static boolean ganhou(String[] mao) {
        return getValorMao(mao) == 21;
    }
    
    
    public static boolean estourou(String[] mao) {
        return getValorMao(mao) > 21;
    }
    
    public static void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public static void limparMao(String[] mao) {
        for (int i = 0; i < mao.length - 1; i++) {
            if (mao[i] != null) {
                mao[i] = null;
            }
        }
    }
    
    public static boolean checarFimDeJogo(String[] maoJogador, String[] maoDealer) {
        return estourou(maoJogador) || estourou(maoDealer) || ganhou(maoJogador) || ganhou(maoDealer);
    }
    
    public static void mostrarStatus(String[] maoJogador, String[] maoDealer) {
        if (ganhou(maoJogador)) {
            System.out.println("Jogador, você ganhou!");
            vitorias++;
        }
        else if (ganhou(maoDealer)) {
            System.out.println("O Dealer ganhou.");
        }
        else if (estourou(maoDealer)) {
            System.out.println("O dealer estourou!");
            vitorias++;
        }
        else if (estourou(maoJogador)) {
            System.out.println("Você estourou!");
        }
        else if (ganhou(maoDealer)){
            System.out.println("O Dealer ganhou!");
        }
        else if (getValorMao(maoJogador) > getValorMao(maoDealer)) {
            System.out.println("Você ganhou!");
            vitorias++;
        }
        else {
            System.out.println("Você Perdeu!");
        }
    }
}
