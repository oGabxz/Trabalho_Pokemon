package pokemon;

/** Fogo vence Planta e perde para Agua. */
public class PokemonFogo extends Pokemon {

    public PokemonFogo(String nome, int vida, int ataque) {
        super(nome, Tipo.FOGO, vida, ataque);
        adicionarGolpe(new Ataque("Investida", 100));
        adicionarGolpe(new Ataque("Brasa", 85));
        adicionarGolpe(new Ataque("Lança-Chamas", 125));
    }

    @Override
    protected double getMultiplicadorContra(Pokemon alvo) {
        if (alvo.getTipo() == Tipo.PLANTA) {
            return 2.0;   // vantagem
        }
        if (alvo.getTipo() == Tipo.AGUA) {
            return 0.5;   // desvantagem
        }
        return 1.0;       // neutro
    }
}
