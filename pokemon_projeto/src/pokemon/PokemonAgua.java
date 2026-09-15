package pokemon;

/** Agua vence Fogo e perde para Planta. */
public class PokemonAgua extends Pokemon {

    public PokemonAgua(String nome, int vida, int ataque) {
        super(nome, Tipo.AGUA, vida, ataque);
    }

    @Override
    protected double getMultiplicadorContra(Pokemon alvo) {
        if (alvo.getTipo() == Tipo.FOGO) {
            return 2.0;   // vantagem
        }
        if (alvo.getTipo() == Tipo.PLANTA) {
            return 0.5;   // desvantagem
        }
        return 1.0;       // neutro
    }
}
