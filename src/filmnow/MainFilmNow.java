package filmnow;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * interface com menus texto para manipular o sistema filmnow
 *
 * @author eliane
 *
 */
public class MainFilmNow {

    private static final int QUANTIDADE_FILMES = 100;
    private static final int TAMANHO_HOTLIST = 10;

    public static void main(String[] args) {
        FilmNow fn = new FilmNow();

        System.out.println("Carregando filmes ...");
        try {
            /*
             * essa e a maneira de lidar com possiveis erros por falta do arquivo
             */
            carregaFilmes("filmes_inicial.csv", fn);
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro lendo arquivo: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String opcao = menu(scanner);
            comando(opcao, fn, scanner);
        }
    }

    /**
     * exibe o menu e captura a escolha do usuario
     *
     * @param scanner para captura da opcao do usuario
     * @return o comando escolhido
     */
    private static String menu(Scanner scanner) {
        System.out.print(
                "\n" +
                        "(A)Adicionar filme\n" +
                        "(M)Mostrar todos\n" +
                        "(D)Detalhar filme\n" +
                        "(E)Exibir HotList\n" +
                        "(H)Atribuir Hot\n" +
                        "(R)Remover Hot\n" +
                        "(S)Sair\n" +
                        "\n" +
                        "Opção> ");
        return scanner.nextLine().trim().toUpperCase();
    }

    /**
     * interpreta a opcao escolhida por quem esta usando o sistema
     *
     * @param opcao   opcao digitada
     * @param fn      o sistema filmnow que estamos manipulando
     * @param scanner objeto scanner para o caso do comando precisar de mais input
     */
    static void comando(String opcao, FilmNow fn, Scanner scanner) {
        switch (opcao) {
            case "A":
                adicionaFilme(fn, scanner);
                break;
            case "M":
                mostrarFilmes(fn);
                break;
            case "D":
                detalharFilme(fn, scanner);
                break;
            case "E":
                exibirHotList(fn);
                break;
            case "H":
                atribuirHot(fn, scanner);
                break;
            case "R":
                removerHot(fn, scanner);
                break;
            case "S":
                sai();
                break;
            default:
                System.out.println("OPÇÃO INVÁLIDA!");
        }
    }

    /**
     * imprime lista de filmes
     *
     * @param fn o sistema filmnow a ser manipulado
     */
    private static void mostrarFilmes(FilmNow fn) {
        System.out.println();
        Filme[] filmes = fn.getFilmes();
        for (int i = 0; i < filmes.length; i++) {
            if (filmes[i] != null) {
                System.out.println(formataFilme(i + 1, filmes[i]));
            }
        }
    }

    /**
     * imprime os detalhes de um dos filmes
     *
     * @param fn      o sistema filmnow a ser manipulado
     * @param scanner scanner para capturar qual filme
     */
    private static void detalharFilme(FilmNow fn, Scanner scanner) {
        System.out.print("\nFilme> ");
        int posicao = leInteiro(scanner.nextLine());
        if (posicao < 1 || posicao > QUANTIDADE_FILMES) {
            System.out.println("POSIÇÃO INVÁLIDA!");
            return;
        }
        Filme filme = fn.getFilme(posicao);
        if (filme == null) {
            return;
        }

        String prefixo = fn.contemHot(filme) ? "🔥 " : "";
        System.out.println(prefixo + filme.toString());
        System.out.println(filme.getLocal());
    }

    /**
     * exibe a hotlist
     *
     * @param fn o sistema filmnow a ser manipulado
     */
    private static void exibirHotList(FilmNow fn) {
        System.out.println();
        System.out.println(fn.listaHots());
    }

    /**
     * cadastra um filme no sistema
     *
     * @param fn      o sistema filmnow a ser manipulado
     * @param scanner scanner para pedir informacoes do filme
     */
    private static void adicionaFilme(FilmNow fn, Scanner scanner) {
        System.out.print("\nPosição> ");
        int posicao = leInteiro(scanner.nextLine());
        if (posicao < 1 || posicao > QUANTIDADE_FILMES) {
            System.out.println("POSIÇÃO INVÁLIDA");
            return;
        }

        System.out.print("Nome> ");
        String nome = scanner.nextLine().trim();
        System.out.print("Ano> ");
        String ano = scanner.nextLine().trim();
        System.out.print("Local> ");
        String local = scanner.nextLine().trim();

        if (nome.isEmpty() || local.isEmpty()) {
            System.out.println("FILME INVALIDO");
            return;
        }

        if (!fn.cadastraFilme(posicao, nome, ano, local)) {
            System.out.println("FILME JÁ ADICIONADO");
            return;
        }

        System.out.println("FILME ADICIONADO");
    }

    /**
     * atribui o status hot a um filme
     *
     * @param fn      o sistema filmnow a ser manipulado
     * @param scanner scanner para pedir informacoes do filme
     */
    private static void atribuirHot(FilmNow fn, Scanner scanner) {
        System.out.print("\nFilme> ");
        int posicaoFilme = leInteiro(scanner.nextLine());
        if (posicaoFilme < 1 || posicaoFilme > QUANTIDADE_FILMES || fn.getFilme(posicaoFilme) == null) {
            System.out.println("POSIÇÃO INVÁLIDA!");
            return;
        }

        System.out.print("Posicao> ");
        int posicaoHot = leInteiro(scanner.nextLine());
        if (posicaoHot < 1 || posicaoHot > TAMANHO_HOTLIST) {
            System.out.println("POSIÇÃO INVÁLIDA!");
            return;
        }

        Filme filme = fn.getFilme(posicaoFilme);
        fn.adicionaHot(filme, posicaoHot);
        System.out.println("ADICIONADO À HOTLIST NA POSIÇÃO " + posicaoHot + "!");
    }

    /**
     * remove o status hot de um filme
     *
     * @param fn      o sistema filmnow a ser manipulado
     * @param scanner scanner para pedir a posicao na hotlist
     */
    private static void removerHot(FilmNow fn, Scanner scanner) {
        System.out.print("\nPosicao> ");
        int posicao = leInteiro(scanner.nextLine());
        if (posicao < 1 || posicao > TAMANHO_HOTLIST) {
            System.out.println("POSIÇÃO INVÁLIDA!");
            return;
        }
        fn.removeHot(posicao);
    }

    /**
     * formata um filme para impressao
     *
     * @param posicao a posicao do filme que e exibida
     * @param filme   o filme a ser impresso
     * @return a string formatada
     */
    private static String formataFilme(int posicao, Filme filme) {
        return posicao + " - " + filme.getNome();
    }

    /**
     * sai da aplicacao
     */
    private static void sai() {
        System.out.println("\nVlw flw o/");
        System.exit(0);
    }

    /**
     * converte uma entrada em inteiro retorna um valor invalido caso nao seja um numero valido
     *
     * @param texto entrada lida do usuario
     * @return o inteiro lido ou um valor invalido
     */
    private static int leInteiro(String texto) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * le carga de filmes de um arquivo csv
     *
     * @param arquivoFilmes o caminho para o arquivo
     * @param fn            o sistema filmnow a ser populado com os dados
     * @throws IOException caso o arquivo nao exista ou nao possa ser lido
     */
    private static void carregaFilmes(String arquivoFilmes, FilmNow fn) throws FileNotFoundException, IOException {
        LeitorFilmNow leitor = new LeitorFilmNow();

        int carregados = leitor.carregaContatos(arquivoFilmes, fn);
        System.out.println("Carregamos " + carregados + " registros.");
    }
}
