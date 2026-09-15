package monstraria;

import java.util.ArrayList;
import java.util.Random;

/** O treinador possui uma equipe de até 6 monstros e captura selvagens. */
public class Treinador {

    public static final int TAMANHO_MAXIMO_EQUIPE = 6;
    private static final double CHANCE_MINIMA = 0.10;
    private static final double CHANCE_MAXIMA = 0.90;

    private final String nome;
    private final ArrayList<Monstro> equipe;

    public Treinador(String nome) {
        this.nome = nome;
        this.equipe = new ArrayList<>();
    }

    /** Adiciona um monstro à equipe, respeitando o limite de 6. */
    public boolean adicionarMonstro(Monstro monstro) {
        if (equipe.size() >= TAMANHO_MAXIMO_EQUIPE) {
            return false;
        }
        equipe.add(monstro);
        return true;
    }

    /** Tenta capturar uma criatura selvagem. Quanto MENOR a vida restante, MAIOR a chance. */
    public boolean tentarCapturar(Monstro selvagem) {
        return tentarCapturar(selvagem, new Random());
    }

    /** Sobrecarga com Random injetado, para permitir testes determinísticos. */
    public boolean tentarCapturar(Monstro selvagem, Random random) {
        if (equipe.size() >= TAMANHO_MAXIMO_EQUIPE) {
            return false;
        }
        double chance = calcularChanceCaptura(selvagem);
        double sorteio = random.nextDouble();
        if (sorteio < chance) {
            equipe.add(selvagem);
            return true;
        }
        return false;
    }

    /** Chance de captura = 1 - (vida restante / vida máxima), entre 10% e 90%. */
    public double calcularChanceCaptura(Monstro selvagem) {
        double fracaoVidaPerdida = 1.0 - (double) selvagem.getVidaAtual() / selvagem.getVidaMaxima();
        return Math.max(CHANCE_MINIMA, Math.min(CHANCE_MAXIMA, fracaoVidaPerdida));
    }

    public Monstro escolherMonstro(int indice) {
        if (indice < 0 || indice >= equipe.size()) {
            return null;
        }
        return equipe.get(indice);
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Monstro> getEquipe() {
        return equipe;
    }
}
