package monstraria;

/**
 * Linha evolutiva de Planta: Sproutle -> Mossclaw -> Floragon.
 * Vence Água, perde para Fogo.
 */
public class MonstroPlanta extends Monstro {

    public MonstroPlanta() {
        super(Tipo.PLANTA, 36, 8);
    }

    @Override
    protected String nomeEstagio(int estagio) {
        switch (estagio) {
            case 1: return "Sproutle";
            case 2: return "Mossclaw";
            default: return "Floragon";
        }
    }

    @Override
    protected Ataque[] golpesIniciais() {
        return new Ataque[]{
                new Ataque("Chicote de Vinha", 65, "Um golpe rápido com um broto flexível."),
                new Ataque("Investida Espinhosa", 85, "Uma investida coberta de pequenos espinhos.")
        };
    }

    @Override
    protected Ataque golpeDeEvolucao(int novoEstagio) {
        if (novoEstagio == 2) {
            return new Ataque("Folha Navalha", 112, "Folhas endurecidas lançadas como lâminas.");
        }
        return new Ataque("Fúria da Selva", 142, "Convoca toda a força da vegetação ao redor.");
    }

    @Override
    protected double getMultiplicadorContra(Monstro alvo) {
        if (alvo.getTipo() == Tipo.AGUA) return 2.0;
        if (alvo.getTipo() == Tipo.FOGO) return 0.5;
        return 1.0;
    }
}
