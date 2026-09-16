package monstraria;

/**
 * Linha evolutiva de Água: Ripplet -> Aquabite -> Leviatide.
 * Vence Fogo, perde para Planta.
 */
public class MonstroAgua extends Monstro {

    public MonstroAgua() {
        super(Tipo.AGUA, 38, 8);
    }

    @Override
    protected String nomeEstagio(int estagio) {
        switch (estagio) {
            case 1: return "Ripplet";
            case 2: return "Aquabite";
            default: return "Leviatide";
        }
    }

    @Override
    protected Ataque[] golpesIniciais() {
        return new Ataque[]{
                new Ataque("Borrifada", 60, "Um jato curto e impreciso de água."),
                new Ataque("Investida Aquática", 85, "Uma investida envolta em uma fina película de água.")
        };
    }

    @Override
    protected Ataque golpeDeEvolucao(int novoEstagio) {
        if (novoEstagio == 2) {
            return new Ataque("Corrente Cortante", 110, "Uma lâmina de água de alta pressão.");
        }
        return new Ataque("Maremoto", 148, "Convoca uma onda massiva sobre o adversário.");
    }

    @Override
    protected double getMultiplicadorContra(Monstro alvo) {
        if (alvo.getTipo() == Tipo.FOGO) return 2.0;
        if (alvo.getTipo() == Tipo.PLANTA) return 0.5;
        return 1.0;
    }
}
