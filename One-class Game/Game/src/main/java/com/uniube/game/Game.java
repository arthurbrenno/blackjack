/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.uniube.game;

import java.util.Scanner;
import java.security.SecureRandom;

public class Game {
    
    private static final Scanner scanner = new Scanner(System.in);
    private static final SecureRandom random = new SecureRandom();
    private static final int NUMERO_DE_CARTAS = 52;
    private static int pilha;
    private static int indexBaralho = 0;
    private static String[] maoJogador = new String[10];
    private static String[] maoDealer = new String[6];
    private static String[] valores = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Valete", "Dama", "Rei"};
    private static String[] naipes = {"Paus", "Ouros", "Copas", "Espadas"};
    private static String[] baralho = new String[NUMERO_DE_CARTAS];
    private static boolean jogoAcabou = false;
    private static boolean jogarDeNovo = true;
    private static int vitorias = 0;
    private static String nomeJogador;

    public static void main(String[] args) {

        mostrarBoasVindas();
        gerarBaralho();
        
        while (jogarDeNovo) {
            
            pilha = NUMERO_DE_CARTAS - 1;
            limparConsole();
            jogoAcabou = false;
            int valorMaoJogador;
            int valorMaoDealer;
            String pedirOuParar;
            embaralhar();

            distribuirCarta(maoJogador);
            distribuirCarta(maoDealer);
            distribuirCarta(maoJogador);
            distribuirCarta(maoDealer);

            valorMaoJogador = getValorMao(maoJogador);
            valorMaoDealer = getValorMao(maoDealer);

            System.out.printf("[Vitorias: %d]%n%n", vitorias);
            mostrarCartas(maoDealer, "Dealer", true);
            mostrarCartas(maoJogador, nomeJogador, false);
            jogoAcabou = valorMaoJogador == 21 || valorMaoDealer == 21;

            while (!jogoAcabou) {
                System.out.printf("%s, PEDIR(1) ou PARAR(0): ",nomeJogador);
                pedirOuParar = scanner.nextLine();
                switch (pedirOuParar) {
                    case "1":
                        distribuirCarta(maoJogador);
                        System.out.println();
                        mostrarCartas(maoDealer, "Dealer", false);
                        mostrarCartas(maoJogador, nomeJogador, false);
                        jogoAcabou = checarFimDeJogo(); 
                        break;
                    default:
                        jogarDealer();
                        System.out.println();
                        mostrarCartas(maoDealer, "Dealer", false);
                        mostrarCartas(maoJogador, nomeJogador, false);
                        jogoAcabou = checarFimDeJogo()  || getValorMao(maoDealer) > getValorMao(maoJogador) || getValorMao(maoDealer) == 17 || getValorMao(maoDealer) < getValorMao(maoJogador); 
                }
            }
            
            mostrarStatus();
            limparMao(maoJogador);
            limparMao(maoDealer);
            System.out.printf("%n%s, vamos jogar de novo? (1/0): ", nomeJogador);
            jogarDeNovo = scanner.nextLine().equals("1");
        }
        scanner.close();
    }
    
    private static void mostrarBoasVindas() {
        System.out.printf("C A S S I N O%nU N I U B E%n%n");
        System.out.print("Bem vindo, jogador! Qual o seu nome? >> ");
        nomeJogador = scanner.nextLine();
        System.out.printf("Certo, %s. Pressione Enter quando estiver pronto. >> ", nomeJogador);
        scanner.nextLine();
        System.out.print("\n\n");
    }

    private static void gerarBaralho() {
        for (String valor : valores) {
            for (String naipe : naipes) {
                baralho[indexBaralho++] = String.format("%s de %s", valor, naipe);
            }
        }
    }

    private static void distribuirCarta(String[] mao) {
        //se tiver vazia, adiciona a ultima carta da pilha e decrementa ela
        for (int i = 0; i < mao.length; i++) {
            if (mao[i] == null) {
                mao[i] = baralho[pilha--];
                return;
            }
        }
    }
    
    private static void mostrarCartas(String[] mao, String nome, boolean flag) {
        if (flag) {
            System.out.printf("%s: [ %s [?] ] (%d)%n", nome, mao[0], getValorCarta(mao[0]));
            return;
        }
        System.out.printf("%s: [ ", nome);
        for (String carta : mao) {
            if (carta != null) {
                System.out.printf("%s ", carta);
            }
        }
        System.out.printf("] (%d) %n", getValorMao(mao));
    }
    
    private static void embaralhar() {
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
    
    private static void jogarDealer() {
        while(getValorMao(maoDealer) < 17) {
            distribuirCarta(maoDealer);
        }
    }
    
    private static int getValorCarta(String carta) {
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
    
    private static int getValorMao(String[] mao) {
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
    
    private static boolean ganhou(String[] mao) {
        return getValorMao(mao) == 21;
    }
    
    private static boolean estourou(String[] mao) {
        return getValorMao(mao) > 21;
    }
    
    private static void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    private static void limparMao(String[] mao) {
        for (int i = 0; i < mao.length - 1; i++) {
            if (mao[i] != null) {
                mao[i] = null;
            }
        }
    }
    
    private static boolean checarFimDeJogo() {
        return estourou(maoJogador) || estourou(maoDealer) || ganhou(maoJogador) || ganhou(maoDealer) || getValorMao(maoDealer) == getValorMao(maoJogador);
    }
    
    private static void mostrarStatus() {
        if ((ganhou(maoJogador) || getValorMao(maoJogador) > getValorMao(maoDealer)) && !estourou(maoJogador)) {
            System.out.printf("%s, você ganhou!%n", nomeJogador);
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
        else if (getValorMao(maoJogador) == getValorMao(maoDealer)){
            System.out.println("Empate!");
        }
        else {
            System.out.println("Você Perdeu!");
        }
    }

}
