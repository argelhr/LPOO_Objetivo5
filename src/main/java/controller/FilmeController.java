package controller;

import dao.FilmeDAO;
import model.Filme;

import java.util.List;
import java.util.Scanner;

public class FilmeController {
    private static final Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("Escolha a opção para gerenciar os filmes");
            System.out.println("""
                    1. Cadastrar Filme
                    2. Alterar Filme
                    3. Listar Filmes
                    4. Desativar filmes
                    5. Reativar filmes
                    0. Voltar ao menu anterior...""");
            opcao = teclado.nextInt();
            teclado.nextLine();
            switch (opcao) {
                case 1 -> FilmeController.cadastrarFilme();
                case 2 -> FilmeController.alterarFilme();
                case 3 -> FilmeController.listarFilmes();
                case 4 -> FilmeController.desativarFilme();
                case 5 -> FilmeController.reativarFilme();
                case 0 -> CinemaController.main(null);
                default -> System.out.println("Opção incorreta tente novamente!");
            }

        } while (opcao != 0);

    }

    public static void cadastrarFilme() {
        int opcao;
        do {
            Filme filme = new Filme();
            do {
                System.out.println("Informe o nome do filme");
                filme.setTitulo(teclado.nextLine());

                System.out.println("Informe a duração do filme:");
                filme.setDuracao(teclado.nextInt());
                teclado.nextLine();

                do {
                    System.out.println("Titulo:" + filme.getTitulo());
                    System.out.println("Duração: " + filme.tempo());
                    System.out.println("As informações estão corretas para o cadastro?(1.Sim 2.Não)");
                    opcao = teclado.nextInt();
                    teclado.nextLine();

                    if (opcao < 1 || opcao > 2)
                        System.out.println("Valor incorreto, tente novamente");
                }
                while (opcao < 1 || opcao > 2);
            }
            while (opcao != 1);

            if (FilmeDAO.insertFilme(filme)) System.out.println("Filme cadastrado");
            else System.out.println("Ocorreu algum problema...");

            do {
                System.out.println("Deseja cadastrar mais algum filme?(1.Sim 2.Não)");
                opcao = teclado.nextInt();
                teclado.nextLine();
                if (opcao < 1 || opcao > 2)
                    System.out.println("Valor informado incorreto, tente novamente");

            } while (opcao < 1 || opcao > 2);
        } while (opcao < 1 || opcao > 2);
        FilmeController.main(null);


    }

    public static void listarFilmes() {
        List<Filme> filmes = FilmeDAO.buscarFilmes();

        if (filmes.size() != 0)
            filmes.forEach(filme -> {
                System.out.println("Descrição: " + filme.getTitulo());
                System.out.println("Código: " + filme.getCodfilme() + " Duração: " + filme.tempo());
                System.out.println("-------------------------------");
            });
        else
            System.out.println("Nenhum filme ativo no momento");

    }

    public static void listarCadastroFilmes() {
        //metodo criado para retornar ao menu do filme
        listarFilmes();
        FilmeController.main(null);
    }

    public static void alterarFilme() {

        long opcao;
        do {
            listarFilmes();
            System.out.println("Digite o código do filme a ser alterado?(Zero para retornar ao menu anterior");
            opcao = teclado.nextLong();
            teclado.nextLine();
            if (opcao != 0) {
                Filme filme = FilmeDAO.selectFilmeByID(opcao);

                if (filme == null) {
                    System.out.println("Código informado não foi encontrado");
                } else {
                    do {
                        System.out.println("Titulo: " + filme.getTitulo());
                        System.out.println("Precisa alterar o título? (1.Sim 2.Não)");
                        opcao = teclado.nextInt();
                        teclado.nextLine();
                        if (opcao < 1 || opcao > 2)
                            System.out.println("Valor incorreto, tente novamente");
                    } while (opcao < 1 || opcao > 2);

                    if (opcao == 1) {
                        System.out.println("Informe o nome atualizado");
                        filme.setTitulo(teclado.nextLine());
                    }
                    do {
                        System.out.println("Duração: ");
                        System.out.println("Deseja atualizar a duração do filme?(1.Sim 2.Não)");
                        opcao = teclado.nextInt();
                        teclado.nextLine();

                        if (opcao < 1 || opcao > 2)
                            System.out.println("Valor incorreto, tente novamente");
                    } while (opcao < 1 || opcao > 2);

                    if (opcao == 1) {
                        System.out.println("Informe a nova duração do filme em total de minutos");
                        filme.setDuracao(teclado.nextInt());
                    }

                    if (FilmeDAO.alteraFilme(filme)) {
                        System.out.println("Filme atualizado");
                    } else {
                        System.out.println("Ocorreu algum problema ao atualizar");
                    }

                    System.out.println("Deseja atualizar algum filme mais?(1.Sim 2.Não)");
                    if (opcao < 1 || opcao > 2)
                        System.out.println("Valor incorreto, tente novamente");
                }

            }
        } while (opcao != 0);
        FilmeController.main(null);

    }

    public static void desativarFilme() {

        long opcao;
        listarFilmes();
        do {
            System.out.println("Qual o código do filme para excluir?(Zero para retornar ao menu anterior...");
            opcao = teclado.nextLong();

            if (opcao != 0) {
                Filme filme = FilmeDAO.selectFilmeByID(opcao);
                do {
                    System.out.println("Tem certeza que deseja desativar este filme?(1.OK 2.Cancelar");
                    opcao = teclado.nextLong();
                } while (opcao < 1 || opcao > 2);
                if (opcao == 1) {
                    if (FilmeDAO.softdelete(filme.getCodfilme())) {
                        System.out.println("Desativação do filme: " + filme.getTitulo() + ", concluida");
                    } else System.out.println("Aconteceu algum problema");
                }
                do {
                    System.out.println("Deseja realizar outra operação de delete?");
                    opcao = teclado.nextLong();
                    if (opcao < 1 || opcao > 2)
                        System.out.println("Valor incorreto, tente novamente");
                } while (opcao < 1 || opcao > 2);

            }
        }
        while (opcao != 0);
        FilmeController.main(null);


    }

    public static void reativarFilme() {
        long opcao;
        do {
            List<Filme> filmes = FilmeDAO.buscarFilmesDesativados();

            if (filmes.size() == 0) {
                System.out.println("Não existem filmes desativados no momento");
                FilmeController.main(null);
            }

            filmes.forEach(filme -> {
                System.out.println("Cod: " + filme.getCodfilme() + "- " + filme.getTitulo());
            });

            opcao = teclado.nextLong();
            teclado.nextLine();

            System.out.println("Qual o código do filme para reativar?(Zero para retornar ao menu anterior...");

            if (opcao != 0) {
                Filme filme = FilmeDAO.selectFilmeByID(opcao);
                do {
                    System.out.println("Tem certeza que deseja reativar este filme?(1.OK 2.Cancelar");
                    opcao = teclado.nextLong();
                } while (opcao < 1 || opcao > 2);
                if (opcao == 1) {
                    if (FilmeDAO.updateFilmeSituacao(filme.getCodfilme()))
                        System.out.println("Reativação do filme: " + filme.getTitulo() + ", concluida");
                } else System.out.println("Aconteceu algum problema");
            }
        } while (opcao != 0);

        FilmeController.main(null);

    }

}
