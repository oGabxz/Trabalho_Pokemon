package monstraria;

/** Linha evolutiva de Gelo: Glacub -> Frosthorn -> Glacior. */
public class MonstroGelo extends Monstro {
    public MonstroGelo() { super(Tipo.GELO, 37, 8); }

    @Override protected String nomeEstagio(int estagio) {
        switch (estagio) {
            case 1: return "Glacub";
            case 2: return "Frosthorn";
            default: return "Glacior";
        }
    }

    @Override protected Ataque[] golpesIniciais() {
        return new Ataque[]{
                new Ataque("Estilhaço", 65, "Dispara pequenos fragmentos de gelo.") ,
                new Ataque("Patada Gélida", 84, "Uma investida que deixa o chão congelado.")
        };
    }

    @Override protected Ataque golpeDeEvolucao(int novoEstagio) {
        return novoEstagio == 2
                ? new Ataque("Lança de Gelo", 112, "Forma uma lança cristalina e a arremessa.")
                : new Ataque("Aurora Glacial", 148, "Uma tempestade de cristais de gelo envolve o campo.");
    }

    @Override protected double getMultiplicadorContra(Monstro alvo) {
        if (alvo.getTipo() == Tipo.PLANTA) return 2.0;
        if (alvo.getTipo() == Tipo.FOGO) return 0.5;
        return 1.0;
    }
}
