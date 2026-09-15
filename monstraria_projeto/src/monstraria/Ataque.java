package monstraria;

/**
 * Representa um golpe/ataque que um Monstro pode usar em batalha.
 * O poder é um percentual aplicado sobre o atributo "ataque" do Monstro:
 * poder 100 = dano igual ao ataque base; poder 130 = 30% mais forte; etc.
 */
public class Ataque {

    private final String nome;
    private final int poder;
    private final String descricao;

    public Ataque(String nome, int poder) {
        this(nome, poder, "");
    }

    public Ataque(String nome, int poder, String descricao) {
        this.nome = nome;
        this.poder = poder;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public int getPoder() {
        return poder;
    }

    public String getDescricao() {
        return descricao;
    }

    /** Dano = ataque base do Monstro * (poder/100) * multiplicador de tipo. */
    public int calcularDano(int ataqueBase, double multiplicador) {
        return (int) Math.round(ataqueBase * (poder / 100.0) * multiplicador);
    }

    @Override
    public String toString() {
        return nome + " (poder " + poder + ")";
    }
}
