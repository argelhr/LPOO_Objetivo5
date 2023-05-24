package controller;

import dao.SalaDAO;
import model.Sala;

import java.util.List;
import java.util.Scanner;

public class SalaController {
    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("""
                    1. Cadastrar Sala
                    2. Listar Salas
                    3. Alterar Sala
                    4. Desativar Sala
                    0. Retornar ao menu anterior""");
            opcao = teclado.nextInt();
            teclado.nextLine();

        }
        while (opcao < 0 || opcao > 4);
        switch (opcao) {
            case 1 -> cadastrarSala();
            case 2 -> listarSalas();
            case 3 -> updateSala();
        }

    }

    private static void cadastrarSala() {
        Sala sala = new Sala();

        System.out.println("Informe o numero da Sala");
        sala.setNr_sala(teclado.nextInt());
        teclado.nextLine();

        System.out.println("Informe a capacidada da sala");
        sala.setCapacidade(teclado.nextInt());
        teclado.nextLine();

        sala.setStatus(true);

        if (SalaDAO.insertSALA(sala))
            System.out.println("Cadastro efetuado");
        else
            System.out.println("Cadastro não efetuado");

    }

    private static void listarSalas() {

        List<Sala> salas = SalaDAO.listarSalas();

        System.out.println("Lista de salas\n=========");
        salas.forEach(sala -> {
            System.out.print("Cod:" + sala.getCod_sala());
            System.out.print((" | Nº:" + sala.getNr_sala()));
            System.out.print(" | Capacidade: " + sala.getCapacidade());
            System.out.print(" | Status: " + (sala.getStatus() ? "Ativo" : "Desativado") + "\n");

        });
    }

    private static boolean updateSala() {

        listarSalas();
        System.out.println("qual a sala a ser atualizada");

        Sala sala = SalaDAO.selectSalabyID(teclado.nextInt());

        if (sala == null) {
            System.out.println("Sala não encontrada");
        } else {
            System.out.println("1. Nº:" + sala.getNr_sala());
            System.out.println("2. Capacidade: " + sala.getCapacidade());
            System.out.println("Qual o atributo a ser atualizado:");
            int opcao = teclado.nextInt();
            teclado.nextLine();

            switch (opcao) {
                case 1 -> {
                    int numero;
                    System.out.println("Qual o numero atual:");
                    numero = teclado.nextInt();
                    teclado.nextLine();
                    SalaDAO.updateSala(opcao ,numero);
                }

            }


        }

    return true;
    }
}
