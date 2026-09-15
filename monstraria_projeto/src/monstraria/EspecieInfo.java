package monstraria;

/** Dados fixos de uma espécie, usados para montar o grid do Bestiário (mesmo não capturada). */
public final class EspecieInfo {
    public final String nome;
    public final Tipo tipo;
    public final int estagio;

    public EspecieInfo(String nome, Tipo tipo, int estagio) {
        this.nome = nome;
        this.tipo = tipo;
        this.estagio = estagio;
    }
}
