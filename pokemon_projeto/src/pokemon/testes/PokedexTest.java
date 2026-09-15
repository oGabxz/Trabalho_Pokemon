package pokemon.testes;

import org.junit.jupiter.api.Test;
import pokemon.Pokedex;
import pokemon.PokemonAgua;
import pokemon.PokemonFogo;
import pokemon.PokemonPlanta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Testes do registro sem duplicacao da Pokedex. */
class PokedexTest {

    @Test
    void naoDuplicaRegistros() {
        Pokedex pokedex = new Pokedex();
        PokemonFogo vulpix1 = new PokemonFogo("Vulpix", 90, 10);
        PokemonFogo vulpix2 = new PokemonFogo("Vulpix", 90, 10); // mesmo nome

        pokedex.registrar(vulpix1);
        pokedex.registrar(vulpix2);

        assertEquals(1, pokedex.getQuantidadeRegistrada());
    }

    @Test
    void registraCriaturasDiferentes() {
        Pokedex pokedex = new Pokedex();
        pokedex.registrar(new PokemonFogo("Vulpix", 90, 10));
        pokedex.registrar(new PokemonAgua("Squirtle", 110, 9));
        pokedex.registrar(new PokemonPlanta("Oddish", 100, 8));

        assertEquals(3, pokedex.getQuantidadeRegistrada());
    }

    @Test
    void verificaSeJaFoiRegistrado() {
        Pokedex pokedex = new Pokedex();
        pokedex.registrar(new PokemonAgua("Squirtle", 110, 9));

        assertTrue(pokedex.jaRegistrado(new PokemonAgua("Squirtle", 50, 5)));
        assertFalse(pokedex.jaRegistrado(new PokemonFogo("Vulpix", 90, 10)));
    }
}
