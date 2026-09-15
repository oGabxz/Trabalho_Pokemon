package pokemon.testes;

import org.junit.jupiter.api.Test;
import pokemon.PokemonAgua;
import pokemon.PokemonFogo;
import pokemon.PokemonPlanta;
import pokemon.Treinador;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Testes da logica de captura baseada na vida restante. */
class CapturaTest {

    @Test
    void chanceAumentaQuandoVidaBaixa() {
        Treinador treinador = new Treinador("Misty");
        PokemonAgua selvagem = new PokemonAgua("Psyduck", 100, 8);

        double chanceCheio = treinador.calcularChanceCaptura(selvagem);
        selvagem.receberDano(90); // vida 10/100
        double chanceFraco = treinador.calcularChanceCaptura(selvagem);

        assertTrue(chanceFraco > chanceCheio);
    }

    @Test
    void chanceMinimaComVidaCheia() {
        Treinador treinador = new Treinador("Misty");
        PokemonAgua selvagem = new PokemonAgua("Psyduck", 100, 8);

        // Vida cheia -> fracao perdida = 0, mas a chance minima e 10%
        assertEquals(0.10, treinador.calcularChanceCaptura(selvagem), 0.0001);
    }

    @Test
    void capturaBemSucedidaComSorteAlta() {
        Treinador treinador = new Treinador("Misty");
        PokemonPlanta selvagem = new PokemonPlanta("Oddish", 100, 8);

        boolean capturado = treinador.tentarCapturar(selvagem, new Random() {
            @Override
            public double nextDouble() {
                return 0.0; // sempre "sorte maxima"
            }
        });

        assertTrue(capturado);
        assertEquals(1, treinador.getEquipe().size());
    }

    @Test
    void capturaFalhaComSorteBaixa() {
        Treinador treinador = new Treinador("Misty");
        PokemonPlanta selvagem = new PokemonPlanta("Oddish", 100, 8);

        boolean capturado = treinador.tentarCapturar(selvagem, new Random() {
            @Override
            public double nextDouble() {
                return 0.99; // sempre "sorte minima"
            }
        });

        assertFalse(capturado);
        assertEquals(0, treinador.getEquipe().size());
    }

    @Test
    void equipeLimitadaASeisPokemons() {
        Treinador treinador = new Treinador("Misty");
        for (int i = 0; i < 6; i++) {
            assertTrue(treinador.adicionarPokemon(new PokemonFogo("Vulpix " + i, 50, 5)));
        }
        assertFalse(treinador.adicionarPokemon(new PokemonAgua("Psyduck", 50, 5)));
        assertEquals(6, treinador.getEquipe().size());
    }
}
