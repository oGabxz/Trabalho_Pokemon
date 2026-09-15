package pokemon;

/** Planta vence Agua e perde para Fogo. */
public class PokemonPlanta extends Pokemon {

    public PokemonPlanta(String nome, int vida, int ataque) {
        super(nome, Tipo.PLANTA, vida, ataque);
        adicionarGolpe(new Ataque("Investida", 100));
        adicionarGolpe(new Ataque("Chicotada", 85));
        adicionarGolpe(new Ataque("Folha Navalha", 120));
    }

    @Override
    protected double getMultiplicadorContra(Pokemon alvo) {
        if (alvo.getTipo() == Tipo.AGUA) {
            return 2.0;   // vantagem
        }
        if (alvo.getTipo() == Tipo.FOGO) {
            return 0.5;   // desvantagem
        }
        return 1.0;       // neutro
    }
}
