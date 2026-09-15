package pokemon;

/**
 * Classe base de todas as criaturas.
 * Centraliza os atributos comuns e a logica de batalha.
 * O calculo de vantagem de tipo fica em getMultiplicadorContra(),
 * que cada subclasse sobrescreve - assim a Batalha nao precisa
 * conhecer os detalhes de cada tipo.
 */
public abstract class Pokemon {

    protected String nome;
    protected Tipo tipo;
    protected int vidaMaxima;
    protected int vidaAtual;
    protected int ataque;

    public Pokemon(String nome, Tipo tipo, int vida, int ataque) {
        this.nome = nome;
        this.tipo = tipo;
        this.vidaMaxima = vida;
        this.vidaAtual = vida;
        this.ataque = ataque;
    }

    /**
     * Metodo polimorfico: o dano depende do tipo de quem ataca
     * e de quem e atacado, sem que este codigo mude ao criar
     * novos tipos de Pokemon.
     */
    public void atacar(Pokemon alvo) {
        double multiplicador = getMultiplicadorContra(alvo);
        int dano = calcularDano(multiplicador);
        alvo.receberDano(dano);
        System.out.println(nome + " (" + tipo + ") ataca " + alvo.getNome()
                + " (" + alvo.getTipo() + ")! Dano: " + dano + efetividade(multiplicador));
    }

    /** Dano base = ataque * multiplicador de tipo. */
    protected int calcularDano(double multiplicador) {
        return (int) Math.round(ataque * multiplicador);
    }

    /**
     * Vantagem de tipo. A classe base nao tem vantagem (1.0);
     * cada subclasse sobrescreve este metodo.
     */
    protected double getMultiplicadorContra(Pokemon alvo) {
        return 1.0;
    }

    protected String efetividade(double multiplicador) {
        if (multiplicador > 1.0) {
            return " - super efetivo!";
        } else if (multiplicador < 1.0) {
            return " - pouco efetivo...";
        }
        return "";
    }

    /** A vida so muda por meio deste metodo (encapsulamento). */
    public void receberDano(int dano) {
        vidaAtual = Math.max(0, vidaAtual - dano);
    }

    public boolean estaVivo() {
        return vidaAtual > 0;
    }

    public void curar() {
        vidaAtual = vidaMaxima;
    }

    // Acesso controlado aos atributos (encapsulamento)
    public String getNome() {
        return nome;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public int getVidaAtual() {
        return vidaAtual;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getAtaque() {
        return ataque;
    }

    @Override
    public String toString() {
        return nome + " [" + tipo + "] Vida: " + vidaAtual + "/" + vidaMaxima + " Ataque: " + ataque;
    }
}
