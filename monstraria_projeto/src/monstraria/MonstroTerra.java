package monstraria;

/** Linha evolutiva de Terra: Terrakid -> Terragon -> Terralord. */
public class MonstroTerra extends Monstro {
    public MonstroTerra() { super(Tipo.TERRA, 42, 8); }

    @Override protected String nomeEstagio(int estagio) {
        switch (estagio) {
            case 1: return "Terrakid";
            case 2: return "Terragon";
            default: return "Terralord";
        }
    }

    @Override protected Ataque[] golpesIniciais() {
        return new Ataque[]{
                new Ataque("Pedrada", 66, "Arremessa uma pedra compacta.") ,
                new Ataque("Pisada", 86, "Uma pisada pesada que faz o solo vibrar.")
        };
    }

    @Override protected Ataque golpeDeEvolucao(int novoEstagio) {
        return novoEstagio == 2
                ? new Ataque("Raiz Rochosa", 108, "Colunas de pedra surgem sob o alvo.")
                : new Ataque("Terremoto", 146, "Abala toda a arena com força bruta.");
    }

    @Override protected double getMultiplicadorContra(Monstro alvo) {
        if (alvo.getTipo() == Tipo.FOGO) return 2.0;
        if (alvo.getTipo() == Tipo.AGUA) return 0.5;
        return 1.0;
    }
}
