package pokemon;

import java.util.Random;
import java.util.Scanner;

/**
 * Ponto de entrada: menu interativo para testar captura, batalha,
 * equipe e Pokedex. Serve tambem para gerar os prints da entrega.
 */
public class Main {

    private static final Random RANDOM = new Random();
    private static Pokemon selvagemAtual = null;

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
            System.out.println("1 - Procurar criatura selvagem");
            System.out.println("2 - Tentar capturar a criatura selvagem");
            System.out.println("3 - Ver equipe");
            System.out.println("4 - Ver Pokedex");
            System.out.println("5 - Curar equipe");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = lerInteiro(scanner);

            switch (opcao) {
                case 1 -> procurarSelvagem(treinador, pokedex, scanner);
                case 2 -> capturar(treinador, pokedex);
                case 3 -> listarEquipe(treinador);
                case 4 -> pokedex.listar();
                case 5 -> curarEquipe(treinador);
                case 0 -> System.out.println("Ate a proxima!");
                default -> System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    private static void procurarSelvagem(Treinador treinador, Pokedex pokedex, Scanner scanner) {
        selvagemAtual = gerarSelvagem();
        System.out.println("Uma criatura selvagem apareceu: " + selvagemAtual);
        pokedex.registrar(selvagemAtual);
        System.out.println("Registrada na Pokedex!");

        Pokemon meu = escolherPokemonBatalha(treinador, scanner);
        if (meu == null) {
            return;
        }
        System.out.println("Batalhando para enfraquecer " + selvagemAtual.getNome()
                + " (capturar e mais facil com a vida baixa)...");
        // Apenas um turno: enfraquece a criatura sem derrota-la
        meu.atacar(selvagemAtual);
        if (selvagemAtual.estaVivo()) {
            selvagemAtual.atacar(meu);
        }
        System.out.println("Vida restante de " + selvagemAtual.getNome() + ": "
                + selvagemAtual.getVidaAtual() + "/" + selvagemAtual.getVidaMaxima());
    }

    private static void capturar(Treinador treinador, Pokedex pokedex) {
        if (selvagemAtual == null || !selvagemAtual.estaVivo()) {
            System.out.println("Nenhuma criatura selvagem disponivel. Procure uma primeiro (opcao 1).");
            return;
        }
        boolean capturado = treinador.tentarCapturar(selvagemAtual);
        if (capturado) {
            pokedex.registrar(selvagemAtual);
            System.out.println(selvagemAtual.getNome() + " foi capturado e entrou para a equipe!");
            selvagemAtual = null;
        } else {
            System.out.println(selvagemAtual.getNome() + " escapou da Pokebola!");
        }
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
