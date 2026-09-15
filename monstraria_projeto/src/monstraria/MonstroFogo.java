package monstraria;

/**
 * Linha evolutiva de Fogo: Emberit -> Cindrake -> Pyronix.
 * Vence Planta, perde para Água.
 */
public class MonstroFogo extends Monstro {

    public MonstroFogo() {
        super(Tipo.FOGO, 34, 9);
    }

    @Override
    protected String nomeEstagio(int estagio) {
        switch (estagio) {
            case 1: return "Emberit";
            case 2: return "Cindrake";
            default: return "Pyronix";
        }
    }

    @Override
    protected Ataque[] golpesIniciais() {
        return new Ataque[]{
                new Ataque("Fagulha", 65, "Uma pequena centelha lançada contra o alvo."),
                new Ataque("Investida Quente", 85, "Um investida coberta por uma fina camada de brasas.")
        };
    }

    @Override
    protected Ataque golpeDeEvolucao(int novoEstagio) {
        if (novoEstagio == 2) {
            return new Ataque("Rajada Flamejante", 108, "Uma explosão de fogo concentrado.");
        }
        return new Ataque("Nova Solar", 145, "Libera toda a energia interna em uma onda de calor.");
    }

    @Override
    protected double getMultiplicadorContra(Monstro alvo) {
        if (alvo.getTipo() == Tipo.PLANTA) return 2.0;
        if (alvo.getTipo() == Tipo.AGUA) return 0.5;
        return 1.0;
    }
}
