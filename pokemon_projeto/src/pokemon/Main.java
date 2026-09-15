package pokemon;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Ponto de entrada: menu interativo para testar captura, batalha,
 * equipe e Pokedex. Serve tambem para gerar os prints da entrega.
 */
public class Main {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Treinador treinador = new Treinador("Ash");
        Pokedex pokedex = new Pokedex();

        Pokemon inicial = new PokemonFogo("Charmander", 100, 14);
        treinador.adicionarPokemon(inicial);
        pokedex.registrar(inicial);

        int opcao;
        do {
            System.out.println();
            System.out.println("===== POKEMON: CAPTURA E BATALHA =====");
            System.out.println("Treinador: " + treinador.getNome());
            System.out.println("1 - Procurar criatura selvagem e batalhar");
            System.out.println("2 - Ver equipe");
            System.out.println("3 - Ver Pokedex");
            System.out.println("4 - Curar equipe");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = lerInteiro(scanner);

            switch (opcao) {
                case 1 -> procurarSelvagem(treinador, pokedex, scanner);
                case 2 -> listarEquipe(treinador);
                case 3 -> pokedex.listar();
                case 4 -> curarEquipe(treinador);
                case 0 -> System.out.println("Ate a proxima!");
                default -> System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    /**
     * Gera uma criatura selvagem e, se o jogador escolher um Pokemon,
     * entra na batalha interativa contra ela.
     */
    private static void procurarSelvagem(Treinador treinador, Pokedex pokedex, Scanner scanner) {
        Pokemon selvagem = gerarSelvagem();
        System.out.println("Uma criatura selvagem apareceu: " + selvagem);
        pokedex.registrar(selvagem);
        System.out.println("Registrada na Pokedex!");

        Pokemon meu = escolherPokemonBatalha(treinador, scanner);
        if (meu == null) {
            return;
        }
        if (!meu.estaVivo()) {
            System.out.println(meu.getNome() + " esta desmaiado e nao pode batalhar. Cure a equipe (opcao 4).");
            return;
        }

        batalhar(meu, selvagem, treinador, pokedex, scanner);
    }

    /**
     * Loop da batalha: so volta ao menu principal quando a luta termina
     * (desmaio de um dos dois lados, captura bem-sucedida ou fuga).
     * A cada turno o jogador escolhe Atacar / Capturar / Fugir; o menu
     * de batalha e mostrado de novo apos cada acao, sem reiniciar o
     * menu principal do jogo.
     */
    private static void batalhar(Pokemon meu, Pokemon selvagem, Treinador treinador,
                                  Pokedex pokedex, Scanner scanner) {
        System.out.println();
        System.out.println("===== BATALHA =====");
        System.out.println(meu.getNome() + " VS " + selvagem.getNome());

        boolean lutando = true;
        while (lutando && meu.estaVivo() && selvagem.estaVivo()) {
            System.out.println();
            System.out.println(meu.getNome() + " - HP: " + meu.getVidaAtual() + "/" + meu.getVidaMaxima());
            System.out.println(selvagem.getNome() + " - HP: " + selvagem.getVidaAtual() + "/" + selvagem.getVidaMaxima());
            System.out.println("1 - Atacar");
            System.out.println("2 - Tentar capturar");
            System.out.println("3 - Fugir");
            System.out.print("Escolha: ");
            int acao = lerInteiro(scanner);

            switch (acao) {
                case 1 -> {
                    Ataque golpe = escolherGolpe(meu, scanner);
                    if (golpe != null) {
                        meu.atacar(selvagem, golpe);
                        if (selvagem.estaVivo()) {
                            selvagem.atacar(meu, golpeAleatorio(selvagem));
                        }
                    }
                }
                case 2 -> {
                    boolean capturado = treinador.tentarCapturar(selvagem);
                    if (capturado) {
                        pokedex.registrar(selvagem);
                        System.out.println(selvagem.getNome() + " foi capturado e entrou para a equipe!");
                        lutando = false;
                    } else {
                        System.out.println(selvagem.getNome() + " escapou da Pokebola!");
                        selvagem.atacar(meu, golpeAleatorio(selvagem));
                    }
                }
                case 3 -> {
                    System.out.println("Voce fugiu da batalha.");
                    lutando = false;
                }
                default -> System.out.println("Opcao invalida.");
            }
        }

        if (!meu.estaVivo()) {
            System.out.println(meu.getNome() + " desmaiou! Voce perdeu a batalha.");
        } else if (!selvagem.estaVivo()) {
            System.out.println(selvagem.getNome() + " desmaiou!");
        }
        System.out.println("===== FIM DA BATALHA =====");
    }

    /** Mostra os golpes do Pokemon e devolve o escolhido (ou null se invalido). */
    private static Ataque escolherGolpe(Pokemon pokemon, Scanner scanner) {
        List<Ataque> golpes = pokemon.getGolpes();
        System.out.println("Golpes de " + pokemon.getNome() + ":");
        for (int i = 0; i < golpes.size(); i++) {
            System.out.println(" [" + i + "] " + golpes.get(i));
        }
        System.out.print("Escolha o golpe: ");
        int indice = lerInteiro(scanner);
        if (indice < 0 || indice >= golpes.size()) {
            System.out.println("Golpe invalido. Voce perdeu a vez!");
            return null;
        }
        return golpes.get(indice);
    }

    /** A criatura selvagem usa um golpe aleatorio da sua lista. */
    private static Ataque golpeAleatorio(Pokemon pokemon) {
        List<Ataque> golpes = pokemon.getGolpes();
        return golpes.get(RANDOM.nextInt(golpes.size()));
    }

    private static void listarEquipe(Treinador treinador) {
        System.out.println("===== EQUIPE (" + treinador.getEquipe().size() + "/6) =====");
        for (int i = 0; i < treinador.getEquipe().size(); i++) {
            System.out.println(" [" + i + "] " + treinador.getEquipe().get(i));
        }
    }

    private static void curarEquipe(Treinador treinador) {
        for (Pokemon p : treinador.getEquipe()) {
            p.curar();
        }
        System.out.println("Equipe totalmente curada!");
    }

    private static Pokemon escolherPokemonBatalha(Treinador treinador, Scanner scanner) {
        listarEquipe(treinador);
        System.out.print("Escolha o Pokemon para batalhar: ");
        int indice = lerInteiro(scanner);
        Pokemon escolhido = treinador.escolherPokemon(indice);
        if (escolhido == null) {
            System.out.println("Indice invalido.");
        }
        return escolhido;
    }

    private static Pokemon gerarSelvagem() {
        return switch (RANDOM.nextInt(3)) {
            case 0 -> new PokemonFogo("Vulpix", 90, 10);
            case 1 -> new PokemonAgua("Squirtle", 110, 9);
            default -> new PokemonPlanta("Oddish", 100, 8);
        };
    }

    private static int lerInteiro(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Digite um numero: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }
}
