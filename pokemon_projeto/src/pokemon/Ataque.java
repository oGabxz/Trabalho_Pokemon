package pokemon;

/**
 * Representa um golpe/ataque que um Pokemon pode usar em batalha.
 * O poder e um percentual aplicado sobre o atributo "ataque" do Pokemon:
 * poder 100 = dano igual ao ataque base; poder 130 = 30% mais forte; etc.
 */
public class Ataque {

    private String nome;
    private int poder;

    public Ataque(String nome, int poder) {
        this.nome = nome;
        this.poder = poder;
    }

    public String getNome() {
        return nome;
    }

    public int getPoder() {
        return poder;
    }

    /** Dano = ataque base do Pokemon * (poder/100) * multiplicador de tipo. */
    public int calcularDano(int ataqueBase, double multiplicador) {
        return (int) Math.round(ataqueBase * (poder / 100.0) * multiplicador);
    }

    @Override
    public String toString() {
        return nome + " (poder " + poder + ")";
    }
}
