package pokemon.testes;

import org.junit.jupiter.api.Test;
import pokemon.PokemonAgua;
import pokemon.PokemonFogo;
import pokemon.PokemonPlanta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Testes do calculo de dano com e sem vantagem de tipo. */
class PokemonTest {

    @Test
    void danoComVantagemDeTipo() {
        PokemonFogo atacante = new PokemonFogo("Charmander", 100, 10);
        PokemonPlanta alvo = new PokemonPlanta("Bulbasaur", 100, 10);

        atacante.atacar(alvo); // Fogo vence Planta: 10 * 2.0 = 20

        assertEquals(80, alvo.getVidaAtual());
    }

    @Test
    void danoSemVantagemDeTipo() {
        PokemonFogo atacante = new PokemonFogo("Charmander", 100, 10);
        PokemonFogo alvo = new PokemonFogo("Vulpix", 100, 10);

        atacante.atacar(alvo); // mesmo tipo: 10 * 1.0 = 10

        assertEquals(90, alvo.getVidaAtual());
    }

    @Test
    void danoComDesvantagemDeTipo() {
        PokemonPlanta atacante = new PokemonPlanta("Bulbasaur", 100, 10);
        PokemonFogo alvo = new PokemonFogo("Charmander", 100, 10);

        atacante.atacar(alvo); // Planta perde para Fogo: 10 * 0.5 = 5

        assertEquals(95, alvo.getVidaAtual());
    }

    @Test
    void aguaTemVantagemContraFogo() {
        PokemonAgua atacante = new PokemonAgua("Squirtle", 100, 8);
        PokemonFogo alvo = new PokemonFogo("Vulpix", 100, 8);

        atacante.atacar(alvo); // Agua vence Fogo: 8 * 2.0 = 16

        assertEquals(84, alvo.getVidaAtual());
    }

    @Test
    void pokemonDesmaiaAoZerarVida() {
        PokemonFogo atacante = new PokemonFogo("Charmander", 100, 10);
        PokemonPlanta alvo = new PokemonPlanta("Oddish", 20, 5);

        atacante.atacar(alvo); // 20 de dano, vida vai a 0

        assertFalse(alvo.estaVivo());
    }

    @Test
    void pokemonSobreviveComDanoParcial() {
        PokemonAgua atacante = new PokemonAgua("Squirtle", 100, 10);
        PokemonFogo alvo = new PokemonFogo("Vulpix", 100, 10);

        atacante.atacar(alvo);

        assertTrue(alvo.estaVivo());
        assertEquals(80, alvo.getVidaAtual());
    }
}
