package pokemon;

import java.util.ArrayList;
import java.util.List;

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
    protected List<Ataque> golpes = new ArrayList<>();

    public Pokemon(String nome, Tipo tipo, int vida, int ataque) {
        this.nome = nome;
        this.tipo = tipo;
        this.vidaMaxima = vida;
        this.vidaAtual = vida;
        this.ataque = ataque;
    }

    /** Usado pelas subclasses para cadastrar os golpes padrao de cada tipo. */
    protected void adicionarGolpe(Ataque golpe) {
        golpes.add(golpe);
    }

    /** Lista de golpes disponiveis para este Pokemon usar em batalha. */
    public List<Ataque> getGolpes() {
        return golpes;
    }

    /**
     * Metodo polimorfico: o dano depende do tipo de quem ataca
     * e de quem e atacado, sem que este codigo mude ao criar
     * novos tipos de Pokemon.
     * Ataque generico (poder 100), mantido para compatibilidade
     * com quem so quer um golpe padrao (ex.: os testes).
     */
    public void atacar(Pokemon alvo) {
        double multiplicador = getMultiplicadorContra(alvo);
        int dano = calcularDano(multiplicador);
        alvo.receberDano(dano);
        System.out.println(nome + " (" + tipo + ") ataca " + alvo.getNome()
                + " (" + alvo.getTipo() + ")! Dano: " + dano + efetividade(multiplicador));
    }

    /**
     * Ataca o alvo usando um golpe especifico da lista de golpes do Pokemon.
     * O dano leva em conta o poder do golpe alem da vantagem de tipo.
     */
    public void atacar(Pokemon alvo, Ataque golpe) {
        double multiplicador = getMultiplicadorContra(alvo);
        int dano = golpe.calcularDano(ataque, multiplicador);
        alvo.receberDano(dano);
        System.out.println(nome + " (" + tipo + ") usa " + golpe.getNome()
                + " contra " + alvo.getNome() + " (" + alvo.getTipo() + ")! Dano: "
                + dano + efetividade(multiplicador));
    }

    /** Dano base = ataque * multiplicador de tipo (golpe generico, poder 100). */
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
