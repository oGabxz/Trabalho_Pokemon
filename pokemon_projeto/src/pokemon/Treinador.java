package pokemon;

import java.util.ArrayList;
import java.util.Random;

/** O treinador possui uma equipe de ate 6 Pokemon e captura selvagens. */
public class Treinador {

    public static final int TAMANHO_MAXIMO_EQUIPE = 6;
    private static final double CHANCE_MINIMA = 0.10;
    private static final double CHANCE_MAXIMA = 0.90;

    private String nome;
    private ArrayList<Pokemon> equipe;

    public Treinador(String nome) {
        this.nome = nome;
        this.equipe = new ArrayList<>();
    }

    /** Adiciona um Pokemon a equipe, respeitando o limite de 6. */
    public boolean adicionarPokemon(Pokemon pokemon) {
        if (equipe.size() >= TAMANHO_MAXIMO_EQUIPE) {
            System.out.println("Equipe cheia! Nao foi possivel adicionar " + pokemon.getNome() + ".");
            return false;
        }
        equipe.add(pokemon);
        return true;
    }

    /**
     * Tenta capturar uma criatura selvagem.
     * Quanto MENOR a vida restante, MAIOR a chance de sucesso.
     */
    public boolean tentarCapturar(Pokemon selvagem) {
        return tentarCapturar(selvagem, new Random());
    }

    /** Sobrecarga com Random injetado para permitir testes deterministicos. */
    public boolean tentarCapturar(Pokemon selvagem, Random random) {
        if (equipe.size() >= TAMANHO_MAXIMO_EQUIPE) {
            System.out.println("Equipe cheia! Capture outra criatura depois.");
            return false;
        }
        double chance = calcularChanceCaptura(selvagem);
        double sorteio = random.nextDouble();
        System.out.printf("Chance de captura: %.0f%% ... sorteio: %.0f%% %s%n",
                chance * 100, sorteio * 100, sorteio < chance ? " - capturado!" : " - escapou!");
        if (sorteio < chance) {
            equipe.add(selvagem);
            return true;
        }
        return false;
    }

    /**
     * Chance de captura = 1 - (vida restante / vida maxima),
     * limitada entre 10% e 90%.
     */
    public double calcularChanceCaptura(Pokemon selvagem) {
        double fracaoVidaPerdida = 1.0 - (double) selvagem.getVidaAtual() / selvagem.getVidaMaxima();
        return Math.max(CHANCE_MINIMA, Math.min(CHANCE_MAXIMA, fracaoVidaPerdida));
    }

    /** Retorna o Pokemon da equipe pelo indice, ou null se invalido. */
    public Pokemon escolherPokemon(int indice) {
        if (indice < 0 || indice >= equipe.size()) {
            return null;
        }
        return equipe.get(indice);
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Pokemon> getEquipe() {
        return equipe;
    }
}
