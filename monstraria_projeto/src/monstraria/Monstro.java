package monstraria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe base de todas as criaturas do jogo.
 * Centraliza atributos comuns (vida, ataque, nível, XP, evolução) e a
 * lógica de batalha. O cálculo de vantagem de tipo e os dados de cada
 * linha evolutiva (nomes, golpes novos) ficam nas subclasses, que cada
 * uma sobrescreve — assim a Batalha e a interface não precisam conhecer
 * detalhes de cada espécie.
 */
public abstract class Monstro implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int MAX_GOLPES = 4;
    public static final int NIVEL_EVOLUCAO_1 = 5;   // estágio 1 -> 2
    public static final int NIVEL_EVOLUCAO_2 = 10;  // estágio 2 -> 3

    protected String nome;
    protected final Tipo tipo;
    protected int estagio;      // 1 (básico), 2 (intermediário) ou 3 (final)
    protected int nivel;
    protected int xpAtual;
    protected int vidaMaxima;
    protected int vidaAtual;
    protected int ataque;
    protected List<Ataque> golpes = new ArrayList<>();

    protected Monstro(Tipo tipo, int vida, int ataque) {
        this.tipo = tipo;
        this.estagio = 1;
        this.nivel = 1;
        this.xpAtual = 0;
        this.vidaMaxima = vida;
        this.vidaAtual = vida;
        this.ataque = ataque;
        this.nome = nomeEstagio(1);
        for (Ataque golpe : golpesIniciais()) {
            adicionarGolpe(golpe);
        }
    }

    // ---------- Pontos de extensão definidos por cada linha evolutiva ----------

    /** Nome da espécie neste estágio (1, 2 ou 3) da linha evolutiva. */
    protected abstract String nomeEstagio(int estagio);

    /** Golpes que a criatura já nasce sabendo, no estágio básico. */
    protected abstract Ataque[] golpesIniciais();

    /** Novo golpe aprendido ao evoluir para o estágio informado (2 ou 3). */
    protected abstract Ataque golpeDeEvolucao(int novoEstagio);

    /**
     * Vantagem de tipo (polimorfismo): o multiplicador de dano depende do
     * tipo de quem ataca e de quem é atacado, sem que a Batalha precise
     * conhecer a regra.
     */
    protected abstract double getMultiplicadorContra(Monstro alvo);

    // ---------- Golpes ----------

    protected void adicionarGolpe(Ataque golpe) {
        golpes.add(golpe);
        if (golpes.size() > MAX_GOLPES) {
            golpes.remove(0);
        }
    }

    public List<Ataque> getGolpes() {
        return golpes;
    }

    // ---------- Batalha ----------

    /** Ataca o alvo usando um golpe específico da lista de golpes do monstro. */
    public int atacar(Monstro alvo, Ataque golpe) {
        double multiplicador = getMultiplicadorContra(alvo);
        int dano = golpe.calcularDano(ataque, multiplicador);
        alvo.receberDano(dano);
        return dano;
    }

    protected String efetividade(double multiplicador) {
        if (multiplicador > 1.0) return " - super efetivo!";
        if (multiplicador < 1.0) return " - pouco efetivo...";
        return "";
    }

    public double calcularEfetividadeContra(Monstro alvo) {
        return getMultiplicadorContra(alvo);
    }

    /** A vida só muda por meio deste método (encapsulamento). */
    public void receberDano(int dano) {
        vidaAtual = Math.max(0, vidaAtual - dano);
    }

    public boolean estaVivo() {
        return vidaAtual > 0;
    }

    public void curar() {
        vidaAtual = vidaMaxima;
    }

    // ---------- Nível, experiência e evolução ----------

    public int xpParaProximoNivel() {
        return 20 + (nivel - 1) * 8;
    }

    /**
     * Concede experiência ao monstro. Pode subir vários níveis de uma vez
     * e disparar uma ou mais evoluções.
     *
     * @return true se o monstro evoluiu ao ganhar esta experiência.
     */
    public boolean ganharExperiencia(int quantidade) {
        xpAtual += quantidade;
        boolean evoluiu = false;
        while (xpAtual >= xpParaProximoNivel()) {
            xpAtual -= xpParaProximoNivel();
            subirNivel();
            if (tentarEvoluir()) {
                evoluiu = true;
            }
        }
        return evoluiu;
    }

    protected void subirNivel() {
        nivel++;
        vidaMaxima += 6;
        vidaAtual = Math.min(vidaMaxima, vidaAtual + 6);
        ataque += 2;
    }

    protected boolean tentarEvoluir() {
        boolean podeEvoluir = (estagio == 1 && nivel >= NIVEL_EVOLUCAO_1)
                || (estagio == 2 && nivel >= NIVEL_EVOLUCAO_2);
        if (!podeEvoluir || estagio >= 3) {
            return false;
        }
        estagio++;
        nome = nomeEstagio(estagio);
        vidaMaxima += 24;
        ataque += 6;
        vidaAtual = vidaMaxima;
        adicionarGolpe(golpeDeEvolucao(estagio));
        return true;
    }

    /** Usado pela fábrica para gerar criaturas selvagens já em nível mais alto. */
    public void definirNivelInicial(int nivelAlvo) {
        while (nivel < nivelAlvo) {
            subirNivel();
            tentarEvoluir();
        }
        vidaAtual = vidaMaxima;
    }

    /** Pequena variação aleatória de estatísticas, usada em criaturas selvagens. */
    public void receberBonus(int bonusVida, int bonusAtaque) {
        vidaMaxima = Math.max(10, vidaMaxima + bonusVida);
        ataque = Math.max(1, ataque + bonusAtaque);
        vidaAtual = vidaMaxima;
    }

    // ---------- Acesso controlado aos atributos (encapsulamento) ----------

    public String getNome() { return nome; }
    public Tipo getTipo() { return tipo; }
    public int getEstagio() { return estagio; }
    public int getNivel() { return nivel; }
    public int getXpAtual() { return xpAtual; }
    public int getVidaAtual() { return vidaAtual; }
    public int getVidaMaxima() { return vidaMaxima; }
    public int getAtaque() { return ataque; }

    @Override
    public String toString() {
        return nome + " [" + tipo.nomeExibicao() + "] Nv." + nivel
                + " Vida: " + vidaAtual + "/" + vidaMaxima + " Ataque: " + ataque;
    }
}
