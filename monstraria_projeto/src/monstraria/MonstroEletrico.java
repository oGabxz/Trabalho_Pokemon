package monstraria;

/** Linha evolutiva de Elétrico: Voltik -> Stormion -> Zephyron. */
public class MonstroEletrico extends Monstro {
    public MonstroEletrico() { super(Tipo.ELETRICO, 33, 10); }

    @Override protected String nomeEstagio(int estagio) {
        switch (estagio) {
            case 1: return "Voltik";
            case 2: return "Stormion";
            default: return "Zephyron";
        }
    }

    @Override protected Ataque[] golpesIniciais() {
        return new Ataque[]{
                new Ataque("Faísca", 68, "Uma descarga rápida e precisa.") ,
                new Ataque("Mordida Voltaica", 82, "Uma mordida carregada de eletricidade.")
        };
    }

    @Override protected Ataque golpeDeEvolucao(int novoEstagio) {
        return novoEstagio == 2
                ? new Ataque("Arco Elétrico", 110, "Um arco de energia salta até o adversário.")
                : new Ataque("Trovão Celeste", 150, "Invoca uma descarga poderosa do alto.");
    }

    @Override protected double getMultiplicadorContra(Monstro alvo) {
        if (alvo.getTipo() == Tipo.AGUA) return 2.0;
        if (alvo.getTipo() == Tipo.PLANTA) return 0.5;
        return 1.0;
    }
}
